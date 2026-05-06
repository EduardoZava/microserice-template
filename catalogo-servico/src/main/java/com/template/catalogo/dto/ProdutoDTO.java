package com.template.catalogo.dto;

import com.template.catalogo.entity.Produto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoDTO(
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
    public static ProdutoDTO fromEntity(Produto entity) {
        return new ProdutoDTO(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao(),
            entity.getPreco(),
            entity.getEstoque(),
            entity.getCategoria(),
            entity.getAtivo(),
            entity.getCriadoEm(),
            entity.getAtualizadoEm()
        );
    }
}
