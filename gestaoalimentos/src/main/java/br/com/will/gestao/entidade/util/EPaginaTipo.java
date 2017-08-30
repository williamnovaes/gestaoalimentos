package br.com.will.gestao.entidade.util;

public enum EPaginaTipo {

	CADASTRO("CADASTRO"),
	LISTAGEM("LISTAGEM");
	
	private String label;
	
	EPaginaTipo(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}