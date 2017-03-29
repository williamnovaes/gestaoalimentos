package br.com.nx.tickets.dao;

import java.util.List;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.ArquivoAplicativo;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class ArquivoAplicativoDAO extends BaseDAO<ArquivoAplicativo> {

	private static final long serialVersionUID = 1L;

	public ArquivoAplicativoDAO() {
		super(ArquivoAplicativo.class);
	}

	@Override
	public void verificarDuplicidade(ArquivoAplicativo t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(ArquivoAplicativo.class, getEm(), filtravel)
								.setupPaginador(paginador)
								.filterSimpleBy("aa.id")
								.orderBy("aa.id")
								.sortedBy(ESortedBy.ASC)
								.build()
								.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}