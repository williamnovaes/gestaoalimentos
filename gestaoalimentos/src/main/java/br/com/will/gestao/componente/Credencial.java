package br.com.will.gestao.componente;

import javax.enterprise.inject.Model;

import br.com.will.gestao.util.Util;

@Model
public class Credencial {

	private String username;
	private String password;
	
	public Credencial() {
		
	}

	public Credencial(String username, String password) {
		this.username = username.toUpperCase();
		this.password = password.toUpperCase();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		if (password != null) {
			password =  Util.criptografarString(password.toUpperCase());
		} 
		return password;
	}

	public String getPasswordDescriptografado() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}