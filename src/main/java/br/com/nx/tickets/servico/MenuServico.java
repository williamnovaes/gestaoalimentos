package br.com.nx.tickets.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.MenuDAO;
import br.com.nx.tickets.entidade.permissao.Menu;

@Stateless
public class MenuServico extends BaseServico<Menu> {

	private static final long serialVersionUID = 1L;
	@Inject
	private MenuDAO menuDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(menuDao);
	}
}