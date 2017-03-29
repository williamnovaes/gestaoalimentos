package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.ClienteServico;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.IngressoTipoServico;
import br.com.nx.tickets.servico.PontoVendaServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class ClienteListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ClienteServico clienteServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private PontoVendaServico pontoVendaServico;
	@EJB
	private IngressoTipoServico ingressoTipoServico;
	@EJB
	private UsuarioServico usuarioServico;
	
	private Cliente cliente;
	private List<PontoVenda> pontosVendasAssociados;
	private List<PontoVenda> pontosVendasDisponiveis;
	private List<IngressoTipo> ingressosTiposAssociados;
	private List<IngressoTipo> ingressosTiposDisponiveis;
	private List<Usuario> usuariosAssociados;
	private List<Usuario> usuariosDisponiveis;
	private List<Evento> eventos;
	
	private boolean exibirModalPontoVenda;
	private boolean exibirModalEvento;
	private boolean exibirModalIngressoTipo;
	private boolean exibirModalUsuario;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), clienteServico, SistemaConstantes.DEZ);
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
	
	
	public void obterEventosPorCliente(Cliente cl) {
		try {
			this.cliente = cl;
			exibirModalEvento = true;
			eventos = eventoServico.obterEventosPorCliente(this.cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pontos de Venda
	 * */
	public void obterPontosVendasPorCliente(Cliente cl) {
		try {
			this.cliente = cl;
			exibirModalPontoVenda = true;
			pontosVendasDisponiveis = pontoVendaServico.obterTodos("nome");
			
			pontosVendasAssociados = clienteServico.obterPontosVendas(this.cliente);
			
			pontosVendasDisponiveis.removeAll(pontosVendasAssociados);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarPontoVenda(PontoVenda pv) {
		try {
			pontosVendasAssociados.add(pv);
			pontosVendasDisponiveis.remove(pv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerPontoVenda(PontoVenda pv) {
		try {
			pontosVendasAssociados.remove(pv);
			pontosVendasDisponiveis.add(pv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarPontoVendaAssociado() {
		try {
			fecharModal();
			clienteServico.atualizarPontosVendas(this.cliente, this.pontosVendasAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ingresso Tipo
	 * */
	public void obterIngressosTiposPorCliente(Cliente cl) {
		try {
			this.cliente = cl;
			exibirModalIngressoTipo = true;
			ingressosTiposDisponiveis = ingressoTipoServico.obterTodos("descricao");
			
			ingressosTiposAssociados = clienteServico.obterIngressosTipos(this.cliente);
			
			ingressosTiposDisponiveis.removeAll(ingressosTiposAssociados);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarIngressoTipo(IngressoTipo it) {
		try {
			ingressosTiposAssociados.add(it);
			ingressosTiposDisponiveis.remove(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerIngressoTipo(IngressoTipo it) {
		try {
			ingressosTiposAssociados.remove(it);
			ingressosTiposDisponiveis.add(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarIngressoTipoAssociado() {
		try {
			fecharModal();
			clienteServico.atualizarIngressosTipos(this.cliente, this.ingressosTiposAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Usuarios 
	 * */
	public void obterUsuariosPorCliente(Cliente cl) {
		try {
			this.cliente = cl;
			exibirModalUsuario = true;
			usuariosDisponiveis = usuarioServico.obterTodos("nome");
			usuariosAssociados = usuarioServico.obterUsuariosPorCliente(this.cliente);
			usuariosDisponiveis.removeAll(usuariosAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarUsuario(Usuario us) {
		try {
			usuariosAssociados.add(us);
			usuariosDisponiveis.remove(us);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerUsuario(Usuario us) {
		try {
			usuariosAssociados.remove(us);
			usuariosDisponiveis.add(us);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarUsuariosAssociados() {
		try {
			getLog().info("CLIENTE: " + cliente.getId());
			clienteServico.atualizarUsuarios(this.cliente, this.usuariosAssociados);
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * GETTERS AND SETTERS
	 * */
	@Override
	public void fecharModal() {
		exibirModalPontoVenda	= false;
		exibirModalEvento 		= false;
		exibirModalIngressoTipo = false;
		exibirModalUsuario 		= false;
	}
	
	public boolean isExibirModalPontoVenda() {
		return exibirModalPontoVenda;
	}

	public boolean isExibirModalEvento() {
		return exibirModalEvento;
	}
	
	public List<PontoVenda> getPontosVendasAssociados() {
		return pontosVendasAssociados;
	}
	
	public List<PontoVenda> getPontosVendasDisponiveis() {
		return pontosVendasDisponiveis;
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public List<IngressoTipo> getIngressosTiposAssociados() {
		return ingressosTiposAssociados;
	}
	
	public List<IngressoTipo> getIngressosTiposDisponiveis() {
		return ingressosTiposDisponiveis;
	}
	
	public boolean isExibirModalIngressoTipo() {
		return exibirModalIngressoTipo;
	}
	
	public List<Usuario> getUsuariosAssociados() {
		return usuariosAssociados;
	}
	
	public List<Usuario> getUsuariosDisponiveis() {
		return usuariosDisponiveis;
	}
	
	public boolean isExibirModalUsuario() {
		return exibirModalUsuario;
	}
}