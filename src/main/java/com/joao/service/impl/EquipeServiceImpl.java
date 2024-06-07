package com.joao.service.impl;

import com.joao.model.Equipe;
import com.joao.repository.EquipeRepository;
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
public class EquipeServiceImpl implements EquipeService {
    @Autowired
    EquipeRepository repository;

    @Override
    public List<Equipe> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Equipe> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Equipe create(Equipe equipe) {
        if (equipe.getDataCriacao() == null){
            equipe.setDataCriacao(new Date());
        }
        return repository.save(equipe);
    }

    @Override
    public Optional<Equipe> update(Long id, Equipe equipe) {
        Optional<Equipe> item =  repository.findById(id);
        if(!item.isEmpty() && item.isPresent()) {
            Equipe recordFound = item.get();
            recordFound.setSetor(equipe.getSetor());
            recordFound.setDescricao(equipe.getDescricao());
            return Optional.of(repository.save(recordFound));
        }
        return Optional.empty();
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return repository.findById(id)
                .map(item ->{
                    if(!item.getFuncionarios().isEmpty()){
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Não é possível excluir Equipe, enquanto existem funcionários cadastrados a ele!");
                    }
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
