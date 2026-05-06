package com.template.bff.controller;

import com.template.bff.dto.EnderecoDTO;
import com.template.bff.service.ViaCepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cep")
@Tag(name = "CEP", description = "Address lookup via ViaCEP")
public class CepController {

    private final ViaCepService viaCepService;

    public CepController(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    @GetMapping("/{cep}")
    @Operation(summary = "Busca endereço por CEP", description = "Consulta o ViaCEP para obter dados de endereço")
    public ResponseEntity<EnderecoDTO> buscarCep(
            @Parameter(description = "CEP no formato 00000000") @PathVariable String cep) {
        return ResponseEntity.ok(viaCepService.buscarEndereco(cep));
    }
}
