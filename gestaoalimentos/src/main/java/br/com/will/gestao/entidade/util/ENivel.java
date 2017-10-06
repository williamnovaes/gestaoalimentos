package br.com.will.gestao.entidade.util;

public enum ENivel {
	
	SUPER_USER(1, "SUPERUSER", ETipoNivel.ADMINISTRADOR),
	ADMINISTRADOR(2, "ADMINISTRADOR", ETipoNivel.FUNCIONARIO),
	CLIENTE(3,  "CLIENTE", ETipoNivel.CLIENTE);
	
	private final int id;
	private final String descricao;
	private final ETipoNivel tipoNivel;

	ENivel(int id, String descricao, ETipoNivel tipoNivel) {
		this.id = id;
		this.descricao = descricao;
		this.tipoNivel = tipoNivel;
	}

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getCodigo() {
		return descricao;
	}
	
	public ETipoNivel getTipoNivel() {
		return tipoNivel;
	}
}