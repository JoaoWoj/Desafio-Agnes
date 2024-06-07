package com.joao.controller;

import com.joao.model.Funcionario;
import com.joao.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public List<Funcionario> findAll() {
        return funcionarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> findById(@PathVariable Long id) {
        return funcionarioService.findById(id).map(a -> ResponseEntity.ok().body(a)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipe/{id}")
    public ResponseEntity<?> findByEquipe(@PathVariable Long id) {
        return funcionarioService.findByEquipe(id);
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public ResponseEntity<?> create(@RequestBody Funcionario funcionario) {
        return funcionarioService.create(funcionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        return funcionarioService.update(id, funcionario).map(item -> ResponseEntity.ok().body(item))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return funcionarioService.delete(id);
    }

    @PatchMapping("/{id}/equipe/{equipeId}")
    public ResponseEntity<?> changeEquipe(@PathVariable Long id, @PathVariable Long equipeId) {
        return funcionarioService.changeEquipe(id, equipeId);
    }
    
}
