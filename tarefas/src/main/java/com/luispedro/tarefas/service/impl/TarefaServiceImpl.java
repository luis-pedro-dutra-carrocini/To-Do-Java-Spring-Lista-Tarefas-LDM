package com.luispedro.tarefas.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luispedro.tarefas.model.entity.Tarefa;
import com.luispedro.tarefas.model.repository.TarefaRepository;
import com.luispedro.tarefas.service.TarefaService;

@Service
public class TarefaServiceImpl implements TarefaService{
	
	private TarefaRepository repository;

	@Autowired
	public TarefaServiceImpl(TarefaRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Tarefa criarTarefa(Tarefa tarefa) {
		return repository.save(tarefa);
	}
	
	@Override
	public Tarefa alterarTarefa(Tarefa tarefa) {
		return repository.save(tarefa);
	}
	
	 @Override
    public void excluirTarefa(Long id) {
        repository.deleteById(id);
    }
	 
	 @Override
    public Optional<Tarefa> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Tarefa> listarTodas() {
        return repository.findAll();
    }

}
