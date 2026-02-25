package com.controller;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeechToTextTranscriptionController {

    // This is used to convert Voice (to) Text
    OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    @Value("classpath:input.mp4")
    Resource audioFile;

    public SpeechToTextTranscriptionController(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
    }

    @GetMapping("/transcribe")
    public String transcribe() {
        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .temperature(0f) // This method use for control randomness of the model output .
                // but in my case I turn off this feature
                .build();

        // Is a request object used to send audio + configuration to OpenAiAudioTranscriptionModel for speech-to-text
        AudioTranscriptionPrompt audioTranscriptionPrompt = new AudioTranscriptionPrompt(audioFile, options);
        // This is result Object
        AudioTranscriptionResponse audioTranscriptionResponse = openAiAudioTranscriptionModel.call(audioTranscriptionPrompt);
        return audioTranscriptionResponse.getResult().getOutput();
    }

}
