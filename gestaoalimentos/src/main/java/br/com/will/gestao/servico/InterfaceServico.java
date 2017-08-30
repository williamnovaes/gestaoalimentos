package br.com.will.gestao.servico;

import java.io.Serializable;
import java.util.List;

import br.com.will.gestao.entidade.util.Descritivel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.servico.exception.BaseServicoException;

public interface InterfaceServico<T> extends Serializable {

	T salvar(T t) throws BaseServicoException;
	
	T alterar(T t) throws BaseServicoException;
	
	void deletar(T t, Integer id) throws BaseServicoException;

	List<T> obterTodos() throws BaseServicoException;
	
	List<T> obterTodos(String nomeColuna) throws BaseServicoException;
	
	T obterPorId(Integer id) throws BaseServicoException;
	
	<K extends Descritivel> T obterPorDescricao(String descricao) throws BaseServicoException;
	
	List<? extends SituacaoAlteravel> obterPorSituacao(ESituacao situacao) throws BaseServicoException;
	
	int obterQuantidade() throws BaseServicoException;
}