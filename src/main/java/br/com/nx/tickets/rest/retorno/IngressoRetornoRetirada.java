package br.com.nx.tickets.rest.retorno;

import javax.xml.bind.annotation.XmlElement;

import br.com.nx.tickets.entidade.Retirada;

public class IngressoRetornoRetirada extends IngressoRetorno {
	
	@XmlElement(name = "retirada")
	private Retirada retirada;

	public Retirada getRetirada() {
		return retirada;
	}

	public void setRetirada(Retirada retirada) {
		this.retirada = retirada;
	}
}
