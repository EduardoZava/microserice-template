package com.template.bff.controller;

import com.template.bff.dto.ProdutoCatalogoDTO;
import com.template.bff.dto.ServicoCatalogoDTO;
import com.template.bff.service.CatalogoBffService;
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
@RequestMapping("/api/catalogo")
@Tag(name = "Catalogo BFF", description = "BFF para CRUD de produtos e servicos do catalogo")
public class CatalogoBffController {

    private final CatalogoBffService service;

    public CatalogoBffController(CatalogoBffService service) {
        this.service = service;
    }

    @GetMapping("/produtos")
    @Operation(summary = "Listar produtos")
    public ResponseEntity<List<ProdutoCatalogoDTO>> listarProdutos() {
        return ResponseEntity.ok(service.listarProdutos());
    }

    @GetMapping("/produtos/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoCatalogoDTO> buscarProduto(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarProduto(id));
    }

    @PostMapping("/produtos")
    @Operation(summary = "Criar produto")
    public ResponseEntity<ProdutoCatalogoDTO> criarProduto(@RequestBody ProdutoCatalogoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarProduto(dto));
    }

    @PutMapping("/produtos/{id}")
    @Operation(summary = "Atualizar produto")
    public ResponseEntity<ProdutoCatalogoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoCatalogoDTO dto) {
        return ResponseEntity.ok(service.atualizarProduto(id, dto));
    }

    @DeleteMapping("/produtos/{id}")
    @Operation(summary = "Deletar produto")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        service.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/servicos")
    @Operation(summary = "Listar servicos")
    public ResponseEntity<List<ServicoCatalogoDTO>> listarServicos() {
        return ResponseEntity.ok(service.listarServicos());
    }

    @GetMapping("/servicos/{id}")
    @Operation(summary = "Buscar servico por ID")
    public ResponseEntity<ServicoCatalogoDTO> buscarServico(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarServico(id));
    }

    @PostMapping("/servicos")
    @Operation(summary = "Criar servico")
    public ResponseEntity<ServicoCatalogoDTO> criarServico(@RequestBody ServicoCatalogoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarServico(dto));
    }

    @PutMapping("/servicos/{id}")
    @Operation(summary = "Atualizar servico")
    public ResponseEntity<ServicoCatalogoDTO> atualizarServico(@PathVariable Long id, @RequestBody ServicoCatalogoDTO dto) {
        return ResponseEntity.ok(service.atualizarServico(id, dto));
    }

    @DeleteMapping("/servicos/{id}")
    @Operation(summary = "Deletar servico")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        service.deletarServico(id);
        return ResponseEntity.noContent().build();
    }
}

