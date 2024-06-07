package com.joao.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDTO {

    private Long id;
    private String nome;
    private String descricao;

}
