package com.mcp_client.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.modelcontextprotocol.client.McpSyncClient;

import java.util.List;

@Configuration
public class ClientAiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, List<McpSyncClient> mcpSyncClients) {
        // Automatically converts all tools hosted on our MCP server into execution callbacks
        SyncMcpToolCallbackProvider toolProvider = new SyncMcpToolCallbackProvider(mcpSyncClients);

        // FIX: Use defaultToolCallbacks instead of defaultTools
        return builder
                .defaultToolCallbacks(toolProvider.getToolCallbacks())
                .build();
    }
}