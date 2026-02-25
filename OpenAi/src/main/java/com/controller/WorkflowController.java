package com.controller;

import com.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class WorkflowController {

    @Autowired
    private WorkFlowService workflowService;


    @PostMapping("/workflow")
    public String processWorkflow(@RequestParam  String userInput) {
        // Delegate the workflow processing to the service
        return workflowService.chain(userInput);
    }
}
