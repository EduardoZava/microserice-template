package com.template.ordem.saga;

import com.template.saga.event.PagamentoProcessadoEvent;
import com.template.ordem.service.OrdemServicoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoSagaListener {

    private final OrdemServicoService ordemServicoService;

    public PagamentoSagaListener(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @KafkaListener(topics = "${saga.topics.pagamento-status}")
    public void onPagamentoProcessado(PagamentoProcessadoEvent event) {
        ordemServicoService.processarResultadoPagamento(event);
    }
}

