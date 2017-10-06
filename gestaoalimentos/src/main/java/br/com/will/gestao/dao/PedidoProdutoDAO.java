package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.PedidoProduto;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class PedidoProdutoDAO extends BaseDAO<PedidoProduto> {

	private static final long serialVersionUID = 1L;

	public PedidoProdutoDAO() {
		super(PedidoProduto.class);
	}

	@Override
	public void verificarDuplicidade(PedidoProduto t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
//			FiltroUsuarioPedido filtroPedido = (FiltroUsuarioPedido) filtravel;
			return new SQLFilter.SQLFilterBuilder(PedidoProduto.class, getEm(), filtravel)
					.setupPaginador(paginador)
					.filterSimpleBy("cl.nome")
					.orderBy("pd.dataCadastro")
					.sortedBy(ESortedBy.DESC).build()
					.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}