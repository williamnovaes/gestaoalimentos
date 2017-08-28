package br.com.will.gestao.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.ProdutoTipoDAO;
import br.com.will.gestao.entidade.Empresa;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class ProdutoTipoServico extends BaseServico<ProdutoTipo> {

	private static final long serialVersionUID = 1L;
	@Inject
	private ProdutoTipoDAO produtoTipoDao;
	
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

	public ProdutoTipo obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return produtoTipoDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
