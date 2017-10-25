package br.com.will.gestao.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class SaborDAO extends BaseDAO<Sabor> {

	private static final long serialVersionUID = 1L;

	public SaborDAO() {
		super(Sabor.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(Sabor t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Sabor.class, getEm(), filtravel)
							    .setupPaginador(paginador)
							    .filterSimpleBy("s.descricao")
							    .orderBy("s.index")
							    .sortedBy(ESortedBy.ASC)
							    .build()
							    .dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Sabor consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT s FROM Sabor s ");
			sql.append(" JOIN FETCH s.empresa em ");
			sql.append(" WHERE s.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Sabor.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre) {
			getLog().error("SABOR NAO ENCONTRADO");
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Sabor> consultarTodosPorProduto(Produto produto) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT s FROM Sabor s ");
			sql.append(" JOIN FETCH s.produto p ");
			sql.append(" JOIN FETCH s.empresa e ");
			sql.append(" WHERE p =:_produto ");
			sql.append(" ORDER BY s.index ");
			
			return getEm().createQuery(sql.toString(), Sabor.class)
						  .setParameter("_produto", produto)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}