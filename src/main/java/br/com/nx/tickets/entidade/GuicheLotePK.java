package br.com.nx.tickets.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GuicheLotePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "_guiche")
	private int guiche;

	@Column(name = "_lote")
	private int lote;

	public GuicheLotePK() {
	}

	public GuicheLotePK(int guiche, int lote) {
		this.guiche = guiche;
		this.lote = lote;
	}

	public int getUsuario() {
		return this.guiche;
	}

	public int getPontoVenda() {
		return lote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lote;
		result = prime * result + guiche;
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
		GuicheLotePK other = (GuicheLotePK) obj;
		if (lote != other.lote) {
			return false;
		}
		if (guiche != other.guiche) {
			return false;
		}
		return true;
	}
}