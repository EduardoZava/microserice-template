package com.template.catalogo.controller;

import com.template.catalogo.dto.ServicoCatalogoDTO;
import com.template.catalogo.service.ServicoCatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@Tag(name = "Serviços do Catálogo", description = "CRUD de serviços oferecidos no catálogo")
public class ServicoCatalogoController {

    private final ServicoCatalogoService service;

    public ServicoCatalogoController(ServicoCatalogoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os serviços do catálogo")
    public ResponseEntity<List<ServicoCatalogoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço do catálogo por ID")
    public ResponseEntity<ServicoCatalogoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo serviço no catálogo")
    public ResponseEntity<ServicoCatalogoDTO> criar(@RequestBody ServicoCatalogoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar serviço do catálogo")
    public ResponseEntity<ServicoCatalogoDTO> atualizar(@PathVariable Long id, @RequestBody ServicoCatalogoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar serviço do catálogo")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

