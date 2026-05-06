package com.template.admin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    @Indexed(unique = true)
    private String nome;

    private String descricao;

    private Set<String> permissaoIds;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Set<String> getPermissaoIds() { return permissaoIds; }
    public void setPermissaoIds(Set<String> permissaoIds) { this.permissaoIds = permissaoIds; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
