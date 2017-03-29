package br.com.nx.tickets.componente;

import br.com.nx.tickets.entidade.Usuario;

public class FiltroUsuarioSocioTorcedor extends FiltroPermissao {

	private static final long serialVersionUID = 1L;

	public FiltroUsuarioSocioTorcedor(Usuario usuarioLogado) {
		super(usuarioLogado);
	}
}
