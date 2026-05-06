package com.template.ordem.dto;

import com.template.ordem.entity.OrdemServico;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdemServicoDTO(
    Long id,
    String titulo,
    String descricao,
    OrdemServico.StatusOrdem status,
    BigDecimal valor,
    Long clienteId,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    public static OrdemServicoDTO fromEntity(OrdemServico entity) {
        return new OrdemServicoDTO(
            entity.getId(),
            entity.getTitulo(),
            entity.getDescricao(),
            entity.getStatus(),
            entity.getValor(),
            entity.getClienteId(),
            entity.getCriadoEm(),
            entity.getAtualizadoEm()
        );
    }
}
