package br.com.nx.tickets.entidade.util;

public enum ETamanho {
	PEQUENO("P"), MEDIO("M"), GRANDE("G"), GRANDE_GRANGE("GG"), TRES_GRANGE("3G"), QUATRO_GRANGE("4G");

	private final String label;

	ETamanho(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}