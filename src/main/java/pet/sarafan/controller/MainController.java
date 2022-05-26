package pet.sarafan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pet.sarafan.domain.User;
import pet.sarafan.domain.Views;
import pet.sarafan.dto.MessagePageDto;
import pet.sarafan.repository.MessageRepo;
import pet.sarafan.service.MessageService;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final MessageService messageService;
    private final ObjectWriter writer;

    @Autowired
    public MainController(MessageService messageService,
                          ObjectMapper mapper
    ) {
        this.messageService = messageService;

        this.writer = mapper
                        .setConfig(mapper.getSerializationConfig())
                        .writerWithView(Views.FullMessage.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if(user!=null) {

            Sort sort=Sort.by(Sort.Direction.DESC, "id");
            PageRequest page = PageRequest.of(0, MessageController.MESSAGE_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findAll(page);
            String message = writer.writeValueAsString(messagePageDto.getMessages());

            data.put("profile", user);
            data.put("currentPage", messagePageDto.getCurrentPage());
            data.put("totalPage", messagePageDto.getTotalPage());
            model.addAttribute("messages", message);

        }else {
            model.addAttribute("messages", "[]");
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", true);

        return "index";
    }
}
