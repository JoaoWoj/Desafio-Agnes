package com.joao.service.impl;

import com.joao.model.Funcionario;
import com.joao.model.Equipe;
import com.joao.repository.FuncionarioRepository;
import com.joao.service.AtividadeService;
import com.joao.service.FuncionarioService;
import com.joao.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    FuncionarioRepository repository;

    @Autowired
    EquipeService equipeService;

    @Override
    public List<Funcionario> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<?> findByEquipe(Long id) {
        Optional<Equipe> equipe = equipeService.findById(id);
        if(!equipe.isEmpty() && equipe.isPresent()){
            return ResponseEntity.ok().body(repository.findByEquipe(equipe.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipe não encontrado!");
    }

    @Override
    public List<Funcionario> listByEquipe(Long id) {
        Optional<Equipe> equipe = equipeService.findById(id);
        if(!equipe.isEmpty() && equipe.isPresent()){
            return repository.findByEquipe(equipe.get());
        }
        return null;
    }

    @Override
    public ResponseEntity<?> create(Funcionario funcionario) {
        if(funcionario.getEquipe() != null) {
            Optional<Equipe> equipe = equipeService.findById(funcionario.getEquipe().getId());
            if (!equipe.isEmpty() && equipe.isPresent()) {
                if (funcionario.getDataCadastro() == null) {
                    funcionario.setDataCadastro(new Date());
                }
                return ResponseEntity.ok().body(repository.save(funcionario));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipe não encontrada!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("É nescessário informar a Equipe!");
    }

    @Override
    public Optional<Funcionario> update(Long id, Funcionario funcionario) {
        Optional<Funcionario> item =  repository.findById(id);
        if(!item.isEmpty() && item.isPresent()) {
            Funcionario recordFound = item.get();
            recordFound.setNome(funcionario.getNome());
            recordFound.setFuncao(funcionario.getFuncao());
            if(funcionario.getEquipe() != null) {
                Optional<Equipe> equipe = equipeService.findById(funcionario.getEquipe().getId());
                if (!equipe.isEmpty() && equipe.isPresent()){
                    recordFound.setEquipe(equipe.get());
                }
            }
            return Optional.of(repository.save(recordFound));
        }
        return Optional.empty();
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return repository.findById(id)
                .map(item ->{
                    repository.deleteById(id);
                    return ResponseEntity.ok().body("Excluído com sucesso!");
                }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> changeEquipe(Long id, Long equipeId) {
        Optional<Equipe> equipe = equipeService.findById(equipeId);
        if(!equipe.isEmpty() && equipe.isPresent()){
            return repository.findById(id).map(item ->{
                item.setEquipe(equipe.get());
                return ResponseEntity.ok().body("Excluído com sucesso!");
            }).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não é possível mudar o equipe, pois a equipe não foi encontrada!");
    }
}
