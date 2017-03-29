package br.com.nx.tickets.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.dto.ExtratoRetiradaDTO;
import br.com.nx.tickets.entidade.Bilheteria;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class GuicheDAO extends BaseDAO<Guiche> {

	private static final long serialVersionUID = 1L;

	public GuicheDAO() {
		super(Guiche.class);
	}

	@Override
	public void verificarDuplicidade(Guiche t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Guiche.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("g.id")
									.orderBy("g.id")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public void limparGuicheUsuario(Integer id) {
		try {
			getEm().createNativeQuery(" UPDATE guiche SET _usuario = null "
									+ " WHERE _usuario =:_usuario ", Guiche.class)
				   .setParameter("_usuario", id)
				   .executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Guiche> consultarDisponiveisPorUsuario(Usuario usuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT distinct (g) ");
			sql.append(" FROM Guiche g ");
			sql.append(" JOIN FETCH g.bilheteria b ");
			sql.append(" JOIN FETCH b.pontoVenda pv ");
			sql.append(" JOIN FETCH pv.eventosPontosVendas evpdv ");
			sql.append(" JOIN FETCH evpdv.evento e ");
			sql.append(" LEFT JOIN pv.usuariosPontosVendas updv ");
			sql.append(" LEFT JOIN updv.usuario u ");
			sql.append(" JOIN FETCH b.portaria p ");
			sql.append(" WHERE u =:_usuario and e.situacao = 'ATIVO' ");
			sql.append(" ORDER BY b.descricao, g.descricao");
			return getEm().createQuery(sql.toString(), Guiche.class)
						  .setParameter("_usuario", usuario)
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public Guiche consultarPorUsuario(Usuario usuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT g ");
			sql.append(" FROM Guiche g ");
			sql.append(" JOIN FETCH g.bilheteria b ");
			sql.append(" JOIN FETCH b.portaria p ");
			sql.append(" JOIN FETCH p.local l ");
			sql.append(" WHERE g.usuario =:_usuario order by b.descricao, g.descricao");
			return getEm().createQuery(sql.toString(), Guiche.class)
						  .setParameter("_usuario", usuario)
						  .getSingleResult();
		} catch (NoResultException e) {
			throw new BaseDAOException("Usuário sem Guiche associado para cadastrar o pedido!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Guiche> consultarPorPortaria(Portaria portaria) {
		try {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT g FROM Guiche g ");
		sql.append(" JOIN FETCH g.portaria p ");
		sql.append(" WHERE g.portaria =:_portaria ");
		sql.append(" ORDER BY g.descricao ");
		
		return getEm()
				.createQuery(sql.toString(), Guiche.class)
				.setParameter("_portaria", portaria)
				.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Guiche> consultarPorBilheteria(Bilheteria bi) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT g FROM Guiche g ");
			sql.append(" JOIN FETCH g.bilheteria b ");
			sql.append(" WHERE b =:_bilheteria ");
			sql.append(" ORDER BY g.id ");
			
			return getEm().createQuery(sql.toString(), Guiche.class)
					.setParameter("_bilheteria", bi)
					.getResultList();
					
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Guiche consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT gi FROM Guiche gi ");
			sql.append(" JOIN FETCH gi.usuario us ");
			sql.append(" JOIN FETCH gi.bilheteria bi ");
			sql.append(" WHERE gi.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Guiche.class)
					.setParameter("_id", id)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Guiche> consultarPorLote(Lote lt) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT gi FROM GuicheLote glt ");
			sql.append(" JOIN glt.lote lt ");
			sql.append(" JOIN glt.guiche gi ");
			sql.append(" JOIN FETCH gi.bilheteria bi ");
			sql.append(" JOIN FETCH bi.portaria p ");
			sql.append(" JOIN FETCH p.local l ");
			sql.append(" WHERE lt =:_lote");
			sql.append(" ORDER BY gi.descricao ");
			return getEm().createQuery(sql.toString(), Guiche.class)
					.setParameter("_lote", lt)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public ExtratoRetiradaDTO consultarExtratoRetiradaPorGuicheEvento(Guiche guiche, Evento evento) {
		try {
			String sql = "SELECT COUNT(i.id) AS qtd, SUM(l.valor) AS total, "
					+ " SUM(l.valor_taxa_administrativa) AS total_taxa "
					+ " FROM ingresso i "
					+ " JOIN pedido p ON p.id = i._pedido "
					+ " JOIN guiche g ON g.id = p._guiche "
					+ "	JOIN ingresso_situacao iss on iss.id = i._ingresso_situacao "
					+ "	JOIN lote l ON l.id = i._lote "
					+ "	JOIN ingresso_tipo it ON it.id = l._ingresso_tipo "
					+ " JOIN evento e ON e.id = p._evento "
					+ "WHERE iss.considerar_entrada_caixa = 'TRUE' AND it.cortesia = 'FALSE' AND g.id = :_idGuiche AND e.id = :_idEvento ";
			Object[] obj = (Object[]) getEm().createNativeQuery(sql)
											 .setParameter("_idGuiche", guiche.getId())
											 .setParameter("_idEvento", evento.getId())
											 .getSingleResult();
			return new ExtratoRetiradaDTO(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<ExtratoGuicheDTO> consultarExtratoPorGuicheEvento(Guiche guiche, Evento evento) {
		try {
			List<ExtratoGuicheDTO> extratos = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			if (guiche != null && guiche.getId() != null) {
				sb.append("g.id AS id_guiche, ");
				sb.append("g.descricao  AS descricao_guiche, ");
			} else {
				sb.append("0 AS id_guiche, ");
				sb.append("'TOTAL'  AS descricao_guiche, ");
			}
			sb.append("it.descricao AS descricao_ingresso_tipo, ");
			sb.append("COUNT(i.id) AS quantidade, ");
			sb.append("SUM(l.valor) AS valor, ");
			sb.append("SUM(l.valor_taxa_administrativa) AS valor_taxa ");
			sb.append("FROM ingresso i JOIN pedido p ON p.id = i._pedido ");
			sb.append("JOIN guiche g ON g.id = p._guiche ");
			sb.append("JOIN ingresso_situacao iss ON iss.id = i._ingresso_situacao ");
			sb.append("JOIN lote l ON l.id = i._lote ");
			sb.append("JOIN ingresso_tipo it ON it.id = l._ingresso_tipo ");
			sb.append("JOIN evento e ON e.id = p._evento ");
			sb.append("WHERE iss.considerar_entrada_caixa = 'TRUE' AND it.cortesia = 'FALSE' AND e.id = :_idEvento ");
			if (guiche != null && guiche.getId() != null) {
				sb.append(" AND g.id = :_idGuiche ");
			}
			sb.append("group by 1, 2, 3 ");
			sb.append("order by 2, 3, 4");
			Query q = getEm().createNativeQuery(sb.toString())
							 .setParameter("_idEvento", evento.getId());
			
			if (guiche != null && guiche.getId() != null) {
				q.setParameter("_idGuiche", guiche.getId());
			}
			@SuppressWarnings("unchecked")
			List<Object[]> obj = ((List<Object[]>) q.getResultList());
			for (Object[] object : obj) {
				extratos.add(new ExtratoGuicheDTO(object));
			}
			return extratos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public ExtratoGuicheDTO consultarExtratoTotalGuiche(Guiche guiche, Evento evento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT new br.com.nx.tickets.dto.ExtratoGuicheDTO(count(i.id), sum(lt.valor) "
					+ "+ sum(lt.valorTaxaAdministrativa)) FROM Ingresso i ");
			sql.append("JOIN i.ingressoSituacao iss ");
			sql.append("JOIN i.pedido p ");
			sql.append("JOIN p.evento e ");
			sql.append("JOIN i.guicheLote gl ");
			sql.append("JOIN gl.guiche g ");
			sql.append("JOIN gl.lote lt ");
			sql.append("JOIN lt.ingressoTipo itt ");
			sql.append("WHERE g.id =:_idGuiche AND e.id =:_idEvento ");
			sql.append("AND iss.considerarEntradaCaixa =:_entrada AND itt.cortesia = 'FALSE' ");
			return getEm().createQuery(sql.toString(), ExtratoGuicheDTO.class)
									.setParameter("_idGuiche", guiche.getId())
									.setParameter("_idEvento", evento.getId())
									.setParameter("_entrada", EBoolean.TRUE)
									.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Guiche> consultarTodosCompletos() {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT g FROM Guiche g ");
			sql.append(" JOIN FETCH g.bilheteria b ");
			sql.append(" JOIN FETCH b.portaria p ");
			sql.append(" JOIN FETCH p.local l ");
			sql.append(" ORDER BY g.descricao ");
			
			return getEm()
					.createQuery(sql.toString(), Guiche.class)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Guiche> consultarPorEvento(Evento evento, EBoolean offline) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT g FROM Guiche g ");
			sql.append(" JOIN FETCH g.bilheteria b ");
			sql.append(" JOIN FETCH b.portaria p ");
			sql.append(" JOIN FETCH b.pontoVenda pv ");
			sql.append(" JOIN pv.eventosPontosVendas epv ");
			sql.append(" JOIN epv.evento ev ");
			sql.append(" WHERE ev =:_evento ");
			if (offline != null) {
				sql.append(" AND g.offline =:_offline ");
			}
			
			sql.append(" ORDER BY g.descricao ");
			
			TypedQuery<Guiche> q = getEm().createQuery(sql.toString(), Guiche.class)
							 .setParameter("_evento", evento);
			if (offline != null) {
				q.setParameter("_offline", offline);
			}
			return q.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}