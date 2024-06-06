package com.joao.service;

import com.joao.model.Projeto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProjetoService {
    List<Projeto> findAll();
    Optional<Projeto> findById(Long id);
    ResponseEntity<?> findByCliente(Long id);
    ResponseEntity<?> create(Projeto projeto);
    Optional<Projeto> update(Long id, Projeto projeto);
    boolean delete(Long id);
}
