package com.joao.service;

import com.joao.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);
    Cliente create(Cliente cliente);
    Optional<Cliente> update(Long id, Cliente cliente);
    boolean delete(Long id);
}
