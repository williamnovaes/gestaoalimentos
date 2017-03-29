package br.com.nx.tickets.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.FiltroUsuarioPedido;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class PedidoDAO extends BaseDAO<Pedido> {

	private static final long serialVersionUID = 1L;

	public PedidoDAO() {
		super(Pedido.class);
	}

	@Override
	public void verificarDuplicidade(Pedido t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			FiltroUsuarioPedido filtroPedido = (FiltroUsuarioPedido) filtravel;
			return new SQLFilter.SQLFilterBuilder(Pedido.class, getEm(), filtravel)
					.filterBy("pd.evento.id", filtroPedido.getIdEventoSelecionado())
					.filterBy("pd.guiche.id", filtroPedido.getIdGuicheSelecionado())
					.filterDateBy("pd.dataCadastro", filtroPedido.getDataInicio(), filtroPedido.getDataFim())
//					.filterLongBy("pd.id")
					.setupPaginador(paginador)
					.filterSimpleBy("pd.evento.descricao", "pd.guiche.descricao")
					.orderBy("pd.dataCadastro")
					.sortedBy(ESortedBy.DESC).build()
					.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Pedido consultarPorId(BigInteger id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ped FROM Pedido ped ");
			sql.append("WHERE ped.id =:_id");

			return getEm().createQuery(sql.toString(), Pedido.class).setParameter("_id", id).getSingleResult();
		} catch (NoResultException nr) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Pedido consultarCompletoPorId(Long id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ped FROM Pedido ped ");
			sql.append("LEFT JOIN FETCH ped.evento ev ");
			sql.append("LEFT JOIN FETCH ev.cliente c ");
			sql.append("LEFT JOIN FETCH ev.eventoTipo et ");
			sql.append("LEFT JOIN FETCH ev.local lo ");
			sql.append("LEFT JOIN FETCH lo.endereco ed ");
			sql.append("LEFT JOIN FETCH ed.cidade cd ");
			sql.append("LEFT JOIN FETCH cd.estado est ");
			sql.append("WHERE ped.id =:_id");

			return getEm().createQuery(sql.toString(), Pedido.class).setParameter("_id", id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Integer sincronizarDadosAplicativo(List<Pedido> pedidos) {
		try {
			Integer quantidade = 0;
			for (Pedido pedido : pedidos) {
				if (this.consultarPorId(pedido.getId()) == null) {
					pedido = super.salvar(pedido);
					for (Ingresso i : pedido.getIngressos()) {
						quantidade += alterarIngressoSituacao(pedido, i);
					}
				}
			}
			getEm().flush();
			getEm().clear();
			return quantidade;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	private Integer alterarIngressoSituacao(Pedido pedido, Ingresso i) {
		try {
			return getEm()
					.createQuery("UPDATE Ingresso i set i.pedido =:_pedido, ingressoSituacao =:_ingressoSituacao "
							+ "WHERE i =:_ingresso ")
					.setParameter("_pedido", pedido).setParameter("_ingressoSituacao", i.getIngressoSituacao())
					.setParameter("_ingresso", i).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Pedido> consultarPorGuicheEvento(Guiche guiche, Evento evento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT distinct (p) FROM Pedido p ");
			sql.append("JOIN p.evento ev ");
			sql.append("JOIN p.guiche g ");
			sql.append("JOIN g.bilheteria b ");
			sql.append("JOIN FETCH p.ingressos i ");
			sql.append("JOIN FETCH i.ingressoSituacao iss ");
			sql.append("JOIN FETCH i.guicheLote gl ");
			sql.append("JOIN FETCH gl.lote lt ");
			sql.append("JOIN FETCH lt.ingressoTipo itt ");
			sql.append("JOIN p.portaria por ");
			sql.append("WHERE itt.cortesia = 'FALSE' ");
			sql.append("AND ev =:_evento AND g =:_guiche ORDER BY p.dataCadastro, i.id");
			return getEm().createQuery(sql.toString(), Pedido.class).setParameter("_evento", evento)
					.setParameter("_guiche", guiche).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}