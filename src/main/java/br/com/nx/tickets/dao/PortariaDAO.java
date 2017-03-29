package br.com.nx.tickets.dao;

import java.util.List;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class PortariaDAO extends BaseDAO<Portaria> {

	private static final long serialVersionUID = 1L;

	public PortariaDAO() {
		super(Portaria.class);
	}

	@Override
	public void verificarDuplicidade(Portaria t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Portaria.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("p.descricao")
									.orderBy("p.id")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Portaria> consultarPorEvento(Evento evento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT por ");
			sql.append("FROM Evento ev ");
			sql.append("JOIN ev.local loc ");
			sql.append("JOIN loc.portarias por ");
			sql.append("WHERE ev =:_evento ");
			sql.append("ORDER BY por.descricao ");
			return getEm().createQuery(sql.toString(), Portaria.class)
						  .setParameter("_evento", evento)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Portaria consultarPorUsuario(Usuario usuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pr FROM Portaria pr ");
			sql.append("JOIN pr.usuario us ");
			sql.append("WHERE us =:_usuario ");
			return getEm().createQuery(sql.toString(), Portaria.class)
						  .setParameter("_usuario", usuario)
						  .getSingleResult();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}