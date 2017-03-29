package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.BaseDAOException;
import br.com.nx.tickets.dao.GuicheDAO;
import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.dto.ExtratoRetiradaDTO;
import br.com.nx.tickets.entidade.Bilheteria;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class GuicheServico extends BaseServico<Guiche> {

	private static final long serialVersionUID = 1L;
	@EJB
	private RetiradaServico retiradaServico;

	@Inject
	private GuicheDAO guicheDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(guicheDao);
	}

	public void limparGuicheUsuario(Integer id) throws BaseServicoException {
		try {
			guicheDao.limparGuicheUsuario(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Guiche> obterDisponiveisPorUsuario(Usuario usuario) throws BaseServicoException {
		try {
			return guicheDao.consultarDisponiveisPorUsuario(usuario);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Guiche obterPorUsuario(Usuario usuario) throws BaseServicoException {
		try {
			return guicheDao.consultarPorUsuario(usuario);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Guiche> obterPorPortaria(Portaria portaria) throws BaseServicoException {
		try {
			return guicheDao.consultarPorPortaria(portaria);
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Guiche> obterPorBilheteria(Bilheteria bi) throws BaseServicoException {
		try {
			return guicheDao.consultarPorBilheteria(bi);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Guiche obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return guicheDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Guiche> obterPorLote(Lote lt) throws BaseServicoException {
		try {
			return guicheDao.consultarPorLote(lt);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public ExtratoRetiradaDTO obterExtratoRetiradaPorGuicheEvento(Guiche guiche, Evento evento)
			throws BaseServicoException {
		try {
			ExtratoRetiradaDTO extratoRetiradaDto = guicheDao.consultarExtratoRetiradaPorGuicheEvento(guiche, evento);
			extratoRetiradaDto.setRetiradas(retiradaServico.obterPorGuicheEvento(guiche, evento));
			return extratoRetiradaDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<ExtratoGuicheDTO> obterExtratoPorGuicheEvento(Guiche guiche, Evento evento)
			throws BaseServicoException {
		try {
			return guicheDao.consultarExtratoPorGuicheEvento(guiche, evento);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public ExtratoGuicheDTO obterExtratoTotalGuiche(Guiche guiche, Evento evento) throws BaseServicoException {
		try {
			return guicheDao.consultarExtratoTotalGuiche(guiche, evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Guiche> obterTodosCompletos() throws BaseServicoException {
		try {
			return guicheDao.consultarTodosCompletos();
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Guiche> obterPorEvento(Evento evento, EBoolean offline) throws BaseServicoException {
		try {
			return guicheDao.consultarPorEvento(evento, offline);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}