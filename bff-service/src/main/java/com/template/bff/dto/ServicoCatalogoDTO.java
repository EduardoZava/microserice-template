package com.template.bff.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ServicoCatalogoDTO(
    Long id,
    String nome,
    String descricao,
    BigDecimal precoBase,
    Integer duracaoEstimadaMinutos,
    String categoria,
    Boolean ativo,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
}

