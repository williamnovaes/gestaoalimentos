package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SaborIngredientePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "_sabor")
	private int sabor;

	@Column(name = "_ingrediente")
	private int ingrediente;

	public SaborIngredientePK(int sabor, int ingrediente) {
		this.sabor = sabor;
		this.ingrediente = ingrediente;
	}

	public SaborIngredientePK() {
	}
	
	public int getSabor() {
		return sabor;
	}
	
	public int getIngrediente() {
		return ingrediente;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sabor;
		result = prime * result + ingrediente;
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
		SaborIngredientePK other = (SaborIngredientePK) obj;
		if (sabor != other.sabor) {
			return false;
		}
		if (ingrediente != other.ingrediente) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SaborIngredientePK [sabor=" + sabor + ", ingrediente=" + ingrediente + "]";
	}
}