package br.com.nx.tickets.componente;

import br.com.nx.tickets.entidade.Usuario;

public class FiltroUsuarioCaixa extends FiltroPermissao {

	private static final long serialVersionUID = 1L;

	private Integer idPontoVendaSelecionado;

	public FiltroUsuarioCaixa(Usuario usuarioLogado) {
		super(usuarioLogado);
	}

	public Integer getIdPontoVendaSelecionado() {
		return idPontoVendaSelecionado;
	}

	public void setIdPontoVendaSelecionado(Integer idPontoVendaSelecionado) {
		this.idPontoVendaSelecionado = idPontoVendaSelecionado;
	}
}
