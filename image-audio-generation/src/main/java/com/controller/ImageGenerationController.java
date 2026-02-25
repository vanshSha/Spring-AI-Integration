package com.controller;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGenerationController {

    // This is use for making image form prompt
    OpenAiImageModel openAiImageModel;

    public ImageGenerationController(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    @GetMapping("/imagegeneration")
    public String imageGeneration(@RequestParam(value = "prompt", defaultValue = "A beautiful sunset over the ocean") String prompt) {
        ImageResponse imageResponse = openAiImageModel.call(new ImagePrompt(prompt,
                // ImagePrompt is a request object used to send image generation instruction to OpenAiImageModel
                OpenAiImageOptions
                        .builder()
                        .quality("hd")
                        .width(512)
                        .build()));
        return imageResponse
                .getResult()
                .getOutput()
                .getUrl();
    }

}
