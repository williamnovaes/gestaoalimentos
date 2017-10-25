package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Ingrediente;
import br.com.will.gestao.servico.IngredienteServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class IngredienteListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IngredienteServico ingredienteServico;
	
	private Ingrediente ingrediente;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), ingredienteServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(Ingrediente i) {
		try {
			i.alterarSituacao();
			ingredienteServico.alterar(i);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
	}
	
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
}
