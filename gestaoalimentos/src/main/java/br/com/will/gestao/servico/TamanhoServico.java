package br.com.will.gestao.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.TamanhoDAO;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class TamanhoServico extends BaseServico<Tamanho> {

	private static final long serialVersionUID = 1L;
	@Inject
	private TamanhoDAO tamanhoDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(tamanhoDao);
	}

	public List<Tamanho> obterPorProdutoTipo(ProdutoTipo produtoTipo) throws BaseServicoException {
		try {
			return tamanhoDao.consultarPorProdutoTipo(produtoTipo);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Tamanho obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return tamanhoDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Tamanho> obterTodosDisponiveis(String coluna) throws BaseServicoException {
		try {
			return tamanhoDao.consultarTodosDisponiveis(coluna);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Tamanho> obterPorIds(List<Integer> ids) throws BaseServicoException {
		try {
			return tamanhoDao.consultarPorIds(ids);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Tamanho> obterPorProdutoAssociados(Produto produto) throws BaseServicoException {
		try {
			return tamanhoDao.consultarPorProdutoAssociados(produto);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Tamanho> obterTodosPorSituacao(ESituacao situacao, boolean desassociados) throws BaseServicoException {
		try {
			return tamanhoDao.consultarTodosPorSituacao(situacao, desassociados);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void alterarProduto(Tamanho t) throws BaseServicoException {
		try {
			tamanhoDao.alterarProduto(t);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
