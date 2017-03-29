package br.com.nx.tickets.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Bilheteria;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class BilheteriaDAO extends BaseDAO<Bilheteria> {

	private static final long serialVersionUID = 1L;

	public BilheteriaDAO() {
		super(Bilheteria.class);
	}

	@Override
	public void verificarDuplicidade(Bilheteria t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Bilheteria.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("bi.descricao")
									.orderBy("bi.descricao")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Bilheteria> consultarPorPontoVenda(PontoVenda pv) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT bi FROM Bilheteria bi ");
			sql.append(" JOIN FETCH bi.pontoVenda pv ");
			sql.append(" WHERE pv =:_pv ");
			sql.append(" ORDER BY bi.id ");
			
			return getEm().createQuery(sql.toString(), Bilheteria.class)
					.setParameter("_pv", pv)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Bilheteria consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT b FROM Bilheteria b ");
			sql.append(" JOIN FETCH b.portaria p ");
			sql.append(" JOIN FETCH b.pontoVenda pv ");
			sql.append(" WHERE b.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Bilheteria.class)
					.setParameter("_id", id)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}