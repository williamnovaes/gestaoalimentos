package br.com.will.gestao.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Ingrediente;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.servico.IngredienteServico;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.SaborServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class SaborListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private SaborServico saborServico;
	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private IngredienteServico ingredienteServico;
	
	private Sabor sabor;
	private boolean exibirModalIngrediente;
	private List<Ingrediente> ingredientesAssociados;
	private List<Ingrediente> ingredientesDisponiveis;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), saborServico, SistemaConstantes.DEZ);
		exibirModalIngrediente = false;
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(Sabor s) {
		try {
			s.alterarSituacao();
			saborServico.alterar(s);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public void abrirModalIngredientes(Sabor sabor) {
		this.sabor = sabor;
		exibirModalIngrediente = true;
		carregarSaborIngrediente();
	}
	
	public void carregarSaborIngrediente() {
		try {
			ingredientesDisponiveis = ingredienteServico.obterTodos("sequencia");
			ingredientesAssociados = ingredienteServico.obterAssociadosPorSabor(this.sabor);
			ingredientesDisponiveis.removeAll(ingredientesAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarIngrediente(Ingrediente ingrediente) {
		try {
			ingredientesAssociados.add(ingrediente);
			saborServico.salvarSaborIngrediente(sabor, ingredientesAssociados);
			carregarSaborIngrediente();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerIngrediente(Ingrediente ingrediente) {
		try {
			ingredientesAssociados.remove(ingrediente);
			saborServico.salvarSaborIngrediente(sabor, ingredientesAssociados);
			carregarSaborIngrediente();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
		exibirModalIngrediente = false;
	}
	
	public Sabor getSabor() {
		return sabor;
	}
	
	public boolean isExibirModalIngrediente() {
		return exibirModalIngrediente;
	}
	
	public List<Ingrediente> getIngredientesAssociados() {
		return ingredientesAssociados;
	}
	
	public List<Ingrediente> getIngredientesDisponiveis() {
		return ingredientesDisponiveis;
	}
}
