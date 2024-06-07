package com.joao.controller;

import com.joao.model.Atividade;
import com.joao.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/atividade")
public class AtividadeController {

    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) {
        this.atividadeService = atividadeService;
    }

    @GetMapping
    public List<Atividade> findAll() {
        return atividadeService.findAll();
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public ResponseEntity<?> create(@RequestBody Atividade atividade) {
        return atividadeService.create(atividade);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividade> findById(@PathVariable Long id) {
        return atividadeService.findById(id).map(a -> ResponseEntity.ok().body(a)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/responsavel/{id}")
    public ResponseEntity<?> findByResponsavel(@PathVariable Long id) {
        return atividadeService.findByResponsavel(id);

    }

    @GetMapping("/projeto/{id}")
    public ResponseEntity<?> findByProjeto(@PathVariable Long id) {
        return atividadeService.findByProjeto(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atividade> update(@PathVariable Long id, @RequestBody Atividade atividade) {
        return atividadeService.update(id, atividade).map(item -> ResponseEntity.ok().body(item))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(atividadeService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @PathVariable String status) {
        if(atividadeService.changeStatus(id, status)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
