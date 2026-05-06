package com.template.saga.event;

public record PagamentoProcessadoEvent(Long ordemId, Long pagamentoId, String status) {
}

