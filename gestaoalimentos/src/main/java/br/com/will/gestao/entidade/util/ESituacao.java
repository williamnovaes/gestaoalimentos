package br.com.will.gestao.entidade.util;

public enum ESituacao {
	AMBOS("AMBOS", null),
	ATIVO("ATIVO", "bullet_green.png"), 
	INATIVO("INATIVO", "bullet_red.png"); 
	
	private final String texto;
	private final String icone;
	
	ESituacao(String texto, String icone) {
		this.texto = texto;
		this.icone = icone;
	}

	public String getTexto() {
		return texto;
	}
	
	public String getIcone() {
		return icone;
	}
}