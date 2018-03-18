package br.com.will.gestao.entidade.util;

public enum EOrdenacao {
	DESCRICAO("descricao", " descricao ASC "),
	SEQUENCIA("sequencia", " sequencia ASC "),
	MAIOR_PRECO("maior preco", " valor DESC "),
	MENOR_PRECO("menor preco", " valor ASC ");
	
	private final String texto;
	private final String order;
	
	EOrdenacao(String texto, String order) {
		this.texto = texto;
		this.order = order;
	}

	public String getTexto() {
		return texto;
	}
	
	public String getOrder() {
		return order;
	}
}