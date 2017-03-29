package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.ClienteDAO;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class ClienteServico extends BaseServico<Cliente> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteDAO clienteDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(clienteDao);
	}
	
	public List<PontoVenda> obterPontosVendas(Cliente cliente) throws BaseServicoException {
		try {
			return clienteDao.consultarPontosVendas(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public void atualizarPontosVendas(Cliente cliente, List<PontoVenda> pontosVendas) throws BaseServicoException {
		try {
			clienteDao.atualizarPontosVendas(cliente, pontosVendas);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public void atualizarIngressosTipos(Cliente cliente, List<IngressoTipo> it) throws BaseServicoException {
		try {
			clienteDao.atualizarIngressoTipo(cliente, it);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<IngressoTipo> obterIngressosTipos(Cliente cliente) throws BaseServicoException {
		try {
			return clienteDao.consultarIngressosTipos(cliente);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Cliente> obterClientesPorUsuario(Usuario usuario) throws BaseServicoException {
		try {
			return clienteDao.consultarClientesPorUsuario(usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void atualizarUsuarios(Cliente cliente, List<Usuario> usuariosAssociados) throws BaseServicoException {
		try {
			clienteDao.atualizarUsuarios(cliente, usuariosAssociados);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}