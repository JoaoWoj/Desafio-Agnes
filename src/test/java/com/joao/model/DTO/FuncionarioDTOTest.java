package com.joao.model.DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioDTOTest {

    private FuncionarioDTO funcionario;

    @BeforeEach
    void setup(){
        funcionario = new FuncionarioDTO();
    }

    @Test
    void testGetterAndSetter() {
        funcionario.setId(1L);
        funcionario.setNome("FuncionarioDTO");
        funcionario.setFuncao("Função");

        assertNotNull(funcionario);
        assertEquals(1L, funcionario.getId());
        assertEquals("FuncionarioDTO", funcionario.getNome());
        assertEquals("Função", funcionario.getFuncao());

    }

    @Test
    void construtorTest(){
        FuncionarioDTO funcionario = new FuncionarioDTO(1L, "FuncionarioDTO", "Função");
        assertNotNull(funcionario);
        assertEquals(1L, funcionario.getId());
        assertEquals("FuncionarioDTO", funcionario.getNome());
        assertEquals("Função", funcionario.getFuncao());
    }

}