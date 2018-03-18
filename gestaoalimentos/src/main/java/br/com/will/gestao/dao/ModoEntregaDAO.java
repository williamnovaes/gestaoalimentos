package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.ModoEntrega;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class ModoEntregaDAO extends BaseDAO<ModoEntrega> {

	private static final long serialVersionUID = 1L;

	public ModoEntregaDAO() {
		super(ModoEntrega.class);
	}

	@Override
	public void verificarDuplicidade(ModoEntrega t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT me FROM ModoEntrega me ");
			sql.append(" WHERE me.situacao =:_situacao ");
			sql.append(" ORDER BY me.descricao ");
			
			return getEm().createQuery(sql.toString(), ModoEntrega.class).getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(ModoEntrega.class, getEm(), filtravel)
					.setupPaginador(paginador)
					.filterSimpleBy("me.descricao")
					.orderBy("me.descricao")
					.sortedBy(ESortedBy.DESC)
					.build()
					.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}