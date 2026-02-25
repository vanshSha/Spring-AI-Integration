package com.controller;

import com.service.SentimentBasedRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Routing Work flow
//@RestController
public class FeedbackController {
    @Autowired
    private SentimentBasedRouterService sentimentBasedRouterService;

    @PostMapping("/feedback")
    public ResponseEntity<String> submitFeedback(@RequestBody String feedback) {
        System.out.println("Received feedback: " + feedback);
        sentimentBasedRouterService.routeFeedback(feedback);
        return ResponseEntity.ok("Feedback received and is being processed.");
    }

}
