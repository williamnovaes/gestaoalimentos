package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.MenuDAO;
import br.com.will.gestao.entidade.permissao.Menu;

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