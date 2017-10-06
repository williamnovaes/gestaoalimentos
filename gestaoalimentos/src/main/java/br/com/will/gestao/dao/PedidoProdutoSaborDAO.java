package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.PedidoProdutoSabor;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class PedidoProdutoSaborDAO extends BaseDAO<PedidoProdutoSabor> {

	private static final long serialVersionUID = 1L;

	public PedidoProdutoSaborDAO() {
		super(PedidoProdutoSabor.class);
	}

	@Override
	public void verificarDuplicidade(PedidoProdutoSabor t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		return null;
	}
}