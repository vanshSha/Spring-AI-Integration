package com.example.springAI.controller.chatmemoryjdbccontroller;


import com.example.springAI.service.ChatMemoryJDBCService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatMemoryJDBCService chatService;

    @PostMapping("/send")
    public String chat(@RequestBody String request) {
        return chatService.chat(request);
    }

    @GetMapping("/sessions")
    public List<Message> listAllChats() {
        return chatService.listAllChats();
    }
}

