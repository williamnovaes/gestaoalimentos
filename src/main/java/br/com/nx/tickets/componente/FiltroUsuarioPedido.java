package br.com.nx.tickets.componente;

import java.util.Date;

import br.com.nx.tickets.entidade.Usuario;

public class FiltroUsuarioPedido extends FiltroPermissao {

	private static final long serialVersionUID = 1L;

	private Integer idEventoSelecionado;
	private Integer idGuicheSelecionado;
	private Date dataInicio;
	private Date dataFim;

	public FiltroUsuarioPedido(Usuario usuarioLogado) {
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
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
}
