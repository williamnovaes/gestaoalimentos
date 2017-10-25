package br.com.will.gestao.entidade.util;

public enum EOrdenacao {
	DESCRICAO("descricao"),
	INDEX("index"),
	SEQUENCIA("sequencia"),
	MAIOR_PRECO("maior preco"),
	MENOR_PRECO("menor preco");
	
	private final String texto;
	
	EOrdenacao(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}
}