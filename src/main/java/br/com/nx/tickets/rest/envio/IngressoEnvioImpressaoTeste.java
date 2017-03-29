package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioImpressaoTeste extends IngressoEnvio {

	@XmlElement(name = "guiche_id")
	private Integer idGuiche;

	@XmlElement(name = "teste_tipo")
	private String testeTipo = "INGRESSO";

	public Integer getIdGuiche() {
		return idGuiche;
	}

	public String getTesteTipo() {
		return testeTipo;
	}

	@Override
	public String toString() {
		return "IngressoEnvioImpressaoTeste [idGuiche=" + idGuiche + ", testeTipo=" + testeTipo + "]";
	}
}
