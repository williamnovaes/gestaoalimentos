package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.SaborIngrediente;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class ProdutoIngredienteDAO extends BaseDAO<SaborIngrediente> {

	private static final long serialVersionUID = 1L;

	public ProdutoIngredienteDAO() {
		super(SaborIngrediente.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(SaborIngrediente t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
//			return new SQLFilter.SQLFilterBuilder(ProdutoIngrediente.class, getEm(), filtravel)
//							    .setupPaginador(paginador)
//							    .filterSimpleBy("i.descricao")
//							    .orderBy("i.descricao")
//							    .sortedBy(ESortedBy.ASC)
//							    .build()
//							    .dadosPaginados();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}