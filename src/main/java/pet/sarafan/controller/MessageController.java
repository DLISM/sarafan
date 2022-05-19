package pet.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pet.sarafan.domain.Message;
import pet.sarafan.domain.Views;
import pet.sarafan.dto.EventType;
import pet.sarafan.dto.ObjectType;
import pet.sarafan.repository.MessageRepo;
import pet.sarafan.util.WsSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {
    private final BiConsumer<EventType, Message> wsSender;

    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(WsSender wsSender, MessageRepo messageRepo) {
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list(){
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message){
        message.setDateTime(LocalDateTime.now());
        Message upDateMessage= messageRepo.save(message);

        wsSender.accept(EventType.CREATE, upDateMessage);
        return upDateMessage;
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDB,
            @RequestBody Message message)
    {
        BeanUtils.copyProperties(message, messageFromDB, "id");
        Message upDateMessage= messageRepo.save(messageFromDB);
        wsSender.accept(EventType.UPDATE, upDateMessage);
        return upDateMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        messageRepo.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

//    @MessageMapping("/changeMessage")
//    @SendTo("/topic/activity")
//    public Message change(Message message){
//        return messageRepo.save(message);
//    }
}
