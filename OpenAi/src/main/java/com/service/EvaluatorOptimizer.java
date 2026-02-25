package com.service;


import com.model.Generation;
import com.model.RefinedResponse;
import org.springframework.ai.chat.client.ChatClient;

import java.util.ArrayList;
import java.util.List;

public class EvaluatorOptimizer {

    public static final String DEFAULT_GENERATOR_PROMPT_MOVIE_REVIEW = """
       Your goal is to write a concise movie review based on the provided information. If you have received feedback on previous reviews, incorporate it to improve your current one.

       CRITICAL: Your response must be a SINGLE LINE of valid JSON with NO LINE BREAKS except those explicitly escaped with \\n.
       Here is the exact format to follow, including all quotes and braces:

       {"thoughts":"Brief description of your review approach","response":"A compelling story with great acting.\\nHowever, the pacing felt slow at times."}

       Rules for the review field:
       1. ALL line breaks should use \\n
       2. ALL quotes should use \\"
       3. NO actual line breaks or formatting - everything on one line
       4. NO tabs or special characters

       Example of properly formatted review:
       {"thoughts":"Providing a balanced view","response":"The visual effects were stunning,\\nbut the plot was predictable."}

       Follow this format EXACTLY - your response must be valid JSON on a single line.
       """;

    public static final String DEFAULT_EVALUATOR_PROMPT_MOVIE_REVIEW = """
       Evaluate this movie review for its clarity, conciseness, and helpfulness to a potential viewer. Consider if it mentions key aspects like plot, acting, visuals, and pacing, where relevant.

       Respond with EXACTLY this JSON format on a single line:

       {"evaluation":"PASS, NEEDS_IMPROVEMENT, or FAIL", "feedback":"Your feedback here"}

       The evaluation field must be one of: "PASS", "NEEDS_IMPROVEMENT", or "FAIL"
       Use "PASS" only if the review is clear, concise, and provides helpful insights.
       """;

    public static record EvaluationResponse(Evaluation evaluation, String feedback) {

        public enum Evaluation {
            PASS, NEEDS_IMPROVEMENT, FAIL
        }
    }

    private final ChatClient chatClient;

    private final String generatorPrompt;

    private final String evaluatorPrompt;

    public EvaluatorOptimizer(ChatClient chatClient) {
        this(chatClient, DEFAULT_GENERATOR_PROMPT_MOVIE_REVIEW,
                DEFAULT_EVALUATOR_PROMPT_MOVIE_REVIEW);
    }

    public EvaluatorOptimizer(ChatClient chatClient, String generatorPrompt, String evaluatorPrompt) {

        this.chatClient = chatClient;
        this.generatorPrompt = generatorPrompt;
        this.evaluatorPrompt = evaluatorPrompt;
    }

    public RefinedResponse loop(String task) {
        List<String> memory = new ArrayList<>();
        List<Generation> chainOfThought = new ArrayList<>();

        return loop(task, "", memory, chainOfThought);
    }

    private RefinedResponse loop(String task, String context, List<String> memory,
                                 List<Generation> chainOfThought) {

        Generation generation = generate(task, context);
        memory.add(generation.response());
        chainOfThought.add(generation);

        EvaluationResponse evaluationResponse = evalute(generation.response(), task);

        if (evaluationResponse.evaluation().equals(EvaluationResponse.Evaluation.PASS)) {
            // Solution is accepted!
            return new RefinedResponse(generation.response(), chainOfThought);
        }

        // Accumulated new context including the last and the previous attempts and
        // feedbacks.
        StringBuilder newContext = new StringBuilder();
        newContext.append("Previous attempts:");
        for (String m : memory) {
            newContext.append("\n- ").append(m);
        }
        newContext.append("\nFeedback: ").append(evaluationResponse.feedback());

        return loop(task, newContext.toString(), memory, chainOfThought);
    }

    private Generation generate(String task, String context) {
        Generation generationResponse = chatClient.prompt()
                .user(u -> u.text("{prompt}\n{context}\nTask: {task}")
                        .param("prompt", this.generatorPrompt)
                        .param("context", context)
                        .param("task", task))
                .call()
                .entity(Generation.class);
        System.out.println("generationResponse"+ generationResponse);
        System.out.println(String.format("\n=== GENERATOR OUTPUT ===\nTHOUGHTS: %s\n\nRESPONSE:\n %s\n",
                generationResponse.thoughts(), generationResponse.response()));
        return generationResponse;
    }


    private EvaluationResponse evalute(String content, String task) {

        EvaluationResponse evaluationResponse = chatClient.prompt()
                .user(u -> u.text("{prompt}\nOriginal task: {task}\nContent to evaluate: {content}")
                        .param("prompt", this.evaluatorPrompt)
                        .param("task", task)
                        .param("content", content))
                .call()
                .entity(EvaluationResponse.class);

        System.out.println(String.format("\n=== EVALUATOR OUTPUT ===\nEVALUATION: %s\n\nFEEDBACK: %s\n",
                evaluationResponse.evaluation(), evaluationResponse.feedback()));
        return evaluationResponse;
    }

}
