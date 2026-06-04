package com.mcp_client.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/agent/api/chat")
public class AgentController {

    private final ChatClient chatClient;

    public AgentController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

   // Matches your target curl request: /api/chat?prompt=...
    @GetMapping
    public Mono<String> chat(@RequestParam String prompt) {
        return Mono.fromCallable(() -> 
            this.chatClient.prompt()
                .user(prompt)
                .call()
                .content()
        );
    }

}
