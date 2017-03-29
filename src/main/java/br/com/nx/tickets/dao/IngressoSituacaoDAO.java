package br.com.nx.tickets.dao;

import java.util.List;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class IngressoSituacaoDAO extends BaseDAO<IngressoSituacao> {

	private static final long serialVersionUID = 1L;

	public IngressoSituacaoDAO() {
		super(IngressoSituacao.class);
	}

	@Override
	public void verificarDuplicidade(IngressoSituacao t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		return null;
	}

	public List<IngressoSituacao> consultarPorAtivo(EBoolean ativo) {
		try {
			return getEm().createQuery("SELECT iss FROM IngressoSituacao iss WHERE iss.ativo =:_ativo",
					IngressoSituacao.class).setParameter("_ativo", ativo).getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}