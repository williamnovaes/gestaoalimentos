package br.com.nx.tickets.componente;

import br.com.nx.tickets.entidade.Usuario;

public class FiltroUsuarioIngresso extends FiltroPermissao {

	private static final long serialVersionUID = 1L;

	private Integer idEventoSelecionado;
	private Integer idGuicheSelecionado;
	private Integer idIngressoSituacaoSelecionado;

	public FiltroUsuarioIngresso(Usuario usuarioLogado) {
		super(usuarioLogado);
	}

	public Integer getIdEventoSelecionado() {
		return idEventoSelecionado;
	}

	public void setIdEventoSelecionado(Integer idEventoSelecionado) {
		this.idEventoSelecionado = idEventoSelecionado;
	}
	
	
	public Integer getIdGuicheSelecionado() {
		return idGuicheSelecionado;
	}
	
	public void setIdGuicheSelecionado(Integer idGuicheSelecionado) {
		this.idGuicheSelecionado = idGuicheSelecionado;
	}
	
	public Integer getIdIngressoSituacaoSelecionado() {
		return idIngressoSituacaoSelecionado;
	}
	
	public void setIdIngressoSituacaoSelecionado(Integer idIngressoSituacaoSelecionado) {
		this.idIngressoSituacaoSelecionado = idIngressoSituacaoSelecionado;
	}
}
