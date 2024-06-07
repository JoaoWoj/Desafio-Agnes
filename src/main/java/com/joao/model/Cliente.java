package com.joao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Length(max=200)
    @Column(length = 200, nullable = false)
    private String nome;

    @NotBlank
    @NotNull
    @Length(max=300)
    @Column(length = 300, nullable = false)
    private String endereco;

    @JsonIgnore
    private Date dataCriacao;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Projeto> projetos;

    public Cliente(Long id, String nome, String endereco, List<Projeto> projetos) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.projetos = projetos;
    }
}
