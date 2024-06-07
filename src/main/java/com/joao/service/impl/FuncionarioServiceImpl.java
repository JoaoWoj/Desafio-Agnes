package com.joao.service.impl;

import com.joao.model.Cliente;
import com.joao.model.DTO.ClienteDTO;
import com.joao.model.DTO.EquipeDTO;
import com.joao.model.DTO.FuncionarioDTO;
import com.joao.model.Funcionario;
import com.joao.model.Equipe;
import com.joao.model.Projeto;
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
        List<Funcionario> listFuncionarios = repository.findAll();
        listFuncionarios.forEach(item ->{
            Equipe equipe = item.getEquipe();
            if(equipe != null) {
                item.setEquipeDTO(new EquipeDTO(equipe.getId(), equipe.getSetor(), equipe.getDescricao()));
            }
        });
        return listFuncionarios;
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        Optional<Funcionario> funcionarioOptional = repository.findById(id);
        if(!funcionarioOptional.isEmpty() && funcionarioOptional.isPresent()) {
            Equipe equipe = funcionarioOptional.get().getEquipe();
            if(equipe != null) {
                funcionarioOptional.get().setEquipeDTO(new EquipeDTO(equipe.getId(), equipe.getSetor(), equipe.getDescricao()));
            }
        }
        return funcionarioOptional;
    }

    @Override
    public ResponseEntity<?> findByEquipe(Long id) {
        Optional<Equipe> equipe = equipeService.findById(id);
        if(!equipe.isEmpty() && equipe.isPresent()){
            List<Funcionario> listFuncionario = repository.findByEquipe(equipe.get());
            listFuncionario.forEach(item ->{
                item.setEquipeDTO(new EquipeDTO(equipe.get().getId(), equipe.get().getSetor(), equipe.get().getDescricao()));
            });
            return ResponseEntity.ok().body(listFuncionario);
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
        if(funcionario.getEquipeDTO() != null) {
            Optional<Equipe> equipe = equipeService.findById(funcionario.getEquipeDTO().getId());
            if (!equipe.isEmpty() && equipe.isPresent()) {
                if (funcionario.getDataCadastro() == null) {
                    funcionario.setDataCadastro(new Date());
                }
                funcionario.setEquipe(equipe.get());
                Funcionario funcionarioSalvo = repository.save(funcionario);
                EquipeDTO equipecarregada = new EquipeDTO(equipe.get().getId(), equipe.get().getSetor(), equipe.get().getDescricao());
                funcionarioSalvo.setEquipeDTO(equipecarregada);
                return ResponseEntity.ok().body(funcionarioSalvo);
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
            if(funcionario.getEquipeDTO() != null) {
                Optional<Equipe> equipe = equipeService.findById(funcionario.getEquipeDTO().getId());
                if (!equipe.isEmpty() && equipe.isPresent()){
                    recordFound.setEquipe(equipe.get());
                }
            }
            Funcionario retorno = repository.save(recordFound);
            retorno.setEquipeDTO(new EquipeDTO(recordFound.getEquipe().getId(), recordFound.getEquipe().getSetor(),recordFound.getEquipe().getDescricao()));
            return Optional.of(retorno);
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
                repository.save(item);
                return ResponseEntity.ok().body("Equipe alterada com sucesso!");
            }).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não é possível mudar o equipe, pois a equipe não foi encontrada!");
    }
}
