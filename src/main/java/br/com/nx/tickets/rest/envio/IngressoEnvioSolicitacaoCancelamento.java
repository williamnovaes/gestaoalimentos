package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioSolicitacaoCancelamento extends IngressoEnvio {

	@XmlElement(name = "guiche_id")
	private int idGuiche;

	@XmlElement(name = "ingresso_id")
	private long idIngresso;

	public int getIdGuiche() {
		return idGuiche;
	}
	
	public long getIdIngresso() {
		return idIngresso;
	}

	public void setIdIngresso(long idIngresso) {
		this.idIngresso = idIngresso;
	}
	
	@Override
	public String toString() {
		return "IngressoEnvioSolicitacaoCancelamento [idGuiche=" + idGuiche + ", idIngresso=" + idIngresso + "]";
	}
}