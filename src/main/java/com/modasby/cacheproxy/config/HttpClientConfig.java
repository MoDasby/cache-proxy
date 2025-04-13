package com.modasby.cacheproxy.config;

import com.modasby.cacheproxy.client.HttpClient;
import com.modasby.cacheproxy.client.WebClientHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient httpClient(@Value("${origin}") String origin) {
        WebClient webClient = WebClient.create(origin);

        return new WebClientHttpClient(webClient);
    }
}
