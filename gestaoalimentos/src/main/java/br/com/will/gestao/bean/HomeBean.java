package br.com.will.gestao.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.entidade.Empresa;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.servico.CaixaServico;
import br.com.will.gestao.servico.EmpresaServico;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.ProdutoTipoServico;
import br.com.will.gestao.servico.UsuarioServico;

@Named
@ViewScoped
public class HomeBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CaixaServico caixaServico;
	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private ProdutoTipoServico produtoTipoServico;
	@EJB
	private EmpresaServico empresaServico;
	
	private List<Produto> produtos;
	private Caixa caixa;
	private List<Usuario> usuarios;
	private List<ProdutoTipo> produtosTipo;
	private Map<ProdutoTipo, List<Produto>> produtosCache;
	private List<Empresa> empresas;
	private boolean exbirModalSabores = false;
	private boolean exibirModalObservacao = false;
	private boolean exibirModalTamanho = false;
	private List<Produto> saboresDisponiveis;
	private List<Produto> saboresSelecionados; 
	
	private Tamanho tamanhoSelecionado;
	private ProdutoTipo produtoTipoSelecionado;
	private Produto produtoSelecionado;
	
	private Integer abaSelecionada;

	
	@PostConstruct
	public void inicializar() {
		try {
			produtosTipo = new ArrayList<>();
			if (getLoginBean().getEmpresa() != null) {
				produtosTipo = produtoTipoServico.obterTodosAtivosPorEmpresa(getLoginBean().getEmpresa());
				for (ProdutoTipo pt : produtosTipo) {
					produtosCache.put(pt, produtoServico.obterTodosPorTipo(pt));
				}
			} else {
				empresas = empresaServico.obterTodos("nomeFantasia");
				produtosCache = produtoTipoServico.obterTodosAtivosPorEmpresas(empresas);
				for (ProdutoTipo pt : produtosCache.keySet()) {
					produtosTipo.add(pt);
				}
			}
			abaSelecionada = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void carregarProdutosPorTipo(Integer idTipo) {
		try {
			abaSelecionada = idTipo;
			produtos = produtoServico.obterTodosPorIdTipo(idTipo);
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError("ERRO");
		}
	}
	
	public void adicionarProduto(ProdutoTipo tipo, Produto produto) {
		try {
			switch (tipo.getOrigemPreco()) {
				case PRODUTO:
					this.produtoSelecionado = produto;
					this.produtoTipoSelecionado = tipo;
					this.exibirModalObservacao = true;
					break;
				case TIPO_PRODUTO:
					this.produtoSelecionado = produto;
					this.produtoTipoSelecionado = tipo;
					this.exibirModalObservacao = true;
					break;
				case TAMANHO: 
					this.produtoSelecionado = produto;
					this.produtoTipoSelecionado = tipo;
					this.exibirModalTamanho = true;
					break;
				default:
					adicionarError("Selecione um produto");
					break;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
		
 	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public Caixa getCaixa() {
		return caixa;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public List<ProdutoTipo> getProdutosTipo() {
		return produtosTipo;
	}
	
	public Integer getAbaSelecionada() {
		return abaSelecionada;
	}
	
	public void setAbaSelecionada(Integer abaSelecionada) {
		this.abaSelecionada = abaSelecionada;
	}
	
	public boolean isAbaAtiva(Integer aba) {
		return aba == abaSelecionada;
	}
	
	public Map<ProdutoTipo, List<Produto>> getProdutosCache() {
		return produtosCache;
	}
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	
	public List<ProdutoTipo> getProdutosTipoCache() {
		List<ProdutoTipo> pts = new ArrayList<>();
		for (ProdutoTipo produtoTipo : produtosCache.keySet()) {
			pts.add(produtoTipo);
		}
		return pts;
	}
	
	public List<Produto> getProdutosPorTipo(ProdutoTipo pt) {
		return produtosCache.get(pt);
	}
	
	public void adicionarProduto(Produto produto) {
		
	}
	
	public void abrirModalSabores(ProdutoTipo produtoTipo) {
		this.setExibirModalSabores(true);
	}
	
	public boolean isExbirModalSabores() {
		return exbirModalSabores;
	}
	
	public void setExibirModalSabores(boolean exibirModalSabres) {
		this.setExibirModalSabores(exibirModalSabres);
	}
	
	public List<Produto> getSaboresDisponiveis() {
		return saboresDisponiveis;
	}
	
	public void setSaboresDisponiveis(List<Produto> saboresDisponiveis) {
		this.saboresDisponiveis = saboresDisponiveis;
	}
	
	public List<Produto> getSaboresSelecionados() {
		return saboresSelecionados;
	}
	
	public void setSaboresSelecionados(List<Produto> saboresSelecionados) {
		this.saboresSelecionados = saboresSelecionados;
	}
	
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}
	
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
}