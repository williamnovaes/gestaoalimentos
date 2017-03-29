package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.IngressoSituacaoDAO;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class IngressoSituacaoServico extends BaseServico<IngressoSituacao> {

	private static final long serialVersionUID = 1L;
	@Inject
	private IngressoSituacaoDAO ingressoSituacaoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(ingressoSituacaoDao);
	}

	public List<IngressoSituacao> obterPorAtivo(EBoolean ativo) throws BaseServicoException {
		try {
			return ingressoSituacaoDao.consultarPorAtivo(ativo);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}