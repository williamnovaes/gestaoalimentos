package br.com.will.gestao.componente;

public enum ESeparador {

	PONTO_VIRGULA(";"), PIPE("\\|"), VIRGULA("\\,");

	private String split;

	ESeparador(final String split) {
		this.split = split;
	}

	public String getSplit() {
		return split;
	}
}