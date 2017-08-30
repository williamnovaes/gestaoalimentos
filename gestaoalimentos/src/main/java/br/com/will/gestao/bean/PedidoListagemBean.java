package br.com.will.gestao.bean;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.componente.FiltroPermissaoUsuarioPedido;
import br.com.will.gestao.entidade.Pedido;
import br.com.will.gestao.servico.PedidoServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class PedidoListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private FiltroPermissaoUsuarioPedido filtroPermissaoUsuarioPedido;
	
	private Pedido pedido;

	private Date dataFim;
	private Date dataInicio;
	private String observacao;
	private boolean exibirModalCancelamento;
	
	@Override
	@PostConstruct
	public void inicializar() {
		try {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("idEvento");
				if (idParam != null && !idParam.equals("")) {
					try {
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Pedidos por Eventos ");
					}
					if (this.pedido == null) {
						this.pedido = new Pedido();
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
		configurarPaginador(filtroPermissaoUsuarioPedido, pedidoServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}
	
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
			e.printStackTrace();
		}
	}

	public void abrirModalCancelamento(Pedido pd) {
		this.pedido = pd;
		exibirModalCancelamento = true;
	}
	
	@Override
	public void fecharModal() {
		exibirModalCancelamento = false;
	}
	
	public Pedido getPedido() {
		return pedido;
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
	
	public FiltroPermissaoUsuarioPedido getFiltroPermissaoUsuarioPedido() {
		return filtroPermissaoUsuarioPedido;
	} 
}
