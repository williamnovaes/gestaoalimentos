package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.EmpresaDAO;
import br.com.will.gestao.entidade.Empresa;

@Stateless
public class EmpresaServico extends BaseServico<Empresa> {

	private static final long serialVersionUID = 1L;
	@Inject
	private EmpresaDAO empresaDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(empresaDao);
	}
}
