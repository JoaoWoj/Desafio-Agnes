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
@Getter
@Setter
public class Projeto {

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
    @Length(max=500)
    @Column(length = 500, nullable = false)
    private String descricao;

    @JsonIgnore
    private Date dataCriacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades;

    public Projeto(Long id, String nome, String descricao, Cliente cliente, List<Atividade> atividades) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cliente = cliente;
        this.atividades = atividades;
    }
}
