package br.com.will.gestao.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.Promocao;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class PromocaoDAO extends BaseDAO<Promocao> {

	private static final long serialVersionUID = 1L;

	public PromocaoDAO() {
		super(Promocao.class);
	}

	@Override
	public void verificarDuplicidade(Promocao t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Promocao.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("pr.descricao")
									.orderBy("pr.id")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Promocao> consultarCompletoPorUsuario(Integer idUsuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pr FROM Promocao pr ");
			sql.append("JOIN pr.usuario us ");
			sql.append("WHERE us.id =:_id");

			return getEm().createQuery(sql.toString(), Promocao.class)
					.setParameter("_id", idUsuario)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public Promocao consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pr FROM Promocao pr ");
			sql.append(" JOIN FETCH pr.usuario US ");
			sql.append(" WHERE pr.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Promocao.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}