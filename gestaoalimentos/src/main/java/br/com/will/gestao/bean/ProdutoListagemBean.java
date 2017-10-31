package br.com.will.gestao.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.ProdutoTipoServico;
import br.com.will.gestao.servico.SaborServico;
import br.com.will.gestao.servico.TamanhoServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class ProdutoListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private ProdutoTipoServico produtoTipoServico;
	@EJB
	private TamanhoServico tamanhoServico;
	@EJB
	private SaborServico saborServico;
	
	private Produto produto;
	private List<Tamanho> tamanhosAssociados;
	private List<Tamanho> tamanhosDisponiveis;
	private List<Sabor> saboresAssociados;
	private List<Sabor> saboresDisponiveis;
	
	private boolean exibirModalTamanhos = false;
	private boolean exibirModalSabores = false;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), produtoServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(Produto p) {
		try {
			p.alterarSituacao();
			produtoServico.alterar(p);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public void abrirModalSabores(Produto produto) {
		try {
			fecharModal();
			exibirModalSabores = true;
			this.produto = produto;
			carregarSabores();
		} catch (Exception e) {
			adicionarError(e.getMessage());
		}
	}
	
	private void carregarSabores() throws Exception {
		try {
			this.saboresAssociados = saborServico.obterPorProdutoAssociados(this.produto);
			this.saboresDisponiveis = saborServico.obterTodosPorSituacao(ESituacao.ATIVO, true);
			this.saboresDisponiveis.removeAll(saboresAssociados);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void associarSabor(Sabor sabor) {
		try {
			sabor.setProduto(produto);
			saborServico.alterarProduto(sabor);
			carregarSabores();
		} catch (Exception e) {
			adicionarError(e.getMessage());
		}
	}
	
	public void desassociarSabor(Sabor sabor) {
		try {
			sabor.setProduto(null);
			saborServico.alterarProduto(sabor);
			carregarSabores();
		} catch (Exception e) {
			adicionarError(e.getMessage());
		}
	}
	
	public void abrirModalTamanho(Produto produto) {
		try {
			fecharModal();
			exibirModalTamanhos = true;
			this.produto = produto;
			carregarTamanhos();
		} catch (Exception e) {
			adicionarError(e.getMessage());
		}
	}
	
	private void carregarTamanhos() throws Exception {
		try {
			this.tamanhosAssociados = tamanhoServico.obterPorProdutoAssociados(this.produto);
			this.tamanhosDisponiveis = tamanhoServico.obterTodosPorSituacao(ESituacao.ATIVO, true);
			this.tamanhosDisponiveis.removeAll(tamanhosAssociados);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void associarTamanho(Tamanho t) {
		try {
			t.setProduto(this.produto);
			tamanhoServico.alterarProduto(t);
			carregarTamanhos();
		} catch (Exception e) {
			adicionarError(e.getMessage());
		}
	}
	
	public void desassociarSabor(Tamanho t) {
		try {
			t.setProduto(null);
			tamanhoServico.alterarProduto(t);
			carregarTamanhos();
		} catch (Exception e) {
			adicionarError(e.getMessage());
		}
	}
	
	@Override
	public void fecharModal() {
		exibirModalSabores = false;
		exibirModalTamanhos = false;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public List<Sabor> getSaboresAssociados() {
		return saboresAssociados;
	}
	
	public List<Sabor> getSaboresDisponiveis() {
		return saboresDisponiveis;
	}
	
	public List<Tamanho> getTamanhosAssociados() {
		return tamanhosAssociados;
	}
	
	public List<Tamanho> getTamanhosDisponiveis() {
		return tamanhosDisponiveis;
	}
	
	public boolean isExibirModalSabores() {
		return exibirModalSabores;
	}
	
	public boolean isExibirModalTamanhos() {
		return exibirModalTamanhos;
	}
}