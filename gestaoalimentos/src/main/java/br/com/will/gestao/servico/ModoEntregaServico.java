package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.ModoEntregaDAO;
import br.com.will.gestao.entidade.ModoEntrega;

@Stateless
public class ModoEntregaServico extends BaseServico<ModoEntrega> {

	private static final long serialVersionUID = 1L;
	@Inject
	private ModoEntregaDAO modoEntregaDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(modoEntregaDao);
	}
}
