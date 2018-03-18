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

public class ProdutoTipoDAO extends BaseDAO<ProdutoTipo> {

	private static final long serialVersionUID = 1L;

	public ProdutoTipoDAO() {
		super(ProdutoTipo.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT pt FROM ProdutoTipo pt ");
		sql.append(" WHERE pt.situacao =:_situacao ");
		sql.append(" ORDER BY pt.descricao ");
		return getEm().createQuery(sql.toString(), ProdutoTipo.class)
					  .setParameter("_situacao", situacao)
					  .getResultList();
	}

	@Override
	public void verificarDuplicidade(ProdutoTipo t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(ProdutoTipo.class, getEm(), filtravel)
					.setupPaginador(paginador)
					.filterSimpleBy("pt.descricao")
					.orderBy("pt.descricao")
					.sortedBy(ESortedBy.ASC)
					.build()
					.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<ProdutoTipo> consultarTodosAtivos() {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pt FROM ProdutoTipo pt  ");
			sql.append(" WHERE pt.situacao =:_situacao ");
			sql.append(" ORDER BY pt.sequencia ");
			
			return getEm().createQuery(sql.toString(), ProdutoTipo.class)
						  .setParameter("_situacao", ESituacao.ATIVO)
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public ProdutoTipo consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pt FROM ProdutoTipo pt ");
			sql.append(" WHERE pt.id =:_id ");
			
			return getEm().createQuery(sql.toString(), ProdutoTipo.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre) {
			getLog().info("NENHUM PRODUTO TIPO ENCONTRADO PARA O ID: " + id);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<ProdutoTipo> consultarTodosAtivosComProduto() {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pt FROM ProdutoTipo pt  ");
			sql.append(" JOIN FETCH pt.produtos p ");
			sql.append(" WHERE pt.situacao =:_situacao ");
			sql.append(" ORDER BY pt.sequencia ");
			
			return getEm().createQuery(sql.toString(), ProdutoTipo.class)
						  .setParameter("_situacao", ESituacao.ATIVO)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public ProdutoTipo consultarPorProduto(Produto produto) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pt FROM Produto p ");
			sql.append(" JOIN p.produtoTipo pt ");
			sql.append(" WHERE p =:_produto ");
			
			return getEm().createQuery(sql.toString(), ProdutoTipo.class)
						  .setParameter("_produto", produto)
						  .getSingleResult();
		} catch (NoResultException ne) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}		