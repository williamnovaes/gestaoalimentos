package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioLotesPorEvento extends IngressoEnvio {
	
	@XmlElement(name = "evento_id")
	private int idEvento;
	
	@XmlElement(name = "guiche_id")
	private int idGuiche;

	public IngressoEnvioLotesPorEvento() {
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public int getIdGuiche() {
		return idGuiche;
	}
	
	public void setIdGuiche(int idGuiche) {
		this.idGuiche = idGuiche;
	}

	@Override
	public String toString() {
		return "IngressoEnvioLotesPorEvento [idEvento=" + idEvento + ", idGuiche=" + idGuiche + "]";
	}
}