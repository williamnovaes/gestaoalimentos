package br.com.will.gestao.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.servico.CaixaServico;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.ProdutoTipoServico;
import br.com.will.gestao.servico.UsuarioServico;

@Named
@ViewScoped
public class HomeBean extends BaseBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CaixaServico caixaServico;
	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private ProdutoTipoServico produtoTipoServico;
	
	private List<Produto> produtos;
	private Caixa caixa;
	private List<Usuario> usuarios;
	private List<ProdutoTipo> produtosTipo;
	
	private Integer abaSelecionada;
	
	@PostConstruct
	public void inicializar() {
		try {
			produtosTipo = produtoTipoServico.obterTodosAtivosPorEmpresa(getLoginBean().getEmpresa());
			
			if (produtosTipo != null && !produtosTipo.isEmpty()) {
				abaSelecionada = produtosTipo.get(0).getId();
			} else {
				abaSelecionada = 0;
			}
			carregarProdutosPorTipo(abaSelecionada);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void carregarProdutosPorTipo(Integer idTipo) {
		try {
			produtos = produtoServico.obterTodosPorIdTipo(idTipo);
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError("ERRO");
		}
	}
	
	@Override
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
}
	
