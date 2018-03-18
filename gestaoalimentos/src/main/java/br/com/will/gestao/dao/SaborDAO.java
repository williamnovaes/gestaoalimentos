package br.com.will.gestao.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
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
			sql.append(" JOIN s.produto p ");
			sql.append(" WHERE p =:_produto ");
			sql.append(" AND s.situacao =:_situacao ");
			sql.append(" ORDER BY s.sequencia ");
			
			return getEm().createQuery(sql.toString(), Sabor.class)
						  .setParameter("_produto", produto)
						  .setParameter("_situacao", ESituacao.ATIVO)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Sabor> consultarPorProdutoAssociados(Produto produto) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT sb FROM Sabor sb ");
			sql.append(" WHERE sb.produto =:_produto ");
			sql.append(" AND sb.situacao =:_situacao ");
			sql.append(" ORDER BY sb.index ");
			
			return getEm().createQuery(sql.toString(), Sabor.class)
						  .setParameter("_produto", produto)
						  .setParameter("_situacao", ESituacao.ATIVO)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Sabor> consultarTodosPorSituacao(ESituacao situacao, boolean desassociados) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT sb FROM Sabor sb ");
			sql.append(" WHERE  sb.situacao =:_situacao ");
			if (desassociados) {
				sql.append(" AND sb.produto IS NULL ");
			}
			sql.append(" ORDER BY sb.index ");
			
			return getEm().createQuery(sql.toString(), Sabor.class)
						  .setParameter("_situacao", situacao)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void alterarProduto(Sabor sabor) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE Sabor ");
			sql.append(" SET produto =:_produto ");
			sql.append(" WHERE id =:_idSabor ");
			
			getEm().createQuery(sql.toString())
				   .setParameter("_produto", sabor.getProduto())
				   .setParameter("_idSabor", sabor.getId())
				   .executeUpdate();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Sabor> consultarPorProdutoSituacao(Produto produto, ESituacao situacao) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT s FROM Sabor s ");
			sql.append(" JOIN s.produto p ");
			sql.append(" WHERE p =:_produto ");
			sql.append(" AND s.situacao =:_situacao ");
			sql.append(" ORDER BY s.sequencia ");
			
			return getEm().createQuery(sql.toString(), Sabor.class)
						  .setParameter("_produto", produto)
						  .setParameter("_situacao", situacao)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Sabor> consultarPorProdutoTipo(ProdutoTipo produtoTipo) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT s FROM Sabor s ");
			sql.append(" JOIN s.produto p ");
			sql.append(" JOIN p.produtoTipo pt ");
			sql.append(" WHERE pt =:_tipo ");
			sql.append(" ORDER BY s.sequencia ");
			
			return getEm().createQuery(sql.toString(), Sabor.class)
						  .setParameter("_tipo", produtoTipo)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}