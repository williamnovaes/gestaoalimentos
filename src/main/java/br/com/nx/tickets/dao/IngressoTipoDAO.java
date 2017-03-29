package br.com.nx.tickets.dao;

import java.util.List;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class IngressoTipoDAO extends BaseDAO<IngressoTipo> {

	private static final long serialVersionUID = 1L;

	public IngressoTipoDAO() {
		super(IngressoTipo.class);
	}

	@Override
	public void verificarDuplicidade(IngressoTipo t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(IngressoTipo.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("it.id")
									.orderBy("it.id")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<IngressoTipo> consultarPorCliente(Cliente cliente) {
		try {
			return getEm()
					.createQuery(" SELECT cit.ingressoTipo FROM Cliente cl "
							   + " JOIN cl.clientesIngressosTipos cit "
							   + " WHERE cl =:_cliente "
							   + " ORDER BY cit.ingressoTipo.descricao ", IngressoTipo.class)
					.setParameter("_cliente", cliente)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}