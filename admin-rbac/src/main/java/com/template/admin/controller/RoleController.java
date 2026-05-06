package com.template.admin.controller;

import com.template.admin.entity.Role;
import com.template.admin.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin/roles")
@Tag(name = "Roles", description = "Gerenciamento de roles")
public class RoleController {

    private final RoleRepository repository;

    public RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar todas as roles")
    public ResponseEntity<List<Role>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar role por ID")
    public ResponseEntity<Role> buscar(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Role não encontrada: " + id)));
    }

    @PostMapping
    @Operation(summary = "Criar nova role")
    public ResponseEntity<Role> criar(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(role));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar role")
    public ResponseEntity<Role> atualizar(@PathVariable String id, @RequestBody Role dados) {
        Role existente = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Role não encontrada: " + id));
        existente.setNome(dados.getNome());
        existente.setDescricao(dados.getDescricao());
        existente.setPermissaoIds(dados.getPermissaoIds());
        return ResponseEntity.ok(repository.save(existente));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar role")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
