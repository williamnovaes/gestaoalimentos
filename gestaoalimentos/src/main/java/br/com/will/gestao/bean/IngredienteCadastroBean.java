package br.com.will.gestao.bean;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Ingrediente;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.servico.EmpresaServico;
import br.com.will.gestao.servico.IngredienteServico;

@Named
@ViewScoped
public class IngredienteCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private IngredienteServico ingredienteServico;
	@EJB
	private EmpresaServico empresaServico;
	
	private Ingrediente ingrediente;
	private EBoolean adicionalSelecionado;
	private EBoolean[] ebooleans = EBoolean.values();
	
	private String valorAdicional;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Ingrediente Cadastro Bean");
		try {
			if (ingrediente == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						ingrediente = ingredienteServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.ingrediente == null) {
					this.ingrediente = new Ingrediente();
					this.adicionalSelecionado = EBoolean.FALSE;
					this.valorAdicional = "0";
				} else {
					this.adicionalSelecionado = this.ingrediente.getAdicional();
					this.valorAdicional = ingrediente.getValorAdicional().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			this.ingrediente.setEmpresa(getLoginBean().getEmpresa());
			this.ingrediente.setAdicional(adicionalSelecionado);
			if (this.valorAdicional != null && !this.valorAdicional.isEmpty()) {
				this.ingrediente.setValorAdicional(new BigDecimal(this.valorAdicional));
			}
			if (this.ingrediente.getId() != null) {
				ingredienteServico.alterar(this.ingrediente);
				adicionarInfo("Produto " + this.ingrediente.getNome() + " alterado com sucesso");
			} else {
				ingredienteServico.salvar(this.ingrediente);
				adicionarInfo("Produto cadastrado com sucesso");
			}
			
			return "ingredientes";
		} catch (Exception e) {
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	
	public EBoolean[] getEbooleans() {
		return ebooleans;
	}
	
	public EBoolean getAdicionalSelecionado() {
		return adicionalSelecionado;
	}
	
	public void setAdicionalSelecionado(EBoolean adicionalSelecionado) {
		this.adicionalSelecionado = adicionalSelecionado;
	}
	
	public String getValorAdicional() {
		return valorAdicional;
	}
	
	public void setValorAdicional(String valorAdicional) {
		this.valorAdicional = valorAdicional;
	}
}