package com.template.catalogo.repository;

import com.template.catalogo.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByAtivoTrue();

    List<Produto> findByCategoria(String categoria);

    @Query("SELECT p FROM Produto p WHERE p.ativo = true AND p.estoque > 0")
    List<Produto> findProdutosDisponiveis();

    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produto> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT p FROM Produto p WHERE p.categoria = :categoria AND p.ativo = true")
    List<Produto> findByCategoriaAndAtivo(@Param("categoria") String categoria);
}
