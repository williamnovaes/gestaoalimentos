package br.com.nx.tickets.rest.retorno;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import br.com.nx.tickets.entidade.Evento;

public class IngressoRetornoEventos extends IngressoRetorno {
	
	@XmlElement(name = "eventos")
	private List<Evento> eventos;
	
	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
}
