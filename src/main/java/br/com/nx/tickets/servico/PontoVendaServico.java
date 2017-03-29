package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.BaseDAOException;
import br.com.nx.tickets.dao.PontoVendaDAO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class PontoVendaServico extends BaseServico<PontoVenda> {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private RetiradaServico retiradaServico;
	
	@Inject
	private PontoVendaDAO pontoVendaDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(pontoVendaDao);
	}
	
	public PontoVenda obterPorUsuario(Integer idUsuario) throws BaseServicoException {
		try {
			return pontoVendaDao.consultarPorUsuario(idUsuario);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void atualizarEventos(PontoVenda pontoVenda, List<Evento> eventosAssociados) throws BaseServicoException {
		try {
			pontoVendaDao.atualizarEventos(pontoVenda, eventosAssociados);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Integer obterQuantidadeIngressoVendidoPorEventoPontoVenda(Evento evento, PontoVenda pontoVenda) throws BaseServicoException {
		try {
			return pontoVendaDao.consultarQuantidadeIngressoVendidoPorEventoPontoVenda(evento, pontoVenda);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void atualizarUsuarios(PontoVenda pontoVenda, List<Usuario> usuarios) throws BaseServicoException {
		try {
			pontoVendaDao.atualizarUsuarios(pontoVenda, usuarios);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<PontoVenda> obterPorEvento(Evento ev) throws BaseServicoException {
		try {
			return pontoVendaDao.consultarPorEvento(ev);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}