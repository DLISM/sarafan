package pet.sarafan.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pet.sarafan.domain.Message;
import pet.sarafan.domain.User;
import pet.sarafan.domain.UserSubscription;
import pet.sarafan.domain.Views;
import pet.sarafan.dto.EventType;
import pet.sarafan.dto.MessagePageDto;
import pet.sarafan.dto.MetaDto;
import pet.sarafan.dto.ObjectType;
import pet.sarafan.repository.MessageRepo;
import pet.sarafan.repository.UserSubscriptionRepo;
import pet.sarafan.util.WsSender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX= Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMAGE_REGEX= Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final UserSubscriptionRepo userSubscriptionRepo;
    private final BiConsumer<EventType, Message> wsSender;

    private final MessageRepo messageRepo;

    @Autowired
    public MessageService(
            UserSubscriptionRepo userSubscriptionRepo,
            WsSender wsSender,
            MessageRepo messageRepo
    ) {
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
        this.messageRepo = messageRepo;
    }

    private void fillMeta(Message message) throws IOException {
        String text=message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if(matcher.find()){
            String url = text.substring(matcher.start(), matcher.end());

            matcher=IMAGE_REGEX.matcher(url);

            message.setLink(url);

            if(matcher.find()){
                message.setLinkCover(url);
            }else if(!url.contains("youtu")){

                MetaDto metaDto = getMeta(url);

                message.setLinkCover(metaDto.getCover());
                message.setLinkTitle(metaDto.getTitle());
                message.setLinkDescription(metaDto.getDescription());
            }
        }

    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element){
        return element==null ? "" : element.attr("content");
    }

    public void delete(Message message) {
        messageRepo.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

    public Message update(Message messageFromDB, Message message) throws IOException {
        BeanUtils.copyProperties(message, messageFromDB, "id");
        fillMeta(messageFromDB);
        Message upDateMessage= messageRepo.save(messageFromDB);
        wsSender.accept(EventType.UPDATE, upDateMessage);
        return upDateMessage;
    }

    public Message create(Message message, User user) throws IOException {
        message.setDateTime(LocalDateTime.now());
        fillMeta(message);
        message.setAuthor(user);

        Message upDateMessage= messageRepo.save(message);

        wsSender.accept(EventType.CREATE, upDateMessage);
        return upDateMessage;
    }

    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepo.findBySubscriber(user)
                .stream()
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page=messageRepo.findByAuthorIn(channels ,pageable);
        return new MessagePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }
}
