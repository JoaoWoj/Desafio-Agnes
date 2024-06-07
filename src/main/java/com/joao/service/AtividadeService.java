package com.joao.service;

import com.joao.model.Atividade;
import com.joao.model.Funcionario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AtividadeService {

    List<Atividade> findAll();
    Optional<Atividade> findById(Long id);
    ResponseEntity<?> findByResponsavel(Long id);
    List<Atividade> listByResponsavel(Long id);
    ResponseEntity<?> findByProjeto(Long id);
    List<Atividade> listByProjeto(Long id);
    ResponseEntity<?> create(Atividade atividade);
    Optional<Atividade> update(Long id, Atividade atividade);
    boolean delete(Long id);
    boolean changeStatus(Long id, String status);

}
