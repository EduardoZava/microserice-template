package com.template.catalogo.dto;

import com.template.catalogo.entity.ServicoCatalogo;

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
    public static ServicoCatalogoDTO fromEntity(ServicoCatalogo entity) {
        return new ServicoCatalogoDTO(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao(),
            entity.getPrecoBase(),
            entity.getDuracaoEstimadaMinutos(),
            entity.getCategoria(),
            entity.getAtivo(),
            entity.getCriadoEm(),
            entity.getAtualizadoEm()
        );
    }
}

