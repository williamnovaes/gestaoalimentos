package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioValidacao extends IngressoEnvio {

	@XmlElement(name = "codigo")
	private String codigo;

	@XmlElement(name = "evento_id")
	private Integer idEvento;

	@XmlElement(name = "portaria_id")
	private Integer idPortaria;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getIdPortaria() {
		return idPortaria;
	}

	public void setIdPortaria(Integer idPortaria) {
		this.idPortaria = idPortaria;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	@Override
	public String toString() {
		return "IngressoEnvioValidacao [codigo=" + codigo + ", idEvento=" + idEvento + ", idPortaria=" + idPortaria
				+ "]";
	}
}