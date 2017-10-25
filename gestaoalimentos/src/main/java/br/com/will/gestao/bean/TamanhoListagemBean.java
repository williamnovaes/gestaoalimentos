package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Tamanho;
import br.com.will.gestao.servico.TamanhoServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class TamanhoListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private TamanhoServico tamanhoServico;
	
	private Tamanho tamanho;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), tamanhoServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(Tamanho t) {
		try {
			t.alterarSituacao();
			tamanhoServico.alterar(t);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
	}
	
	public Tamanho getTamanho() {
		return tamanho;
	}
}
