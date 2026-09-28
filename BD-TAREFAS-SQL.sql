CREATE SCHEMA tarefas;


CREATE TABLE tarefas.tarefa (
id bigserial NOT NULL PRIMARY KEY,
nome character varying(150),
descricao character varying(500),
status character varying(20),
observacao character varying(250),
data_criacao date DEFAULT NOW(),
data_atualizacao date
);


SELECT * FROM tarefas.tarefa;

