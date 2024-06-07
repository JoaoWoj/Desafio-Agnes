package com.joao.model.DTO;

import com.joao.model.Projeto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDTOTest {

    private ClienteDTO cliente;

    @BeforeEach
    void setup(){
        cliente = new ClienteDTO();
    }

    @Test
    void testGetterAndSetter() {
        cliente.setId(1L);
        cliente.setNome("Cliente");
        cliente.setEndereco("Endereço");

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto");

        assertNotNull(cliente);
        assertEquals(1L, cliente.getId());
        assertEquals("Cliente", cliente.getNome());
        assertEquals("Endereço", cliente.getEndereco());

    }

    @Test
    void construtorTest(){
        ClienteDTO cliente = new ClienteDTO(1L, "Cliente", "Endereço");
        assertNotNull(cliente);
        assertEquals(1L, cliente.getId());
        assertEquals("Cliente", cliente.getNome());
        assertEquals("Endereço", cliente.getEndereco());
    }

}