package com.joao.controller;

import com.joao.model.Projeto;
import com.joao.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequestMapping("/api/projeto")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    public List<Projeto> findAll() {
        return projetoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> findById(@PathVariable Long id) {
        return projetoService.findById(id).map(a -> ResponseEntity.ok().body(a)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> findByCliente(@PathVariable Long id) {
        return projetoService.findByCliente(id);
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public ResponseEntity<?> create(@RequestBody Projeto projeto) {
        return projetoService.create(projeto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projeto> update(@PathVariable Long id, @RequestBody Projeto projeto) {
        return projetoService.update(id, projeto).map(item -> ResponseEntity.ok().body(item))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(projetoService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
