package com.joao.service;

import com.joao.model.Equipe;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface EquipeService {
    List<Equipe> findAll();
    Optional<Equipe> findById(Long id);
    Equipe create(Equipe equipe);
    Optional<Equipe> update(Long id, Equipe equipe);
    ResponseEntity<?> delete(Long id);
}
