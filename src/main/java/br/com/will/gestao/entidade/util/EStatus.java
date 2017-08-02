package br.com.will.gestao.entidade.util;

public enum EStatus {
	ENVIADO("ENVIADO", null),
	PENDENTE("PENDENTE", null),
	CANCELADO("CANCELADO", "bullet_green.png"),
	PRONTO("PRONTO", "bullet_green.png"),
	SAIU_ENTREGA("SAIU PARA ENTREGA", "bullet_green.png"),
	EM_PRODUCAO("EM PRODUCAO", "bullet_red.png"); 
	
	private final String texto;
	private final String icone;
	
	EStatus(String texto, String icone) {
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