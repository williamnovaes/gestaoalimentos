package br.com.will.gestao.entidade.util;

public enum EOrigemPreco {
	PRODUTO("PRODUTO"),
	TIPO_PRODUTO("TIPO DO PRODUTO"), 
	TAMANHO("TAMANHO"); 
	
	private final String texto;
	
	EOrigemPreco(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}
}