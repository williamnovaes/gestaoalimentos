package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.EventoDAO;
import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.entidade.Atracao;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.EventoUsuarioRetirada;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class EventoServico extends BaseServico<Evento> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EventoDAO eventoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(eventoDao);
	}

	public Evento obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return eventoDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Evento> obterEventosPorCliente(Cliente cliente) throws BaseServicoException {
		try {
			return eventoDao.consultarEventosPorCliente(cliente);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public void atualizarPontosVendas(Evento evento, List<PontoVenda> pontosVendas) throws BaseServicoException {
		try {
			eventoDao.atualizarPontosVendas(evento, pontosVendas);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public void atualizarAtracoes(Evento evento, List<Atracao> atracoes) throws BaseServicoException {
		try {
			eventoDao.atualizarAtracoes(evento, atracoes);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public void atualizarUsuarios(Evento evento, List<Usuario> usuarios) throws BaseServicoException {
		try {
			eventoDao.atualizarUsuarios(evento, usuarios);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<PontoVenda> obterPontosVendas(Evento evento) throws BaseServicoException {
		try {
			return eventoDao.consultarPontosVendas(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Evento> obterEventosPorPontoVenda(PontoVenda pontoVenda) throws BaseServicoException {
		try {
			return eventoDao.consultarEventosPorPontoVenda(pontoVenda);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Evento> obterEventosVigentesPorPontoVenda(PontoVenda pontoVenda) throws BaseServicoException {
		try {
			return eventoDao.consultarEventosVigentesPorPontoVenda(pontoVenda);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Atracao> obterAtracoes(Evento evento) throws BaseServicoException {
		try {
			return eventoDao.consultarAtracoes(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Ingresso> obterPedidosPorEvento(Evento evento) throws BaseServicoException {
		try {
			return eventoDao.consultarPedidosPorEvento(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Evento obterPorIdComCliente(Integer idEvento) throws BaseServicoException {
		try {
			return eventoDao.consultarPorIdComCliente(idEvento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public EventoUsuarioRetirada validarUsuarioRetirada(Evento evento, Usuario usuario) throws BaseServicoException {
		try {
			return eventoDao.validarUsuarioRetirada(evento, usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Usuario> obterUsuariosRetirada(Evento evento) throws BaseServicoException {
		try {
			return eventoDao.consultarUsuariosRetirada(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Evento> obterDisponiveisPorUsuario(Usuario usuario) throws BaseServicoException {
		try {
			return eventoDao.consultarAtivosPorUsuario(usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<ExtratoGuicheDTO> obterExtratoVenda(Evento evento) throws BaseServicoException {
		try {
			return eventoDao.consultarExtratoVenda(evento);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Evento> obterAtivosPorGuiche(Guiche guiche) throws BaseServicoException {
		try {
			return eventoDao.consultarAtivosPorGuiche(guiche);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Evento> obterTodosPorGuiche(Guiche guiche) throws BaseServicoException {
		try {
			return eventoDao.consultarTodosPorGuiche(guiche);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Evento> obterAtivos() throws BaseServicoException {
		try {
			return eventoDao.consultarAtivos();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
}