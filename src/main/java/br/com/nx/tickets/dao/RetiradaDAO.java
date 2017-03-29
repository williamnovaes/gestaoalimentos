package br.com.nx.tickets.dao;

import java.util.List;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Retirada;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class RetiradaDAO extends BaseDAO<Retirada> {

	private static final long serialVersionUID = 1L;

	public RetiradaDAO() {
		super(Retirada.class);
	}

	@Override
	public void verificarDuplicidade(Retirada t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Retirada.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("rt.id")
									.orderBy("rt.id")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Retirada> consultarPorCliente(Usuario usuario) {
		try {
			return getEm()
					.createQuery("SELECT rt FROM Retirada rt "
							   + "WHERE tr.usuario =:_usuario "
							   + "ORDER BY p.id ", Retirada.class)
					.setParameter("_usuario", usuario)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Retirada> consultarPorGuicheEvento(Guiche guiche, Evento evento) {
		try {
			return getEm()
					.createQuery("SELECT rt FROM Retirada rt "
							+ " JOIN FETCH rt.guiche g "
							+ " JOIN FETCH rt.evento e "
							+ " JOIN FETCH e.eventoTipo et "
							+ " JOIN FETCH rt.usuario us "
							+ " WHERE g =:_guiche AND e =:_evento "
						    + " ORDER BY rt.dataCadastro ", Retirada.class)
					.setParameter("_guiche", guiche)
					.setParameter("_evento", evento)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}