package pet.sarafan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pet.sarafan.domain.User;
import pet.sarafan.domain.Views;
import pet.sarafan.repository.MessageRepo;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final MessageRepo messageRepo;
    private final ObjectWriter writer;

    @Autowired
    public MainController(MessageRepo messageRepo,
                          ObjectMapper mapper
    ) {
        this.messageRepo = messageRepo;

        this.writer = mapper
                        .setConfig(mapper.getSerializationConfig())
                        .writerWithView(Views.FullMessage.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if(user!=null) {
            data.put("profile", user);
            String message = writer.writeValueAsString(messageRepo.findAll());
            model.addAttribute("messages", message);
        }else {
            model.addAttribute("messages", "[]");
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", true);

        return "index";
    }
}
