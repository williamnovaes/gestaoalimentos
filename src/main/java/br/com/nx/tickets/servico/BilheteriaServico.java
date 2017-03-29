package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.BilheteriaDAO;
import br.com.nx.tickets.entidade.Bilheteria;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class BilheteriaServico extends BaseServico<Bilheteria> {

	private static final long serialVersionUID = 1L;
	@Inject
	private BilheteriaDAO bilheteriaDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(bilheteriaDao);
	}

	public List<Bilheteria> obterPorPontoVenda(PontoVenda pv) throws BaseServicoException {
		try {
			return bilheteriaDao.consultarPorPontoVenda(pv);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Bilheteria obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return bilheteriaDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}