package com.zyjisaboy.springairagassistant.controller;

import com.zyjisaboy.springairagassistant.dto.ChatRequest;
import com.zyjisaboy.springairagassistant.dto.ChatResponse;
import com.zyjisaboy.springairagassistant.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "对话", description = "AI 对话接口")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    @Operation(summary = "发送消息", description = "发送消息给 AI 助手并获取回复")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String reply = chatService.chat(request.message());
        return new ChatResponse(reply);
    }
}
