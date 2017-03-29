package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioEventos extends IngressoEnvio {

	@XmlElement(name = "guiche_id")
	private Integer idGuiche;

	public Integer getIdGuiche() {
		return idGuiche;
	}

	@Override
	public String toString() {
		return "IngressoEnvioEventos [idGuiche=" + idGuiche + "]";
	}
}