package br.com.will.gestao.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Empresa;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.servico.EmpresaServico;
import br.com.will.gestao.servico.ProdutoTipoServico;
import br.com.will.gestao.servico.TamanhoServico;

@Named
@ViewScoped
public class ProdutoTipoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProdutoTipoServico produtoTipoServico;
	@EJB
	private TamanhoServico tamanhoServico;
	@EJB
	private EmpresaServico empresaServico;

	private ProdutoTipo produtoTipo;
	private Tamanho tamanho;
	private List<Tamanho> tamanhos;
	private List<Empresa> empresas;
	private Integer empresaSelecionada;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Página Cadastro Bean");
		try {
			if (produtoTipo == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.isEmpty()) {
					try {
						produtoTipo = produtoTipoServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.produtoTipo == null) {
					this.produtoTipo = new ProdutoTipo();
				} else {
					this.tamanhos = tamanhoServico.obterPorProdutoTipo(this.produtoTipo);
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
				if (getLoginBean().getEmpresa() != null) {
					this.produtoTipo.setEmpresa(getLoginBean().getEmpresa());
				} else {
//					if (empresaSelecionada == null || empresaSelecionada <= 0) {
//						adicionarError("Campo Empresa obrigatório!");
//						return null;
//					}
					Empresa empresa = empresaServico.obterPorId(1);
					this.produtoTipo.setEmpresa(empresa);
				}
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
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	
	public Integer getEmpresaSelecionada() {
		return empresaSelecionada;
	}
	
	public void setEmpresaSelecionada(Integer empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}
}
