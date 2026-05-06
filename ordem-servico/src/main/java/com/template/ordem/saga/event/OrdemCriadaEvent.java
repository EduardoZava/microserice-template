package com.template.saga.event;

import java.math.BigDecimal;

public record OrdemCriadaEvent(Long ordemId, Long clienteId, BigDecimal valor) {
}

