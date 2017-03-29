package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IngressoEnvioSorteioPromocao extends IngressoEnvio {

	@XmlElement(name = "evento_id")
	private Integer idEvento;
	
	public Integer getIdEvento() {
		return idEvento;
	}
	
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	@Override
	public String toString() {
		return "IngressoEnvioSorteioPromocao [idEvento=" + idEvento + "]";
	}
}
