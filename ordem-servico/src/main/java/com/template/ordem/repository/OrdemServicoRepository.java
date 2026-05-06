package com.template.ordem.repository;

import com.template.ordem.entity.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    List<OrdemServico> findByClienteId(Long clienteId);

    List<OrdemServico> findByStatus(OrdemServico.StatusOrdem status);

    @Query("SELECT o FROM OrdemServico o WHERE o.clienteId = :clienteId AND o.status = :status")
    List<OrdemServico> findByClienteIdAndStatus(
        @Param("clienteId") Long clienteId,
        @Param("status") OrdemServico.StatusOrdem status
    );

    @Query("SELECT COUNT(o) FROM OrdemServico o WHERE o.status = :status")
    long countByStatus(@Param("status") OrdemServico.StatusOrdem status);
}
