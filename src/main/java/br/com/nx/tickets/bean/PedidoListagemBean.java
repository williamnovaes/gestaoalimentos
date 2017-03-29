package br.com.nx.tickets.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.nx.tickets.componente.FiltroPermissaoUsuarioPedido;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.IngressoServico;
import br.com.nx.tickets.servico.LoteServico;
import br.com.nx.tickets.servico.PedidoServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class PedidoListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private LoteServico loteServico;
	@EJB
	private IngressoServico ingressoServico;
	@EJB
	private GuicheServico guicheServico;
	@Inject
	private FiltroPermissaoUsuarioPedido filtroPermissaoUsuarioPedido;

	private Lote lote;
	private Guiche guiche;
	private Pedido pedido;
	private Evento eventoFiltro;
	private List<Guiche> guiches;
	private List<Evento> eventos;
	private List<Ingresso> ingressos;
	private HashMap<Integer, Lote> lotes;

	private Date dataFim;
	private Date dataInicio;
	private String observacao;
	private Integer quantidade;
	private Integer idEventoSelecionado;
	private Integer idGuicheSelecionado;
	private boolean exibirModalIngresso;
	private boolean exibirModalCancelamento;
	
	@Override
	@PostConstruct
	public void inicializar() {
		try {
			if (pedido == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("idEvento");
				if (idParam != null && !idParam.equals("")) {
					try {
						Evento evento = eventoServico.obterPorId(Integer.parseInt(idParam));
						filtroPermissaoUsuarioPedido.getFiltravel().setIdEventoSelecionado(evento.getId());
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Pedidos por Eventos ");
					}
					if (this.pedido == null) {
						this.pedido = new Pedido();
					}
				}
			}
			eventos 	= eventoServico.obterTodos("descricao");
			guiches 	= guicheServico.obterTodos("descricao");
			ingressos 	= new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
		configurarPaginador(filtroPermissaoUsuarioPedido, pedidoServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}
	
	public void buscarPorEvento() {
		try {
			if (idEventoSelecionado != null) {
				eventoFiltro = eventoServico.obterPorId(idEventoSelecionado);
				filtroPermissaoUsuarioPedido.getFiltravel().setIdEventoSelecionado(eventoFiltro.getId());
			} else {
				filtroPermissaoUsuarioPedido.getFiltravel().setIdEventoSelecionado(null);
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
				filtroPermissaoUsuarioPedido.getFiltravel().setIdGuicheSelecionado(guiche.getId());
			} else {
				filtroPermissaoUsuarioPedido.getFiltravel().setIdGuicheSelecionado(null);
			}
			buscarRegistros();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public void buscarPorData() {
//		try {
//			if (dataInicio != null && dataFim != null) {
//				filtroPermissaoUsuarioPedido.getFiltravel().setDataFim(dataFim);
//				filtroPermissaoUsuarioPedido.getFiltravel().setDataInicio(dataInicio);
//			} else {
//				filtroPermissaoUsuarioPedido.getFiltravel().setDataFim(null);
//				filtroPermissaoUsuarioPedido.getFiltravel().setDataInicio(null);
//			}
//			buscarRegistros();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public void buscarRegistros() {
		try {
			if (dataInicio != null && dataFim != null) {
				filtroPermissaoUsuarioPedido.getFiltravel().setDataFim(dataFim);
				filtroPermissaoUsuarioPedido.getFiltravel().setDataInicio(dataInicio);
			} else {
				filtroPermissaoUsuarioPedido.getFiltravel().setDataFim(null);
				filtroPermissaoUsuarioPedido.getFiltravel().setDataInicio(null);
			}
			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
//			adicionarError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void obterIngressosPorPedido(Pedido pd) {
		try {
			this.pedido = pd;
			exibirModalIngresso = true;
			ingressos = new ArrayList<>();
			ingressos = ingressoServico.obterPorIdPedido(this.pedido.getId());
			quantidade = ingressos.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abrirModalCancelamento(Pedido pd) {
		this.pedido = pd;
		exibirModalCancelamento = true;
	}
	
	public void cancelarIngressos() {
		try {
			if (observacao != null && !observacao.isEmpty()) {
				ingressoServico.cancelarTodosIngressosPorPedido(pedido, observacao);
				fecharModal();
			}
			observacao = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fecharModal() {
		exibirModalIngresso = false;
		exibirModalCancelamento = false;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public Lote getLote() {
		return lote;
	}

	public HashMap<Integer, Lote> getLotes() {
		return lotes;
	}

	public boolean isExibirModalIngresso() {
		return exibirModalIngresso;
	}

	public List<Ingresso> getIngressos() {
		return ingressos;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public boolean isExibirModalCancelamento() {
		return exibirModalCancelamento;
	}
	
	public List<Guiche> getGuiches() {
		return guiches;
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public Integer getIdEventoSelecionado() {
		return idEventoSelecionado;
	}
	
	public void setIdEventoSelecionado(Integer idEventoSelecionado) {
		this.idEventoSelecionado = idEventoSelecionado;
	}
	
	public Integer getIdGuicheSelecionado() {
		return idGuicheSelecionado;
	}
	public void setIdGuicheSelecionado(Integer idGuicheSelecionado) {
		this.idGuicheSelecionado = idGuicheSelecionado;
	}
	
	public FiltroPermissaoUsuarioPedido getFiltroPermissaoUsuarioPedido() {
		return filtroPermissaoUsuarioPedido;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
}
