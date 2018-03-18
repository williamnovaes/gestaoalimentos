package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.StatusAtendimentoDAO;
import br.com.will.gestao.entidade.StatusAtendimento;

@Stateless
public class StatusAtendimentoServico extends BaseServico<StatusAtendimento> {

	private static final long serialVersionUID = 1L;
	@Inject
	private StatusAtendimentoDAO statusAtendimentoDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(statusAtendimentoDao);
	}
}
