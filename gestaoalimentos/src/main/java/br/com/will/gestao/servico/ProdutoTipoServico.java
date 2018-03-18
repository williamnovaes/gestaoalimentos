package br.com.will.gestao.servico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.BaseDAOException;
import br.com.will.gestao.dao.ProdutoTipoDAO;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class ProdutoTipoServico extends BaseServico<ProdutoTipo> {

	private static final long serialVersionUID = 1L;
	@Inject
	private ProdutoTipoDAO produtoTipoDao;
	@EJB
	private ProdutoServico produtoServico;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(produtoTipoDao);
	}

	public List<ProdutoTipo> obterTodosAtivos() throws BaseServicoException {
		try {
			return produtoTipoDao.consultarTodosAtivos();
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Map<ProdutoTipo, List<Produto>> obterTodosAtivosCache()
			throws BaseServicoException {
		try {
			Map<ProdutoTipo, List<Produto>> cacheProdutos = new HashMap<>();
			List<ProdutoTipo> produtosTipo = produtoTipoDao.consultarTodosAtivos();
			for (ProdutoTipo produtoTipo : produtosTipo) {
				List<Produto> produtos = new ArrayList<>();
				produtos = produtoServico.obterTodosPorTipo(produtoTipo);
				cacheProdutos.put(produtoTipo, produtos);
			}

			return cacheProdutos;
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public ProdutoTipo obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return produtoTipoDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<ProdutoTipo> obterTodosAtivosComProduto() throws BaseServicoException {
		try {
			return produtoTipoDao.consultarTodosAtivosComProduto();
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public ProdutoTipo obterPorProduto(Produto produto) throws BaseServicoException {
		try {
			return produtoTipoDao.consultarPorProduto(produto);
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}
