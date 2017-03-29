package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

import br.com.nx.tickets.entidade.Pedido;

public class IngressoEnvioPedido extends IngressoEnvio {

	@XmlElement(name = "evento_id")
	private Integer idEvento;

	@XmlElement(name = "guiche_id")
	private Integer idGuiche;

	@XmlElement(name = "pedido")
	private Pedido pedido;

	@XmlElement(name = "versao_lote")
	private Float versaoLote;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Float getVersaoLote() {
		return versaoLote;
	}

	public void setVersaoLote(Float versaoLote) {
		this.versaoLote = versaoLote;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public Integer getIdGuiche() {
		return idGuiche;
	}

	@Override
	public String toString() {
		return "IngressoEnvioPedido [idEvento=" + idEvento + ", idGuiche=" + idGuiche + ", pedido=" + pedido
				+ ", versaoLote=" + versaoLote + ", getIdUsuario()=" + getIdUsuario() + ", getToken()=" + getToken()
				+ "]";
	}
}