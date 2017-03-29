package br.com.nx.tickets.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.CortesiaDAO;
import br.com.nx.tickets.entidade.Cortesia;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class CortesiaServico extends BaseServico<Cortesia> {

	private static final long serialVersionUID = 1L;
	@Inject
	private CortesiaDAO cortesiaDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(cortesiaDao);
	}

	public Cortesia obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return cortesiaDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}