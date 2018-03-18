package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.FormaPagamento;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class FormaPagamentoDAO extends BaseDAO<FormaPagamento> {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoDAO() {
		super(FormaPagamento.class);
	}

	@Override
	public void verificarDuplicidade(FormaPagamento t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(FormaPagamento.class, getEm(), filtravel)
					.setupPaginador(paginador)
					.filterSimpleBy("fp.descricao")
					.orderBy("fp.descricao")
					.sortedBy(ESortedBy.DESC)
					.build()
					.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}