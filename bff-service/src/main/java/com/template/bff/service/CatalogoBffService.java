package com.template.bff.service;

import com.template.bff.dto.ProdutoCatalogoDTO;
import com.template.bff.dto.ServicoCatalogoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CatalogoBffService {

    private final WebClient internalApiWebClient;
    private final String catalogoBaseUrl;

    public CatalogoBffService(
        WebClient internalApiWebClient,
        @Value("${bff.catalogo.base-url:http://catalogo-servico:8083/api}") String catalogoBaseUrl
    ) {
        this.internalApiWebClient = internalApiWebClient;
        this.catalogoBaseUrl = catalogoBaseUrl;
    }

    public List<ProdutoCatalogoDTO> listarProdutos() {
        return execute(
            internalApiWebClient.get()
                .uri(catalogoBaseUrl + "/produtos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProdutoCatalogoDTO>>() {}),
            "Falha ao listar produtos"
        );
    }

    public ProdutoCatalogoDTO buscarProduto(Long id) {
        return execute(
            internalApiWebClient.get()
                .uri(catalogoBaseUrl + "/produtos/{id}", id)
                .retrieve()
                .bodyToMono(ProdutoCatalogoDTO.class),
            "Falha ao buscar produto"
        );
    }

    public ProdutoCatalogoDTO criarProduto(ProdutoCatalogoDTO dto) {
        return execute(
            internalApiWebClient.post()
                .uri(catalogoBaseUrl + "/produtos")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ProdutoCatalogoDTO.class),
            "Falha ao criar produto"
        );
    }

    public ProdutoCatalogoDTO atualizarProduto(Long id, ProdutoCatalogoDTO dto) {
        return execute(
            internalApiWebClient.put()
                .uri(catalogoBaseUrl + "/produtos/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ProdutoCatalogoDTO.class),
            "Falha ao atualizar produto"
        );
    }

    public void deletarProduto(Long id) {
        executeVoid(
            internalApiWebClient.delete()
                .uri(catalogoBaseUrl + "/produtos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class),
            "Falha ao deletar produto"
        );
    }

    public List<ServicoCatalogoDTO> listarServicos() {
        return execute(
            internalApiWebClient.get()
                .uri(catalogoBaseUrl + "/servicos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ServicoCatalogoDTO>>() {}),
            "Falha ao listar servicos"
        );
    }

    public ServicoCatalogoDTO buscarServico(Long id) {
        return execute(
            internalApiWebClient.get()
                .uri(catalogoBaseUrl + "/servicos/{id}", id)
                .retrieve()
                .bodyToMono(ServicoCatalogoDTO.class),
            "Falha ao buscar servico"
        );
    }

    public ServicoCatalogoDTO criarServico(ServicoCatalogoDTO dto) {
        return execute(
            internalApiWebClient.post()
                .uri(catalogoBaseUrl + "/servicos")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ServicoCatalogoDTO.class),
            "Falha ao criar servico"
        );
    }

    public ServicoCatalogoDTO atualizarServico(Long id, ServicoCatalogoDTO dto) {
        return execute(
            internalApiWebClient.put()
                .uri(catalogoBaseUrl + "/servicos/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ServicoCatalogoDTO.class),
            "Falha ao atualizar servico"
        );
    }

    public void deletarServico(Long id) {
        executeVoid(
            internalApiWebClient.delete()
                .uri(catalogoBaseUrl + "/servicos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class),
            "Falha ao deletar servico"
        );
    }

    private <T> T execute(Mono<T> mono, String context) {
        try {
            return mono.block();
        } catch (WebClientResponseException ex) {
            throw new ResponseStatusException(
                HttpStatus.valueOf(ex.getStatusCode().value()),
                context + ": " + sanitizeBody(ex.getResponseBodyAsString()),
                ex
            );
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, context + ": catalogo-servico indisponivel", ex);
        }
    }

    private void executeVoid(Mono<Void> mono, String context) {
        execute(mono.thenReturn(Boolean.TRUE), context);
    }

    private String sanitizeBody(String body) {
        if (body == null || body.isBlank()) {
            return "erro sem detalhes";
        }
        return body.length() > 300 ? body.substring(0, 300) : body;
    }
}

