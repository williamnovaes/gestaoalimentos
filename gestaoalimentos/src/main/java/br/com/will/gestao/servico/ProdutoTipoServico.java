package br.com.will.gestao.servico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import br.com.will.gestao.dao.ProdutoTipoDAO;
import br.com.will.gestao.entidade.Empresa;
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

	public List<ProdutoTipo> obterTodosAtivosPorEmpresa(Empresa empresa) throws BaseServicoException {
		try {
			return produtoTipoDao.consultarTodosAtivosPorEmpresa(empresa);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Map<ProdutoTipo, List<Produto>> obterTodosAtivosPorEmpresas(List<Empresa> empresas) throws BaseServicoException {
		try {
			Map<ProdutoTipo, List<Produto>> cacheProdutos = new HashMap<>();
			List<ProdutoTipo> produtosTipo = produtoTipoDao.consultarTodosAtivosPorEmpresas(empresas);
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
}
