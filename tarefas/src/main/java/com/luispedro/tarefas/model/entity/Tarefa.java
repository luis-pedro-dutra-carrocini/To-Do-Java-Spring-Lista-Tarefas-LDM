package com.luispedro.tarefas.model.entity;

import java.time.LocalDate;

import com.luispedro.tarefas.model.enums.StatusTarefa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name = "tarefa", schema = "tarefas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusTarefa status;

}
