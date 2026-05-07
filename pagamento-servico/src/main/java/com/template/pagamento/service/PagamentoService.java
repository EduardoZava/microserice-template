package com.template.pagamento.service;

import com.template.pagamento.dto.PagamentoDTO;
import com.template.pagamento.entity.Pagamento;
import com.template.pagamento.repository.PagamentoRepository;
import com.template.saga.event.OrdemCriadaEvent;
import com.template.saga.event.PagamentoProcessadoEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class PagamentoService {

    private final PagamentoRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String pagamentoStatusTopic;

    public PagamentoService(
        PagamentoRepository repository,
        KafkaTemplate<String, Object> kafkaTemplate,
        @Value("${saga.topics.pagamento-status}") String pagamentoStatusTopic
    ) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.pagamentoStatusTopic = pagamentoStatusTopic;
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> listarTodos() {
        return repository.findAll().stream().map(PagamentoDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public PagamentoDTO buscarPorId(Long id) {
        return repository.findById(id)
            .map(PagamentoDTO::fromEntity)
            .orElseThrow(() -> new NoSuchElementException("Pagamento não encontrado: " + id));
    }

    public PagamentoDTO criar(PagamentoDTO dto) {
        Pagamento entity = new Pagamento();
        entity.setOrdemServicoId(dto.ordemServicoId());
        entity.setValor(dto.valor());
        entity.setStatus(Pagamento.StatusPagamento.PENDENTE);
        entity.setFormaPagamento(dto.formaPagamento());
        return PagamentoDTO.fromEntity(repository.save(entity));
    }

    public PagamentoDTO atualizarStatus(Long id, Pagamento.StatusPagamento novoStatus) {
        Pagamento entity = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Pagamento não encontrado: " + id));
        entity.setStatus(novoStatus);
        if (novoStatus == Pagamento.StatusPagamento.APROVADO) {
            entity.setPagoEm(java.time.LocalDateTime.now());
        }
        return PagamentoDTO.fromEntity(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> listarPorOrdem(Long ordemServicoId) {
        return repository.findByOrdemServicoId(ordemServicoId).stream()
            .map(PagamentoDTO::fromEntity).toList();
    }

    public void processarOrdemCriada(OrdemCriadaEvent event) {
        Pagamento entity = new Pagamento();
        entity.setOrdemServicoId(event.ordemId());
        entity.setValor(event.valor());
        entity.setFormaPagamento(Pagamento.FormaPagamento.PIX);
        entity.setStatus(Pagamento.StatusPagamento.APROVADO);
        entity.setPagoEm(java.time.LocalDateTime.now());

        Pagamento saved = repository.save(entity);
        kafkaTemplate.send(
            pagamentoStatusTopic,
            String.valueOf(event.ordemId()),
            new PagamentoProcessadoEvent(event.ordemId(), saved.getId(), saved.getStatus().name())
        );
    }
}
