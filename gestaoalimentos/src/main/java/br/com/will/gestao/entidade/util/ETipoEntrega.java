package br.com.will.gestao.entidade.util;

public enum ETipoEntrega {
	ENTREGAR("ENTREGAR", null),
	BUSCAR_LOCAL("BUSCAR NO LOCAL", null),
	COMER_LOCAL("COMER NO LOCAL", "bullet_green.png"); 
	
	private final String texto;
	private final String icone;
	
	ETipoEntrega(String texto, String icone) {
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