package br.com.nx.tickets.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.ImpressaoTesteLogDAO;
import br.com.nx.tickets.entidade.ImpressaoTesteLog;

@Stateless
public class ImpressaoTesteLogServico extends BaseServico<ImpressaoTesteLog> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImpressaoTesteLogDAO impressaoTesteLogDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(impressaoTesteLogDao);
	}
}