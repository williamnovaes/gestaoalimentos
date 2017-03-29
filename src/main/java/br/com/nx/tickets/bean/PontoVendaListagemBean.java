package br.com.nx.tickets.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.PontoVendaServico;
import br.com.nx.tickets.servico.RetiradaServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class PontoVendaListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PontoVendaServico pontoVendaServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private RetiradaServico retiradaServico;
	@EJB
	private UsuarioServico usuarioServico;
	
	private PontoVenda pontoVenda;
	private Evento evento;
	private List<Evento> eventosDisponiveis;
	private List<Evento> eventosAssociados;
	private List<Usuario> usuariosAssociados;
	private List<Usuario> usuariosDisponiveis;
	private List<Usuario> usuariosNovos;
	
	private boolean exibirModalEvento;
	private boolean exibirModalUsuario;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), pontoVendaServico, SistemaConstantes.DEZ);
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
	
	public void obterEventosPorPontoVenda(PontoVenda pv) {
		try {
			this.pontoVenda = pv;
			exibirModalEvento = true;
			eventosDisponiveis = eventoServico.obterTodos("descricao");
			eventosAssociados = eventoServico.obterEventosPorPontoVenda(this.pontoVenda);
			eventosDisponiveis.removeAll(eventosAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adicionarEvento(Evento ev) {
		try {
			eventosAssociados.add(ev);
			eventosDisponiveis.remove(ev);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removerEvento(Evento ev) {
		try {
			eventosAssociados.remove(ev);
			eventosDisponiveis.add(ev);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarEventosAssociados() {
		try {
			pontoVendaServico.atualizarEventos(this.pontoVenda, eventosAssociados);
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarUsuarioPontoVenda() {
		try {
			if (!usuariosNovos.isEmpty()) {
				pontoVendaServico.atualizarUsuarios(pontoVenda, usuariosNovos);
				fecharModal();
				inicializar();
			}
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obterUsuariosPorPontoVenda(PontoVenda pv) {
		try {
			this.pontoVenda = pv;
			exibirModalUsuario = true;
			usuariosAssociados = usuarioServico.obterUsuariosPontoVendaAssociado(this.pontoVenda);
			usuariosDisponiveis = usuarioServico.obterUsuariosDisponiveis(this.pontoVenda);
			usuariosNovos = new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarUsuario(Usuario us) {
		usuariosNovos.add(us);
		usuariosDisponiveis.remove(us);
	}
	
	public void removerUsuario(Usuario us) {
		usuariosNovos.remove(us);
		usuariosDisponiveis.add(us);
	}

	@Override
	public void fecharModal() {
		exibirModalEvento 		= false;
		exibirModalUsuario		= false;
	}
	
	public PontoVenda getPontoVenda() {
		return pontoVenda;
	}

	public Evento getEvento() {
		return evento;
	}

	public List<Evento> getEventosAssociados() {
		return eventosAssociados;
	}

	public List<Evento> getEventosDisponiveis() {
		return eventosDisponiveis;
	}
	
	public boolean isExibirModalEvento() {
		return exibirModalEvento;
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
	
	public List<Usuario> getUsuariosNovos() {
		return usuariosNovos;
	}
}
