package br.com.will.gestao.entidade.util;

public enum ETipoNivel {

	CLIENTE("CLIENTE"),
	FUNCIONARIO("FUNCIONARIO"),
	ADMINISTRADOR("ADMINISTRADOR");

	private final String descricao;

	ETipoNivel(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
