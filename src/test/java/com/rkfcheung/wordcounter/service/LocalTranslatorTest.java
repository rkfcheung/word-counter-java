package com.rkfcheung.wordcounter.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

class LocalTranslatorTest {

    private LocalTranslator translator;

    @Test
    void should_ignore_null_string() {
        translator = new LocalTranslator();

        assertNull(translator.translate(null));
    }

    @Test
    void should_load_default_file() throws IOException {
        final Properties defaultFile = new Properties();
        defaultFile.load(Files.newInputStream(ResourceUtils.getFile(CLASSPATH_URL_PREFIX + "translation/local.properties").toPath()));

        translator = new LocalTranslator();

        defaultFile.stringPropertyNames()
                .forEach(word -> assertEquals(defaultFile.getProperty(word), translator.translate(word)));
    }

    @Test
    void should_not_fail_for_file_not_found() {
        try {
            translator = new LocalTranslator("file-not-found.properties");
        } catch (RuntimeException e) {
            fail();
        }
    }
}