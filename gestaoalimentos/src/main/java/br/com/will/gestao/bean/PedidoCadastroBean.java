package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Pedido;
import br.com.will.gestao.entidade.util.EFormaPagamento;
import br.com.will.gestao.servico.PedidoServico;
import br.com.will.gestao.servico.UsuarioServico;

@Named
@ViewScoped
public class PedidoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private UsuarioServico usuarioServico;

	private Pedido pedido;

	
	private EFormaPagamento[] pagamentosTipos = EFormaPagamento.values(); 

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Pedido Cadastro Bean");
		try {
			if (pedido == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("idEvento");
				if (idParam != null && !idParam.equals("")) {
					try {
						
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Evento ");
					}
				}
				if (this.pedido == null) {
					this.pedido = new Pedido();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	public Pedido getPedido() {
		return pedido;
	}

	public EFormaPagamento[] getPagamentosTipos() {
		return pagamentosTipos;
	}
}