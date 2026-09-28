package com.luispedro.tarefas.service;

import java.util.List;
import java.util.Optional;

import com.luispedro.tarefas.model.entity.Tarefa;

public interface TarefaService {

	Tarefa criarTarefa(Tarefa tarefa);
	Tarefa alterarTarefa(Tarefa tarefa);
	void excluirTarefa(Long id);
	Optional<Tarefa> buscarPorId(Long id);
    List<Tarefa> listarTodas();
	
}
