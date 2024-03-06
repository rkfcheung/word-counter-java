package com.rkfcheung.wordcounter.router;

import com.rkfcheung.wordcounter.model.WordCount;
import com.rkfcheung.wordcounter.model.WordsRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WordCounterRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void should_add_words() {
        webTestClient.post()
                .uri("/add")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(WordsRequest.of(Arrays.asList("hello", "bonjour")))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void should_count_word() {
        webTestClient.get()
                .uri("/count/" + UUID.randomUUID())
                .accept(MediaType.ALL)
                .exchange()
                .expectStatus().isOk()
                .expectBody(WordCount.class).value(it -> assertThat(it.getCount()).isEqualTo(0L));
    }

    @Test
    void should_ignore_empty_words() {
        webTestClient.post()
                .uri("/add")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(WordsRequest.of(Collections.emptyList()))
                .exchange()
                .expectStatus().isOk();
    }
}