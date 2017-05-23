package br.com.nx.tickets.dao;

import java.util.List;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.Categoria;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class CategoriaDAO extends BaseDAO<Categoria> {

	private static final long serialVersionUID = 1L;

	public CategoriaDAO() {
		super(Categoria.class);
	}

	@Override
	public void verificarDuplicidade(Categoria t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		return null;
//		try {
//			return new SQLFilter.SQLFilterBuilder(Cortesia.class, getEm(), filtravel)
//								.setupPaginador(paginador)
//								.filterSimpleBy("ct.descricao")
//								.orderBy("ct.descricao")
//								.sortedBy(ESortedBy.ASC)
//								.build()
//								.dadosPaginados();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BaseDAOException(e.getMessage());
//		}
	}
}