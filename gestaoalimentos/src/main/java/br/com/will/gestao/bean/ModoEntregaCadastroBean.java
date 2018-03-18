package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.ModoEntrega;
import br.com.will.gestao.servico.ModoEntregaServico;

@Named
@ViewScoped
public class ModoEntregaCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private ModoEntregaServico modoEntregaServico;
	
	private ModoEntrega modoEntrega;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Modo Entrega Cadastro Bean");
		try {
			if (modoEntrega == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						modoEntrega = modoEntregaServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.modoEntrega == null) {
					this.modoEntrega = new ModoEntrega();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (this.modoEntrega.getId() != null) {
				modoEntregaServico.alterar(this.modoEntrega);
				adicionarInfo("Modo entrega " + this.modoEntrega.getDescricao() + " alterado com sucesso");
			} else {
				modoEntregaServico.salvar(this.modoEntrega);
				adicionarInfo("Modo Entrega cadastrado com sucesso");
			}
			
			return "modosEntrega?faces-redirect=true";
		} catch (Exception e) {
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public ModoEntrega getModoEntrega() {
		return modoEntrega;
	}
	
	public void setModoEntrega(ModoEntrega modoEntrega) {
		this.modoEntrega = modoEntrega;
	}
}