package br.com.will.gestao.dao;

import java.io.Serializable;
import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.Descritivel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public interface InterfaceDAO<T> extends Serializable {

	T salvar(final T t);

	T alterar(final T t);

	void deletar(final T t, final Integer id);

	List<T> consultarTodos();

	List<T> consultarTodos(String nomeColuna);

	int consultarQuantidade();

	T consultarPorId(final int id);

	<K extends Descritivel> T consultarPorDescricao(final String descricao);

	List<? extends SituacaoAlteravel> consultarPorSituacao(final ESituacao situacao);

	void verificarDuplicidade(final T t) throws ViolacaoDeConstraintException;

	Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel);
}
