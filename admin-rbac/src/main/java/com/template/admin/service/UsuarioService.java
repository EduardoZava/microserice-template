package com.template.admin.service;

import com.template.admin.entity.Usuario;
import com.template.admin.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado: " + id));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public Usuario criar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario atualizar(String id, Usuario dados) {
        Usuario existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setRoleIds(dados.getRoleIds());
        existente.setAtivo(dados.isAtivo());
        existente.setAtualizadoEm(LocalDateTime.now());
        return repository.save(existente);
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }

    public Usuario atribuirRoles(String id, java.util.Set<String> roleIds) {
        Usuario usuario = buscarPorId(id);
        usuario.setRoleIds(roleIds);
        usuario.setAtualizadoEm(LocalDateTime.now());
        return repository.save(usuario);
    }
}
