package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.servico.CaixaServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class CaixaListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CaixaServico caixaServico;
	
	private Caixa caixa;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), caixaServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(Caixa cx) {
		try {
			cx.alterarSituacao();
			caixaServico.alterar(cx);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	@Override
	public void fecharModal() {
	}
	
	public Caixa getCaixa() {
		return caixa;
	}
}