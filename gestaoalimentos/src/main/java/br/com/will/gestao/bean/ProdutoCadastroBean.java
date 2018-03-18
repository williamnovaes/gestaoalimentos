package br.com.will.gestao.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.ProdutoTipoServico;
import br.com.will.gestao.servico.TamanhoServico;
import br.com.will.gestao.util.Util;

@Named
@ViewScoped
public class ProdutoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private ProdutoTipoServico produtoTipoServico;
	@EJB
	private TamanhoServico tamanhoServico;

	private Produto produto;
	private ProdutoTipo produtoTipo;
	private Integer produtoTipoSelecionado;
	private List<ProdutoTipo> produtosTipos;
	private List<Tamanho> tamanhosDisponiveis;
	private List<Tamanho> tamanhosAssociados;
	
	private List<String> idsTamanhosAssociar;
	private EBoolean[] permiteSabores = EBoolean.values();
	private EBoolean permiteSabor = EBoolean.FALSE;
	private EBoolean[] tamanhos = EBoolean.values();
	private EBoolean tamanho = EBoolean.FALSE;
	
	private String preco = "0";
	
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
						adicionarError("Erro ao Buscar Produto ");
					}
				}
				if (this.produto == null) {
					this.produto = new Produto();
					this.tamanhosAssociados = new ArrayList<>();
				} else {
					this.tamanhosAssociados = this.produto.getTamanhos();
					this.preco = this.produto.getPreco().toString();
					this.tamanho = this.produto.getTamanho();
					this.permiteSabor = this.produto.getPermiteSaboresEBool();
					this.produtoTipoSelecionado = this.produto.getProdutoTipo().getId();
				}
				
				this.produtosTipos = produtoTipoServico.obterTodosAtivos();
				this.tamanhosDisponiveis = tamanhoServico.obterTodosDisponiveis("tamanho");
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (produtoTipoSelecionado == null && this.produtoTipoSelecionado <= 0) {
				adicionarError("Selecione um Tipo");
				return null;
			}
			
			produtoTipo = produtoTipoServico.obterCompletoPorId(produtoTipoSelecionado);
			this.produto.setProdutoTipo(produtoTipo);
			this.produto.setPermiteSaboresEBool(permiteSabor);
			this.produto.setPreco(new BigDecimal(preco));
			this.produto.setTamanho(tamanho);
			
			if (this.produto.getId() != null) {
				produtoServico.alterar(this.produto);
				adicionarInfo("Produto alterado com sucesso");
			} else {
				this.produto = produtoServico.salvar(this.produto);
				adicionarInfo("Produto cadastrado com sucesso");
			}
			
			salvarTamanhosAssociados(this.produto);
			
			return "produtos";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public void associarTamanhos() {
		try {
			if (idsTamanhosAssociar != null && !idsTamanhosAssociar.isEmpty()) {
				tamanhosAssociados = tamanhoServico.obterPorIds(Util.converterIds(this.idsTamanhosAssociar));
				tamanhosDisponiveis.removeAll(tamanhosAssociados);
			}
		} catch (Exception e) {
			getLog().info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removerTamanhoAssociado(Tamanho t) {
		try {
			tamanhosAssociados.remove(t);
			tamanhosDisponiveis.add(t);
			Collections.sort(tamanhosDisponiveis);
		} catch (Exception e) {
			getLog().info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void salvarTamanhosAssociados(Produto p) {
		try {
			for (Tamanho t : tamanhosAssociados) {
				t.setProduto(p);
				tamanhoServico.alterar(t);
			}
			for (Tamanho t : tamanhosDisponiveis) {
				if (t.getProduto() != null) {
					t.setProduto(null);
					tamanhoServico.alterar(t);
				}
			}
		} catch (Exception e) {
			getLog().info(e.getMessage());
			e.printStackTrace();
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
	
	public List<Tamanho> getTamanhosDisponiveis() {
		return tamanhosDisponiveis;
	}
	
	public List<Tamanho> getTamanhosAssociados() {
		return tamanhosAssociados;
	}
	
	public void setTamanhosAssociados(List<Tamanho> tamanhosAssociados) {
		this.tamanhosAssociados = tamanhosAssociados;
	}
	
	public List<String> getIdsTamanhosAssociar() {
		return idsTamanhosAssociar;
	}
	
	public void setIdsTamanhosAssociar(List<String> idsTamanhosAssociar) {
		this.idsTamanhosAssociar = idsTamanhosAssociar;
	}
	
	public EBoolean[] getPermiteSabores() {
		return permiteSabores;
	}
	
	public EBoolean getPermiteSabor() {
		return permiteSabor;
	}
	
	public void setPermiteSabor(EBoolean permiteSabor) {
		this.permiteSabor = permiteSabor;
	}
	
	public String getPreco() {
		return preco;
	}
	
	public void setPreco(String preco) {
		this.preco = preco;
	}
	
	public EBoolean getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(EBoolean tamanho) {
		this.tamanho = tamanho;
	}
	
	public EBoolean[] getTamanhos() {
		return tamanhos;
	}
}