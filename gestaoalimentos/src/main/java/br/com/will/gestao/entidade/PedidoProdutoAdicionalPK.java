package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PedidoProdutoAdicionalPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "_pedido_produto")
	private int pedidoProduto;

	@Column(name = "_ingrediente")
	private int ingrediente;

	public PedidoProdutoAdicionalPK(int pedidoProduto, int ingrediente) {
		this.pedidoProduto = pedidoProduto;
		this.ingrediente = ingrediente;
	}

	public PedidoProdutoAdicionalPK() {
	}
	
	public int getPedidoProduto() {
		return pedidoProduto;
	}
	
	public int getIngrediente() {
		return ingrediente;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pedidoProduto;
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
		PedidoProdutoAdicionalPK other = (PedidoProdutoAdicionalPK) obj;
		if (pedidoProduto != other.pedidoProduto) {
			return false;
		}
		if (ingrediente != other.ingrediente) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("PedidoProdutoIntegredientePK [pedidoProduto=").append(pedidoProduto).append(", ingrediente=");
		str.append(ingrediente).append("]");
		return str.toString();
	}
}