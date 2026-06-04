package com.mcp_client.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    // Clean final field, no field-level @Autowired
    private final ChatClient chatClient;

    // Spring will automatically inject the ChatClient.Builder.
    // If you have multiple ToolCallback beans registered by your MCP tools, inject them as a List.
    public ChatController(ChatClient.Builder chatClientBuilder, List<ToolCallback> toolCallbacks) {
        this.chatClient = chatClientBuilder
                .defaultTools(toolCallbacks.toArray()) // Efficiently binds discovered tools
                .build();
    }

    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestParam String prompt) {
        return this.chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }

    @GetMapping("/chat/stream/data")
    public Mono<String> chat(@RequestParam String prompt) {
        return Mono.fromCallable(() -> 
            this.chatClient.prompt()
                .user(prompt)
                .call()
                .content()
        );
    }
}