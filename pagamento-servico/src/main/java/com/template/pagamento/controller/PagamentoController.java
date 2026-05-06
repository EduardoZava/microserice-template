package com.template.pagamento.controller;

import com.template.pagamento.dto.PagamentoDTO;
import com.template.pagamento.entity.Pagamento;
import com.template.pagamento.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamentos", description = "CRUD de pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os pagamentos")
    public ResponseEntity<List<PagamentoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID")
    public ResponseEntity<PagamentoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo pagamento")
    public ResponseEntity<PagamentoDTO> criar(@RequestBody PagamentoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pagamento")
    public ResponseEntity<PagamentoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam Pagamento.StatusPagamento status) {
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }

    @GetMapping("/ordem/{ordemServicoId}")
    @Operation(summary = "Listar pagamentos por ordem de serviço")
    public ResponseEntity<List<PagamentoDTO>> listarPorOrdem(@PathVariable Long ordemServicoId) {
        return ResponseEntity.ok(service.listarPorOrdem(ordemServicoId));
    }
}
