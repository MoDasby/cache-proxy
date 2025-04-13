package com.modasby.cacheproxy.client;

import com.modasby.cacheproxy.exception.BadGatewayException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientHttpClient implements HttpClient {
    private final WebClient client;

    public WebClientHttpClient(WebClient client) {
        this.client = client;
    }

    @Override
    public <T> Mono<ResponseEntity<T>> get(String path, Class<T> responseType) {
        return client
            .get()
            .uri(path)
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError, r ->
                    r.bodyToMono(String.class).map(b ->
                            new BadGatewayException("Server error: " + b)
            ))
            .onStatus(HttpStatusCode::is4xxClientError, r ->
                    r.bodyToMono(String.class).map(b ->
                            new BadGatewayException("Client error: " + b)
            ))
            .toEntity(responseType);
    }
}
