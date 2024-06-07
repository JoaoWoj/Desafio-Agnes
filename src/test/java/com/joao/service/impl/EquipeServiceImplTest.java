package com.joao.service.impl;

import com.joao.model.Equipe;
import com.joao.model.Funcionario;
import com.joao.repository.EquipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class EquipeServiceImplTest {

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Mock
    private EquipeRepository repository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setSetor("Setor");
        equipe.setDescricao("Descrição");
        equipe.setFuncionarios(new ArrayList<>());
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario");
        funcionario.setFuncao("Função");
        equipe.getFuncionarios().add(funcionario);
        equipe.setFuncionariosDTO(new ArrayList<>());

        when(repository.findAll()).thenReturn(Arrays.asList(equipe));
        assertEquals(1, equipeService.findAll().size());
    }
    @Test
    public void testFindAllSemFuncionario() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Equipe()));
        assertEquals(1, equipeService.findAll().size());
    }


    @Test
    public void testFindById() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setSetor("Setor");
        equipe.setDescricao("Descrição");
        equipe.setFuncionarios(new ArrayList<>());
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario");
        funcionario.setFuncao("Função");
        equipe.getFuncionarios().add(funcionario);
        equipe.setFuncionariosDTO(new ArrayList<>());

        when(repository.findById(1L)).thenReturn(Optional.of(equipe));
        assertTrue(equipeService.findById(1L).isPresent());
    }

    @Test
    public void testFindByIdSemFuncionario() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(equipe));
        assertTrue(equipeService.findById(1L).isPresent());
    }

    @Test
    public void testCreate() {
        Equipe equipe = new Equipe();
        equipe.setSetor("Teste");
        when(repository.save(any(Equipe.class))).thenReturn(equipe);
        Equipe created = equipeService.create(equipe);
        assertEquals("Teste", created.getSetor());
    }

    @Test
    public void testUpdate() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setSetor("Setor");
        equipe.setDescricao("Descrição");
        equipe.setFuncionarios(new ArrayList<>());
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario");
        funcionario.setFuncao("Função");
        equipe.getFuncionarios().add(funcionario);
        equipe.setFuncionariosDTO(new ArrayList<>());

        when(repository.findById(1L)).thenReturn(Optional.of(equipe));
        when(repository.save(any(Equipe.class))).thenReturn(equipe);

        Optional<Equipe> updated = equipeService.update(1L, equipe);
        assertTrue(updated.isPresent());
        assertEquals("Setor", updated.get().getSetor());
    }

    @Test
    public void testUpdateSemFuncionarios() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setSetor("Teste");
        when(repository.findById(1L)).thenReturn(Optional.of(equipe));
        when(repository.save(any(Equipe.class))).thenReturn(equipe);
        Optional<Equipe> updated = equipeService.update(1L, equipe);
        assertTrue(updated.isPresent());
        assertEquals("Teste", updated.get().getSetor());
    }

    @Test
    public void testUpdateNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Optional<Equipe> updated = equipeService.update(1L, null);
        assertFalse(updated.isPresent());
    }

    @Test
    public void testDelete() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setFuncionarios(Arrays.asList(new Funcionario()));
        when(repository.findById(1L)).thenReturn(Optional.of(equipe));
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não é possível excluir Equipe, enquanto existem funcionários cadastrados a ele!"), equipeService.delete(1L));
    }

    @Test
    public void testDeleteSemFuncionarios() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(equipe));
        doNothing().when(repository).deleteById(1L);
        assertEquals(ResponseEntity.noContent().build(), equipeService.delete(1L));
    }
}