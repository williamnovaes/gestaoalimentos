package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.servico.ProdutoTipoServico;

@Named
@ViewScoped
public class ProdutoTipoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProdutoTipoServico produtoTipoServico;

	private ProdutoTipo produtoTipo;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Página Cadastro Bean");
		try {
			if (produtoTipo == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						produtoTipo = produtoTipoServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.produtoTipo == null) {
					this.produtoTipo = new ProdutoTipo();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (this.produtoTipo.getId() != null) {
				produtoTipoServico.alterar(this.produtoTipo);
			} else {
				this.produtoTipo.setEmpresa(getLoginBean().getEmpresa());
				produtoTipoServico.salvar(this.produtoTipo);
			}
			return "produtosTipo?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}
	
	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}
}
