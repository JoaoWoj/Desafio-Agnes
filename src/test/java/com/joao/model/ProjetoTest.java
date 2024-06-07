package com.joao.model;

import com.joao.model.DTO.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProjetoTest {

    private Projeto projeto;

    @BeforeEach
    void setup(){
        projeto = new Projeto();
    }

    @Test
    void testGetterAndSetter() {
        projeto.setId(1L);
        projeto.setNome("Projeto");
        projeto.setDescricao("Descrição");
        projeto.setDataCriacao(new Date());
        projeto.setAtividades(new ArrayList<>());
        projeto.setClienteDTO(new ClienteDTO());
        projeto.setCliente(new Cliente());

        assertNotNull(projeto);
        assertEquals(1L, projeto.getId());
        assertEquals("Projeto", projeto.getNome());
        assertEquals("Descrição", projeto.getDescricao());
        assertNotNull(projeto.getDataCriacao());
        assertNotNull(projeto.getCliente());
        assertNotNull(projeto.getClienteDTO());
        assertNotNull(projeto.getAtividades());

    }

    @Test
    void construtorTest(){
        Projeto projeto = new Projeto(1L, "Projeto", "Descrição", new Date(), new Cliente(), new ClienteDTO(), new ArrayList<>());
        assertNotNull(projeto);
        assertEquals(1L, projeto.getId());
        assertEquals("Projeto", projeto.getNome());
        assertEquals("Descrição", projeto.getDescricao());
        assertNotNull(projeto.getDataCriacao());
        assertNotNull(projeto.getCliente());
        assertNotNull(projeto.getClienteDTO());
        assertNotNull(projeto.getAtividades());
    }

}