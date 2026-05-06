package com.template.pagamento.dto;

import com.template.pagamento.entity.Pagamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoDTO(
    Long id,
    Long ordemServicoId,
    BigDecimal valor,
    Pagamento.StatusPagamento status,
    Pagamento.FormaPagamento formaPagamento,
    LocalDateTime pagoEm,
    LocalDateTime criadoEm
) {
    public static PagamentoDTO fromEntity(Pagamento entity) {
        return new PagamentoDTO(
            entity.getId(),
            entity.getOrdemServicoId(),
            entity.getValor(),
            entity.getStatus(),
            entity.getFormaPagamento(),
            entity.getPagoEm(),
            entity.getCriadoEm()
        );
    }
}
