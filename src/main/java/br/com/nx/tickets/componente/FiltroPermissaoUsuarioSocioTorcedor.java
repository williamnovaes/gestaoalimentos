package br.com.nx.tickets.componente;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class FiltroPermissaoUsuarioSocioTorcedor extends FiltroPermissaoUsuario {

	private static final long serialVersionUID = 1L;
	private FiltroUsuarioSocioTorcedor filtravel;

	@PostConstruct
	public void prepararFiltro() {
		filtravel = new FiltroUsuarioSocioTorcedor(getLoginBean().getUsuarioLogado());
		inicializarFiltro(filtravel);
	}
	
	public FiltroUsuarioSocioTorcedor getFiltravel() {
		return filtravel;
	}
}
