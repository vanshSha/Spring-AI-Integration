package com.tool_calling.config;

import com.tool_calling.tools.ProgrammaticDevOpsTools;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

@Configuration
public class ToolCallbackConfiguration {

    @Autowired
    private ProgrammaticDevOpsTools devOpsTools;


    @Bean
    public List<ToolCallback> toolCallbacks() {
        return List.of(
                createDeploymentStatusToolCallback(),
                createRestartServiceToolCallback()
        );
    }


    private ToolCallback createDeploymentStatusToolCallback() {
        Method method = ReflectionUtils.findMethod(ProgrammaticDevOpsTools.class,
                "getDeploymentStatus", String.class, String.class);

        assert method != null;
        return MethodToolCallback.builder()
                .toolDefinition(ToolDefinition
                        .builder()
                        .name("getDeploymentStatus")
                        .description("Retrieves the current deployment status of a microservice including version, pod count, and health status. Use this when asked about service status, deployment information, or health checks.")
                        .inputSchema(JsonSchemaGenerator.generateForMethodInput(method))
                        .build())
                .toolMethod(method)
                .toolObject(devOpsTools)
                .build();
    }

    private ToolCallback createRestartServiceToolCallback() {
        Method method = ReflectionUtils.findMethod(ProgrammaticDevOpsTools.class, "restartService", String.class, String.class);
        assert method != null;
        return MethodToolCallback.builder()
                .toolDefinition(ToolDefinition
                        .builder()
                        .name("restartService")
                        .description("Triggers a rolling restart of a microservice. Use this when asked to restart a service or perform maintenance.")
                        .inputSchema(JsonSchemaGenerator.generateForMethodInput(method))
                        .build())
                .toolMethod(method)
                .toolObject(devOpsTools)
                .build();
    }
}
