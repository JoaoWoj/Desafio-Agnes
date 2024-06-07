package com.joao.service.impl;

import com.joao.model.Atividade;
import com.joao.model.Funcionario;
import com.joao.model.DTO.FuncionarioDTO;
import com.joao.model.Projeto;
import com.joao.model.DTO.ProjetoDTO;
import com.joao.model.enumerators.StatusEnum;
import com.joao.repository.AtividadeRepository;
import com.joao.service.FuncionarioService;
import com.joao.service.ProjetoService;
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

public class AtividadeServiceImplTest {

    @InjectMocks
    private AtividadeServiceImpl atividadeService;

    @Mock
    private AtividadeRepository atividadeRepository;

    @Mock
    private FuncionarioService funcionarioService;

    @Mock
    private ProjetoService projetoService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("Atividade 1");
        when(atividadeRepository.findAll()).thenReturn(Arrays.asList(atividade));

        assertEquals(1, atividadeService.findAll().size());
        verify(atividadeRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("Atividade 1");
        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));

        Optional<Atividade> atividadeOptional = atividadeService.findById(1L);
        assertEquals("Atividade 1", atividadeOptional.get().getDescricao());
        verify(atividadeRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByResponsavel() {
        Long id = 1L;
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome("Nome do Funcionário");
        funcionario.setFuncao("Função do Funcionário");
        Atividade atividade = new Atividade();
        atividade.setResponsavel(funcionario);
        atividade.setFuncionarioDTO(new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getFuncao()));
        atividade.setProjetoDTO(new ProjetoDTO(1L, "Nome do Projeto", "Descrição do Projeto"));

        when(funcionarioService.findById(id)).thenReturn(Optional.of(funcionario));
        when(atividadeRepository.findByResponsavel(funcionario)).thenReturn(Arrays.asList(atividade));

        ResponseEntity<?> response = atividadeService.findByResponsavel(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(atividade), response.getBody());
    }

    @Test
    public void testFindByResponsavelNotFound() {
        Long id = 1L;

        when(funcionarioService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = atividadeService.findByResponsavel(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Funcionário não encontrado!", response.getBody());
    }

    @Test
    public void testListByResponsavel() {
        Long id = 1L;
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome("Nome do Funcionário");
        funcionario.setFuncao("Função do Funcionário");
        Atividade atividade = new Atividade();
        atividade.setResponsavel(funcionario);

        when(funcionarioService.findById(id)).thenReturn(Optional.of(funcionario));
        when(atividadeRepository.findByResponsavel(funcionario)).thenReturn(Arrays.asList(atividade));

        List<Atividade> response = atividadeService.listByResponsavel(id);

        assertEquals(Arrays.asList(atividade), response);
    }

    @Test
    public void testListByResponsavelNotFound() {
        Long id = 1L;

        when(funcionarioService.findById(id)).thenReturn(Optional.empty());

        List<Atividade> response = atividadeService.listByResponsavel(id);

        assertNull(response);
    }

    @Test
    public void testFindByProjeto() {
        Long id = 1L;
        Projeto projeto = new Projeto();
        projeto.setId(id);
        projeto.setNome("Nome do Projeto");
        projeto.setDescricao("Descrição do Projeto");
        Atividade atividade = new Atividade();
        atividade.setProjeto(projeto);
        atividade.setFuncionarioDTO(new FuncionarioDTO(1L, "Nome do Funcionário", "Função do Funcionário"));
        atividade.setProjetoDTO(new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao()));

        when(projetoService.findById(id)).thenReturn(Optional.of(projeto));
        when(atividadeRepository.findByProjeto(projeto)).thenReturn(Arrays.asList(atividade));

        ResponseEntity<?> response = atividadeService.findByProjeto(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(atividade), response.getBody());
    }

    @Test
    public void testFindByProjetoNotFound() {
        Long id = 1L;

        when(projetoService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = atividadeService.findByProjeto(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Projeto não encontrado!", response.getBody());
    }

    @Test
    public void testListByProjeto() {
        Long id = 1L;
        Projeto projeto = new Projeto();
        projeto.setId(id);
        projeto.setNome("Nome do Projeto");
        projeto.setDescricao("Descrição do Projeto");
        Atividade atividade = new Atividade();
        atividade.setProjeto(projeto);

        when(projetoService.findById(id)).thenReturn(Optional.of(projeto));
        when(atividadeRepository.findByProjeto(projeto)).thenReturn(Arrays.asList(atividade));

        List<Atividade> response = atividadeService.listByProjeto(id);

        assertEquals(Arrays.asList(atividade), response);
    }

    @Test
    public void testListByProjetoNotFound() {
        Long id = 1L;

        when(projetoService.findById(id)).thenReturn(Optional.empty());

        List<Atividade> response = atividadeService.listByProjeto(id);

        assertNull(response);
    }

    @Test
    public void testCreate() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("Atividade 1");
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setId(1L);
        atividade.setFuncionarioDTO(funcionarioDTO);
        ProjetoDTO projetoDTO = new ProjetoDTO();
        projetoDTO.setId(1L);
        atividade.setProjetoDTO(projetoDTO);
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario 1");
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto 1");
        when(funcionarioService.findById(1L)).thenReturn(Optional.of(funcionario));
        when(projetoService.findById(1L)).thenReturn(Optional.of(projeto));
        when(atividadeRepository.save(atividade)).thenReturn(atividade);

        ResponseEntity<?> responseEntity = atividadeService.create(atividade);
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(funcionarioService, times(1)).findById(1L);
        verify(projetoService, times(1)).findById(1L);
        verify(atividadeRepository, times(1)).save(atividade);
    }

    @Test
    public void testUpdate() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("Atividade 1");

        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setId(1L);
        atividade.setFuncionarioDTO(funcionarioDTO);

        ProjetoDTO projetoDTO = new ProjetoDTO();
        projetoDTO.setId(1L);
        atividade.setProjetoDTO(projetoDTO);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario 1");
        atividade.setResponsavel(funcionario);

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto 1");
        atividade.setProjeto(projeto);

        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));
        when(funcionarioService.findById(1L)).thenReturn(Optional.of(funcionario));
        when(projetoService.findById(1L)).thenReturn(Optional.of(projeto));
        when(atividadeRepository.save(atividade)).thenReturn(atividade);

        Optional<Atividade> atividadeOptional = atividadeService.update(1L, atividade);

        assertTrue(atividadeOptional.isPresent());
        assertEquals("Atividade 1", atividadeOptional.get().getDescricao());

    }

    @Test
    public void testUpdateSemResponsaveleProjeto() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("Atividade 1");

        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setId(1L);
        atividade.setFuncionarioDTO(funcionarioDTO);

        ProjetoDTO projetoDTO = new ProjetoDTO();
        projetoDTO.setId(1L);
        atividade.setProjetoDTO(projetoDTO);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario 1");

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto 1");

        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));
        when(funcionarioService.findById(1L)).thenReturn(Optional.of(funcionario));
        when(projetoService.findById(1L)).thenReturn(Optional.of(projeto));
        when(atividadeRepository.save(atividade)).thenReturn(atividade);

        Optional<Atividade> atividadeOptional = atividadeService.update(1L, atividade);

        assertTrue(atividadeOptional.isPresent());
        assertEquals("Atividade 1", atividadeOptional.get().getDescricao());

    }


    @Test
    public void testDelete() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("Atividade 1");
        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));
        doNothing().when(atividadeRepository).deleteById(1L);

        boolean isDeleted = atividadeService.delete(1L);
        assertTrue(isDeleted);
        verify(atividadeRepository, times(1)).findById(1L);
        verify(atividadeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testChangeStatus() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("Atividade 1");
        atividade.setStatus(StatusEnum.EM_ANDAMENTO);
        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));
        when(atividadeRepository.save(atividade)).thenReturn(atividade);

        boolean isStatusChanged = atividadeService.changeStatus(1L, "CONCLUIDO");
        assertTrue(isStatusChanged);
        verify(atividadeRepository, times(1)).findById(1L);
        verify(atividadeRepository, times(1)).save(atividade);
    }
}
