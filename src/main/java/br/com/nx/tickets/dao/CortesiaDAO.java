package br.com.nx.tickets.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Cortesia;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class CortesiaDAO extends BaseDAO<Cortesia> {

	private static final long serialVersionUID = 1L;

	public CortesiaDAO() {
		super(Cortesia.class);
	}

	@Override
	public void verificarDuplicidade(Cortesia t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Cortesia.class, getEm(), filtravel)
								.setupPaginador(paginador)
								.filterSimpleBy("ct.descricao")
								.orderBy("ct.descricao")
								.sortedBy(ESortedBy.ASC)
								.build()
								.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Cortesia consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ct FROM Cortesia ct ");
			sql.append(" JOIN FETCH ct.ingressoTipo it ");
			sql.append(" JOIN FETCH ct.categoria ca ");
			sql.append(" WHERE ct.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Cortesia.class)
						  .setParameter("_id", id)
						  .getSingleResult();
					
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}