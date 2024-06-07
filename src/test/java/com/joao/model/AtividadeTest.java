package com.joao.model;

import com.joao.model.DTO.FuncionarioDTO;
import com.joao.model.DTO.ProjetoDTO;
import com.joao.model.enumerators.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AtividadeTest {

    private Atividade atividade;

    @BeforeEach
    void setup(){
        atividade = new Atividade();
    }

    @Test
    void testGetterAndSetter() {
        atividade.setId(1L);
        atividade.setDescricao("Descrição");
        atividade.setDataInicioAtividade(new Date());
        atividade.setStatus(StatusEnum.CONCLUIDO);
        atividade.setDataCriacaoAtividade(new Date());
        atividade.setDataFimAtividade(new Date());

        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setId(1L);
        funcionarioDTO.setNome("Gabriel");
        atividade.setFuncionarioDTO(funcionarioDTO);

        ProjetoDTO projetoDTO = new ProjetoDTO();
        projetoDTO.setId(1L);
        projetoDTO.setNome("Projeto");
        atividade.setProjetoDTO(projetoDTO);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Gabriel");
        atividade.setResponsavel(funcionario);

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto");
        atividade.setProjeto(projeto);

        assertNotNull(atividade);
        assertEquals(1L, atividade.getId());
        assertEquals("Descrição", atividade.getDescricao());
        assertNotNull(atividade.getDataInicioAtividade());
        assertNotNull(atividade.getDataCriacaoAtividade());
        assertNotNull(atividade.getDataFimAtividade());
        assertEquals(StatusEnum.CONCLUIDO, atividade.getStatus());
        assertNotNull(atividade.getFuncionarioDTO());
        assertEquals(1L, atividade.getFuncionarioDTO().getId());
        assertEquals("Gabriel", atividade.getFuncionarioDTO().getNome());
        assertNotNull(atividade.getProjetoDTO());
        assertEquals(1L, atividade.getProjetoDTO().getId());
        assertEquals("Projeto", atividade.getProjetoDTO().getNome());
        assertNotNull(atividade.getFuncionarioDTO());
        assertEquals(1L, atividade.getResponsavel().getId());
        assertEquals("Gabriel", atividade.getResponsavel().getNome());
        assertNotNull(atividade.getProjeto());
        assertEquals(1L, atividade.getProjeto().getId());
        assertEquals("Projeto", atividade.getProjeto().getNome());

    }

    @Test
    void construtorTest(){
        Atividade atividade = new Atividade(1L, "descricao", new Date(), new Date(), new Date(), StatusEnum.CONCLUIDO, new Funcionario(), new Projeto(), new FuncionarioDTO(), new ProjetoDTO());
        assertEquals(1L, atividade.getId());
        assertEquals("descricao", atividade.getDescricao());
        assertEquals(StatusEnum.CONCLUIDO, atividade.getStatus());
        assertNotNull(atividade.getDataInicioAtividade());
        assertNotNull(atividade.getDataCriacaoAtividade());
        assertNotNull(atividade.getDataFimAtividade());
        assertNotNull(atividade.getResponsavel());
        assertNotNull(atividade.getProjeto());
        assertNotNull(atividade.getResponsavel());
        assertNotNull(atividade.getProjetoDTO());
    }

}