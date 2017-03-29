package br.com.nx.tickets.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Cortesia;
import br.com.nx.tickets.servico.CortesiaServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class CortesiaListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CortesiaServico cortesiaServico;
	
	private Cortesia cortesia;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), cortesiaServico, SistemaConstantes.DEZ);
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
	
	public void alterarSituacao(Cortesia crt) {
		try {
			crt.alterarSituacao();
			cortesiaServico.alterar(crt);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public Cortesia getCortesia() {
		return cortesia;
	}
}
