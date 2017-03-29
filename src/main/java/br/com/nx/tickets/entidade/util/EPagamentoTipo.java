package br.com.nx.tickets.entidade.util;

public enum EPagamentoTipo {
	CARTAO("CARTAO"), 
	DINHEIRO("DINHEIRO");

	private final String label;

	EPagamentoTipo(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
