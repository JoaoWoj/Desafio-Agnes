package com.joao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.joao.model.DTO.FuncionarioDTO;
import com.joao.model.DTO.ProjetoDTO;
import com.joao.model.enumerators.StatusEnum;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max=500)
    @Column(length = 500, nullable = false)
    private String descricao;

    @JsonIgnore
    private Date dataCriacaoAtividade;

    @NotNull
    @Column(name = "data_inicio_atividade")
    private Date dataInicioAtividade;

    @Column(name = "data_fim_atividade")
    private Date dataFimAtividade;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Funcionario responsavel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    @Transient
    @JsonProperty("responsavel")
    private FuncionarioDTO funcionarioDTO;

    @Transient
    @JsonProperty("projeto")
    private ProjetoDTO projetoDTO;

}
