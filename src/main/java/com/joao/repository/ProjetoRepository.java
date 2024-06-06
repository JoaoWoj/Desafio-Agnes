package com.joao.repository;

import com.joao.model.Cliente;
import com.joao.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByCliente(Cliente cliente);

}
