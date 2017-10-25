package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.ProdutoIngredienteDAO;
import br.com.will.gestao.entidade.SaborIngrediente;

@Stateless
public class ProdutoIngredienteServico extends BaseServico<SaborIngrediente> {

	private static final long serialVersionUID = 1L;
	@Inject
	private ProdutoIngredienteDAO produtoIngredienteDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(produtoIngredienteDao);
	}
}
