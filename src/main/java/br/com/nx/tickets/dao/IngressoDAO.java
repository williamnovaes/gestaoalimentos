package br.com.nx.tickets.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.FiltroUsuarioIngresso;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;

public class IngressoDAO extends BaseDAO<Ingresso> {

	private static final long serialVersionUID = 1L;

	@Inject
	private IngressoSituacaoDAO ingressoSituacaoDao;

	public IngressoDAO() {
		super(Ingresso.class);
	}

	@Override
	public void verificarDuplicidade(Ingresso t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	public void salvar(List<Ingresso> ingressos) {
		try {
			int indice = 1;
			for (Ingresso ingresso : ingressos) {
				ingresso.setCodigo(gerarCodigoBarras(indice, ingresso.getGuicheLote().getGuiche().getId()));
				super.salvar(ingresso);
				if (indice % SistemaConstantes.CEM == 0) {
					getEm().flush();
					getEm().clear();
					getLog().info("LIMPANDO INGRESSOS: " + indice);
				}
				indice++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public Ingresso consultarPorId(Long id) {
		try {
			return getEm().createQuery("SELECT i FROM Ingresso i WHERE i.id =:_id", Ingresso.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	private static String gerarCodigoBarras(Integer indice, Integer idGuiche) {
		String inicio = indice + idGuiche.toString();
		String data = String.valueOf(Calendar.getInstance().getTimeInMillis());
		String codigo = data.substring(inicio.length(), data.length());
		codigo = codigo + inicio;
		return codigo;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			FiltroUsuarioIngresso filtroIngresso = (FiltroUsuarioIngresso) filtravel;
			return new SQLFilter.SQLFilterBuilder(Ingresso.class, getEm(), filtravel)
					.filterBy("i.guicheLote.lote.evento.id", filtroIngresso.getIdEventoSelecionado())
					.filterBy("i.ingressoSituacao.id", filtroIngresso.getIdIngressoSituacaoSelecionado())
					.filterBy("i.guicheLote.guiche.id", filtroIngresso.getIdGuicheSelecionado())
					.filterLongBy("i.id", "p.id")
					.setupPaginador(paginador)
					.filterSimpleBy("lt.ingressoTipo.descricao")
					.orderBy("i.id")
					.sortedBy(ESortedBy.ASC).build().dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Ingresso> consultarPorIdPedido(BigInteger idPedido) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT i FROM Ingresso i ");
			sql.append("JOIN FETCH i.pedido p ");
			sql.append("JOIN FETCH i.guicheLote lt ");
			sql.append("JOIN FETCH lt.guiche g ");
			sql.append("JOIN FETCH lt.lote lt ");
			sql.append("JOIN FETCH lt.ingressoTipo it ");
			sql.append("JOIN FETCH i.ingressoSituacao st ");
			sql.append("WHERE i.pedido.id =:_id ");
			sql.append("ORDER BY i.codigo ");
			return getEm().createQuery(sql.toString(), Ingresso.class).setParameter("_id", idPedido).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Ingresso atualizarCodigoBarra(Ingresso ingresso) {
		try {
			getEm().createQuery("UPDATE Ingresso i set i.codigo =:_codigo, i.codigoPromocional =:_codigoPromocional "
					+ "WHERE i =:_ingresso").setParameter("_codigo", ingresso.getCodigo())
					.setParameter("_codigoPromocional", ingresso.getCodigoPromocao())
					.setParameter("_ingresso", ingresso).executeUpdate();
			return ingresso;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Ingresso consultarPorCodigoEvento(String codigo, Integer idEvento) {
		try {
			return getEm()
					.createQuery("SELECT i FROM Ingresso i " + "JOIN FETCH i.guicheLote gl " + "JOIN FETCH gl.lote lt "
							+ "JOIN FETCH gl.guiche g " + "JOIN FETCH lt.evento e " + "JOIN FETCH lt.ingressoTipo itt "
							+ "LEFT JOIN FETCH i.portariaValidacao pl " + "JOIN FETCH i.ingressoSituacao iss "
							+ "WHERE i.codigo =:_codigo and e.id =:_idEvento ", Ingresso.class)
					.setParameter("_codigo", codigo).setParameter("_idEvento", idEvento).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Ingresso alterarIngressoValidacao(Ingresso ingresso) {
		try {
			getEm().createQuery("UPDATE Ingresso i " + "SET i.dataValidacao =:_dataValidacao, "
					+ "i.ingressoSituacao =:_ingressoSituacao, " + "i.portariaValidacao =:_portariaValidacao "
					+ "WHERE i.codigo =:_codigo ").setParameter("_ingressoSituacao", ingresso.getIngressoSituacao())
					.setParameter("_dataValidacao", Calendar.getInstance())
					.setParameter("_portariaValidacao", ingresso.getPortariaValidacao())
					.setParameter("_codigo", ingresso.getCodigo()).executeUpdate();
			return ingresso;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Integer cancelarIngresso(Ingresso ingresso) {
		try {
			IngressoSituacao situacao = ingressoSituacaoDao.consultarPorDescricao("CANCELADO");
			return getEm()
					.createNativeQuery(" UPDATE ingresso SET _ingresso_situacao =:_situacao, observacao =:_observacao "
							+ " WHERE id =:_id AND id not in (SELECT i.id FROM ingresso i "
							+ "WHERE _ingresso_situacao =:_situacao AND i.id !=:_id) ", Ingresso.class)
					.setParameter("_id", ingresso.getId()).setParameter("_situacao", situacao.getId())
					.setParameter("_observacao", ingresso.getObservacao()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void cancelarTodosIngressosPorPedido(Pedido pedido, String observacao) {
		try {
			IngressoSituacao situacao = ingressoSituacaoDao.consultarPorDescricao("CANCELADO");
			getEm().createNativeQuery("UPDATE ingresso " + " SET _ingresso_situacao =:_situacao, "
					+ " observacao =:_observacao " + " WHERE _pedido =:_pedido ", Ingresso.class)
					.setParameter("_situacao", situacao.getId()).setParameter("_pedido", pedido.getId())
					.setParameter("_observacao", observacao).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Ingresso consultarParaPromocao(Ingresso ingresso) {
		try {
			return getEm()
					.createQuery("SELECT i FROM Ingresso i " + "JOIN i.guicheLote gl " + "JOIN gl.lote lt "
							+ "JOIN lt.evento e " + "JOIN e.promocoes pr " + "JOIN i.ingressoSituacao iss "
							+ "WHERE i.codigo =:_codigo AND i.codigoPromocao =:_codigoPromocional "
							+ "AND pr.situacao =:_situacao AND iss.ativo =:_ativo "
							+ "AND :_dataAtual between pr.dataInicio AND pr.dataFim ", Ingresso.class)
					.setParameter("_codigo", ingresso.getCodigo())
					.setParameter("_codigoPromocional", ingresso.getCodigoPromocao())
					.setParameter("_dataAtual", Calendar.getInstance()).setParameter("_situacao", ESituacao.ATIVO)
					.setParameter("_ativo", EBoolean.TRUE).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Ingresso> consultarOffLinePorEventoGuiche(Evento evento, Guiche guiche) {
		try {
			List<Ingresso> ingressos = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT lt.id AS lote_id, i.id AS ingresso_id, i.codigo, lt.valor, "
					+ "lt.valor_taxa_administrativa AS taxa, " + "(lt.valor + lt.valor_taxa_administrativa) AS total, "
					+ "i.codigo_promocao, " + "iss.id " + "FROM ingresso i "
					+ "JOIN ingresso_situacao iss ON iss.id = i._ingresso_situacao "
					+ "JOIN lote lt ON lt.id = i._lote " + "JOIN guiche g ON g.id = i._guiche "
					+ "JOIN evento e ON e.id = lt._evento " + "WHERE e.id =:_evento AND g.id =:_guiche AND iss.descricao = 'LOTE' "
					+ "ORDER BY 1, 2");
			@SuppressWarnings("unchecked")
			List<Object[]> objs = getEm().createNativeQuery(sb.toString())
					.setParameter("_evento", evento.getId())
					.setParameter("_guiche", guiche.getId())
					.setMaxResults(SistemaConstantes.VINTE_CINCO_MIL).getResultList();
			for (Object[] objects : objs) {
				ingressos.add(new Ingresso(objects));
			}
			return ingressos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Ingresso> consultarValidosPorEvento(Evento evento) {
		try {
			return getEm()
					.createQuery("SELECT i FROM Ingresso i " + "JOIN FETCH i.guicheLote gl " + "JOIN FETCH gl.guiche g "
							+ "JOIN FETCH gl.lote lt " + "JOIN FETCH lt.ingressoTipo itt " + "JOIN lt.evento e "
							+ "LEFT JOIN FETCH i.portariaValidacao pl " + "JOIN FETCH i.ingressoSituacao iss "
							+ "WHERE iss.descricao != 'CANCELADO' AND e.id =:_evento ", Ingresso.class)
					.setParameter("_evento", evento.getId()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Integer atualizarIngressosValidacao(List<Ingresso> ingressos) {
		try {
			IngressoSituacao iss = ingressoSituacaoDao.consultarPorDescricao("VALIDADO");
			Integer quantidade = 0;
			for (Ingresso ingresso : ingressos) {
				if (ingresso.getSincronizar().equals(EBoolean.TRUE)) {
					quantidade += getEm()
							.createNativeQuery(
									"UPDATE ingresso " + " SET data_validacao =:_dataValidacao, "
											+ " _portaria_validacao =:_portariaValidacao, _ingresso_situacao =:_ingressoSituacao "
											+ " WHERE data_validacao is null and _portaria_validacao is null AND id =:_id ",
									Ingresso.class)
							.setParameter("_dataValidacao", ingresso.getDataValidacao())
							.setParameter("_portariaValidacao", ingresso.getPortariaValidacao().getId())
							.setParameter("_ingressoSituacao", iss.getId()).setParameter("_id", ingresso.getId())
							.executeUpdate();
				}
			}
			return quantidade;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Ingresso> consultarPorEventoSituacao(Evento evento, List<IngressoSituacao> situacoes) {
		try {
			return getEm()
					.createQuery(
							"SELECT i FROM Ingresso i " + "JOIN FETCH i.guicheLote gl " + "JOIN FETCH gl.guiche g "
									+ "JOIN FETCH gl.lote lt " + "JOIN FETCH lt.ingressoTipo itt "
									+ "JOIN lt.evento ev " + "JOIN FETCH i.ingressoSituacao iss "
									+ "WHERE ev =:_evento and iss in (:_situacoes) ORDER BY g.descricao, i.id",
							Ingresso.class)
					.setParameter("_evento", evento).setParameter("_situacoes", situacoes).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Integer consultarPorEventoGuicheSituacao(Evento evento, Guiche guiche, List<IngressoSituacao> situacoes) {
		try {
			return ((Long) getEm()
					.createQuery("SELECT count(i.id) FROM Ingresso i " 
								+ "JOIN i.guicheLote gl " 
							+ "JOIN gl.guiche g "
							+ "JOIN gl.lote lt " 
							+ "JOIN lt.ingressoTipo itt " 
							+ "JOIN lt.evento ev "
							+ "JOIN i.ingressoSituacao iss "
							+ "WHERE itt.cortesia = 'FALSE' AND ev =:_evento AND g =:_guiche and iss in (:_situacoes)")
					.setParameter("_evento", evento).setParameter("_guiche", guiche)
					.setParameter("_situacoes", situacoes).getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Ingresso> consultarPorEventoSituacao(Evento evento, IngressoSituacao situacao) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT i FROM Ingresso i ");
			sql.append("JOIN FETCH i.portariaValidacao pv ");
			sql.append("JOIN i.guicheLote gl ");
			sql.append("JOIN gl.guiche g ");
			sql.append("JOIN gl.lote lt ");
			sql.append("JOIN lt.evento ev ");
			sql.append("JOIN i.ingressoSituacao iss ");
			sql.append("WHERE ev =:_evento AND iss =:_situacao");
			return getEm().createQuery(sql.toString(), Ingresso.class)
					.setParameter("_evento", evento).setParameter("_situacao", situacao).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void solicitarCancelamento(Guiche guiche, Ingresso ingresso) {
		try {
			IngressoSituacao iss = ingressoSituacaoDao.consultarPorDescricao("PRE CANCELADO");
			getEm().createNativeQuery("UPDATE ingresso set _ingresso_situacao =:_ingressoSituacao "
					+ "WHERE id =:_id and _guiche =:_guiche")
					.setParameter("_ingressoSituacao", iss.getId())
					.setParameter("_guiche", guiche.getId())
					.setParameter("_id", ingresso.getId())
				   .executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}