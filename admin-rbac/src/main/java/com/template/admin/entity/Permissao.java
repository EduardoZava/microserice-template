package com.template.admin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "permissoes")
public class Permissao {

    @Id
    private String id;

    @Indexed(unique = true)
    private String nome;

    private String descricao;

    private String recurso;

    private String acao;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getRecurso() { return recurso; }
    public void setRecurso(String recurso) { this.recurso = recurso; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
