package br.com.nx.tickets.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.EventoSocioTorcedorDAO;
import br.com.nx.tickets.entidade.EventoSocioTorcedor;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class EventoSocioTorcedorServico extends BaseServico<EventoSocioTorcedor> {

	private static final long serialVersionUID = 1L;
	@Inject
	private EventoSocioTorcedorDAO eventoSocioTorcedorDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(eventoSocioTorcedorDao);
	}
	
	public EventoSocioTorcedor obterPorEventoSocioTorcedor(EventoSocioTorcedor eventoSocioTorcedor) throws BaseServicoException {
		try {
			return eventoSocioTorcedorDao.consultarPorEventoSocioTorcedor(eventoSocioTorcedor);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}