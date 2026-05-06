package com.template.admin.repository;

import com.template.admin.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEntraIdObjectId(String entraIdObjectId);

    List<Usuario> findByAtivoTrue();

    @Query("{ 'roleIds': { $in: [?0] } }")
    List<Usuario> findByRoleId(String roleId);
}
