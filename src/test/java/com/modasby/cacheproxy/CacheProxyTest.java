package com.modasby.cacheproxy;

import com.modasby.cacheproxy.cache.Cache;
import com.modasby.cacheproxy.client.HttpClient;
import com.modasby.cacheproxy.proxy.CacheProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CacheProxyTest {

    @Mock
    private HttpClient client;

    @Mock
    private Cache<String, ResponseEntity<String>> cache;

    @InjectMocks
    private CacheProxy cacheProxy;

    @BeforeEach
    void setUp() {
        client = mock(HttpClient.class);
        cache = mock(Cache.class);
        cacheProxy = new CacheProxy(cache, client);
    }

    @Test
    public void testMissCache() {
        when(
                client.get(anyString(), eq(String.class))
        ).thenReturn(Mono.just(ResponseEntity.ok("ok")));

        when(
                cache.get(anyString())
        ).thenReturn(null);

        String path = "/test";

        Mono<ResponseEntity<String>> data = cacheProxy.getData(path);

        StepVerifier.create(data).expectNextMatches(r ->
                Objects.nonNull(r) && "MISS".equals(r.getHeaders().getFirst("X-Cache"))
        );

        verify(client, atLeastOnce()).get(path, String.class);
        verify(cache, atLeastOnce()).get(path);
    }

    @Test
    public void testHitCache() {
        when(cache.get(anyString())).thenReturn(ResponseEntity.ok("ok"));

        String path = "/test";

        Mono<ResponseEntity<String>> data = cacheProxy.getData(path);

        StepVerifier.create(data).expectNextMatches(r ->
                "HIT".equals(r.getHeaders().getFirst("X-Cache"))
        );

        verify(client, never()).get(path, String.class);
        verify(cache, atLeastOnce()).get(path);
    }
}
