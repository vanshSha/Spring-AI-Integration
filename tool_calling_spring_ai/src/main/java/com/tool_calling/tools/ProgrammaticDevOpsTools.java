package com.tool_calling.tools;

import org.springframework.stereotype.Component;

@Component
// Programmatic tool calling example
public class ProgrammaticDevOpsTools {

    private String currentVersion = "1.4.2";
    private final int podCount = 5;

    public String getDeploymentStatus(String serviceName, String environment) {
        System.out.println("Tool executed: getDeploymentStatus()");
        boolean isHealthy = false;
        String healthStatus = "unhealthy";
        return String.format("Service '%s' in environment '%s' is currently running version %s with %d pods %s.",
                serviceName, environment, currentVersion, podCount, healthStatus);
    }

    public String restartService(String serviceName, String environment) {
        System.out.println("Tool executed: restartService()");
        String[] versionParts = currentVersion.split("\\.");
        int patch = Integer.parseInt(versionParts[2]) + 1;
        currentVersion = versionParts[0] + "." + versionParts[1] + "." + patch;
        return String.format("Rolling restart initiated for service '%s' in environment '%s' with %d pods. " +
                        "Expected downtime is less than 1 minute. " +
                        "Service updated to version %s.",
                serviceName, environment, podCount, currentVersion);
    }
}