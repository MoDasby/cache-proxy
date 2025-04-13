package com.modasby.cacheproxy.controller;

import com.modasby.cacheproxy.proxy.CacheProxy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/**")
public class CacheController {
    private final CacheProxy cacheProxy;

    public CacheController(CacheProxy cacheProxy) {
        this.cacheProxy = cacheProxy;
    }

    @GetMapping
    public Mono<ResponseEntity<String>> getRequest(HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getRequestURI();
        String queryString = httpServletRequest.getQueryString();

        String uri = path+"?"+queryString;

        return this.cacheProxy.getData(uri);
    }

    @GetMapping("/clear")
    public ResponseEntity<?> clear() {
        this.cacheProxy.clearCache();

        return ResponseEntity.ok().build();
    }
}
