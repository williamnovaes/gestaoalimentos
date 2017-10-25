package br.com.will.gestao.bean;

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
import br.com.will.gestao.servico.EmpresaServico;
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
	@EJB
	private EmpresaServico empresaServico;

	private Produto produto;
	private ProdutoTipo produtoTipo;
	private Integer produtoTipoSelecionado;
	private List<ProdutoTipo> produtosTipos;
	private List<Tamanho> tamanhosDisponiveis;
	private List<Tamanho> tamanhosAssociados;
	
	private List<String> idsTamanhosAssociar;
	
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
					this.tamanhosAssociados = new ArrayList<>();
				} else {
					this.tamanhosAssociados = this.produto.getTamanhos();
				}
				
				produtosTipos = produtoTipoServico.obterTodosAtivosPorEmpresa(getLoginBean().getEmpresa());
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
			
			if (this.produto.getId() != null) {
				produtoServico.alterar(this.produto);
			} else {
				this.produto.setEmpresa(getLoginBean().getEmpresa());
				this.produto = produtoServico.salvar(this.produto);
			}
			
			salvarTamanhosAssociados(this.produto);
			
			return "produtos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public void associarTamanhos() {
		try {
			if(idsTamanhosAssociar != null && !idsTamanhosAssociar.isEmpty()) {
				tamanhosAssociados = tamanhoServico.obterPorIds(Util.converterIds(this.idsTamanhosAssociar));
				tamanhosDisponiveis.removeAll(tamanhosAssociados);
			}
		} catch (Exception e) {
			getLog().info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removerTamanhoAssociado(Tamanho tamanho) {
		try {
			tamanhosAssociados.remove(tamanho);
			tamanhosDisponiveis.add(tamanho);
			Collections.sort(tamanhosDisponiveis);
		} catch (Exception e) {
			getLog().info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void salvarTamanhosAssociados(Produto produto) {
		try {
			for (Tamanho t : tamanhosAssociados) {
				t.setProduto(produto);
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
}