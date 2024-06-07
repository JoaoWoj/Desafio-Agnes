package com.joao.service.impl;

import com.joao.model.*;
import com.joao.model.DTO.FuncionarioDTO;
import com.joao.model.DTO.ProjetoDTO;
import com.joao.model.enumerators.StatusEnum;
import com.joao.repository.AtividadeRepository;
import com.joao.service.AtividadeService;
import com.joao.service.FuncionarioService;
import com.joao.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AtividadeServiceImpl implements AtividadeService {

    @Autowired
    private AtividadeRepository repository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ProjetoService projetoService;

    @Override
    public List<Atividade> findAll() {
        List<Atividade> listAtividades = repository.findAll();
        listAtividades.forEach(item ->{
            Funcionario funcionario = item.getResponsavel();
            if(funcionario != null) {
                item.setFuncionarioDTO(new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getFuncao()));
            }
            Projeto projeto = item.getProjeto();
            if(projeto != null) {
                item.setProjetoDTO(new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao()));
            }
        });
        return listAtividades;
    }

    @Override
    public Optional<Atividade> findById(Long id) {
        Optional<Atividade> atividadeOptional = repository.findById(id);
        if(!atividadeOptional.isEmpty() && atividadeOptional.isPresent()){
            Funcionario funcionario = atividadeOptional.get().getResponsavel();
            if(funcionario != null) {
                atividadeOptional.get().setFuncionarioDTO(new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getFuncao()));
            }
            Projeto projeto = atividadeOptional.get().getProjeto();

            if(projeto != null) {
                atividadeOptional.get().setProjetoDTO(new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao()));
            }
        }
        return atividadeOptional;
    }

    @Override
    public ResponseEntity<?> findByResponsavel(Long id) {
        Optional<Funcionario> responsavel = funcionarioService.findById(id);
        if(!responsavel.isEmpty() && responsavel.isPresent()){
            List<Atividade> listAtividades = repository.findByResponsavel(responsavel.get());
            listAtividades.forEach(item ->{
                Funcionario funcionario = item.getResponsavel();
                if(funcionario != null) {
                    item.setFuncionarioDTO(new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getFuncao()));
                }
                Projeto projeto = item.getProjeto();
                if(projeto != null) {
                    item.setProjetoDTO(new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao()));
                }
            });
            return ResponseEntity.ok().body(listAtividades);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado!");
    }

    @Override
    public List<Atividade> listByResponsavel(Long id) {
        Optional<Funcionario> funcionario = funcionarioService.findById(id);
        if(!funcionario.isEmpty() && funcionario.isPresent()){
            return repository.findByResponsavel(funcionario.get());
        }
        return null;
    }

    @Override
    public ResponseEntity<?> findByProjeto(Long id) {
        Optional<Projeto> loadedProjeto = projetoService.findById(id);
        if(!loadedProjeto.isEmpty() && loadedProjeto.isPresent()){
            List<Atividade> listAtividades = repository.findByProjeto(loadedProjeto.get());
            listAtividades.forEach(item ->{
                Funcionario funcionario = item.getResponsavel();
                if(funcionario != null) {
                    item.setFuncionarioDTO(new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getFuncao()));
                }
                Projeto projeto = item.getProjeto();
                if(projeto != null) {
                item.setProjetoDTO(new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao()));
                }
            });
            return ResponseEntity.ok().body(listAtividades);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado!");
    }

    @Override
    public List<Atividade> listByProjeto(Long id) {
        Optional<Projeto> projeto = projetoService.findById(id);
        if(!projeto.isEmpty() && projeto.isPresent()){
            return repository.findByProjeto(projeto.get());
        }
        return null;
    }

    @Override
    public ResponseEntity<?> create(Atividade atividade) {
        if(atividade.getProjetoDTO() != null && atividade.getFuncionarioDTO() != null){
            Optional<Projeto> loadedProjeto = projetoService.findById(atividade.getProjetoDTO().getId());
            Optional<Funcionario> loadedResponsavel = funcionarioService.findById(atividade.getFuncionarioDTO().getId());
            if (loadedProjeto.isEmpty() || !loadedProjeto.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado!");
            } else if (loadedResponsavel.isEmpty() || !loadedResponsavel.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsável não encontrado!");
            } else {
                if (atividade.getDataCriacaoAtividade() == null) {
                    atividade.setDataCriacaoAtividade(new Date());
                }
                atividade.setResponsavel(loadedResponsavel.get());
                atividade.setProjeto(loadedProjeto.get());
                Atividade atividadeSalva = repository.save(atividade);
                Funcionario funcionario = atividadeSalva.getResponsavel();
                atividadeSalva.setFuncionarioDTO(new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getFuncao()));
                Projeto projeto = atividadeSalva.getProjeto();
                atividadeSalva.setProjetoDTO(new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao()));

                return ResponseEntity.ok().body(atividadeSalva);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("É nescessário informar o Projeto e o Responsável!");
    }

    @Override
    public Optional<Atividade> update(Long id, Atividade atividade) {
        Optional<Atividade> item =  repository.findById(id);
        if(!item.isEmpty() && item.isPresent()) {
            Atividade recordFound = item.get();
            recordFound.setDescricao(atividade.getDescricao());
            recordFound.setStatus(atividade.getStatus());
            recordFound.setDataInicioAtividade(atividade.getDataInicioAtividade());
            if(atividade.getProjeto() != null) {
                Optional<Projeto> projeto = projetoService.findById(atividade.getProjeto().getId());
                if (projeto.isPresent()){
                    recordFound.setProjeto(projeto.get());
                }
            }
            if(atividade.getResponsavel() != null) {
                Optional<Funcionario> funcionario = funcionarioService.findById(atividade.getResponsavel().getId());
                if (funcionario.isPresent()){
                    recordFound.setResponsavel(funcionario.get());
                }
            }
            Atividade retorno = repository.save(recordFound);
            Funcionario funcionario = retorno.getResponsavel();
            if(funcionario != null) {
                retorno.setFuncionarioDTO(new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getFuncao()));
            }
            Projeto projeto = retorno.getProjeto();
            if(projeto != null) {
                retorno.setProjetoDTO(new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao()));
            }

            return Optional.of(retorno);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(item ->{
                    repository.deleteById(id);
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean changeStatus(Long id, String status) {
        StatusEnum statusEnum = StatusEnum.fromValue(status);
        return repository.findById(id).map(item ->{
            item.setStatus(statusEnum);
            repository.save(item);
            return true;
        }).orElse(false);
    }
}
