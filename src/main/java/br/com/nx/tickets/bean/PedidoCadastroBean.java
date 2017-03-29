package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.dto.ExtratoIngressoDTO;
import br.com.nx.tickets.dto.ExtratoPedidoDTO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.util.EPagamentoTipo;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.LoteServico;
import br.com.nx.tickets.servico.PedidoServico;
import br.com.nx.tickets.servico.PortariaServico;
import br.com.nx.tickets.servico.UsuarioPontoVendaServico;
import br.com.nx.tickets.servico.UsuarioServico;

@Named
@ViewScoped
public class PedidoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private LoteServico loteServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private PortariaServico portariaServico;
	@EJB
	private UsuarioPontoVendaServico usuarioPontoVendaServico;

	private Pedido pedido;
	private Evento evento;
	private List<Lote> lotes;
	private List<Portaria> portarias;
	private Lote lote;

	private Integer quantidade;
	private Integer idLoteSelecionado;
	private Integer idPortariaSelecionada;
	
	private EPagamentoTipo[] pagamentosTipos = EPagamentoTipo.values(); 
	
	private ExtratoPedidoDTO extratoPedido;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Pedido Cadastro Bean");
		try {
			if (pedido == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("idEvento");
				if (idParam != null && !idParam.equals("")) {
					try {
						evento = eventoServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Evento ");
					}
				}
				if (this.pedido == null) {
					this.pedido = new Pedido();
					this.pedido.setEvento(evento);
				}
				lotes = loteServico.obterLotesValidosPorEvento(evento);
				extratoPedido = new ExtratoPedidoDTO();
				portarias = portariaServico.obterPorEvento(evento);
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (!extratoPedido.getExtratoIngressos().isEmpty()) {
				pedido.setIngressos(extratoPedido.toIngressoList());
				pedidoServico.salvar(getLoginBean().getUsuarioLogado(), pedido);
				return "pedidos?faces-redirect=true";
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}

	public void adicionaIngresso() {
		try {
			if (idLoteSelecionado != null && (quantidade != null && quantidade > 0)) {
				extratoPedido.adicionarExtratoIngresso(lote, quantidade);
				idLoteSelecionado = 0;
				quantidade = 0;
				this.lote = new Lote();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerIngresso(ExtratoIngressoDTO extratoIngressso) {
		extratoPedido.removerIngresso(extratoIngressso);
	}
	
	public void selecionarLote() {
		try {
			if (idLoteSelecionado != null) {
				lote = loteServico.obterPorIdComIngressoTipo(idLoteSelecionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pedido getPedido() {
		return pedido;
	}

	public Evento getEvento() {
		return evento;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public Integer getIdLoteSelecionado() {
		return idLoteSelecionado;
	}

	public void setIdLoteSelecionado(Integer idLoteSelecionado) {
		this.idLoteSelecionado = idLoteSelecionado;
	}
	
	public Integer getIdPortariaSelecionada() {
		return idPortariaSelecionada;
	}
	
	public void setIdPortariaSelecionada(Integer idPortariaSelecionada) {
		this.idPortariaSelecionada = idPortariaSelecionada;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Lote getLote() {
		return lote;
	}

	public List<Portaria> getPortarias() {
		return portarias;
	}
	
	public EPagamentoTipo[] getPagamentosTipos() {
		return pagamentosTipos;
	}
	
	public ExtratoPedidoDTO getExtratoPedido() {
		return extratoPedido;
	}
}