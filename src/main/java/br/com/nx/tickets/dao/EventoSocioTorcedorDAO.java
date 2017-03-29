package br.com.nx.tickets.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.EventoSocioTorcedor;
import br.com.nx.tickets.entidade.socio.SocioTorcedor;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class EventoSocioTorcedorDAO extends BaseDAO<EventoSocioTorcedor> {

	private static final long serialVersionUID = 1L;

	public EventoSocioTorcedorDAO() {
		super(EventoSocioTorcedor.class);
	}

	@Override
	public void verificarDuplicidade(EventoSocioTorcedor t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(SocioTorcedor.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("st.codigo")
									.orderBy("st.id")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public EventoSocioTorcedor consultarPorEventoSocioTorcedor(EventoSocioTorcedor eventoSocioTorcedor) {
		try {
			return getEm().createQuery("SELECT est FROM EventoSocioTorcedor est "
					+ " JOIN FETCH est.socioTorcedor st "
					+ " JOIN FETCH est.portaria pt "
					+ "WHERE st =:_socioTorcedor", EventoSocioTorcedor.class)
						  .setParameter("_socioTorcedor", eventoSocioTorcedor.getSocioTorcedor())
						  .getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}