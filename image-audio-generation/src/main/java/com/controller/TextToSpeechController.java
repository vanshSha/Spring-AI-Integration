package com.controller;

import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class TextToSpeechController {

    // Text --> Audio
    private TextToSpeechModel textToSpeechModel;
    // OpenAiAudioSpeechModel is the OpenAI implementation of TextToSpeechModel.
    // Text → OpenAI API → Generated audio
    private OpenAiAudioSpeechModel openAiAudioSpeechModel;

    public TextToSpeechController(TextToSpeechModel textToSpeechModel, OpenAiAudioSpeechModel openAiAudioSpeechModel) {
        this.textToSpeechModel = textToSpeechModel;
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;
    }

    @GetMapping("/tts")
    public byte[] tts(@RequestParam(value = "message", defaultValue = "Hello, World! This is test message for text to speech using OpenAi") String message) {
        return textToSpeechModel.call(new TextToSpeechPrompt(message)).getResult().getOutput();
    }

    @GetMapping("/ttsaudiostream")
    public Flux<byte[]> getStreaming() throws IOException {
        ClassPathResource resource = new ClassPathResource("tts.txt");

        // String content = new String(Files.readAllBytes(Paths.get("tts.txt")));

        // getInputStream is a method used to read data from a source as a stream of bytes.
        String content = new String(resource.getInputStream().readAllBytes());
        // OpenAiAudioApi openAIAudioApi = new OpenAiAudioApi(apiKey);
        // OpenAiAudioSpeechModel openAIAudioSpeechModel = new OpenAiAudioSpeechModel(openAIAudioApi);

        // This is the configuration class
        // IT controls How generated voice should sound.
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .speed(1.1d)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .model(OpenAiAudioApi.TtsModel.TTS_1.value)
                .build();

        // IS a request model send to Text + Speech Settings to a Text - To - Speech
        // Simple mental model - > What to speak and How to speak
        TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(content, options);
        // Flux - stream of multiple values over time
        // Here Flux because audio generation can be streamed in chunks
        // TextToSpeechResponse - is the result object that contains generated audio data.
        Flux<TextToSpeechResponse> responseStream = openAiAudioSpeechModel.stream(speechPrompt);
        return responseStream.flatMap(speechResponse -> Flux.just(speechResponse.getResult().getOutput()));
    }
    // Flux.just() -> Sending a single file but using streaming format.
}



































