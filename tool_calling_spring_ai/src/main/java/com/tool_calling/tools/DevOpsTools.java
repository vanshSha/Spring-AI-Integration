package com.tool_calling.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class DevOpsTools {

    // @Tool -  makes a Java method callable by the AI.
    @Tool(description = "Get the current deployment status of a microservice in a specific environment")
    public String getDeploymentStatus(@ToolParam(description = "Name of the microservice or service") String serviceName,
                                      //@ToolParam -  explains a parameter to the AI.
                                      @ToolParam(description = "Environment (e.g., production, staging)") String environment) {
        System.out.println("getDeploymentStatus called with input: ");
        // Simulated response
        return "Service  is currently running version 1.4.2 with 5 pods healthy.";
    }

    @Tool(description = "Trigger a rolling restart of a microservice in a given environment")
    public String restartService(@ToolParam(description = "Name of the microservice or service") String serviceName,
                                 @ToolParam(description = "Environment (e.g., production, staging)") String environment) {
        System.out.println("restartService called with input: ");

        // Simulated action
        return "Rolling restart initiated for service with 5 pods. " +
                "Expected downtime is less than 1 minute.";
    }
}
