package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.PortariaDAO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class PortariaServico extends BaseServico<Portaria> {

	private static final long serialVersionUID = 1L;
	@Inject
	private PortariaDAO portariaDao;


	@Override
	@PostConstruct
	public void inicializar() {
		setDao(portariaDao);
	}
	
	public List<Portaria> obterPorEvento(Evento evento) throws BaseServicoException {
		try {
			return portariaDao.consultarPorEvento(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Portaria obterPorUsuario(Usuario usuario) throws BaseServicoException {
		try {
			return portariaDao.consultarPorUsuario(usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}