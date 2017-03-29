package br.com.nx.tickets.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoPromocao;
import br.com.nx.tickets.entidade.IngressoPromocaoPK;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class IngressoPromocaoDAO extends BaseDAO<IngressoPromocao> {

	private static final long serialVersionUID = 1L;

	public IngressoPromocaoDAO() {
		super(IngressoPromocao.class);
	}
	
	@Override
	public void verificarDuplicidade(IngressoPromocao t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}
	
	public IngressoPromocao consultarPorId(IngressoPromocaoPK id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ip FROM IngressoPromocao ip ");
			sql.append(" WHERE ip.id =:_id ");
			return getEm().createQuery(sql.toString(), IngressoPromocao.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(IngressoPromocao.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("pr.descricao")
									.orderBy("ip.dataCadastro")
									.sortedBy(ESortedBy.DESC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public Promocao consultarPorIngresso(Ingresso ingresso) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pr FROM Ingresso i ");
			sql.append(" JOIN i.guicheLote gl ");
			sql.append(" JOIN gl.lote lt ");
			sql.append(" JOIN lt.evento ev ");
			sql.append(" JOIN ev.promocoes pr ");
			sql.append(" WHERE i =:_ingresso ");
			return getEm().createQuery(sql.toString(), Promocao.class)
						  .setParameter("_ingresso", ingresso)
						  .getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<IngressoPromocao> consultarParaSorteioPorEvento(Evento evento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ip FROM IngressoPromocao ip ");
			sql.append(" JOIN FETCH ip.ingresso i ");
			sql.append(" JOIN FETCH ip.promocao p ");
			sql.append(" JOIN FETCH p.evento e ");
			sql.append(" JOIN i.ingressoSituacao iss ");
			sql.append(" WHERE iss.considerarEntradaCaixa = 'TRUE' AND e =:_evento ");
			sql.append("AND ip.dataSorteado is null ");
			sql.append("AND ip.cpf not in (SELECT cpf FROM IngressoPromocao WHERE dataSorteado is not null) ");
			sql.append("AND ip.nome not in (SELECT nome FROM IngressoPromocao WHERE dataSorteado is not null) ");
			return getEm().createQuery(sql.toString(), IngressoPromocao.class)
						  .setParameter("_evento", evento)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public void atualizarIngressoPromocaoSorteado(IngressoPromocao ingressoPromocao) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE IngressoPromocao SET dataSorteado =:_dataSorteado ");
			sql.append(" WHERE id =:_id ");
			getEm().createQuery(sql.toString())
						  .setParameter("_id", ingressoPromocao.getId())
						  .setParameter("_dataSorteado", Calendar.getInstance())
						  .executeUpdate();
			getEm().flush();
			getEm().clear();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public void limparPromocao(Promocao promocao) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE IngressoPromocao SET dataSorteado = null ");
			sql.append(" WHERE promocao =:_promocao ");
			getEm().createQuery(sql.toString())
						  .setParameter("_promocao", promocao)
						  .executeUpdate();
			getEm().flush();
			getEm().clear();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}