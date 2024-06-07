package com.joao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.joao.model.DTO.EquipeDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {

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
    @Length(max=100)
    @Column(length = 100, nullable = false)
    private String funcao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @JsonIgnore
    private Date dataCadastro;

    @JsonIgnore
    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades;

    @Transient
    @JsonProperty("equipe")
    private EquipeDTO equipeDTO;

}
