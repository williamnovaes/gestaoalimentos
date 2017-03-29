package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioExtratoGuiche extends IngressoEnvio {

	@XmlElement(name = "guiche_id")
	private Integer idGuiche;

	@XmlElement(name = "evento_id")
	private Integer idEvento;

	public Integer getIdGuiche() {
		return idGuiche;
	}
	
	public Integer getIdEvento() {
		return idEvento;
	}
	
	@Override
	public String toString() {
		return "IngressoEnvioExtratoGuiche [idGuiche=" + idGuiche + ", idEvento=" + idEvento + "]";
	}
}