package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.AutenticacaoLog;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class AutenticacaoLogDAO extends BaseDAO<AutenticacaoLog> {

	private static final long serialVersionUID = 1L;

	public AutenticacaoLogDAO() {
		super(AutenticacaoLog.class);
	}

	@Override
	public void verificarDuplicidade(AutenticacaoLog t) throws ViolacaoDeConstraintException {
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