package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.FormaPagamento;
import br.com.will.gestao.servico.FormaPagamentoServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class FormaPagamentoListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private FormaPagamentoServico formaPagamentoServico;
	
	private FormaPagamento formaPagamento;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), formaPagamentoServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(FormaPagamento i) {
		try {
			i.alterarSituacao();
			formaPagamentoServico.alterar(i);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
	}
	
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
}