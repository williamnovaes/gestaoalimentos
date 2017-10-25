package br.com.will.gestao.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.SaborDAO;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class SaborServico extends BaseServico<Sabor> {

	private static final long serialVersionUID = 1L;
	@Inject
	private SaborDAO saborDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(saborDao);
	}

	public Sabor obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return saborDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Sabor> obterTodosPorProduto(Produto produto) throws BaseServicoException {
		try {
			return saborDao.consultarTodosPorProduto(produto);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}