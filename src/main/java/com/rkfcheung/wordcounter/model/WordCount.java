package com.rkfcheung.wordcounter.model;

import lombok.Value;

@Value(staticConstructor="of")
public class WordCount {

    long count;
}
