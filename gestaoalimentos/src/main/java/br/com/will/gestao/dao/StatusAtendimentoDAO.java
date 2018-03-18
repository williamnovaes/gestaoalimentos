package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.StatusAtendimento;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class StatusAtendimentoDAO extends BaseDAO<StatusAtendimento> {

	private static final long serialVersionUID = 1L;

	public StatusAtendimentoDAO() {
		super(StatusAtendimento.class);
	}

	@Override
	public void verificarDuplicidade(StatusAtendimento t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT sa FROM StatusAtendimento sa ");
			sql.append(" WHERE sa.situacao =:_situacao ");
			sql.append(" ORDER BY sa.descricao ");
			
			return getEm().createQuery(sql.toString(), StatusAtendimento.class).getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(StatusAtendimento.class, getEm(), filtravel)
					.setupPaginador(paginador)
					.filterSimpleBy("sa.descricao")
					.orderBy("sa.descricao")
					.sortedBy(ESortedBy.DESC)
					.build()
					.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}