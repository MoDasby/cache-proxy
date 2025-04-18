package com.modasby.cacheproxy.proxy;

import com.modasby.cacheproxy.cache.Cache;
import com.modasby.cacheproxy.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CacheProxy {

    private static final Logger log = LoggerFactory.getLogger(CacheProxy.class);

    private final Cache<String, ResponseEntity<String>> cache;
    private final HttpClient client;

    public CacheProxy(
            Cache<String, ResponseEntity<String>> cache,
            HttpClient client
    ) {
        this.cache = cache;
        this.client = client;
    }

    private HttpHeaders addCacheHeader(HttpHeaders currentHeaders, String hitOrMiss) {
        HttpHeaders headersMap = new HttpHeaders();

        headersMap.addAll(currentHeaders);
        headersMap.set("X-Cache", hitOrMiss);

        return headersMap;
    }

    private ResponseEntity<String> responseEntityWithHeaders(ResponseEntity<String> r, HttpHeaders h) {
        return ResponseEntity
                .status(r.getStatusCode())
                .headers(h)
                .body(r.getBody());
    }

    public Mono<ResponseEntity<String>> getData(String path) {
        log.debug("Requesting data from path: {}", path);
        ResponseEntity<String> cachedResponse = this.cache.get(path);

        if (Objects.nonNull(cachedResponse)) {
            log.debug("Data found in cache for path: {}", path);
            HttpHeaders headers = addCacheHeader(cachedResponse.getHeaders(), "HIT");

            return Mono.just(responseEntityWithHeaders(cachedResponse, headers));
        }

        log.debug("Data not found in cache for path: {}", path);
        return client
                .get(path, String.class)
                .map(r -> {

                    HttpHeaders headers = addCacheHeader(r.getHeaders(), "MISS");

                    ResponseEntity<String> response = responseEntityWithHeaders(r, headers);

                    cache.put(path, response);

                    return response;
                });
    }

    public void clearCache() {
        log.debug("Clearing cache");
        this.cache.clear();
    }
}
