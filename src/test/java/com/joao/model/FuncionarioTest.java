package com.joao.model;

import com.joao.model.DTO.EquipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioTest {

    private Funcionario funcionario;

    @BeforeEach
    void setup(){
        funcionario = new Funcionario();
    }

    @Test
    void testGetterAndSetter() {
        funcionario.setId(1L);
        funcionario.setNome("Funcionario");
        funcionario.setFuncao("Função");
        funcionario.setDataCadastro(new Date());
        funcionario.setAtividades(new ArrayList<>());
        funcionario.setEquipeDTO(new EquipeDTO());
        funcionario.setEquipe(new Equipe());

        assertNotNull(funcionario);
        assertEquals(1L, funcionario.getId());
        assertEquals("Funcionario", funcionario.getNome());
        assertEquals("Função", funcionario.getFuncao());
        assertNotNull(funcionario.getDataCadastro());
        assertNotNull(funcionario.getEquipe());
        assertNotNull(funcionario.getEquipeDTO());
        assertNotNull(funcionario.getAtividades());

    }

    @Test
    void construtorTest(){
        Funcionario funcionario = new Funcionario(1L, "Funcionario", "Função", new Equipe(), new Date(), new ArrayList<>(), new EquipeDTO());
        assertNotNull(funcionario);
        assertEquals(1L, funcionario.getId());
        assertEquals("Funcionario", funcionario.getNome());
        assertEquals("Função", funcionario.getFuncao());
        assertNotNull(funcionario.getDataCadastro());
        assertNotNull(funcionario.getEquipe());
        assertNotNull(funcionario.getEquipeDTO());
        assertNotNull(funcionario.getAtividades());
    }

}