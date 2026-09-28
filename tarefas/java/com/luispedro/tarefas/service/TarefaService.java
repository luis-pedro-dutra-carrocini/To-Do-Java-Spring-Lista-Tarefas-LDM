package com.luispedro.tarefas.service;

import com.luispedro.tarefas.model.entity.Tarefa;

public interface TarefaService {

	Tarefa criarTarefa(Tarefa tarefa);
	Tarefa alterarTarefa(Tarefa tarefa);
	void excluirTarefa(Long id);
	
}
