package com.joao.service.impl;

import com.joao.model.Atividade;
import com.joao.model.Cliente;
import com.joao.model.DTO.ClienteDTO;
import com.joao.model.Equipe;
import com.joao.model.Projeto;
import com.joao.repository.AtividadeRepository;
import com.joao.repository.ClienteRepository;
import com.joao.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjetoServiceImplTest {

    @InjectMocks
    private ProjetoServiceImpl projetoService;

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private AtividadeRepository atividadeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto 1");
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente");

        projeto.setCliente(cliente);
        when(projetoRepository.findAll()).thenReturn(Arrays.asList(projeto));

        assertEquals(1, projetoService.findAll().size());
        verify(projetoRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllSemCliente() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto 1");
        when(projetoRepository.findAll()).thenReturn(Arrays.asList(projeto));

        assertEquals(1, projetoService.findAll().size());
        verify(projetoRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente");
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto 1");
        projeto.setCliente(cliente);
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        Optional<Projeto> projetoOptional = projetoService.findById(1L);
        assertEquals("Projeto 1", projetoOptional.get().getNome());
        verify(projetoRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdSemCliente() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto 1");
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        Optional<Projeto> projetoOptional = projetoService.findById(1L);
        assertEquals("Projeto 1", projetoOptional.get().getNome());
        verify(projetoRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto 1");
        projeto.setCliente(cliente);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(projetoRepository.findByCliente(cliente)).thenReturn(Arrays.asList(projeto));

        ResponseEntity<?> responseEntity = projetoService.findByCliente(1L);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof List);
        List<Projeto> projetos = (List<Projeto>) responseEntity.getBody();
        assertEquals(1, projetos.size());
        assertEquals("Projeto 1", projetos.get(0).getNome());

        verify(clienteRepository, times(1)).findById(1L);
        verify(projetoRepository, times(1)).findByCliente(cliente);
    }

    @Test
    public void testFindByClienteNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = projetoService.findByCliente(1L);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(responseEntity.getBody(), "Cliente não encontrado!");
    }

    @Test
    public void testCreate() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto 1");
        projeto.setClienteDTO(clienteDTO);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(projetoRepository.save(projeto)).thenReturn(projeto);

        ResponseEntity<?> responseEntity = projetoService.create(projeto);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof Projeto);
        Projeto projetoSalvo = (Projeto) responseEntity.getBody();
        assertEquals("Projeto 1", projetoSalvo.getNome());

        verify(clienteRepository, times(1)).findById(1L);
        verify(projetoRepository, times(1)).save(projeto);
    }

    @Test
    public void testCreateSemCliente() {

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto 1");

        ResponseEntity<?> responseEntity = projetoService.create(projeto);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(responseEntity.getBody(), "É nescessário informar o Cliente!");
    }

    @Test
    public void testCreateNotFound() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto 1");
        projeto.setClienteDTO(clienteDTO);

        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = projetoService.create(projeto);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(responseEntity.getBody(), "Cliente não encontrado!");
    }

    @Test
    public void testUpdate() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto 1");
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");
        projeto.setCliente(cliente);
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(projetoRepository.save(projeto)).thenReturn(projeto);

        Optional<Projeto> projetoOptional = projetoService.update(1L, projeto);
        assertEquals("Projeto 1", projetoOptional.get().getNome());
        verify(projetoRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).findById(1L);
        verify(projetoRepository, times(1)).save(projeto);
    }

    @Test
    public void testUpdateNotFound() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Projeto> updated = projetoService.update(1L, null);
        assertFalse(updated.isPresent());
    }

    @Test
    public void testDelete() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto 1");
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        boolean isDeleted = projetoService.delete(1L);
        assertTrue(isDeleted);
        verify(projetoRepository, times(1)).findById(1L);
        verify(atividadeRepository, times(1)).deleteByProjeto(projeto);
        verify(projetoRepository, times(1)).deleteById(1L);
    }

}
