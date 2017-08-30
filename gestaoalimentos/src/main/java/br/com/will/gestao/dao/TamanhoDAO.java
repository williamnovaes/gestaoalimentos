package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
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
//		try {
//			return new SQLFilter.SQLFilterBuilder(ProdutoTipo.class, getEm(), filtravel)
//					.setupPaginador(paginador)
//					.filterSimpleBy("pt.descricao")
//					.orderBy("pt.descricao")
//					.sortedBy(ESortedBy.ASC)
//					.build()
//					.dadosPaginados();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BaseDAOException(e.getMessage());
//		}
		return null;
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
}