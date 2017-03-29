package br.com.nx.tickets.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class PromocaoDAO extends BaseDAO<Promocao> {

	private static final long serialVersionUID = 1L;

	public PromocaoDAO() {
		super(Promocao.class);
	}

	@Override
	public void verificarDuplicidade(Promocao t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Promocao.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("pr.descricao")
									.orderBy("pr.id")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Promocao> consultarCompletoPorUsuario(Integer idUsuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pr FROM Promocao pr ");
			sql.append("JOIN pr.usuario us ");
			sql.append("JOIN pr.evento ev ");
			sql.append("WHERE us.id =:_id");

			return getEm().createQuery(sql.toString(), Promocao.class)
					.setParameter("_id", idUsuario)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public Promocao consultarPorEvento(Evento evento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pr FROM Promocao pr ");
			sql.append("JOIN pr.evento ev ");
			sql.append("WHERE ev =:_evento");
			return getEm().createQuery(sql.toString(), Promocao.class)
					.setParameter("_evento", evento)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Promocao consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pr FROM Promocao pr ");
			sql.append(" JOIN FETCH pr.evento ev ");
			sql.append(" JOIN FETCH pr.usuario US ");
			sql.append(" WHERE pr.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Promocao.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}