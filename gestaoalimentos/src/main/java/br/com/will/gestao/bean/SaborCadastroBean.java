package br.com.will.gestao.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.SaborServico;

@Named
@ViewScoped
public class SaborCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private SaborServico saborServico;
	@EJB
	private ProdutoServico produtoServico;

	private List<Produto> produtos;
	private Integer idProduto;
	private Sabor sabor;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Sabor Cadastro Bean");
		try {
			if (sabor == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						sabor = saborServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Sabor ");
					}
				}
				if (this.sabor == null) {
					this.sabor = new Sabor();
				} else {
					idProduto = this.sabor.getProduto().getId();
				}
			}
			
			produtos = produtoServico.obterTodos("sequencia");
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (idProduto == null && idProduto <= 0) {
				adicionarError("Selecione um Produto");
				return null;
			}
			
			Produto produto = produtoServico.obterCompletoPorId(idProduto);
			this.sabor.setProduto(produto);
			
			if (this.sabor.getId() != null) {
				saborServico.alterar(this.sabor);
			} else {
				this.sabor = saborServico.salvar(this.sabor);
			}
			
			return "produtos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Sabor getSabor() {
		return sabor;
	}
	
	public Integer getIdProduto() {
		return idProduto;
	}
	
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
}