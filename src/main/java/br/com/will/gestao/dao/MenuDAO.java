package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.permissao.Menu;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class MenuDAO extends BaseDAO<Menu> {

	private static final long serialVersionUID = 1L;

	public MenuDAO() {
		super(Menu.class);
	}

	@Override
	public void verificarDuplicidade(Menu t) throws ViolacaoDeConstraintException {
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