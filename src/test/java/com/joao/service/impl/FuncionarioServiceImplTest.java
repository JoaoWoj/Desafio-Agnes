package com.joao.service.impl;

import com.joao.model.Equipe;
import com.joao.model.Funcionario;
import com.joao.model.DTO.EquipeDTO;
import com.joao.repository.FuncionarioRepository;
import com.joao.service.EquipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FuncionarioServiceImplTest {

    @InjectMocks
    private FuncionarioServiceImpl funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private EquipeService equipeService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario 1");
        when(funcionarioRepository.findAll()).thenReturn(Arrays.asList(funcionario));

        assertEquals(1, funcionarioService.findAll().size());
        verify(funcionarioRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario 1");
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        Optional<Funcionario> funcionarioOptional = funcionarioService.findById(1L);
        assertEquals("Funcionario 1", funcionarioOptional.get().getNome());
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByEquipe() {
        Long id = 1L;
        Equipe equipe = new Equipe();
        equipe.setId(id);
        equipe.setSetor("Setor da Equipe");
        equipe.setDescricao("Descrição da Equipe");
        Funcionario funcionario = new Funcionario();
        funcionario.setEquipe(equipe);
        funcionario.setEquipeDTO(new EquipeDTO(equipe.getId(), equipe.getSetor(), equipe.getDescricao()));

        when(equipeService.findById(id)).thenReturn(Optional.of(equipe));
        when(funcionarioRepository.findByEquipe(equipe)).thenReturn(Arrays.asList(funcionario));

        ResponseEntity<?> response = funcionarioService.findByEquipe(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(funcionario), response.getBody());
    }

    @Test
    public void testFindByEquipeNotFound() {
        Long id = 1L;

        when(equipeService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = funcionarioService.findByEquipe(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Equipe não encontrado!", response.getBody());
    }

    @Test
    public void testListByEquipe() {
        Long id = 1L;
        Equipe equipe = new Equipe();
        equipe.setId(id);
        equipe.setSetor("Setor da Equipe");
        equipe.setDescricao("Descrição da Equipe");
        Funcionario funcionario = new Funcionario();
        funcionario.setEquipe(equipe);

        when(equipeService.findById(id)).thenReturn(Optional.of(equipe));
        when(funcionarioRepository.findByEquipe(equipe)).thenReturn(Arrays.asList(funcionario));

        List<Funcionario> response = funcionarioService.listByEquipe(id);

        assertEquals(Arrays.asList(funcionario), response);
    }

    @Test
    public void testListByEquipeNotFound() {
        Long id = 1L;

        when(equipeService.findById(id)).thenReturn(Optional.empty());

        List<Funcionario> response = funcionarioService.listByEquipe(id);

        assertNull(response);
    }

    @Test
    public void testCreate() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario 1");
        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setId(1L);
        funcionario.setEquipeDTO(equipeDTO);
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setDescricao("Equipe 1");
        when(equipeService.findById(1L)).thenReturn(Optional.of(equipe));
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        ResponseEntity<?> responseEntity = funcionarioService.create(funcionario);
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(equipeService, times(1)).findById(1L);
        verify(funcionarioRepository, times(1)).save(funcionario);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Nome do Funcionário");
        funcionario.setFuncao("Função do Funcionário");
        Equipe equipe = new Equipe();
        equipe.setId(id);
        equipe.setDescricao("Descrição da Equipe");
        equipe.setSetor("Setor da Equipe");
        funcionario.setEquipe(equipe);

        when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionario));
        when(equipeService.findById(id)).thenReturn(Optional.of(equipe));
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        Optional<Funcionario> response = funcionarioService.update(id, funcionario);

        assertEquals(Optional.of(funcionario), response);
    }

    @Test
    public void testUpdateNotFound() {
        Long id = 1L;
        Funcionario funcionario = new Funcionario();

        when(funcionarioRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Funcionario> response = funcionarioService.update(id, funcionario);

        assertFalse(response.isPresent());
    }

    @Test
    public void testDelete() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario 1");
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        doNothing().when(funcionarioRepository).deleteById(1L);

        ResponseEntity<?> responseEntity = funcionarioService.delete(1L);
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(funcionarioRepository, times(1)).findById(1L);
        verify(funcionarioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testChangeEquipe() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario 1");
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setDescricao("Equipe 1");
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(equipeService.findById(1L)).thenReturn(Optional.of(equipe));
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        ResponseEntity<?> responseEntity = funcionarioService.changeEquipe(1L, 1L);
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(funcionarioRepository, times(1)).findById(1L);
        verify(equipeService, times(1)).findById(1L);
        verify(funcionarioRepository, times(1)).save(funcionario);
    }
}
