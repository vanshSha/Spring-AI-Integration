package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/*records introduced in Java 16 designed for class just old immutable data */
public record AIResponse(

         @JsonProperty("answer")  //  maps JSON key "answer"
         String answer,

         @JsonProperty("basedOn")
         String basedOn,          // which part of context was used

         @JsonProperty("answerable")
         boolean answerable       // did model have enough context?

){}
