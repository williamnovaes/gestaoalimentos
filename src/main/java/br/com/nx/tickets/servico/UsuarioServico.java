package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.componente.Credencial;
import br.com.nx.tickets.dao.BaseDAOException;
import br.com.nx.tickets.dao.UsuarioDAO;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class UsuarioServico extends BaseServico<Usuario> {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioDAO usuarioDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(usuarioDao);
	}
	
	public Usuario logar(Credencial credencial) throws BaseServicoException {
		try {
			return usuarioDao.logar(credencial);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public void atualizarPontosVendas(Usuario usuario, List<PontoVenda> pontosVendas) throws BaseServicoException {
		try {
			usuarioDao.atualizarPontosVendas(usuario, pontosVendas);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Usuario> obterPorNivel(Nivel nivel) throws BaseServicoException {
		try {
			return usuarioDao.conultarPorNivel(nivel);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Usuario> obterUsuariosDisponiveis(PontoVenda pontoVenda) throws BaseServicoException {
		try {
			return usuarioDao.consultarUsuariosDisponiveisPorPontoVenda(pontoVenda);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Usuario> obterUsuariosPontoVendaAssociado(PontoVenda pv) throws BaseServicoException {
		try {
			return usuarioDao.consultarUsuariosPontoVendaAssociados(pv);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Usuario> obterUsuariosPortariaDisponiveis(Portaria portaria) throws BaseServicoException {
		try {
			return usuarioDao.consultarUsuariosDisponiveisPorPortaria(portaria);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Usuario> obterUsuariosPortariaAssociados(Portaria portaria) throws BaseServicoException {
		try {
			return usuarioDao.consultarUsuariosPortariaAssociados(portaria);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void atualizarToken(Usuario usuario) throws BaseServicoException {
		try {
			usuarioDao.atualizarToken(usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public String obterToken(Usuario usuario) throws BaseServicoException {
		try {
			return usuarioDao.consultarToken(usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Usuario> obterUsuariosPorCliente(Cliente cliente) throws BaseServicoException {
		try {
			return usuarioDao.consultarUsuariosPorCliente(cliente);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void atualizarClientes(Usuario usuario, List<Cliente> clientes) throws BaseServicoException {
		try {
			usuarioDao.atualizarClientes(usuario, clientes);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Usuario> obterDisponiveisPorGuiche() throws BaseServicoException {
		try {
			return usuarioDao.consultarDisponiveisPorGuiche();
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
