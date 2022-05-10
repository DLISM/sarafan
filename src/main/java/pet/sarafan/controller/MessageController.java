package pet.sarafan.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pet.sarafan.domain.Message;
import pet.sarafan.exceptions.NotFoundException;
import pet.sarafan.repository.MessageRepo;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> list(){
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message){
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDB,
            @RequestBody Message message)
    {
        BeanUtils.copyProperties(message, messageFromDB, "id");
        return messageRepo.save(messageFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        messageRepo.delete(message);
    }
}
