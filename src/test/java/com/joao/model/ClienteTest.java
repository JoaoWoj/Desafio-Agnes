package com.joao.model;

import com.joao.model.DTO.FuncionarioDTO;
import com.joao.model.DTO.ProjetoDTO;
import com.joao.model.enumerators.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setup(){
        cliente = new Cliente();
    }

    @Test
    void testGetterAndSetter() {
        cliente.setId(1L);
        cliente.setNome("Cliente");
        cliente.setEndereco("Endereço");
        cliente.setDataCriacao(new Date());
        cliente.setProjetos(new ArrayList<>());

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto");
        cliente.getProjetos().add(projeto);

        assertNotNull(cliente);
        assertEquals(1L, cliente.getId());
        assertEquals("Cliente", cliente.getNome());
        assertNotNull(cliente.getDataCriacao());
        assertEquals("Endereço", cliente.getEndereco());
        assertNotNull(cliente.getProjetos());

    }

    @Test
    void construtorTest(){
        Cliente cliente = new Cliente(1L, "Cliente", "Endereço", new Date(),new ArrayList<>());
        assertNotNull(cliente);
        assertEquals(1L, cliente.getId());
        assertEquals("Cliente", cliente.getNome());
        assertNotNull(cliente.getDataCriacao());;
        assertEquals("Endereço", cliente.getEndereco());
        assertNotNull(cliente.getProjetos());
    }

}