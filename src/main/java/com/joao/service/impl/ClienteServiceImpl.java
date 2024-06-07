package com.joao.service.impl;

import com.joao.model.Cliente;
import com.joao.repository.ClienteRepository;
import com.joao.service.ClienteService;
import com.joao.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ProjetoService projetoService;

    @Override
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Cliente create(Cliente cliente) {
        if (cliente.getDataCriacao() == null){
            cliente.setDataCriacao(new Date());
        }
        return repository.save(cliente);
    }

    @Override
    public Optional<Cliente> update(Long id, Cliente cliente) {
        Optional<Cliente> item =  repository.findById(id);
        if(!item.isEmpty() && item.isPresent()) {
            Cliente recordFound = item.get();
            recordFound.setNome(cliente.getNome());
            recordFound.setEndereco(cliente.getEndereco());
            return Optional.of(repository.save(recordFound));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(item ->{
                    if(!item.getProjetos().isEmpty()) {
                        item.getProjetos().forEach(projeto -> {
                            projetoService.delete(projeto.getId());
                        });
                    }
                    repository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
