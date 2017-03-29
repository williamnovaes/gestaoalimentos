package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.GuicheLoteDAO;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.GuicheLote;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class GuicheLoteServico extends BaseServico<GuicheLote> {

	private static final long serialVersionUID = 1L;
	@Inject
	private GuicheLoteDAO guicheLoteDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(guicheLoteDao);
	}
	
	public GuicheLote obterPorGuicheLote(Guiche guiche, Lote lote) throws BaseServicoException {
		try {
			return guicheLoteDao.consultarPorGuicheLote(guiche, lote);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<GuicheLote> obterPorLote(Lote lote) throws BaseServicoException {
		try {
			return guicheLoteDao.consultarPorLote(lote);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}