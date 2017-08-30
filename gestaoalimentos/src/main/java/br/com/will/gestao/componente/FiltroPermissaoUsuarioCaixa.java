package br.com.will.gestao.componente;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class FiltroPermissaoUsuarioCaixa extends FiltroPermissaoUsuario {

	private static final long serialVersionUID = 1L;
	private FiltroUsuarioCaixa filtravel;

	@PostConstruct
	public void prepararFiltro() {
		filtravel = new FiltroUsuarioCaixa(getLoginBean().getUsuarioLogado());
		inicializarFiltro(filtravel);
	}
	
	public FiltroUsuarioCaixa getFiltravel() {
		return filtravel;
	}
}
