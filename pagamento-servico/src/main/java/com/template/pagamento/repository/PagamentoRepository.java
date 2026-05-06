package com.template.pagamento.repository;

import com.template.pagamento.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByOrdemServicoId(Long ordemServicoId);

    List<Pagamento> findByStatus(Pagamento.StatusPagamento status);

    @Query("SELECT p FROM Pagamento p WHERE p.ordemServicoId = :ordemId AND p.status = :status")
    List<Pagamento> findByOrdemServicoIdAndStatus(
        @Param("ordemId") Long ordemId,
        @Param("status") Pagamento.StatusPagamento status
    );

    @Query("SELECT SUM(p.valor) FROM Pagamento p WHERE p.status = 'APROVADO' AND p.ordemServicoId = :ordemId")
    java.math.BigDecimal sumValorAprovadoByOrdem(@Param("ordemId") Long ordemId);
}
