package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProdutoIngredientePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "_produto")
	private int produto;

	@Column(name = "_ingrediente")
	private int ingrediente;

	public ProdutoIngredientePK(int produto, int ingrediente) {
		this.produto = produto;
		this.ingrediente = ingrediente;
	}

	public ProdutoIngredientePK() {
	}
	
	public int getProduto() {
		return produto;
	}
	
	public int getIngrediente() {
		return ingrediente;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + produto;
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
		ProdutoIngredientePK other = (ProdutoIngredientePK) obj;
		if (produto != other.produto) {
			return false;
		}
		if (ingrediente != other.ingrediente) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProdutoIngredientePK [produto=" + produto + ", ingrediente=" + ingrediente + "]";
	}
}