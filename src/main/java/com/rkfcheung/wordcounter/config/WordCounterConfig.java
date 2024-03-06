package com.rkfcheung.wordcounter.config;

import com.rkfcheung.wordcounter.service.LocalTranslator;
import com.rkfcheung.wordcounter.service.Translator;
import com.rkfcheung.wordcounter.service.WordCounter;
import com.rkfcheung.wordcounter.service.WordCounterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordCounterConfig {

    @Bean
    public Translator translator() {
        return new LocalTranslator();
    }

    @Bean
    public WordCounter wordCounter(final Translator translator) {
        return new WordCounterImpl(translator);
    }
}
