package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.UsuarioPontoVendaDAO;
import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.UsuarioPontoVenda;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class UsuarioPontoVendaServico extends BaseServico<UsuarioPontoVenda> {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioPontoVendaDAO usuarioPontoVendaDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(usuarioPontoVendaDao);
	}
	
	public UsuarioPontoVenda obterUsuarioPontoVendaPorUsuario(Usuario usuario) throws BaseServicoException {
		try {
			return usuarioPontoVendaDao.consultarPontoVendaPorUsuario(usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Usuario> obterPorPontoVendaNivel(PontoVenda pontoVenda, Nivel nivel) throws BaseServicoException {
		try {
			return usuarioPontoVendaDao.consultarPorPontoVendaNivel(pontoVenda, nivel);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
