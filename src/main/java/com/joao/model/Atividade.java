package com.joao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joao.model.enumerators.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Length(max=500)
    @Column(length = 500, nullable = false)
    private String descricao;

    @JsonIgnore
    private Date dataCriacaoAtividade;

    @NotBlank
    @NotNull
    @Column(name = "data_inicio_atividade")
    private Date dataInicioAtividade;

    @JsonIgnore
    @Column(name = "data_fim_atividade")
    private Date dataFimAtividade;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Funcionario responsavel;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    public Atividade(Long id, String descricao, StatusEnum status, Funcionario responsavel, Projeto projeto) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.responsavel = responsavel;
        this.projeto = projeto;
    }
}
