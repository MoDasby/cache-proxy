package com.modasby.cacheproxy.controller;

import com.modasby.cacheproxy.proxy.CacheProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class CacheController {
    private final CacheProxy cacheProxy;

    public CacheController(CacheProxy cacheProxy) {
        this.cacheProxy = cacheProxy;
    }

    @GetMapping("/**")
    public Mono<ResponseEntity<String>> getRequest(ServerHttpRequest serverHttpRequest) {
        String path = serverHttpRequest.getURI().getPath();
        String query = serverHttpRequest.getURI().getQuery();

        String uri = query != null ? path + "?" + query : path;

        return this.cacheProxy.getData(uri);
    }

    @GetMapping("/proxy/clear")
    public ResponseEntity<?> clear() {
        this.cacheProxy.clearCache();

        return ResponseEntity.ok().build();
    }
}
