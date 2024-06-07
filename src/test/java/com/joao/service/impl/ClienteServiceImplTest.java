package com.joao.service.impl;
import com.joao.model.Cliente;
import com.joao.model.Projeto;
import com.joao.repository.ClienteRepository;
import com.joao.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository repository;

    @Mock
    private ProjetoService projetoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Cliente()));
        assertEquals(1, clienteService.findAll().size());
    }

    @Test
    public void testFindById() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        assertTrue(clienteService.findById(1L).isPresent());
    }

    @Test
    public void testCreate() {
        Cliente cliente = new Cliente();
        cliente.setNome("Teste");
        when(repository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente created = clienteService.create(cliente);
        assertEquals("Teste", created.getNome());
    }

    @Test
    public void testCreateComData() {
        Cliente cliente = new Cliente();
        cliente.setNome("Teste");
        cliente.setDataCriacao(new Date());
        when(repository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente created = clienteService.create(cliente);
        assertEquals("Teste", created.getNome());
    }

    @Test
    public void testUpdate() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Teste");
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.save(any(Cliente.class))).thenReturn(cliente);
        Optional<Cliente> updated = clienteService.update(1L, cliente);
        assertTrue(updated.isPresent());
        assertEquals("Teste", updated.get().getNome());
    }

    @Test
    public void testUpdateNotFound() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Teste");
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Optional<Cliente> updated = clienteService.update(1L, cliente);
        assertFalse(updated.isPresent());
    }

    @Test
    public void testDelete() {
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto 1");

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");
        cliente.setProjetos(Arrays.asList(projeto));

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(projetoService.delete(projeto.getId())).thenReturn(true);

        assertTrue(clienteService.delete(1L));
    }

    @Test
    public void testDeleteSemProjeto() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(repository).deleteById(1L);
        assertTrue(clienteService.delete(1L));
    }

    @Test
    public void testDeleteNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertFalse(clienteService.delete(1L));
    }

}