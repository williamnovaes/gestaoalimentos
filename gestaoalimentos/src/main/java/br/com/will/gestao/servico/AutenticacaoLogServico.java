package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.AutenticacaoLogDAO;
import br.com.will.gestao.entidade.AutenticacaoLog;

@Stateless
public class AutenticacaoLogServico extends BaseServico<AutenticacaoLog> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AutenticacaoLogDAO autenticacaoLogDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(autenticacaoLogDao);
	}
}