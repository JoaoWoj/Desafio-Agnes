package com.joao.model.DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipeDTOTest {

    private EquipeDTO equipe;

    @BeforeEach
    void setup(){
        equipe = new EquipeDTO();
    }

    @Test
    void testGetterAndSetter() {
        equipe.setId(1L);
        equipe.setSetor("Setor");
        equipe.setDescricao("Descrição");

        assertNotNull(equipe);
        assertEquals(1L, equipe.getId());
        assertEquals("Setor", equipe.getSetor());
        assertEquals("Descrição", equipe.getDescricao());

    }

    @Test
    void construtorTest(){
        EquipeDTO equipe = new EquipeDTO(1L, "Setor", "Descrição");
        assertNotNull(equipe);
        assertEquals(1L, equipe.getId());
        assertEquals("Setor", equipe.getSetor());
        assertEquals("Descrição", equipe.getDescricao());
    }

}