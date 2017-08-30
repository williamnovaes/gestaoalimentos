package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.BaseDAOException;
import br.com.will.gestao.dao.EstadoDAO;
import br.com.will.gestao.entidade.Estado;
import br.com.will.gestao.servico.exception.BaseServicoException;



@Stateless
public class EstadoServico extends BaseServico<Estado> {

	private static final long serialVersionUID = 1L;
	@Inject
	private EstadoDAO estadoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(estadoDao);
	}

	public Estado obterPorUF(String uf) throws BaseServicoException {
		try {
			return estadoDao.buscarPorUF(uf);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}