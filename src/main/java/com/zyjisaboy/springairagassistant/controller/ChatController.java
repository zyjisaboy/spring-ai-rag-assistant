package com.zyjisaboy.springairagassistant.controller;

import com.zyjisaboy.springairagassistant.dto.ChatRequest;
import com.zyjisaboy.springairagassistant.dto.ChatResponse;
import com.zyjisaboy.springairagassistant.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String reply = chatService.chat(request.message());
        return new ChatResponse(reply);
    }
}
