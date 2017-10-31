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
import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class TamanhoDAO extends BaseDAO<Tamanho> {

	private static final long serialVersionUID = 1L;

	public TamanhoDAO() {
		super(Tamanho.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(Tamanho t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Tamanho.class, getEm(), filtravel)
				    .setupPaginador(paginador)
				    .filterSimpleBy("t.descricao")
				    .orderBy("t.descricao")
				    .sortedBy(ESortedBy.ASC)
				    .build()
				    .dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Tamanho> consultarPorProdutoTipo(ProdutoTipo produtoTipo) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT t FROM Tamanho t ");
			sql.append(" JOIN FETCH t.produtoTipo pt ");
			sql.append(" JOIN FETCH pt.empresa e ");
			sql.append(" WHERE pt =:_produtoTipo ");
			
			return getEm().createQuery(sql.toString(), Tamanho.class)
						  .setParameter("_produtoTipo", produtoTipo)
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Tamanho consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT t FROM Tamanho t ");
			sql.append(" LEFT JOIN FETCH t.produto p ");
			sql.append(" WHERE t.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Tamanho.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Tamanho> consultarTodosDisponiveis(String coluna) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT t FROM Tamanho t ");
			sql.append(" LEFT JOIN FETCH t.tamanhoTipo tt ");
			sql.append(" WHERE t.produto IS NULL");
			sql.append(" ORDER BY t.").append(coluna);
			
			return getEm().createQuery(sql.toString(), Tamanho.class).getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Tamanho> consultarPorIds(List<Integer> ids) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT t FROM Tamanho t ");
			sql.append(" LEFT JOIN FETCH t.tamanhoTipo tt ");
			sql.append(" WHERE t.id IN (:_ids) ");
			sql.append(" ORDER BY t.id ");
			
			return getEm().createQuery(sql.toString(), Tamanho.class)
						  .setParameter("_ids", ids)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Tamanho> consultarPorProdutoAssociados(Produto produto) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT tm FROM Tamanho tm ");
			sql.append(" WHERE tm.produto =:_produto ");
			sql.append(" ODER BY tm.sequencia ");
			
			return getEm().createQuery(sql.toString(), Tamanho.class)
						  .setParameter("_produto", produto)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Tamanho> consultarTodosPorSituacao(ESituacao situacao, boolean desassociados) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT tm FROM Tamanho tm ");
			sql.append(" WHERE tm.situacao =:_situacao ");
			if (desassociados) {
				sql.append(" AND tm.produto IS NULL ");
			}
			sql.append(" ORDER BY tm.sequencia ");
			
			return getEm().createQuery(sql.toString(), Tamanho.class)
						  .setParameter("_situacao", ESituacao.ATIVO)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void alterarProduto(Tamanho t) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE Tamanho ");
			sql.append(" SET produto =:_produto ");
			sql.append(" WHERE id =:_idTamanho ");
			
			getEm().createQuery(sql.toString())
				   .setParameter("_produto", t.getProduto())
				   .setParameter("_idTamanho", t.getId())
				   .executeUpdate();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}