package br.com.nx.tickets.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.nx.tickets.util.SistemaConstantes;

@Embeddable
public class IngressoPromocaoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "_ingresso")
	private long ingresso;

	@Column(name = "_promocao")
	private int promocao;

	public IngressoPromocaoPK() {
	}

	public IngressoPromocaoPK(long ingresso, int promocao) {
		this.ingresso = ingresso;
		this.promocao = promocao;
	}

	public long getIngresso() {
		return ingresso;
	}

	public int getPromocao() {
		return promocao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ingresso ^ (ingresso >>> SistemaConstantes.TRINTA_DOIS));
		result = prime * result + promocao;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IngressoPromocaoPK other = (IngressoPromocaoPK) obj;
		if (ingresso != other.ingresso) {
			return false;
		}
		if (promocao != other.promocao) {
			return false;
		}
		return true;
	}
}