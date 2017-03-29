package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioDadosAplicativo extends IngressoEnvio {

	@XmlElement(name = "login_terminal")
	private String loginTerminal;

	@XmlElement(name = "senha_terminal")
	private String senhaTerminal;

	@XmlElement(name = "guiche_id")
	private Integer idGuiche;

	@XmlElement(name = "portaria_id")
	private Integer idPortaria;

	@XmlElement(name = "evento_id")
	private Integer idEvento;

	public IngressoEnvioDadosAplicativo() {
	}

	public String getLoginTerminal() {
		return loginTerminal;
	}

	public void setLoginTerminal(String loginTerminal) {
		this.loginTerminal = loginTerminal;
	}

	public String getSenhaTerminal() {
		return senhaTerminal;
	}

	public void setSenhaTerminal(String senhaTerminal) {
		this.senhaTerminal = senhaTerminal;
	}

	public Integer getIdGuiche() {
		return idGuiche;
	}

	public void setIdGuiche(Integer idGuiche) {
		this.idGuiche = idGuiche;
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
		return "IngressoEnvioDadosAplicativo [loginTerminal=" + loginTerminal + ", senhaTerminal=" + senhaTerminal
				+ ", idGuiche=" + idGuiche + ", idPortaria=" + idPortaria + ", idEvento=" + idEvento + "]";
	}
}