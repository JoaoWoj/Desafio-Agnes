package com.joao.model.DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjetoDTOTest {

    private ProjetoDTO projeto;

    @BeforeEach
    void setup(){
        projeto = new ProjetoDTO();
    }

    @Test
    void testGetterAndSetter() {
        projeto.setId(1L);
        projeto.setNome("ProjetoDTO");
        projeto.setDescricao("Descrição");

        assertNotNull(projeto);
        assertEquals(1L, projeto.getId());
        assertEquals("ProjetoDTO", projeto.getNome());
        assertEquals("Descrição", projeto.getDescricao());

    }

    @Test
    void construtorTest(){
        ProjetoDTO projeto = new ProjetoDTO(1L, "ProjetoDTO", "Descrição");
        assertNotNull(projeto);
        assertEquals(1L, projeto.getId());
        assertEquals("ProjetoDTO", projeto.getNome());
        assertEquals("Descrição", projeto.getDescricao());
    }

}