package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.StatusAtendimento;
import br.com.will.gestao.servico.StatusAtendimentoServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class StatusAtendimentoListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private StatusAtendimentoServico statusAtendimentoServico;
	
	private StatusAtendimento statusAtendimento;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), statusAtendimentoServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(StatusAtendimento i) {
		try {
			i.alterarSituacao();
			statusAtendimentoServico.alterar(i);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
	}
	
	public StatusAtendimento getStatusAtendimento() {
		return statusAtendimento;
	}
}
