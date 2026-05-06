package com.template.pagamento.saga;

import com.template.saga.event.OrdemCriadaEvent;
import com.template.pagamento.service.PagamentoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrdemSagaListener {

    private final PagamentoService pagamentoService;

    public OrdemSagaListener(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @KafkaListener(topics = "${saga.topics.ordem-criada}")
    public void onOrdemCriada(OrdemCriadaEvent event) {
        pagamentoService.processarOrdemCriada(event);
    }
}

