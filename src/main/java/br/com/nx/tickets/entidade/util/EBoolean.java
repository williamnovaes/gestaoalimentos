package br.com.nx.tickets.entidade.util;

public enum EBoolean {
	TRUE("SIM", "bullet_green.png"), 
	FALSE("NAO", "bullet_red.png");

	private final String label;
	private final String icone;

	EBoolean(String label, String icone) {
		this.label = label;
		this.icone = icone;
	}

	public String getLabel() {
		return label;
	}
	
	public String getIcone() {
		return icone;
	}
}
