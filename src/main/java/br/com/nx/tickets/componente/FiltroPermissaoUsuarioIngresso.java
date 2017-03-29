package br.com.nx.tickets.componente;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class FiltroPermissaoUsuarioIngresso extends FiltroPermissaoUsuario {

	private static final long serialVersionUID = 1L;
	private FiltroUsuarioIngresso filtravel;

	@PostConstruct
	public void prepararFiltro() {
		filtravel = new FiltroUsuarioIngresso(getLoginBean().getUsuarioLogado());
		inicializarFiltro(filtravel);
	}
	
	public FiltroUsuarioIngresso getFiltravel() {
		return filtravel;
	}
}
