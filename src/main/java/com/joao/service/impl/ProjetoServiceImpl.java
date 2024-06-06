package com.joao.service.impl;

import com.joao.model.Atividade;
import com.joao.model.Cliente;
import com.joao.model.Projeto;
import com.joao.repository.ProjetoRepository;
import com.joao.service.AtividadeService;
import com.joao.service.ClienteService;
import com.joao.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    @Autowired
    private ProjetoRepository repository;

    @Autowired
    private ClienteService clienteService;

    @Override
    public List<Projeto> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Projeto> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<?> findByCliente(Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        if(!cliente.isEmpty() && cliente.isPresent()){
            return ResponseEntity.ok().body(repository.findByCliente(cliente.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
    }

    @Override
    public ResponseEntity<?> create(Projeto projeto) {
        if(projeto.getCliente() != null) {
            Optional<Cliente> cliente = clienteService.findById(projeto.getCliente().getId());
            if (!cliente.isEmpty() && cliente.isPresent()) {
                if (projeto.getDataCriacao() == null) {
                    projeto.setDataCriacao(new Date());
                }
                return ResponseEntity.ok().body(repository.save(projeto));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("É nescessário informar o Cliente!");
    }

    @Override
    public Optional<Projeto> update(Long id, Projeto projeto) {
        Optional<Projeto> item =  repository.findById(id);
        if(!item.isEmpty() && item.isPresent()) {
            Projeto recordFound = item.get();
            recordFound.setNome(projeto.getNome());
            recordFound.setDescricao(projeto.getDescricao());
            if(projeto.getCliente() != null) {
                Optional<Cliente> cliente = clienteService.findById(projeto.getCliente().getId());
                if (!cliente.isEmpty() && cliente.isPresent()){
                    recordFound.setCliente(cliente.get());
                }
            }
            return Optional.of(repository.save(recordFound));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(item ->{
                    repository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
