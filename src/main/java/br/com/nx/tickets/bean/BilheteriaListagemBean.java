package br.com.nx.tickets.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Bilheteria;
import br.com.nx.tickets.servico.BilheteriaServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class BilheteriaListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private BilheteriaServico bilheteriaServico;

	private Bilheteria bilheteria;


	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), bilheteriaServico, SistemaConstantes.DEZ);
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
	
	public void alterarSituacao(Bilheteria bi) {
		try {
			bi.alterarSituacao();
			bilheteriaServico.alterar(bi);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public Bilheteria getBilheteria() {
		return bilheteria;
	}
}