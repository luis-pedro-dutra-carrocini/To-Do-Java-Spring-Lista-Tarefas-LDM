package com.luispedro.tarefas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispedro.tarefas.model.entity.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	
}