package com.ankitkumar.hello.controller;

import com.ankitkumar.hello.model.MessageEntity;
import com.ankitkumar.hello.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String showHelloPage(Model model) {
        model.addAttribute("message", "Hello, World!");
        return "hello-world";
    }

    @PostMapping("/save")
    public String saveMessage(@RequestParam("messageText") String messageText, Model model) {
        MessageEntity message = new MessageEntity(messageText);
        messageRepository.save(message);
        model.addAttribute("message", "Message saved: " + messageText);
        return "hello-world";
    }
}