package br.com.nx.tickets.entidade.util;

public enum ETipoGuiche {
	AMBOS("AMBOS"),
	ONLINE("ONLINE"), 
	OFFLINE("OFFLINE");

	private final String label;

	ETipoGuiche(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
