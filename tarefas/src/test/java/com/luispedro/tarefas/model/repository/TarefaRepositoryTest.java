package com.luispedro.tarefas.model.repository;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.junit.jupiter.api.MethodOrderer;
import com.luispedro.tarefas.model.entity.Tarefa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.luispedro.tarefas.model.enums.StatusTarefa;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
//@SpringBootTest
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TarefaRepositoryTest {

	@Autowired
	TarefaRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	@Order(1)
	public void deveCadastrarUmaNovaTarefa() {
	    // cenário
	    Tarefa tarefa = criarTarefa();

	    // ação
	    Tarefa tarefaSalva = repository.save(tarefa);

	    // verificação
	    Assertions.assertThat(tarefaSalva).isNotNull();
	    Assertions.assertThat(tarefaSalva.getId()).isNotNull();
	    Assertions.assertThat(tarefaSalva.getNome()).isEqualTo("Tarefa 1");
	    Assertions.assertThat(tarefaSalva.getStatus()).isEqualTo(StatusTarefa.PENDENTE);
	    Assertions.assertThat(repository.findById(tarefaSalva.getId())).isPresent();
	}
	
	@Test
	@Order(2)
	public void deveAlterarUmaTarefa() {
	    // cenário
	    Tarefa tarefa = criarTarefa();
	    Tarefa tarefaSalva = repository.save(tarefa);
	    Long idSalvo = tarefaSalva.getId();

	    // ação
	    tarefaSalva.setNome("Tarefa Alterada");
	    tarefaSalva.setDescricao("Descrição alterada no teste");
	    tarefaSalva.setObservacao("Observação alterada");
	    tarefaSalva.setStatus(StatusTarefa.EFETIVADO);
	    tarefaSalva.setDataAtualizacao(LocalDate.now());

	    Tarefa tarefaAlterada = repository.save(tarefaSalva);

	    // verificação
	    Assertions.assertThat(tarefaAlterada).isNotNull();
	    Assertions.assertThat(tarefaAlterada.getId()).isEqualTo(idSalvo);
	    Assertions.assertThat(tarefaAlterada.getNome()).isEqualTo("Tarefa Alterada");
	    Assertions.assertThat(tarefaAlterada.getDescricao()).isEqualTo("Descrição alterada no teste");
	    Assertions.assertThat(tarefaAlterada.getObservacao()).isEqualTo("Observação alterada");
	    Assertions.assertThat(tarefaAlterada.getStatus()).isEqualTo(StatusTarefa.EFETIVADO);

	    // Confirma que a alteração foi persistida no banco
	    Optional<Tarefa> tarefaDoBanco = repository.findById(idSalvo);
	    Assertions.assertThat(tarefaDoBanco).isPresent();
	    Assertions.assertThat(tarefaDoBanco.get().getNome()).isEqualTo("Tarefa Alterada");
	    Assertions.assertThat(tarefaDoBanco.get().getStatus()).isEqualTo(StatusTarefa.EFETIVADO);
	}

	@Test
	@Order(3)
	public void deveExcluirUmaTarefa() {
	    // cenário
	    Tarefa tarefa = criarTarefa();
	    Tarefa tarefaSalva = repository.save(tarefa);
	    Long idSalvo = tarefaSalva.getId();

	    // Confirma que a tarefa existe antes de excluir
	    Assertions.assertThat(repository.findById(idSalvo)).isPresent();

	    // ação
	    repository.deleteById(idSalvo);

	    // verificação
	    Optional<Tarefa> tarefaExcluida = repository.findById(idSalvo);
	    Assertions.assertThat(tarefaExcluida).isEmpty();
	    Assertions.assertThat(repository.existsById(idSalvo)).isFalse();
	}

	@Test
	@Order(4)
	public void deveBuscarTarefaPorId() {
	    // cenário
	    Tarefa tarefa = criarTarefa();
	    Tarefa tarefaSalva = repository.save(tarefa);
	    Long idSalvo = tarefaSalva.getId();

	    // ação
	    Optional<Tarefa> tarefaEncontrada = repository.findById(idSalvo);

	    // verificação
	    Assertions.assertThat(tarefaEncontrada).isPresent();
	    Assertions.assertThat(tarefaEncontrada.get().getId()).isEqualTo(idSalvo);
	    Assertions.assertThat(tarefaEncontrada.get().getNome()).isEqualTo("Tarefa 1");
	    Assertions.assertThat(tarefaEncontrada.get().getStatus()).isEqualTo(StatusTarefa.PENDENTE);
	}

	@Test
	@Order(5)
	public void deveListarTodasAsTarefas() {
	    // cenário
	    Tarefa tarefa1 = criarTarefa();
	    repository.save(tarefa1);

	    Tarefa tarefa2 = criarTarefa();
	    tarefa2.setNome("Tarefa 2");
	    repository.save(tarefa2);

	    // ação
	    List<Tarefa> tarefas = repository.findAll();

	    // verificação
	    Assertions.assertThat(tarefas).isNotEmpty();
	    Assertions.assertThat(tarefas.size()).isGreaterThanOrEqualTo(2);
	}
	
	
	public static Tarefa criarTarefa() {
	    return Tarefa.builder()
	            .nome("Tarefa 1")
	            .descricao("Criar um novo projeto em Java com Spring, com BD em PostgreSQL.")
	            .observacao("Deve ser entregue o link do GitHub")
	            .status(StatusTarefa.PENDENTE)
	            .dataCriacao(LocalDate.now())
	            .dataAtualizacao(LocalDate.now())
	            .build();
	}
	
}
