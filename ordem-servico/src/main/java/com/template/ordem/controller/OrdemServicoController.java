package com.template.ordem.controller;

import com.template.ordem.dto.OrdemServicoDTO;
import com.template.ordem.service.OrdemServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens")
@Tag(name = "Ordens de Serviço", description = "CRUD de ordens de serviço")
public class OrdemServicoController {

    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as ordens")
    public ResponseEntity<List<OrdemServicoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ordem por ID")
    public ResponseEntity<OrdemServicoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar nova ordem")
    public ResponseEntity<OrdemServicoDTO> criar(@RequestBody OrdemServicoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ordem")
    public ResponseEntity<OrdemServicoDTO> atualizar(@PathVariable Long id, @RequestBody OrdemServicoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ordem")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar ordens por cliente")
    public ResponseEntity<List<OrdemServicoDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }
}
