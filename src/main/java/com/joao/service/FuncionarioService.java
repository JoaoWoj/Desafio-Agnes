package com.joao.service;

import com.joao.model.Funcionario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface FuncionarioService {

    List<Funcionario> findAll();
    Optional<Funcionario> findById(Long id);
    ResponseEntity<?> findByEquipe(Long id);
    List<Funcionario> listByEquipe(Long id);
    ResponseEntity<?> create(Funcionario funcionario);
    Optional<Funcionario> update(Long id, Funcionario funcionario);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> changeEquipe(Long id, Long equipeId);
    
}
