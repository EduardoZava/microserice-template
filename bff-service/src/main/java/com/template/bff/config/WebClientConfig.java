package com.template.bff.config;

import com.template.bff.service.InternalApiAccessTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient viaCepWebClient() {
        return WebClient.builder()
            .baseUrl("https://viacep.com.br/ws")
            .build();
    }

    @Bean
    public WebClient internalApiWebClient(
        InternalApiAccessTokenService tokenService,
        @Value("${internal.api.key:change-me-in-env}") String internalApiKey
    ) {
        return WebClient.builder()
            .filter((request, next) -> next.exchange(
                org.springframework.web.reactive.function.client.ClientRequest.from(request)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.getTokenValue())
                    .header("X-Internal-Api-Key", internalApiKey)
                    .build()
            ))
            .build();
    }
}
