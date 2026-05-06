package com.template.ordem.service;

import com.template.ordem.dto.OrdemServicoDTO;
import com.template.ordem.entity.OrdemServico;
import com.template.ordem.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class OrdemServicoService {

    private final OrdemServicoRepository repository;

    public OrdemServicoService(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> listarTodas() {
        return repository.findAll().stream().map(OrdemServicoDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public OrdemServicoDTO buscarPorId(Long id) {
        return repository.findById(id)
            .map(OrdemServicoDTO::fromEntity)
            .orElseThrow(() -> new NoSuchElementException("Ordem não encontrada: " + id));
    }

    public OrdemServicoDTO criar(OrdemServicoDTO dto) {
        OrdemServico entity = new OrdemServico();
        entity.setTitulo(dto.titulo());
        entity.setDescricao(dto.descricao());
        entity.setStatus(OrdemServico.StatusOrdem.ABERTA);
        entity.setValor(dto.valor());
        entity.setClienteId(dto.clienteId());
        return OrdemServicoDTO.fromEntity(repository.save(entity));
    }

    public OrdemServicoDTO atualizar(Long id, OrdemServicoDTO dto) {
        OrdemServico entity = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Ordem não encontrada: " + id));
        entity.setTitulo(dto.titulo());
        entity.setDescricao(dto.descricao());
        entity.setStatus(dto.status());
        entity.setValor(dto.valor());
        return OrdemServicoDTO.fromEntity(repository.save(entity));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId).stream()
            .map(OrdemServicoDTO::fromEntity).toList();
    }
}
