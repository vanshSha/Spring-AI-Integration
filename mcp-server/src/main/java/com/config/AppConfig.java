package com.config;

import com.service.BookDataService;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

   @Bean
    public List<ToolCallback> toolCallbacks(BookDataService bookDataService) {
        return List.of(ToolCallbacks.from(bookDataService));
    }

    @McpTool
    public String add(int a, int b) {
        return String.valueOf(a + b);
    }
}
