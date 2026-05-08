package com.template.bff.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoCatalogoDTO(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer estoque,
    String categoria,
    Boolean ativo,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
}

