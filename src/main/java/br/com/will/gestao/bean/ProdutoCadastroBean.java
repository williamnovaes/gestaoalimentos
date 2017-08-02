package br.com.will.gestao.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.ProdutoTipoServico;

@Named
@ViewScoped
public class ProdutoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private ProdutoTipoServico produtoTipoServico;

	private Produto produto;
	private ProdutoTipo produtoTipo;
	private Integer produtoTipoSelecionado;
	private List<ProdutoTipo> produtosTipos;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Produto Cadastro Bean");
		try {
			if (produto == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						produto = produtoServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.produto == null) {
					this.produto = new Produto();
				}
				
				produtosTipos = produtoTipoServico.obterTodosAtivosPorEmpresa(getLoginBean().getEmpresa());
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (produtoTipoSelecionado != null && this.produtoTipoSelecionado > 0) {
				produtoTipo = produtoTipoServico.obterPorId(produtoTipoSelecionado);
				this.produto.setProdutoTipo(produtoTipo);
			}
			if (this.produto.getId() != null) {
				produtoServico.alterar(this.produto);
			} else {
				this.produto.setEmpresa(getLoginBean().getEmpresa());
				produtoServico.salvar(this.produto);
			}
			return "produtos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public List<ProdutoTipo> getProdutosTipos() {
		return produtosTipos;
	}
	
	public Integer getProdutoTipoSelecionado() {
		return produtoTipoSelecionado;
	}
	
	public void setProdutoTipoSelecionado(Integer produtoTipoSelecionado) {
		this.produtoTipoSelecionado = produtoTipoSelecionado;
	}
}