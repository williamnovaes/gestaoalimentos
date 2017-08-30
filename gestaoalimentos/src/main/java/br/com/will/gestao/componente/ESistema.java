package br.com.will.gestao.componente;

import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;


public enum ESistema {
	
	SALESCALL("Sales <strong>Call</strong>", 
			"0800 136 0136", "logo-salescall.png", "sales.salescall.com.br", "nx/logo.png", "nx/avatar.png", "nx", ""),
	
	MULTISALES("Multi <strong>Sales</strong>", "43 3373 6901 / 43 3354 1282", 
			"logo-multisales.png", "multisales.com.br", "nx/logo.png", "nx/avatar.png", "net", ""),
	
	TELECOM("Multi <strong>Sales</strong> Telecom", "43 3373 6901 / 43 3354 1282", 
			"logo-multisales.png", "multisales.com.br", "nx/logo.png", "nx/avatar.png", "net", ""),
	
	VIVO("Multi <strong>Sales</strong>", "43 3351 0414", 
					"logo-multisales.png", "gestor.multisales.com.br/vivo", "vivo/logo.png", "vivo/avatar.png", "vivo", ""),
	
	NOVO("Sistema de Gestão do Distribuídor - <strong>SGD</strong>", "43 3373 6901 / 43 3354 1282", 
							"logo-multisales.png", "gestor.multisales.com.br/tim", "nx/logo.png", "nx/avatar.png", "nx", "c2"),
	
	SGD("Sistema de Gestão do Distribuídor - <strong>SGD</strong>", "43 3373 6901 / 43 3354 1282", 
			"logo-multisales.png", "gestor.multisales.com.br/sgd", "nx/logo.png", "nx/avatar.png", "nx", "c2"),
	
	AMBOS("Multi <strong>Sales</strong>", "43 3373 6901 / 43 3354 1282", "logo-multisales.png", "", "nx/logo.png", "nx/avatar.png", "nx", "");
	
	private final String nome;
	private final String telefone;
	private final String header;
	private final String url;
	private final String topo;
	private final String imagemLogin;
	private final String pastaCliente;
	private final String chatSistema;
	
	ESistema(String nome, String telefone, String header, String url, String topo, String imagemLogin, String pastaCliente, String chatSistema) {
		this.nome = nome;
		this.telefone = telefone;
		this.header = header;
		this.url = url;
		this.topo = topo;
		this.imagemLogin = imagemLogin;
		this.pastaCliente = pastaCliente + "/";
		this.chatSistema = chatSistema;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public String getTelefoneParaDiscadora() {
		return Util.removerNaoDigitos(telefone);
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getUrl() {
		return SistemaConstantes.PROTOCOLO + url;
	}
	
	public String getTopo() {
		return topo;
	}
	
	public String getImagemLogin() {
		return imagemLogin;
	}

	public String getPastaCliente() {
		return pastaCliente;
	}

	public String getChatSistema() {
		return chatSistema;
	}
}
