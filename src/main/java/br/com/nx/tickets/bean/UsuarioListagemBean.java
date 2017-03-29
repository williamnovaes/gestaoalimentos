package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.ClienteServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class UsuarioListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private ClienteServico clienteServico;
	
	private Usuario usuario;
	private List<Cliente> clientesAssociados;
	private List<Cliente> clientesDisponiveis;
	
	private boolean exibirModalClientes;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), usuarioServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}

	public void buscarRegistros() {
		try {
			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void obterClientesPorUsuario(Usuario us) {
		try {
			this.usuario = us;
			exibirModalClientes = true;
			clientesDisponiveis = clienteServico.obterTodos("nome");
			clientesAssociados = clienteServico.obterClientesPorUsuario(this.usuario);
			clientesDisponiveis.removeAll(clientesAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarCliente(Cliente cl) {
		clientesAssociados.add(cl);
		clientesDisponiveis.remove(cl);
	}
	
	public void removerCliente(Cliente cl) {
		clientesAssociados.remove(cl);
		clientesDisponiveis.add(cl);
	}
	
	public void salvarClientesAssociados() {
		try {
			usuarioServico.atualizarClientes(this.usuario, clientesAssociados);
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void fecharModal() {
		exibirModalClientes = false;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public List<Cliente> getClientesAssociados() {
		return clientesAssociados;
	}
	
	public List<Cliente> getClientesDisponiveis() {
		return clientesDisponiveis;
	}
	
	public boolean isExibirModalClientes() {
		return exibirModalClientes;
	}
}