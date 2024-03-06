package com.rkfcheung.wordcounter.router;

import com.rkfcheung.wordcounter.model.WordCount;
import com.rkfcheung.wordcounter.model.WordsRequest;
import com.rkfcheung.wordcounter.model.WordsResponse;
import com.rkfcheung.wordcounter.service.WordCounter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WordCounterRouter {

    private final WordCounter wordCounter;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .GET("/count/{word}", accept(MediaType.ALL), this::count)
                .POST("/add", accept(MediaType.APPLICATION_JSON), this::add)
                .build();
    }

    @NonNull
    private Mono<ServerResponse> add(final @NonNull ServerRequest request) {
        return request.bodyToMono(WordsRequest.class)
                .filter(words -> !CollectionUtils.isEmpty(words.getWords()))
                .doOnNext(words -> {
                    log.info("Adding words [size={}] ...", words.getWords());
                    wordCounter.add(words.getWords().toArray(new String[0]));
                })
                .then(ServerResponse.ok().bodyValue(WordsResponse.of(HttpStatus.OK.value())));
    }

    @NonNull
    private Mono<ServerResponse> count(final @NonNull ServerRequest request) {
        return ServerResponse.ok()
                .bodyValue(WordCount.of(wordCounter.count(request.pathVariable("word"))));
    }
}
