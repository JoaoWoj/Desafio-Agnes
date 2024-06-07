package com.joao.controller;

import com.joao.model.Equipe;
import com.joao.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @GetMapping
    public List<Equipe> findAll() {
        return equipeService.findAll();
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public Equipe create(@RequestBody Equipe equipe) {
        return equipeService.create(equipe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> findById(@PathVariable Long id) {
        return equipeService.findById(id).map(a -> ResponseEntity.ok().body(a)).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Equipe> update(@PathVariable Long id, @RequestBody Equipe equipe) {
        return equipeService.update(id, equipe).map(item -> ResponseEntity.ok().body(item))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return equipeService.delete(id);
    }
}
