package com.template.bff.service;

import com.template.bff.dto.EnderecoDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ViaCepService {

    private final WebClient viaCepWebClient;

    public ViaCepService(WebClient viaCepWebClient) {
        this.viaCepWebClient = viaCepWebClient;
    }

    @Cacheable(value = "cep-cache", key = "#cep")
    public EnderecoDTO buscarEndereco(String cep) {
        // Intentionally synchronous: results are cached in Redis, so blocking here is acceptable
        // and simplifies integration with Spring Cache's synchronous @Cacheable contract.
        return viaCepWebClient.get()
            .uri("/{cep}/json/", cep)
            .retrieve()
            .bodyToMono(EnderecoDTO.class)
            .block();
    }
}
