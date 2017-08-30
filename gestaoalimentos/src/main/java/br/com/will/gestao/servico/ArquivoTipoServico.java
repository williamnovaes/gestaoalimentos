package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.ArquivoTipoDAO;
import br.com.will.gestao.entidade.ArquivoTipo;

@Stateless
public class ArquivoTipoServico extends BaseServico<ArquivoTipo> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ArquivoTipoDAO arquivoTipoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(arquivoTipoDao);
	}
}