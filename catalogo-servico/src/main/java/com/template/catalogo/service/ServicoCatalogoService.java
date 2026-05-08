package com.template.catalogo.service;

import com.template.catalogo.dto.ServicoCatalogoDTO;
import com.template.catalogo.entity.ServicoCatalogo;
import com.template.catalogo.repository.ServicoCatalogoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ServicoCatalogoService {

    private final ServicoCatalogoRepository repository;

    public ServicoCatalogoService(ServicoCatalogoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ServicoCatalogoDTO> listarTodos() {
        return repository.findAll().stream().map(ServicoCatalogoDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public ServicoCatalogoDTO buscarPorId(Long id) {
        return repository.findById(id)
            .map(ServicoCatalogoDTO::fromEntity)
            .orElseThrow(() -> new NoSuchElementException("Serviço não encontrado: " + id));
    }

    public ServicoCatalogoDTO criar(ServicoCatalogoDTO dto) {
        ServicoCatalogo entity = new ServicoCatalogo();
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setPrecoBase(dto.precoBase());
        entity.setDuracaoEstimadaMinutos(dto.duracaoEstimadaMinutos());
        entity.setCategoria(dto.categoria());
        entity.setAtivo(dto.ativo() != null ? dto.ativo() : true);
        return ServicoCatalogoDTO.fromEntity(repository.save(entity));
    }

    public ServicoCatalogoDTO atualizar(Long id, ServicoCatalogoDTO dto) {
        ServicoCatalogo entity = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Serviço não encontrado: " + id));
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setPrecoBase(dto.precoBase());
        entity.setDuracaoEstimadaMinutos(dto.duracaoEstimadaMinutos());
        entity.setCategoria(dto.categoria());
        if (dto.ativo() != null) {
            entity.setAtivo(dto.ativo());
        }
        return ServicoCatalogoDTO.fromEntity(repository.save(entity));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

