package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VerificationResult(
        @JsonProperty("answer")
        String answer,

        @JsonProperty("basedOn")
        String basedOn,          // which part of context was used

        @JsonProperty("answerable")
        boolean answerable       // did model have enough context?
)
{}
