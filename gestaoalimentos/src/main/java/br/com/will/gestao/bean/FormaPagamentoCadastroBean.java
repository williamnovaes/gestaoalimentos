package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.FormaPagamento;
import br.com.will.gestao.servico.FormaPagamentoServico;

@Named
@ViewScoped
public class FormaPagamentoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private FormaPagamentoServico formaPagamentoServico;
	
	private FormaPagamento formaPagamento;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Forma Pagamento Cadastro Bean");
		try {
			if (formaPagamento == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						formaPagamento = formaPagamentoServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.formaPagamento == null) {
					this.formaPagamento = new FormaPagamento();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (this.formaPagamento.getId() != null) {
				formaPagamentoServico.alterar(this.formaPagamento);
				adicionarInfo("Forma de pagamento " + this.formaPagamento.getDescricao() + " alterado com sucesso");
			} else {
				formaPagamentoServico.salvar(this.formaPagamento);
				adicionarInfo("Forma de pagamento cadastrado com sucesso");
			}
			
			return "formasPagamentos?faces-redirect=true";
		} catch (Exception e) {
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
}