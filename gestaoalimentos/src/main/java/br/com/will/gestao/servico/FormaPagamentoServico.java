package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.FormaPagamentoDAO;
import br.com.will.gestao.entidade.FormaPagamento;

@Stateless
public class FormaPagamentoServico extends BaseServico<FormaPagamento> {

	private static final long serialVersionUID = 1L;
	@Inject
	private FormaPagamentoDAO formaPagamentoDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(formaPagamentoDao);
	}
}
