package com.rkfcheung.wordcounter.service;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class WordCounterImpl implements WordCounter {

    private final static Map<String, AtomicLong> counter = new ConcurrentHashMap<>();
    private final Translator translator;

    @Override
    public void add(final String... words) {
        if (words == null) {
            return;
        }

        Arrays.stream(words).parallel()
                .filter(this::isValid)
                .forEach(word -> {
                    final String enWord = translator.translate(word);
                    counter.computeIfAbsent(enWord, m -> doCount(enWord)).incrementAndGet();
                });
    }

    @Override
    public long count(final String word) {
        if (!isValid(word)) {
            return 0L;
        }

        return doCount(translator.translate(word)).get();
    }

    public void clear() {
        counter.clear();
    }

    public boolean isEmpty() {
        return counter.isEmpty();
    }

    public int size() {
        return counter.size();
    }

    private AtomicLong doCount(final String enWord) {
        return counter.getOrDefault(enWord, new AtomicLong(0L));
    }

    private boolean isValid(final String word) {
        if (word == null || word.trim().length() == 0) {
            return false;
        }

        return word.matches("[a-zA-Z]+");
    }
}
