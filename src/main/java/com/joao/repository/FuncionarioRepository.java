package com.joao.repository;

import com.joao.model.Funcionario;
import com.joao.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByEquipe(Equipe equipe);

}
