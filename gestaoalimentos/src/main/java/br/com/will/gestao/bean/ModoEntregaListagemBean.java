package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.ModoEntrega;
import br.com.will.gestao.servico.ModoEntregaServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class ModoEntregaListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ModoEntregaServico modoEntregaServico;
	
	private ModoEntrega modoEntrega;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), modoEntregaServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(ModoEntrega i) {
		try {
			i.alterarSituacao();
			modoEntregaServico.alterar(i);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
	}
	
	public ModoEntrega getModoEntrega() {
		return modoEntrega;
	}
}
