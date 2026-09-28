package com.luispedro.tarefas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luispedro.tarefas.exeption.RegraNegocioException;
import com.luispedro.tarefas.model.entity.Tarefa;
import com.luispedro.tarefas.model.enums.StatusTarefa;
import com.luispedro.tarefas.model.repository.TarefaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TarefaServiceTest {

    @Autowired
    TarefaService service;

    @Autowired
    TarefaRepository repository;

    @Test
    public void deveSalvarUmaTarefa() {
        // cenário
        repository.deleteAll();
        Tarefa tarefa = criarTarefa();

        // ação
        Tarefa tarefaSalva = service.criarTarefa(tarefa);

        // verificação
        assertThat(tarefaSalva).isNotNull();
        assertThat(tarefaSalva.getId()).isNotNull();
        assertThat(tarefaSalva.getNome()).isEqualTo("Tarefa 1");
        assertThat(tarefaSalva.getStatus()).isEqualTo(StatusTarefa.PENDENTE);
    }

    @Test
    public void deveAlterarUmaTarefa() {
        // cenário
        repository.deleteAll();
        Tarefa tarefa = criarTarefa();
        Tarefa tarefaSalva = service.criarTarefa(tarefa);

        // ação
        tarefaSalva.setNome("Tarefa Alterada");
        tarefaSalva.setStatus(StatusTarefa.EFETIVADO);
        tarefaSalva.setDataAtualizacao(LocalDate.now());
        Tarefa tarefaAlterada = service.alterarTarefa(tarefaSalva);

        // verificação
        assertThat(tarefaAlterada).isNotNull();
        assertThat(tarefaAlterada.getId()).isEqualTo(tarefaSalva.getId());
        assertThat(tarefaAlterada.getNome()).isEqualTo("Tarefa Alterada");
        assertThat(tarefaAlterada.getStatus()).isEqualTo(StatusTarefa.EFETIVADO);
    }

    @Test
    public void deveExcluirUmaTarefa() {
        // cenário
        repository.deleteAll();
        Tarefa tarefa = criarTarefa();
        Tarefa tarefaSalva = service.criarTarefa(tarefa);
        Long idSalvo = tarefaSalva.getId();

        assertThat(repository.findById(idSalvo)).isPresent();

        // ação
        service.excluirTarefa(idSalvo);

        // verificação
        Optional<Tarefa> tarefaExcluida = repository.findById(idSalvo);
        assertThat(tarefaExcluida).isEmpty();
        assertThat(repository.existsById(idSalvo)).isFalse();
    }

    @Test
    public void deveBuscarTarefaPorId() {
        // cenário
        repository.deleteAll();
        Tarefa tarefa = criarTarefa();
        Tarefa tarefaSalva = service.criarTarefa(tarefa);

        // ação
        Optional<Tarefa> tarefaEncontrada = service.buscarPorId(tarefaSalva.getId());

        // verificação
        assertThat(tarefaEncontrada).isPresent();
        assertThat(tarefaEncontrada.get().getNome()).isEqualTo("Tarefa 1");
        assertThat(tarefaEncontrada.get().getStatus()).isEqualTo(StatusTarefa.PENDENTE);
    }

    @Test
    public void deveListarTodasAsTarefas() {
        // cenário
        repository.deleteAll();
        Tarefa tarefa1 = criarTarefa();
        service.criarTarefa(tarefa1);

        Tarefa tarefa2 = criarTarefa();
        tarefa2.setNome("Tarefa 2");
        service.criarTarefa(tarefa2);

        // ação
        List<Tarefa> tarefas = service.listarTodas();

        // verificação
        assertThat(tarefas).isNotEmpty();
        assertThat(tarefas.size()).isEqualTo(2);
    }

    @Test
    public void deveLancarErroAoCriarTarefaComNomeVazio() {
        // cenário
        repository.deleteAll();
        Tarefa tarefa = criarTarefa();
        tarefa.setNome("");

        // ação e verificação
        Assertions.assertThrows(RegraNegocioException.class, () -> service.criarTarefa(tarefa));
    }

    @Test
    public void deveLancarErroAoAlterarTarefaSemId() {
        // cenário
        repository.deleteAll();
        Tarefa tarefa = criarTarefa();
        // não salvamos, então o id está null

        // ação e verificação
        Assertions.assertThrows(RegraNegocioException.class, () -> service.alterarTarefa(tarefa));
    }

    @Test
    public void deveLancarErroAoExcluirTarefaComIdInexistente() {
        // cenário
        repository.deleteAll();
        Long idInexistente = 999L;

        // ação e verificação
        Assertions.assertThrows(RegraNegocioException.class, () -> service.excluirTarefa(idInexistente));
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
