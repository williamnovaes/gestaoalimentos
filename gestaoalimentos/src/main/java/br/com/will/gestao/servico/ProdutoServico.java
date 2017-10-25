package br.com.will.gestao.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.ProdutoDAO;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class ProdutoServico extends BaseServico<Produto> {

	private static final long serialVersionUID = 1L;
	@Inject
	private ProdutoDAO produtoDao;
	@EJB
	private SaborServico saborServico;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(produtoDao);
	}

	public List<Produto> obterTodosPorTipo(ProdutoTipo produtoTipo) throws BaseServicoException {
		try {
			return produtoDao.consultarTodosPorTipo(produtoTipo);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Produto> obterTodosPorIdTipo(Integer idTipo) throws BaseServicoException {
		try {
			return produtoDao.consultarTodosPorIdTipo(idTipo);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Produto obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return produtoDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Produto> obterTodosParaMenu(String ordem) throws BaseServicoException {
		try {
			List<Produto> produtos = produtoDao.consultarTodosParaMenu(ordem);
			for (Produto produto : produtos) {
				produto.setSabores(saborServico.obterTodosPorProduto(produto));
			}
			return produtos;
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
