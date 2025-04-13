package com.modasby.cacheproxy.client;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface HttpClient {
    <T> Mono<ResponseEntity<T>> get(String path, Class<T> responseType);
}
