package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.ProdutoTipo;
import br.com.will.gestao.servico.ProdutoTipoServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class ProdutoTipoListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProdutoTipoServico produtoTipoServico;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), produtoTipoServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(ProdutoTipo pt) {
		try {
			pt.alterarSituacao();
			produtoTipoServico.alterar(pt);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	@Override
	public void fecharModal() {
	}
}
