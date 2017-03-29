package br.com.nx.tickets.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.dto.ExtratoRetiradaDTO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.Retirada;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.RetiradaServico;
import br.com.nx.tickets.servico.UsuarioPontoVendaServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Named
@ViewScoped
public class GuicheListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GuicheServico guicheServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private UsuarioPontoVendaServico usuarioPontoVendaServico;
	@EJB
	private RetiradaServico retiradaServico;

	private Evento evento;
	private Guiche guiche;

	private List<Usuario> usuarios;
	private List<Retirada> retiradas;

	private boolean exibirModalUsuario;
	private Integer idUsuarioSelecionado;

	private List<ExtratoGuicheDTO> extratosGuiches;
	private List<Evento> eventosAssociados;

	private ExtratoRetiradaDTO extratoRetirada;
	private ExtratoGuicheDTO extratoGuiche;

	private boolean exibirModalExtrato;
	
	private Integer idEventoSelecionado;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), guicheServico, SistemaConstantes.DEZ);
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

	public void salvarGuicheUsuario() {
		try {
			if (idUsuarioSelecionado != null) {
				guicheServico.limparGuicheUsuario(idUsuarioSelecionado);
				this.guiche.setUsuario(usuarioServico.obterPorId(idUsuarioSelecionado));
			} else {
				this.guiche.setUsuario(null);
			}
			guicheServico.alterar(this.guiche);
			idUsuarioSelecionado = null;
			fecharModal();
			inicializar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obterUsuarios(Guiche gi) {
		try {
			this.guiche = gi;
			exibirModalUsuario = true;
			usuarios = usuarioPontoVendaServico.obterPorPontoVendaNivel(gi.getBilheteria().getPontoVenda(),
					new Nivel(2));
			if (this.guiche.getUsuario() != null) {
				idUsuarioSelecionado = this.guiche.getUsuario().getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void alterarOffline(Guiche gi) {
		try {
			gi.setOffline(Util.alteraEBoolean(gi.getOffline()));
			guicheServico.alterar(gi);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar Offline!");
			e.printStackTrace();
		}
	}

	public void alterarImprimirCortesia(Guiche gi) {
		try {
			gi.setImprimirCortesia(Util.alteraEBoolean(gi.getImprimirCortesia()));
			guicheServico.alterar(gi);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar Imprimir Cortesia!");
			e.printStackTrace();
		}
	}

	public void alterarImprimirAntesConfirmacaoPedido(Guiche gi) {
		try {
			gi.setImprimirAntesConfirmacaoPedido(Util.alteraEBoolean(gi.getImprimirAntesConfirmacaoPedido()));
			guicheServico.alterar(gi);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar Imprimir Antes Confirmação Pedido!");
			e.printStackTrace();
		}
	}

	public void abrirExtrato(Guiche g) {
		try {
			this.guiche = g;
			eventosAssociados = eventoServico.obterTodosPorGuiche(this.guiche);
			exibirModalExtrato = true;
			idEventoSelecionado = null;
			extratoRetirada = new ExtratoRetiradaDTO();
			extratosGuiches = new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selecionarEventoPontoVenda() {
		try {
			this.evento = eventoServico.obterPorId(idEventoSelecionado);
			 extratosGuiches = guicheServico.obterExtratoPorGuicheEvento(guiche, evento);
			extratoGuiche = new ExtratoGuicheDTO();
			for (ExtratoGuicheDTO ex : extratosGuiches) {
				extratoGuiche.setQuantidade(extratoGuiche.getQuantidade() + ex.getQuantidade());
				extratoGuiche.setValorTotal(extratoGuiche.getValorTotal().add(ex.getValorTotal()));
				extratoGuiche.setValorTotalTaxaAdministrativa(
						extratoGuiche.getValorTotalTaxaAdministrativa().add(ex.getValorTotalTaxaAdministrativa()));
			}
			retiradas = retiradaServico.obterPorGuicheEvento(guiche, evento);
			// pontoVendaServico.obterExtratoPorGuicheEvento(this.evento,
			// this.pontoVenda);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void alterarSituacao(Guiche gi) {
		try {
			gi.alterarSituacao();
			guicheServico.alterar(gi);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}

	@Override
	public void fecharModal() {
		exibirModalUsuario = false;
		exibirModalExtrato = false;
		idUsuarioSelecionado = null;
	}

	public Guiche getGuiche() {
		return guiche;
	}

	public boolean isExibirModalUsuario() {
		return exibirModalUsuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public Integer getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}

	public void setIdUsuarioSelecionado(Integer idUsuarioSelecionado) {
		this.idUsuarioSelecionado = idUsuarioSelecionado;
	}

	public ExtratoGuicheDTO getExtratoGuiche() {
		return extratoGuiche;
	}

	public List<Evento> getEventosAssociados() {
		return eventosAssociados;
	}

	public boolean isExibirModalExtrato() {
		return exibirModalExtrato;
	}
	
	public Integer getIdEventoSelecionado() {
		return idEventoSelecionado;
	}
	
	public void setIdEventoSelecionado(Integer idEventoSelecionado) {
		this.idEventoSelecionado = idEventoSelecionado;
	}
	
	public List<ExtratoGuicheDTO> getExtratosGuiches() {
		return extratosGuiches;
	}
	
	public Evento getEvento() {
		return evento;
	}
	
	public ExtratoRetiradaDTO getExtratoRetirada() {
		return extratoRetirada;
	}
	
	public List<Retirada> getRetiradas() {
		return retiradas;
	}
}
