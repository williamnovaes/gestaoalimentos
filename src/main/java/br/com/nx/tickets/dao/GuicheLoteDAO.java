package br.com.nx.tickets.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.GuicheLote;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class GuicheLoteDAO extends BaseDAO<GuicheLote> {

	private static final long serialVersionUID = 1L;

	public GuicheLoteDAO() {
		super(GuicheLote.class);
	}

	@Override
	public void verificarDuplicidade(GuicheLote t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}
	
	@Override
	public GuicheLote salvar(GuicheLote t) {
		try {
			GuicheLote gl = super.salvar(t);
			getEm().flush();
			getEm().clear();
			return gl;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Guiche.class, getEm(), filtravel).setupPaginador(paginador)
					.filterSimpleBy("g.id").orderBy("g.id").sortedBy(ESortedBy.ASC).build().dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public GuicheLote consultarPorGuicheLote(Guiche guiche, Lote lote) {
		try {
			GuicheLote gl = new GuicheLote(guiche, lote);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT gl ");
			sql.append(" FROM GuicheLote gl WHERE gl.id =:_id ");
			return getEm().createQuery(sql.toString(), GuicheLote.class).setParameter("_id", gl.getId()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<GuicheLote> consultarPorLote(Lote lote) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT gl FROM GuicheLote gl ");
			sql.append(" JOIN FETCH gl.lote lt ");
			sql.append(" JOIN FETCH gl.guiche g ");
			sql.append(" JOIN FETCH g.bilheteria b ");
			sql.append(" JOIN FETCH b.portaria p ");
			sql.append(" JOIN FETCH p.local lc ");
			sql.append(" WHERE lt =:_lote ");
			sql.append(" ORDER BY g.id ");
			
			return getEm().createQuery(sql.toString(), GuicheLote.class)
					.setParameter("_lote", lote)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}