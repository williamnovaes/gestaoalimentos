package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.servico.ProdutoServico;
import br.com.will.gestao.servico.SaborServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class SaborListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private SaborServico saborServico;
	@EJB
	private ProdutoServico produtoServico;
	
	private Sabor sabor;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), saborServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(Sabor s) {
		try {
			s.alterarSituacao();
			saborServico.alterar(s);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	public void fecharModal() {
	}
	
	public Sabor getSabor() {
		return sabor;
	}
}
