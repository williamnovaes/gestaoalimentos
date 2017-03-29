package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.PaginaDAO;
import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.permissao.Pagina;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class PaginaServico extends BaseServico<Pagina> {

	private static final long serialVersionUID = 1L;
	@Inject
	private PaginaDAO paginaDao;


	@Override
	@PostConstruct
	public void inicializar() {
		setDao(paginaDao);
	}
	
	public void atualizarNiveis(Pagina pagina, List<Nivel> niveis) throws BaseServicoException {
		try {
			paginaDao.atualizarNiveis(pagina, niveis);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Nivel> obterNiveisPorPagina(Pagina pagina) throws BaseServicoException {
		try {
			return paginaDao.consultarNiveisPorPagina(pagina);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Pagina> obterPorNivel(Nivel nivel) throws BaseServicoException {
		try {
			return paginaDao.consultarPorNivel(nivel);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Pagina obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return paginaDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}