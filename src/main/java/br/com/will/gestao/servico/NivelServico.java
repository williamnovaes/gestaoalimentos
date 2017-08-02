package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.NivelDAO;
import br.com.will.gestao.entidade.Nivel;

@Stateless
public class NivelServico extends BaseServico<Nivel> {

	private static final long serialVersionUID = 1L;
	@Inject
	private NivelDAO nivelDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(nivelDao);
	}
}