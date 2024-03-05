package com.rkfcheung.wordcounter.service;

public interface WordCounter {

    void add(final String... words);

    long count(final String word);
}
