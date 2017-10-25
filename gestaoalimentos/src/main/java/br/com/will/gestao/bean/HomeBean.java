package br.com.will.gestao.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.dto.ProdutoPedidoDTO;
import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.entidade.Empresa;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.entidade.util.EOrdenacao;
import br.com.will.gestao.servico.CaixaServico;
import br.com.will.gestao.servico.EmpresaServico;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.ProdutoTipoServico;
import br.com.will.gestao.servico.SaborServico;
import br.com.will.gestao.servico.TamanhoServico;
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
	@EJB
	private SaborServico saborServico;
	@EJB
	private TamanhoServico tamanhoServico;
	
	private List<Produto> produtos;
	private Caixa caixa;
	private List<Usuario> usuarios;
	private List<ProdutoTipo> produtosTipo;
	private Map<ProdutoTipo, List<Produto>> produtosCache;
	private List<Empresa> empresas;
	private boolean exibirModalSabores = false;
	private List<Sabor> saboresDisponiveis;
	private List<Sabor> saboresSelecionados;
	private List<Tamanho> tamanhosDisponiveis;
	private List<Integer> quantidade; 

	private Integer tamanhoSelecionado;
	private Integer limiteSabor;
	private String Observacao;
	private Produto produtoSelecionado;
	private Integer quantidadeSelecionada;
	
	private Integer abaSelecionada;
	private EOrdenacao ordemSelecionada;
	
	
	@PostConstruct
	public void inicializar() {
		try {
			ordemSelecionada = EOrdenacao.SEQUENCIA;
			produtos = produtoServico.obterTodosParaMenu(ordemSelecionada.getTexto());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
		this.exibirModalSabores = false;
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
	
	public void adicionarSabor(Integer idSabor) {
		try {
			Sabor sabor = saborServico.obterCompletoPorId(idSabor);
			this.getSaboresSelecionados().add(sabor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarProduto(Sabor sabor) {
		try {
			this.produtoSelecionado = sabor.getProduto();
			this.produtoSelecionado.getSabores().add(sabor);
			
			if (this.produtoSelecionado.isTamanho()) {
				tamanhosDisponiveis = this.produtoSelecionado.getTamanhos();
				this.tamanhoSelecionado = tamanhosDisponiveis.get(0).getId();
			}
			if (sabor.getProduto().isPermiteSabores()) {
				this.saboresDisponiveis = saborServico.obterTodosPorProduto(this.produtoSelecionado);
				this.limiteSabor = 1;
			}
			
			if (!this.produtoSelecionado.isPermiteSabores() && !this.produtoSelecionado.isTamanho()) {
				adicionarProdutoSaborTamanho();
			}
		} catch (Exception e) {
			adicionarError(e.getMessage());
		}
	}
	
	public void adicionarProdutoSaborTamanho() {
		try {
			verificarCamposObrigatorios();
			
			ProdutoPedidoDTO produto = new ProdutoPedidoDTO();
			produto.setProduto(this.produtoSelecionado);
			produto.setQuantidade(this.quantidadeSelecionada);
			produto.setSabores(this.saboresSelecionados);
			if (this.tamanhoSelecionado != null && this.tamanhoSelecionado > 0) {
				produto.setTamanho(tamanhoServico.obterPorId(this.tamanhoSelecionado));
			}
			produto.setObservacao(this.getObservacao());
			
			getLoginBean().getCarrinhoDto().add(produto);
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}
	
	public void verificarCamposObrigatorios() throws Exception {
		try {
			if (this.produtoSelecionado.isTamanho() 
					&& (this.tamanhoSelecionado == null || this.tamanhoSelecionado <= 0)) {
				throw new Exception("Escolha o Tamanho");
			}
			
			if (this.produtoSelecionado.isPermiteSabores()
					&& (this.saboresSelecionados == null || !this.saboresSelecionados.isEmpty())) {
				throw new Exception("Escolha ao menos 1 sabor");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void selecionarTamanho() {
		try {
			Tamanho tamanho = tamanhoServico.obterCompletoPorId(this.tamanhoSelecionado);
			this.limiteSabor = tamanho.getLimiteSabores();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abrirModalSabores(ProdutoTipo produtoTipo) {
		this.setExibirModalSabores(true);
	}
	
	public boolean isExibirModalSabores() {
		return exibirModalSabores;
	}
	
	public void setExibirModalSabores(boolean exibirModalSabres) {
		this.exibirModalSabores = exibirModalSabres;
	}
	
	public List<Sabor> getSaboresDisponiveis() {
		return saboresDisponiveis;
	}
	
	public void setSaboresDisponiveis(List<Sabor> saboresDisponiveis) {
		this.saboresDisponiveis = saboresDisponiveis;
	}
	
	public List<Sabor> getSaboresSelecionados() {
		return saboresSelecionados;
	}
	
	public void setSaboresSelecionados(List<Sabor> saboresSelecionados) {
		this.saboresSelecionados = saboresSelecionados;
	}
	
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}
	
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	
	public String getObservacao() {
		return Observacao;
	}
	
	public void setObservacao(String observacao) {
		Observacao = observacao;
	}
	
	public List<Integer> getQuantidade() {
		return quantidade;
	}
	
	public Integer getQuantidadeSelecionada() {
		return quantidadeSelecionada;
	}
	
	public void setQuantidadeSelecionada(Integer quantidadeSelecionada) {
		this.quantidadeSelecionada = quantidadeSelecionada;
	}
	
	public Integer getLimiteSabor() {
		return limiteSabor;
	}
	
	public boolean isMaximoSaboresSelecionados() {
		return this.limiteSabor == this.saboresSelecionados.size() ? true : false;
	}
}