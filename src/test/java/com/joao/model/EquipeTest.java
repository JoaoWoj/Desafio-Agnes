package com.joao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EquipeTest {

    private Equipe equipe;

    @BeforeEach
    void setup(){
        equipe = new Equipe();
    }

    @Test
    void testGetterAndSetter() {
        equipe.setId(1L);
        equipe.setSetor("Setor");
        equipe.setDescricao("Descrição");
        equipe.setDataCriacao(new Date());
        equipe.setFuncionarios(new ArrayList<>());
        equipe.setFuncionariosDTO(new ArrayList<>());

        assertNotNull(equipe);
        assertEquals(1L, equipe.getId());
        assertEquals("Setor", equipe.getSetor());
        assertNotNull(equipe.getDataCriacao());
        assertEquals("Descrição", equipe.getDescricao());
        assertNotNull(equipe.getFuncionarios());
        assertNotNull(equipe.getFuncionariosDTO());

    }

    @Test
    void construtorTest(){
        Equipe equipe = new Equipe(1L, "Setor", "Descrição", new Date(), new ArrayList<>(), new ArrayList<>());
        assertNotNull(equipe);
        assertEquals(1L, equipe.getId());
        assertEquals("Setor", equipe.getSetor());
        assertNotNull(equipe.getDataCriacao());;
        assertEquals("Descrição", equipe.getDescricao());
        assertNotNull(equipe.getFuncionarios());
        assertNotNull(equipe.getFuncionariosDTO());
    }

}