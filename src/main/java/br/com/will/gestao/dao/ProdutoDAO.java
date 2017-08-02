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
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class ProdutoDAO extends BaseDAO<Produto> {

	private static final long serialVersionUID = 1L;

	public ProdutoDAO() {
		super(Produto.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(Produto t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Produto.class, getEm(), filtravel).setupPaginador(paginador)
					.filterSimpleBy("p.nome").orderBy("p.index").sortedBy(ESortedBy.ASC).build().dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Produto> consultarTodosPorTipo(ProdutoTipo produtoTipo) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT p FROM Produto p ");
			sql.append(" JOIN FETCH p.produtoTipo pt ");
			sql.append(" WHERE pt =:_produtoTipo ");
			sql.append(" ORDER BY p.id ");
			
			return getEm().createQuery(sql.toString(), Produto.class)
						  .setParameter("_produtoTipo", produtoTipo)
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<Produto> consultarTodosPorIdTipo(Integer idTipo) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT p FROM Produto p ");
			sql.append(" JOIN FETCH p.produtoTipo pt ");
			sql.append(" WHERE pt.id =:_produtoTipo ");
			sql.append(" ORDER BY p.id ");
			
			return getEm().createQuery(sql.toString(), Produto.class)
						  .setParameter("_produtoTipo", idTipo)
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Produto consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT p FROM Produto p ");
			sql.append(" JOIN FETCH p.produtoTipo pt ");
			sql.append(" WHERE p.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Produto.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre){
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}