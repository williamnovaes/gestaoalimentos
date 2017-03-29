package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.LocalDAO;
import br.com.nx.tickets.entidade.Local;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class LocalServico extends BaseServico<Local> {

	private static final long serialVersionUID = 1L;
	@Inject
	private LocalDAO localDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(localDao);
	}

	public List<Portaria> obterPortariasPorLocal(Local local) throws BaseServicoException {
		try {
			return localDao.consultarPortariasPorLocal(local);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Local obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return localDao.consultarCompletoPorID(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}