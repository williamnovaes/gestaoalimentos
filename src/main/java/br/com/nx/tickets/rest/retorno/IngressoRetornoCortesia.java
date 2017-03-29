package br.com.nx.tickets.rest.retorno;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.Cortesia;
import br.com.nx.tickets.entidade.Lote;

public class IngressoRetornoCortesia extends IngressoRetorno {

	@XmlElement(name = "cortesias")
	@JsonInclude(Include.NON_NULL)
	private List<Cortesia> cortesias;

	@XmlElement(name = "lote")
	@JsonInclude(Include.NON_NULL)
	private Lote lote;

	public List<Cortesia> getCortesias() {
		return cortesias;
	}

	public void setCortesias(List<Cortesia> cortesias) {
		this.cortesias = cortesias;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	@Override
	public String toString() {
		return "IngressoRetornoCortesia [cortesias=" + cortesias + ", lote=" + lote + "]";
	}
}
