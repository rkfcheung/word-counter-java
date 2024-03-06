package com.rkfcheung.wordcounter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value(staticConstructor="of")
public class WordsRequest {

    @JsonProperty
    List<String> words;
}
