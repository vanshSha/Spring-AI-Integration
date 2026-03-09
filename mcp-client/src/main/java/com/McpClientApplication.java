package com;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootApplication
public class McpClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpClientApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(List<McpSyncClient> mcpSyncClient, ChatClient.Builder chatClientBuilder) {
		return args -> {
			System.out.println("Spring Ai MCP Client Example Application is running!");
			System.out.println("List of MCP Sync Clients:" +  mcpSyncClient);

			McpSyncClient mspClient = mcpSyncClient.getFirst();
			System.out.println("mspClient" + mspClient.listTools());

			SyncMcpToolCallbackProvider toolCallbackProvider = new SyncMcpToolCallbackProvider(mcpSyncClient);
			ToolCallback[] toolCallbacks =  toolCallbackProvider.getToolCallbacks();

			// Iterate over the toolsCallBacks and Prints Them
			for(ToolCallback toolCallback : toolCallbacks) {
				System.out.println("ToolCallback " + toolCallback.getToolDefinition());
			}

			ChatClient chatClient = chatClientBuilder.defaultToolCallbacks(toolCallbackProvider).build();
			String userInput = "give me books name by author Pritesh Mistry ";
			System.out.println("\n>>> QUESTION " +  userInput);
			System.out.println("\n>>> ASSISTANT " + chatClient.prompt(userInput).call().content());
		};
	}
}
