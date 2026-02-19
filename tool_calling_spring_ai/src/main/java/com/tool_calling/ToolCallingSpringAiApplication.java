package com.tool_calling;

import com.tool_calling.tools.DevOpsTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ToolCallingSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolCallingSpringAiApplication.class, args);
    }

    @Autowired
    private ChatModel chatModel;
    // Send prompts to LLM and receive conversational text responses .

    @Autowired
    private List<ToolCallback> toolCallbacks;
    // is used in spring ai tool calling to allow the LLM to call your java method .


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("CommandLineRunner executed at startup.");
            System.out.println("Let's chat!");

            Scanner scanner = new Scanner(System.in);

            // ChatClient is used to communicate with an AI model
            ChatClient chatClient = ChatClient.create(chatModel);

            // memory that keeps the last N chat messages.
            MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                    .maxMessages(10)
                    .build();

            while (true) {
                System.out.print("USER: ");
                String userInput = scanner.nextLine();

                if ("quit".equalsIgnoreCase(userInput) || "exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Goodbye!");
                    break;
                }

                try {
                    String response = chatClient
                            .prompt(userInput)
                            // Middleware that runs before or after the AI call.
                            .advisors(
                                    // Advisor that sends previous messages to the AI.
                                    MessageChatMemoryAdvisor.builder(chatMemory).build())
                            .toolCallbacks(toolCallbacks)
                            // call() - Sends the request to the AI model
                            .call()
                            // content() - Gets the text response from the AI
                            .content();

                    System.out.println("ASSISTANT: " + response);
                    System.out.println();
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    System.out.println();
                }
            }
            scanner.close();
        };
    }
    /*
    What is the status of user-service in UAT?
    please restart service now
     */
}

//    @Bean
//    public org.springframework.boot.CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
//        return args -> {
//            System.out.println("CommandLineRunner executed at startup.");
//            ChatClient chat = builder.build();
//            Scanner scanner = new Scanner(System.in);
//
//            MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
//                    .maxMessages(10)
//                    .build();
//
//            System.out.println("\nLet's chat!");
//            while (true) {
//                System.out.print("\nUSER: ");
//                System.out.println("ASSISTANT: " +
//                        chat.prompt(scanner.nextLine())
//                                .advisors(
//                                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                                    // This method use for call java method
//                                .tools(new DevOpsTools())
//                                .call().
//                                content());
//            }
//        };
//    }
//
//    @Bean
//    public List<ToolCallback> toolCallbacks(DevOpsTools devOpsTools) {
//        return List.of(ToolCallbacks.from(devOpsTools));
        // ToolCallbacks.from() = Convert your tool methods into AI-callable tools
//    }
//
//}
/*
What is the deployment status of analytics-service in production
Please restart user-service in stagging
please restart user-service in dev environment
 */
