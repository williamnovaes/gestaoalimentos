package br.com.nx.tickets.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.CategoriaDAO;
import br.com.nx.tickets.entidade.Categoria;

@Stateless
public class CategoriaServico extends BaseServico<Categoria> {

	private static final long serialVersionUID = 1L;
	@Inject
	private CategoriaDAO categoriaDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(categoriaDao);
	}
}