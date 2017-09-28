package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.IngredienteDAO;
import br.com.will.gestao.entidade.Ingrediente;

@Stateless
public class IngredienteServico extends BaseServico<Ingrediente> {

	private static final long serialVersionUID = 1L;
	@Inject
	private IngredienteDAO ingredienteDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(ingredienteDao);
	}
}
