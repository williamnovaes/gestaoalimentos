package br.com.nx.tickets.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.EventoPontoVenda;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.UsuarioPontoVenda;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class PontoVendaDAO extends BaseDAO<PontoVenda> {

	private static final long serialVersionUID = 1L;

	public PontoVendaDAO() {
		super(PontoVenda.class);
	}

	@Override
	public void verificarDuplicidade(PontoVenda t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(PontoVenda.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("pv.nome")
									.orderBy("pv.nome")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public PontoVenda consultarPorUsuario(Integer idUsuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pdv FROM UsuarioPontoVenda updv  ");
			sql.append("JOIN updv.usuario us ");
			sql.append("JOIN updv.pontoVenda pdv ");
			sql.append("WHERE us.id =:_id");
			return getEm().createQuery(sql.toString(), PontoVenda.class)
					.setParameter("_id", idUsuario).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarEventos(PontoVenda pontoVenda, List<Evento> eventosAssociados) {
		try {
			removerEventoPorPontoVenda(pontoVenda);
			for (Evento evento : eventosAssociados) {
				getEm().persist(new EventoPontoVenda(evento, pontoVenda));
			}
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	private void removerEventoPorPontoVenda(PontoVenda pv) {
		try {
			getEm().createNativeQuery("DELETE FROM evento_ponto_venda " 
									+ "WHERE _ponto_venda =:_pontoVenda ")
					.setParameter("_pontoVenda", pv)
					.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer consultarQuantidadeIngressoVendidoPorEventoPontoVenda(Evento evento, PontoVenda pontoVenda) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select count(i.id) from ingresso i ");
			sb.append("join ingresso_situacao iss on iss.id = i._ingresso_situacao ");
			sb.append("join lote lt on lt.id = i._lote ");
			sb.append("join ingresso_tipo itt on itt.id = lt._ingresso_tipo ");
			sb.append("join pedido p on p.id = i._pedido ");
			sb.append("join evento e on e.id = p._evento ");
			sb.append("join caixa c on c.id = p._caixa ");
			sb.append("join ponto_venda pv on pv.id = c._ponto_venda ");
			sb.append("where iss.ativo = 'TRUE' AND itt.cortesia = 'FALSE' AND e.id =:_idEvento AND pv.id =:_idPontoVenda ");
			return ((BigInteger) getEm().createNativeQuery(sb.toString())
			 .setParameter("_idEvento", evento.getId())
			 .setParameter("_idPontoVenda", pontoVenda.getId())
			 .getSingleResult()).intValue();
		} catch (NoResultException e) {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarUsuarios(PontoVenda pontoVenda, List<Usuario> usuarios) {
		try {
			for (Usuario usuario : usuarios) {
				getEm().persist(new UsuarioPontoVenda(usuario, pontoVenda));
			}
			
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<PontoVenda> consultarPorEvento(Evento ev) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT epv.pontoVenda FROM Evento ev ");
			sql.append(" JOIN ev.eventosPontosVendas epv ");
//			sql.append(" JOIN FETCH epv.pontoVenda pv ");
			sql.append(" WHERE ev =:_evento ");
			sql.append(" ORDER BY epv.pontoVenda.nome ");
			
			return getEm().createQuery(sql.toString(), PontoVenda.class)
					.setParameter("_evento", ev)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}