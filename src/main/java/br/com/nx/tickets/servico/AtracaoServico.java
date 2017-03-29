package br.com.nx.tickets.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.AtracaoDAO;
import br.com.nx.tickets.entidade.Atracao;

@Stateless
public class AtracaoServico extends BaseServico<Atracao> {

	private static final long serialVersionUID = 1L;
	@Inject
	private AtracaoDAO atracaoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(atracaoDao);
	}
}