package com.template.catalogo.repository;

import com.template.catalogo.entity.ServicoCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoCatalogoRepository extends JpaRepository<ServicoCatalogo, Long> {
}

