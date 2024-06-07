package com.joao.service.impl;

import com.joao.model.Atividade;
import com.joao.model.Cliente;
import com.joao.model.Funcionario;
import com.joao.model.Projeto;
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
        return repository.findAll();
    }

    @Override
    public Optional<Atividade> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<?> findByResponsavel(Long id) {
        Optional<Funcionario> funcionario = funcionarioService.findById(id);
        if(!funcionario.isEmpty() && funcionario.isPresent()){
            return ResponseEntity.ok().body(repository.findByResponsavel(funcionario.get()));
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
        Optional<Projeto> projeto = projetoService.findById(id);
        if(!projeto.isEmpty() && projeto.isPresent()){
            return ResponseEntity.ok().body(repository.findByProjeto(projeto.get()));
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
        if(atividade.getProjeto() != null && atividade.getResponsavel() != null){
            Optional<Projeto> projeto = projetoService.findById(atividade.getProjeto().getId());
            Optional<Funcionario> funcionario = funcionarioService.findById(atividade.getProjeto().getId());
            if (projeto.isEmpty() || !projeto.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado!");
            } else if (funcionario.isEmpty() || !funcionario.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsável não encontrado!");
            } else {
                if (atividade.getDataCriacaoAtividade() == null) {
                    atividade.setDataCriacaoAtividade(new Date());
                }
                return ResponseEntity.ok().body(repository.save(atividade));
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
            return Optional.of(repository.save(recordFound));
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
        return repository.findById(id).map(item ->{
            if(status.equals(StatusEnum.CONCLUIDO)){
                item.setDataFimAtividade(new Date());
            }
            item.setStatus(StatusEnum.valueOf(status));
            return true;
        }).orElse(false);
    }
}
