package br.com.nx.tickets.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.ArquivoAplicativo;
import br.com.nx.tickets.servico.ArquivoAplicativoServico;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Named
@ViewScoped
public class AplicativoListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ArquivoAplicativoServico arquivoAplicativoServico;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), arquivoAplicativoServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}

	public void buscarRegistros() {
		try {
			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void download(Integer id) {
		try {
			Util.download(arquivoAplicativoServico.obterPorId(id));
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(ArquivoAplicativo aa) {
		try {
			aa.alterarSituacao();
			arquivoAplicativoServico.alterar(aa);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
}
