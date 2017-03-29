package br.com.nx.tickets.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Local;
import br.com.nx.tickets.servico.LocalServico;
import br.com.nx.tickets.servico.PortariaServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class LocalListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;

	@EJB
	private LocalServico localServico;
	@EJB
	private PortariaServico portariaServico;

	private Local local;
	
	private boolean exibirModalPortaria;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), localServico, SistemaConstantes.DEZ);
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
	
	public void obterPortariasPorLocal(Local lc) {
		try {
			this.local = lc;
			exibirModalPortaria = true;
			this.local.setPortarias(localServico.obterPortariasPorLocal(lc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fecharModal() {
		exibirModalPortaria = false;
	}
	
	public void alterarSituacao(Local lc) {
		try {
			lc.alterarSituacao();
			localServico.alterar(lc);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
		
	}

	public Local getLocal() {
		return local;
	}
	
	public boolean isExibirModalPortaria() {
		return exibirModalPortaria;
	}
}
