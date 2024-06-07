package com.joao.service.impl;

import com.joao.model.Atividade;
import com.joao.model.Cliente;
import com.joao.model.DTO.ClienteDTO;
import com.joao.model.Projeto;
import com.joao.repository.AtividadeRepository;
import com.joao.repository.ClienteRepository;
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
    private ClienteRepository clienteRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Override
    public List<Projeto> findAll() {
        List<Projeto> listProjetos = repository.findAll();
        listProjetos.forEach(item -> {
            Cliente cliente = item.getCliente();
            item.setClienteDTO(new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEndereco()));
        });
        return listProjetos;
    }

    @Override
    public Optional<Projeto> findById(Long id) {
        Optional<Projeto> projetoOptional = repository.findById(id);
        if(!projetoOptional.isEmpty() && projetoOptional.isPresent()) {
            Cliente cliente = projetoOptional.get().getCliente();
            projetoOptional.get().setClienteDTO(new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEndereco()));
        }
        return projetoOptional;
    }

    @Override
    public ResponseEntity<?> findByCliente(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(!cliente.isEmpty() && cliente.isPresent()){
            return ResponseEntity.ok().body(repository.findByCliente(cliente.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
    }

    @Override
    public ResponseEntity<?> create(Projeto projeto) {
        if(projeto.getClienteDTO() != null) {
            Optional<Cliente> cliente = clienteRepository.findById(projeto.getClienteDTO().getId());
            if (!cliente.isEmpty() && cliente.isPresent()) {
                if (projeto.getDataCriacao() == null) {
                    projeto.setDataCriacao(new Date());
                }
                projeto.setCliente(cliente.get());
                Projeto projetoSalvo = repository.save(projeto);
                ClienteDTO clienteCarregado = new ClienteDTO(cliente.get().getId(), cliente.get().getNome(), cliente.get().getEndereco());
                projetoSalvo.setClienteDTO(clienteCarregado);
                return ResponseEntity.ok().body(projetoSalvo);
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
                Optional<Cliente> cliente = clienteRepository.findById(projeto.getCliente().getId());
                if (!cliente.isEmpty() && cliente.isPresent()){
                    recordFound.setCliente(cliente.get());
                }
            }
            Projeto retorno = repository.save(recordFound);
            retorno.setClienteDTO(new ClienteDTO(recordFound.getCliente().getId(), recordFound.getCliente().getNome(),recordFound.getCliente().getEndereco()));
            return Optional.of(retorno);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(item ->{
                    atividadeRepository.deleteByProjeto(item);
                    repository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
