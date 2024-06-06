package com.joao.repository;

import com.joao.model.Atividade;
import com.joao.model.Funcionario;
import com.joao.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    List<Atividade> findByResponsavel(Funcionario responsavel);
    List<Atividade> findByProjeto(Projeto projeto);

}
