package com.rkfcheung.wordcounter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Slf4j
public class LocalTranslator implements Translator {

    private final static String LOCAL_FILE = CLASSPATH_URL_PREFIX + "translation/local.properties";
    private final static Properties DICT = new Properties();

    public LocalTranslator() {
        load(LOCAL_FILE);
    }

    public LocalTranslator(final String file) {
        load(file);
    }

    @Override
    @Nullable
    public String translate(final String word) {
        if (word == null) {
            return null;
        }

        return DICT.getProperty(word, word);
    }

    private void load(final String file) {
        try {
            DICT.load(Files.newInputStream(ResourceUtils.getFile(file).toPath()));
        } catch (IOException e) {
            log.warn("Failed to load {}: {}", file, e.getMessage());
        }
    }
}
