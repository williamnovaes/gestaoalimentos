package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlElement;

public class IngressoEnvioLogin extends IngressoEnvio {

	@XmlElement(name = "login")
	private String login;
	@XmlElement(name = "senha")
	private String senha;
	@XmlElement(name = "terminal")
	private String terminal;

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}
	
	public String getTerminal() {
		return terminal;
	}
	
	public void setIdUsuario(Integer idUsuario) {
		super.setIdUsuario(idUsuario);
	}

	@Override
	public String toString() {
		return "IngressoEnvioLogin [login=" + login + ", senha=" + senha + ", terminal=" + terminal + "]";
	}
}