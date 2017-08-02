package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.Empresa;
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
		return null;
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

	public List<ProdutoTipo> consultarTodosAtivosPorEmpresa(Empresa empresa) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pt FROM ProdutoTipo pt  ");
			sql.append(" WHERE pt.situacao =:_situacao ");
			sql.append(" AND pt.empresa =:_empresa ORDER BY pt.descricao ");
			
			return getEm().createQuery(sql.toString(), ProdutoTipo.class)
						  .setParameter("_situacao", ESituacao.ATIVO)
						  .setParameter("_empresa", empresa)
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}