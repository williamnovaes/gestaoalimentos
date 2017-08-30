package br.com.will.gestao.entidade.util;

public enum EFormaPagamento {
	CARTAO("CARTAO", "cartao.png"), 
	DINHEIRO("DINHEIRO", "dinheiro.png");

	private final String label;
	private final String icone;

	EFormaPagamento(String label, String icone) {
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
