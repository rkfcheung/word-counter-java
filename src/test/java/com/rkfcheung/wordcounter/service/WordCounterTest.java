package com.rkfcheung.wordcounter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordCounterTest {

    private final WordCounter wordCounter = new WordCounterImpl(new LocalTranslator());

    @BeforeEach
    void setUp() {
        ((WordCounterImpl) wordCounter).clear();
    }

    @Test
    void should_add_concurrently() {
        final List<String> words = Arrays.asList("bonjour", "hello");

        words.parallelStream().forEach(wordCounter::add);

        words.parallelStream().forEach(word -> assertEquals(words.size(), wordCounter.count(word)));
        assertEquals(1L, ((WordCounterImpl) wordCounter).size());
    }

    @Test
    void should_ignore_non_alphabetic_characters() {
        final String[] words = new String[]{
                "123", "h3ll@", null, " "
        };

        wordCounter.add(words);

        Arrays.stream(words).forEach(word -> assertEquals(0L, wordCounter.count(word)));
        assertTrue(((WordCounterImpl) wordCounter).isEmpty());
    }

    @Test
    void should_ignore_null_inputs() {
        wordCounter.add((String) null);
        assertTrue(((WordCounterImpl) wordCounter).isEmpty());

        wordCounter.add((String[]) null);
        assertTrue(((WordCounterImpl) wordCounter).isEmpty());
    }

    @Test
    void should_treat_same_words_written_in_different_languages() {
        final String[] words = new String[]{
                "flower", "flor", "blume"
        };

        wordCounter.add(words);

        Arrays.stream(words).forEach(word -> assertEquals(words.length, wordCounter.count(word)));
        assertEquals(1L, ((WordCounterImpl) wordCounter).size());
    }
}