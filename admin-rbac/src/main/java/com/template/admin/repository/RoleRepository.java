package com.template.admin.repository;

import com.template.admin.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByNome(String nome);

    boolean existsByNome(String nome);
}
