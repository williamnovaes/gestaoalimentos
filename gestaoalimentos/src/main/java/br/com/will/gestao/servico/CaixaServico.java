package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.CaixaDAO;
import br.com.will.gestao.entidade.Caixa;

@Stateless
public class CaixaServico extends BaseServico<Caixa> {

	private static final long serialVersionUID = 1L;
	@Inject
	private CaixaDAO caixaDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(caixaDao);
	}
}
