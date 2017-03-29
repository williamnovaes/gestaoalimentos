package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.nx.tickets.componente.FiltroPermissaoUsuarioIngresso;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.IngressoServico;
import br.com.nx.tickets.servico.IngressoSituacaoServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class IngressoListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IngressoServico ingressoServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private IngressoSituacaoServico ingressoSituacaoServico;
	@EJB
	private GuicheServico guicheServico;
	@Inject
	private FiltroPermissaoUsuarioIngresso filtroPermissaoUsuarioIngresso;

	private Ingresso ingresso;
	private Evento evento;
	private Guiche guiche;
	private IngressoSituacao ingressoSituacao;
	private List<Evento> eventos;
	private List<IngressoSituacao> ingressosSituacao;
	private List<Guiche> guiches;
	private boolean exibirModalCancelamento;
	private boolean exibirModalDetalhes;
	
	private String observacao;
	private Integer idEventoSelecionado;
	private Integer idIngressoSituacaoSelecionado;
	private Integer idGuicheSelecionado;

	@Override
	@PostConstruct
	public void inicializar() {
		try {
			guiches 			= guicheServico.obterTodos("descricao");
			eventos 			= eventoServico.obterTodos("descricao");
			ingressosSituacao 	= ingressoSituacaoServico.obterTodos("descricao");
			if (ingresso == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("idEvento");
				if (idParam != null && !idParam.equals("")) {
					try {
						evento = eventoServico.obterPorId(Integer.parseInt(idParam));
						filtroPermissaoUsuarioIngresso.getFiltravel().setIdEventoSelecionado(evento.getId());
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Ingressos por Evento ");
					}
					if (this.ingresso == null) {
						this.ingresso = new Ingresso();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
		configurarPaginador(filtroPermissaoUsuarioIngresso, ingressoServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}
	
	public void buscarPorEvento() {
		try {
			if (idEventoSelecionado != null) {
				evento = eventoServico.obterPorId(idEventoSelecionado);
				filtroPermissaoUsuarioIngresso.getFiltravel().setIdEventoSelecionado(evento.getId());
			} else {
				filtroPermissaoUsuarioIngresso.getFiltravel().setIdEventoSelecionado(null);
			}
			buscarRegistros();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarPorIngressoSituacao() {
		try {
			if (idIngressoSituacaoSelecionado != null) {
				ingressoSituacao = ingressoSituacaoServico.obterPorId(idIngressoSituacaoSelecionado);
				filtroPermissaoUsuarioIngresso.getFiltravel().setIdIngressoSituacaoSelecionado(ingressoSituacao.getId());
			} else {
				filtroPermissaoUsuarioIngresso.getFiltravel().setIdIngressoSituacaoSelecionado(null);
			}
			buscarRegistros();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarPorGuiche() {
		try {
			if (idGuicheSelecionado != null) {
				guiche = guicheServico.obterPorId(idGuicheSelecionado);
				filtroPermissaoUsuarioIngresso.getFiltravel().setIdGuicheSelecionado(guiche.getId());
			} else {
				filtroPermissaoUsuarioIngresso.getFiltravel().setIdGuicheSelecionado(null);
			}
			buscarRegistros();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscarRegistros() {
		try {
//			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abrirModalCancelamento(Ingresso i) {
		this.ingresso = i;
		exibirModalCancelamento = true;
	}
	
	public void abrirModalDetalhes(Ingresso i) {
		this.ingresso = i;
		exibirModalDetalhes = true;
	}
	
	public void cancelarIngresso() {
		try {
			if (this.ingresso.getObservacao() != null && !this.ingresso.getObservacao().isEmpty()) {
				ingressoServico.cancelarIngresso(this.ingresso);
				buscarRegistros();
				fecharModal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fecharModal() {
		exibirModalCancelamento = false;
		exibirModalDetalhes		= false;
	}
	
	public Ingresso getIngresso() {
		return ingresso;
	}
	
	public FiltroPermissaoUsuarioIngresso getFiltroPermissaoUsuarioIngresso() {
		return filtroPermissaoUsuarioIngresso;
	}
	
	public boolean isExibirModalCancelamento() {
		return exibirModalCancelamento;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Integer getIdEventoSelecionado() {
		return idEventoSelecionado;
	}
	
	public void setIdEventoSelecionado(Integer idEventoSelecionado) {
		this.idEventoSelecionado = idEventoSelecionado;
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public boolean isExibirModalDetalhes() {
		return exibirModalDetalhes;
	}
	
	public List<Guiche> getGuiches() {
		return guiches;
	}
	
	public List<IngressoSituacao> getIngressosSituacao() {
		return ingressosSituacao;
	}
	
	public Integer getIdGuicheSelecionado() {
		return idGuicheSelecionado;
	}
	
	public void setIdGuicheSelecionado(Integer idGuicheSelecionado) {
		this.idGuicheSelecionado = idGuicheSelecionado;
	}
	
	public Integer getIdIngressoSituacaoSelecionado() {
		return idIngressoSituacaoSelecionado;
	}
	
	public void setIdIngressoSituacaoSelecionado(Integer idIngressoSituacaoSelecionado) {
		this.idIngressoSituacaoSelecionado = idIngressoSituacaoSelecionado;
	}
}