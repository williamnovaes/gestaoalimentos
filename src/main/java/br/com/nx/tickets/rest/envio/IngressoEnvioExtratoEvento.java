package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioExtratoEvento extends IngressoEnvio {

	@XmlElement(name = "evento_id")
	private Integer idEvento;

	public Integer getIdEvento() {
		return idEvento;
	}
	
	@Override
	public String toString() {
		return "IngressoEnvioExtratoGuiche [idEvento=" + idEvento + "]";
	}
}