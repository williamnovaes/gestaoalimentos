package br.com.nx.tickets.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.entidade.Atracao;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.EventoAtracao;
import br.com.nx.tickets.entidade.EventoPontoVenda;
import br.com.nx.tickets.entidade.EventoUsuarioRetirada;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class EventoDAO extends BaseDAO<Evento> {

	private static final long serialVersionUID = 1L;

	public EventoDAO() {
		super(Evento.class);
	}

	@Override
	public void verificarDuplicidade(Evento evento) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Evento.class, getEm(), filtravel)
								.setupPaginador(paginador)
								.filterSimpleBy("ev.descricao")
								.orderBy("ev.dataEvento")
								.sortedBy(ESortedBy.ASC)
								.build()
								.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	public List<Evento> consultarEventosPorCliente(Cliente cliente) {
		try {
			return getEm().createQuery(
					"SELECT ev FROM Evento ev " 
							+ "JOIN FETCH ev.eventoTipo et " 
							+ "JOIN FETCH ev.local lc " 
							+ "WHERE ev.cliente =:_cliente " 
							+ "ORDER BY ev.id ",
					Evento.class).setParameter("_cliente", cliente).getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());

		}
	}

	public void atualizarPontosVendas(Evento evento, List<PontoVenda> pontosVendas) {
		try {
			removerPontoVendaPorEvento(evento);
			for (PontoVenda pontoVenda : pontosVendas) {
				getEm().persist(new EventoPontoVenda(evento, pontoVenda));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removerPontoVendaPorEvento(Evento evento) {
		try {
			getEm().createNativeQuery("DELETE FROM evento_ponto_venda " + "WHERE _evento =:_evento ")
					.setParameter("_evento", evento).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PontoVenda> consultarPontosVendas(Evento evento) {
		try {
			return getEm()
					.createQuery(" SELECT pdv.pontoVenda " 
							   + " FROM Evento ev " 
							   + " JOIN ev.eventosPontosVendas pdv "
							   + " WHERE ev =:_evento " 
							   + " ORDER BY pdv.pontoVenda.nome ", PontoVenda.class)
					.setParameter("_evento", evento).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void salvarPontosVendas(Evento evento, List<PontoVenda> pontosVendasAssociados) {
		try {
			for (PontoVenda pontoVenda : pontosVendasAssociados) {
				getEm().persist(new EventoPontoVenda(evento, pontoVenda));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Evento> consultarEventosPorPontoVenda(PontoVenda pontoVenda) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ev ");
			sql.append(" FROM PontoVenda pv ");
			sql.append(" JOIN pv.eventosPontosVendas pdv ");
			sql.append(" JOIN pdv.evento ev ");
			sql.append(" JOIN FETCH ev.eventoTipo evt ");
			sql.append(" JOIN FETCH ev.local l ");
			sql.append(" JOIN FETCH l.endereco en ");
			sql.append(" JOIN FETCH en.cidade c ");
			sql.append(" JOIN FETCH c.estado uf ");
			sql.append(" JOIN FETCH ev.cliente cl");
			sql.append(" WHERE pv =:_pontoVenda ");
			sql.append(" ORDER BY pdv.evento.id ");
			
			return getEm()
					.createQuery(sql.toString(), Evento.class)
					.setParameter("_pontoVenda", pontoVenda).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Evento> consultarEventosVigentesPorPontoVenda(PontoVenda pontoVenda) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pdv.evento ");
			sql.append(" FROM PontoVenda pv ");
			sql.append(" JOIN pv.eventosPontosVendas pdv ");
			sql.append(" JOIN pdv.evento ev ");
			sql.append(" LEFT JOIN FETCH ev.eventoTipo evt ");
			sql.append(" LEFT JOIN FETCH ev.local l ");
			sql.append(" LEFT JOIN FETCH l.endereco en ");
			sql.append(" LEFT JOIN FETCH en.cidade c ");
			sql.append(" LEFT JOIN FETCH c.estado uf ");
			sql.append(" LEFT JOIN FETCH ev.cliente cl");
			sql.append(" WHERE pv =:_pontoVenda ");
			sql.append(" AND (:_dataAtual BETWEEN ev.dataInicioVendaIngresso AND ev.dataFimVendaIngresso) ");
			sql.append(" ORDER BY pdv.evento.id ");
			
			return getEm()
					.createQuery(sql.toString(), Evento.class)
					.setParameter("_pontoVenda", pontoVenda)
					.setParameter("_dataAtual", Calendar.getInstance())
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarAtracoes(Evento evento, List<Atracao> atracoes) {
		try {
			removerAtracaoPorEvento(evento);
			for (Atracao atracao : atracoes) {
				getEm().persist(new EventoAtracao(evento, atracao));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removerAtracaoPorEvento(Evento evento) {
		try {
			getEm()
				.createNativeQuery("DELETE FROM evento_atracao " 
							 	 + "WHERE _evento =:_evento ")
				.setParameter("_evento", evento)
				.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Atracao> consultarAtracoes(Evento evento) {
		try {
			return getEm().createQuery("SELECT at FROM Atracao at " 
						  	   + "JOIN FETCH at.eventosAtracoes evt "
						  	   + "JOIN FETCH evt.evento ev "
							   + "WHERE ev =:_evento " 
							   + "ORDER BY at.id ", Atracao.class)
					.setParameter("_evento", evento)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Ingresso> consultarPedidosPorEvento(Evento evento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT i FROM Ingresso i ");
			sql.append(" JOIN FETCH i.pedido p ");
			sql.append(" JOIN FETCH p.evento ev ");
			sql.append(" WHERE ev =:_evento ");
			sql.append(" GROUP BY i.lote ");

			return getEm().createQuery(sql.toString(), Ingresso.class)
						  .setParameter("_evento", evento)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarUsuarios(Evento evento, List<Usuario> usuarios) {
		try {
			removerUsuariosPorEvento(evento);
			for (Usuario usuario : usuarios) {
				getEm().persist(new EventoUsuarioRetirada(evento, usuario));
			}
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	private void removerUsuariosPorEvento(Evento evento) {
		try {
			getEm().createNativeQuery("DELETE FROM evento_usuario_retirada WHERE _evento =:_evento")
				   .setParameter("_evento", evento)
				   .executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Evento consultarPorIdComCliente(Integer idEvento) {
		try {
			return getEm()
					.createQuery(" SELECT ev FROM Evento ev "
							   + " JOIN FETCH ev.cliente cl "
							   + " WHERE ev.id =:_idEvento ", Evento.class)
					.setParameter("_idEvento", idEvento)
					.getSingleResult();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<ExtratoGuicheDTO> consultarExtratoVenda(Evento evento) {
		try {
			String sql = "SELECT g.id, "
					+ "g.descricao AS guiche, "
					+ "count(i.id) AS qtd, "
					+ "SUM(lt.valor) as valor_total, "
					+ "sum(lt.valor_taxa_administrativa) AS valor_taxa "
					+ "FROM pedido p "
					+ "JOIN evento e ON e.id = p._evento "
					+ "JOIN guiche g ON g.id = p._guiche "
					+ "JOIN bilheteria b ON b.id = g._bilheteria "
					+ "JOIN ingresso i ON i._pedido = p.id "
					+ "JOIN ingresso_situacao iss ON iss.id = i._ingresso_situacao "
					+ "JOIN lote lt ON lt.id = i._lote "
					+ "JOIN ingresso_tipo itt ON itt.id = lt._ingresso_tipo "
					+ "WHERE iss.considerar_entrada_caixa = 'TRUE' AND itt.cortesia = 'FALSE'  "
					+ "AND itt.cortesia = 'FALSE' AND e.id = :_idEvento "
					+ "GROUP BY 1, 2 ORDER BY 3 DESC";
			@SuppressWarnings("unchecked")
			List<Object[]> obj = (List<Object[]>) getEm().createNativeQuery(sql)
											 .setParameter("_idEvento", evento.getId())
											 .getResultList();
			List<ExtratoGuicheDTO> extratos = new ArrayList<>();
			for (Object[] object : obj) {
				extratos.add(new ExtratoGuicheDTO(object));
			}
			return extratos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public EventoUsuarioRetirada validarUsuarioRetirada(Evento evento, Usuario usuario) {
		try {
			return getEm()
					.createQuery(" SELECT eur FROM Evento ev "
							   + " JOIN ev.eventosUsuariosRetirada eur "
							   + " WHERE eur.evento =:_evento "
							   + " AND eur.usuario =:_usuario", EventoUsuarioRetirada.class)
					.setParameter("_evento", evento)
					.setParameter("_usuario", usuario)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new BaseDAOException("Usuário não autorizado!");
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Usuario> consultarUsuariosRetirada(Evento evento) {
		try {
			return getEm()
					.createQuery("SELECT usu FROM Usuario usu " 
						  	   + "JOIN FETCH usu.eventosUsuariosRetirada eur "
						  	   + "JOIN FETCH eur.evento ev "
							   + "WHERE ev =:_evento " 
							   + "ORDER BY usu.nome ", Usuario.class)
					.setParameter("_evento", evento)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Evento consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ev FROM Evento ev ");
			sql.append("JOIN FETCH ev.local lc ");
			sql.append("JOIN FETCH lc.endereco en ");
			sql.append("JOIN FETCH en.cidade ci ");
			sql.append("JOIN FETCH ci.estado ess ");
			sql.append("JOIN FETCH ev.cliente cl ");
			sql.append("JOIN FETCH ev.eventoTipo et ");
			sql.append("WHERE ev.id =:_id");
			return getEm().createQuery(sql.toString(), Evento.class)
					.setParameter("_id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Evento> consultarAtivosPorUsuario(Usuario usuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ev ");
			sql.append("FROM Evento ev ");
			sql.append("JOIN ev.eventosPontosVendas epvd ");
			sql.append("JOIN epvd.pontoVenda pdv ");
			sql.append("JOIN pdv.usuariosPontosVendas updv ");
			sql.append("JOIN updv.usuario u ");
			sql.append("JOIN FETCH ev.eventoTipo evt ");
			sql.append("JOIN FETCH ev.local l ");
			sql.append("JOIN FETCH l.endereco en ");
			sql.append("JOIN FETCH en.cidade c ");
			sql.append("JOIN FETCH c.estado uf ");
			sql.append("JOIN FETCH ev.cliente cl ");
			sql.append("WHERE u =:_usuario AND ev.situacao =:_situacao ");
			sql.append("ORDER BY ev.id ");
			return getEm()
					.createQuery(sql.toString(), Evento.class)
					.setParameter("_usuario", usuario)
					.setParameter("_situacao", ESituacao.ATIVO)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Evento> consultarAtivosPorGuiche(Guiche guiche) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT distinct (ev) ");
			sql.append("FROM Evento ev ");
			sql.append("JOIN FETCH ev.eventoTipo evt ");
			sql.append("JOIN FETCH ev.local l ");
			sql.append("JOIN FETCH l.endereco en ");
			sql.append("JOIN FETCH en.cidade c ");
			sql.append("JOIN FETCH c.estado uf ");
			sql.append("JOIN FETCH ev.cliente cl ");
			sql.append("JOIN ev.eventosPontosVendas epv ");
			sql.append("JOIN epv.pontoVenda pdv ");
			sql.append("JOIN pdv.bilheterias b ");
			sql.append("JOIN b.guiches g ");
			sql.append("WHERE g =:_guiche AND ev.situacao =:_situacao ");
			sql.append("ORDER BY ev.id ");
			return getEm()
					.createQuery(sql.toString(), Evento.class)
					.setParameter("_guiche", guiche)
					.setParameter("_situacao", ESituacao.ATIVO)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Evento> consultarTodosPorGuiche(Guiche guiche) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT distinct (ev) ");
			sql.append("FROM Evento ev ");
			sql.append("JOIN FETCH ev.eventoTipo evt ");
			sql.append("JOIN FETCH ev.local l ");
			sql.append("JOIN FETCH l.endereco en ");
			sql.append("JOIN FETCH en.cidade c ");
			sql.append("JOIN FETCH c.estado uf ");
			sql.append("JOIN FETCH ev.cliente cl ");
			sql.append("JOIN ev.eventosPontosVendas epv ");
			sql.append("JOIN epv.pontoVenda pdv ");
			sql.append("JOIN pdv.bilheterias b ");
			sql.append("JOIN b.guiches g ");
			sql.append("WHERE g =:_guiche ");
			sql.append("ORDER BY ev.id ");
			return getEm()
					.createQuery(sql.toString(), Evento.class)
					.setParameter("_guiche", guiche)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Evento> consultarAtivos() {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ev ");
			sql.append("FROM Evento ev ");
			sql.append("JOIN FETCH ev.eventoTipo evt ");
			sql.append("JOIN FETCH ev.local l ");
			sql.append("JOIN FETCH l.endereco en ");
			sql.append("JOIN FETCH en.cidade c ");
			sql.append("JOIN FETCH c.estado uf ");
			sql.append("JOIN FETCH ev.cliente cl ");
			sql.append("WHERE ev.situacao =:_situacao ");
			sql.append("ORDER BY ev.id ");
			return getEm()
					.createQuery(sql.toString(), Evento.class)
					.setParameter("_situacao", ESituacao.ATIVO)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}