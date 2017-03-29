package br.com.nx.tickets.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.GuicheLote;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

public class LoteDAO extends BaseDAO<Lote> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PromocaoDAO promocaoDao;
	@Inject
	private IngressoSituacaoDAO ingressoSituacaoDao;
	@Inject
	private IngressoDAO ingressoDao;
	
	public LoteDAO() {
		super(Lote.class);
	}

	@Override
	public void verificarDuplicidade(Lote t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Lote.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("lt.numero")
									.orderBy("lt.id")
									.sortedBy(ESortedBy.DESC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Lote> consultarLotePorEvento(Evento evento) {
		try {
			return getEm()
					.createQuery("SELECT lt FROM Lote lt "
							   + "JOIN FETCH lt.evento ev "
							   + "JOIN FETCH lt.usuario usu " 
							   + "JOIN FETCH lt.ingressoTipo it "
							   + "WHERE lt.evento =:_evento "
							   + "AND lt.situacao =:_situacao "
							   + "ORDER BY lt.numero ", Lote.class)
					.setParameter("_evento", evento)
					.setParameter("_situacao", ESituacao.ATIVO)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Lote> consultarLoteValidosPorEvento(Evento evento) {
		try {
			return getEm()
					.createQuery("SELECT lt FROM Lote lt "
							   + "JOIN FETCH lt.evento ev "
							   + "JOIN FETCH lt.usuario usu " 
							   + "JOIN FETCH lt.ingressoTipo it "
							   + "WHERE lt.evento =:_evento "
							   + "AND (:_dataAtual BETWEEN lt.dataInicio AND lt.dataFim) "
							   + "AND it.cortesia = 'FALSE' AND it.situacao =:_situacao AND lt.situacao =:_situacao "
							   + "ORDER BY lt.id ", Lote.class)
					.setParameter("_evento", evento)
					.setParameter("_situacao", ESituacao.ATIVO)
					.setParameter("_dataAtual", Calendar.getInstance())
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Lote> consultarLoteValidosPorGuicheEvento(Guiche guiche, Evento evento) {
		try {
			return getEm().createQuery("SELECT lt FROM Lote lt "
							   + "JOIN lt.guichesLotes gl "
							   + "JOIN gl.guiche g "
							   + "JOIN FETCH lt.evento ev "
							   + "JOIN FETCH lt.usuario usu " 
							   + "JOIN FETCH lt.ingressoTipo it "
							   + "WHERE lt.evento =:_evento AND g =:_guiche "
							   + "AND (:_dataAtual BETWEEN lt.dataInicio AND lt.dataFim) "
							   + "AND it.cortesia = 'FALSE' AND it.situacao =:_situacao AND lt.situacao =:_situacao "
							   + "AND gl.situacao =:_situacao "
							   + "ORDER BY lt.id ", Lote.class)
					.setParameter("_guiche", guiche)
					.setParameter("_evento", evento)
					.setParameter("_situacao", ESituacao.ATIVO)
					.setParameter("_dataAtual", Calendar.getInstance())
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public Lote consultarLoteCortesiaValidoPorEvento(Evento evento) {
		try {
			return getEm().createQuery("SELECT lt FROM Lote lt "
							   + "JOIN FETCH lt.evento ev "
							   + "JOIN FETCH lt.usuario usu " 
							   + "JOIN FETCH lt.ingressoTipo it "
							   + "WHERE lt.evento =:_evento "
							   + "AND (:_dataAtual BETWEEN lt.dataInicio AND lt.dataFim) "
							   + "AND it.cortesia = 'TRUE' AND it.situacao = 'ATIVO' "
							   + "ORDER BY lt.id DESC", Lote.class)
					.setParameter("_evento", evento)
					.setParameter("_dataAtual", Calendar.getInstance())
					.setMaxResults(SistemaConstantes.UM)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new BaseDAOException("Não há lote de cortesia válido para o evento: " + evento.getDescricao());
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public Lote consultarPorIdComIngressoTipo(Integer id) {
		try {
			return getEm()
					.createQuery("SELECT lt FROM Lote lt "
							   + "JOIN FETCH lt.ingressoTipo it "
							   + "JOIN FETCH lt.evento ev "
							   + "WHERE lt.id =:_id ", Lote.class)
					.setParameter("_id", id)
					.getSingleResult();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Lote consultarCompletoPorId(Integer idLote) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT lt FROM Lote lt ");
			sql.append("JOIN FETCH lt.evento ev ");
			sql.append("JOIN FETCH lt.usuario usu ");
			sql.append("JOIN FETCH lt.ingressoTipo it ");
			sql.append("WHERE lt.id =:_lote ");
			
			return getEm()
					.createQuery(sql.toString(), Lote.class)
					.setParameter("_lote", idLote)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarGuiches(Lote lote, List<Guiche> guichesAssociados) {
		try {
//			removerGuichePorEvento(lote);
			for (Guiche guiche : guichesAssociados) {
				GuicheLote guicheLote = new GuicheLote(guiche, lote);
				getEm().persist(guicheLote);
				if (guiche.getOffline().equals(EBoolean.TRUE)) {
					gerarIngressosOffline(guicheLote);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	private void gerarIngressosOffline(GuicheLote guicheLote) throws BaseServicoException {
		try {
			IngressoSituacao is = ingressoSituacaoDao.consultarPorDescricao("LOTE");
			Promocao promocao = promocaoDao.consultarPorEvento(guicheLote.getLote().getEvento());
			Ingresso ingresso = null;
			List<Ingresso> ingressos = new ArrayList<Ingresso>();
			for (int i = 0; i < guicheLote.getLote().getQuantidadeIngressos(); i++) {
				ingresso = new Ingresso();
				ingresso.setGuicheLote(guicheLote);
				ingresso.setIngressoSituacao(is);
				if (promocao != null) {
					ingresso.setCodigoPromocao(Util.gerarCodigoAleatorio(SistemaConstantes.SEIS));
				}
				ingressos.add(ingresso);
			}
			ingressoDao.salvar(ingressos);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}