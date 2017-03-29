package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.LoteDAO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class LoteServico extends BaseServico<Lote> {

	private static final long serialVersionUID = 1L;
	@Inject
	private LoteDAO loteDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(loteDao);
	}
	
	public List<Lote> obterLotePorEvento(Evento evento) throws BaseServicoException {
		try {
			return loteDao.consultarLotePorEvento(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Lote> obterLotesValidosPorEvento(Evento evento) throws BaseServicoException {
		try {
			return loteDao.consultarLoteValidosPorEvento(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Lote> obterLotesValidosPorGuicheEvento(Guiche guiche, Evento evento) throws BaseServicoException {
		try {
			return loteDao.consultarLoteValidosPorGuicheEvento(guiche, evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Lote obterLoteCortesiaValidoPorEvento(Evento evento) throws BaseServicoException {
		try {
			return loteDao.consultarLoteCortesiaValidoPorEvento(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Lote obterPorIdComIngressoTipo(Integer id) throws BaseServicoException {
		try {
			return loteDao.consultarPorIdComIngressoTipo(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Lote obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return loteDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
		
	}

	public void atualizarGuiches(Lote lote, List<Guiche> guichesAssociados) throws BaseServicoException {
		try {
			loteDao.atualizarGuiches(lote, guichesAssociados);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
}