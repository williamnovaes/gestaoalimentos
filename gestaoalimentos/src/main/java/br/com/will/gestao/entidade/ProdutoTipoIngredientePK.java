package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProdutoTipoIngredientePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "_produto_tipo")
	private int produtoTipo;

	@Column(name = "_ingrediente")
	private int ingrediente;

	public ProdutoTipoIngredientePK(int produtoTipo, int ingrediente) {
		this.produtoTipo = produtoTipo;
		this.ingrediente = ingrediente;
	}

	public ProdutoTipoIngredientePK() {
	}
	
	public int getProdutoTipo() {
		return produtoTipo;
	}
	
	public int getIngrediente() {
		return ingrediente;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + produtoTipo;
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
		ProdutoTipoIngredientePK other = (ProdutoTipoIngredientePK) obj;
		if (produtoTipo != other.produtoTipo) {
			return false;
		}
		if (ingrediente != other.ingrediente) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProdutoTipoIngredientePK [produtoTipo=" + produtoTipo + ", Ingrediente=" + ingrediente + "]";
	}
}