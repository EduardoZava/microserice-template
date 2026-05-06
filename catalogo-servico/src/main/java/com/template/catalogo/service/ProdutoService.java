package com.template.catalogo.service;

import com.template.catalogo.dto.ProdutoDTO;
import com.template.catalogo.entity.Produto;
import com.template.catalogo.repository.ProdutoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "produtos")
    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos() {
        return repository.findAll().stream().map(ProdutoDTO::fromEntity).toList();
    }

    @Cacheable(value = "produto", key = "#id")
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        return repository.findById(id)
            .map(ProdutoDTO::fromEntity)
            .orElseThrow(() -> new NoSuchElementException("Produto não encontrado: " + id));
    }

    @CacheEvict(value = {"produtos", "produto"}, allEntries = true)
    public ProdutoDTO criar(ProdutoDTO dto) {
        Produto entity = new Produto();
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setPreco(dto.preco());
        entity.setEstoque(dto.estoque());
        entity.setCategoria(dto.categoria());
        entity.setAtivo(dto.ativo() != null ? dto.ativo() : true);
        return ProdutoDTO.fromEntity(repository.save(entity));
    }

    @CacheEvict(value = {"produtos", "produto"}, allEntries = true)
    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        Produto entity = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Produto não encontrado: " + id));
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setPreco(dto.preco());
        entity.setEstoque(dto.estoque());
        entity.setCategoria(dto.categoria());
        if (dto.ativo() != null) entity.setAtivo(dto.ativo());
        return ProdutoDTO.fromEntity(repository.save(entity));
    }

    @CacheEvict(value = {"produtos", "produto"}, allEntries = true)
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
