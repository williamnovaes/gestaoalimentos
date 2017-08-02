package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Promocao;
import br.com.will.gestao.servico.PromocaoServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class PromocaoListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PromocaoServico promocaoServico;
		
	private Promocao promocao;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), promocaoServico, SistemaConstantes.DEZ);
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
	
	public void alterarSituacao(Promocao pm) {
		try {
			pm.alterarSituacao();
			promocaoServico.alterar(pm);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situacao!");
			e.printStackTrace();
		}
	}

	public Promocao getPromocao() {
		return promocao;
	}
}