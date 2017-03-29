package br.com.nx.tickets.rest.envio;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioRetirada extends IngressoEnvio {

	@XmlElement(name = "evento_id")
	private Integer idEvento;

	@XmlElement(name = "guiche_id")
	private Integer idGuiche;

	@XmlElement(name = "login")
	private String login;

	@XmlElement(name = "senha")
	private String senha;
	
	@XmlElement(name = "retirada_valor")
	private BigDecimal valorRetirada;

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getIdGuiche() {
		return idGuiche;
	}

	public BigDecimal getValorRetirada() {
		return valorRetirada;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "IngressoEnvioRetirada [idEvento=" + idEvento + ", idGuiche=" + idGuiche + ", login=" + login
				+ ", senha=" + senha + ", valorRetirada=" + valorRetirada + "]";
	}
}