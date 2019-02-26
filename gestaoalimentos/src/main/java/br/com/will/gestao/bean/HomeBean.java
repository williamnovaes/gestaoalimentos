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
import br.com.will.gestao.entidade.Ingrediente;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.entidade.util.EOrdenacao;
import br.com.will.gestao.servico.CaixaServico;
import br.com.will.gestao.servico.IngredienteServico;
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
	private SaborServico saborServico;
	@EJB
	private TamanhoServico tamanhoServico;
	@EJB
	private IngredienteServico ingredienteServico;
	
	private List<Produto> produtos;
	private Caixa caixa;
	private List<Usuario> usuarios;
	private List<ProdutoTipo> produtosTipo;
	private Map<ProdutoTipo, List<Produto>> produtosCache;
	private List<Sabor> saboresDisponiveis;
	private List<Sabor> saboresSelecionados;
	private List<Tamanho> tamanhosDisponiveis;
	private List<Integer> quantidade;
	private Tamanho tamanhoSelecionado;
	private List<Ingrediente> adicionais;

	private Integer idTamanhoSelecionado;
	private Integer limiteSabor;
	private String observacao;
	private Produto produtoSelecionado;
	private Integer quantidadeSelecionada;
	
	private Integer abaSelecionada;
	private boolean exibirModalDetalhes = false;
	private boolean exibirSelecaoTamanho = false;
	private boolean exibirSelecaoSabores = false;
	private EOrdenacao ordemSelecionada;
	private EOrdenacao[] ordenacaoDisponivel = EOrdenacao.values();
	
	@PostConstruct
	public void inicializar() {
		try {
			ordemSelecionada = EOrdenacao.SEQUENCIA;
//			produtosTipo = produtoTipoServico.obterTodosAtivosComProduto();

			produtos = produtoServico.obterTodosParaMenu(ordemSelecionada.getOrder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
		this.exibirModalDetalhes = false;
		this.exibirSelecaoSabores = false;
		this.exibirSelecaoTamanho = false;
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
	
	public void adicionarProduto(Sabor sabor) {
		try {
			exibirModalDetalhes = true;
			this.produtoSelecionado = produtoServico.obterPorSabor(sabor);
			saboresSelecionados.add(sabor);
			
			if (this.produtoSelecionado.isTamanho()) {
				exibirSelecaoTamanho = true;
				tamanhosDisponiveis = tamanhoServico.obterPorProduto(produtoSelecionado);
				tamanhoSelecionado = tamanhosDisponiveis.get(0);
				this.idTamanhoSelecionado = tamanhoSelecionado.getId();
				this.limiteSabor = tamanhoSelecionado.getLimiteSabores();
				if (produtoSelecionado.isPermiteSabores()) {
					ProdutoTipo pt = produtoTipoServico.obterPorProduto(produtoSelecionado);
					this.saboresDisponiveis = saborServico.obterPorProdutoTipo(pt);
					exibirSelecaoSabores = true;
				}
			}
			
			
//			if (!this.produtoSelecionado.isPermiteSabores() && !this.produtoSelecionado.isTamanho()) {
//				adicionarProdutoSaborTamanho();
//			}
		} catch (Exception e) {
			adicionarError(e.getMessage());
		}
	}
	
	public boolean isPodeEscolherSabor() {
		return this.limiteSabor < tamanhoSelecionado.getLimiteSabores()
				|| saboresDisponiveis.containsAll(saboresSelecionados);
	}
	
	public void marcarSabor(Sabor sabor) {
		try {
			if (saboresSelecionados != null && !saboresSelecionados.contains(sabor)) {
				saboresSelecionados.add(sabor);
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
			if (this.idTamanhoSelecionado != null && this.idTamanhoSelecionado > 0) {
				produto.setTamanho(tamanhoServico.obterPorId(this.idTamanhoSelecionado));
			}
			produto.setObservacao(this.getObservacao());
			
			getLoginBean().getCarrinhoDto().add(produto);
			limparSelecionados();
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}
	
	private void limparSelecionados() {
		try {
			saboresDisponiveis.clear();
			saboresSelecionados.clear();
			tamanhosDisponiveis.clear();
			produtoSelecionado = null;
			idTamanhoSelecionado = null;
			observacao = null;
			quantidadeSelecionada = 0;
			this.fecharModal();
		} catch (Exception e) {
			getLog().error(e.getMessage());
		}
	}

	public void verificarCamposObrigatorios() throws Exception {
		try {
			if (this.produtoSelecionado.isTamanho() 
					&& (this.idTamanhoSelecionado == null || this.idTamanhoSelecionado <= 0)) {
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
			tamanhoSelecionado = tamanhoServico.obterPorId(this.idTamanhoSelecionado);
			this.limiteSabor = tamanhoSelecionado.getLimiteSabores();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isExibirModalDetalhes() {
		return exibirModalDetalhes;
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
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
	
	public boolean isExibirSelecaoSabores() {
		return exibirSelecaoSabores;
	}
	
	public boolean isExibirSelecaoTamanho() {
		return exibirSelecaoTamanho;
	}
	
	public EOrdenacao getOrdemSelecionada() {
		return ordemSelecionada;
	}
	
	public void setOrdemSelecionada(EOrdenacao ordemSelecionada) {
		this.ordemSelecionada = ordemSelecionada;
	}
	
	public EOrdenacao[] getOrdenacaoDisponivel() {
		return ordenacaoDisponivel;
	}
	
	public Tamanho getTamanhoSelecionado() {
		return tamanhoSelecionado;
	}
	
	public List<Ingrediente> getAdicionais() {
		return adicionais;
	}
}