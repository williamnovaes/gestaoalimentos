package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PedidoProdutoSaborPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "_pedido_produto")
	private int pedidoProduto;

	@Column(name = "_sabor")
	private int sabor;

	public PedidoProdutoSaborPK(int pedidoProduto, int sabor) {
		this.pedidoProduto = pedidoProduto;
		this.sabor = sabor;
	}

	public PedidoProdutoSaborPK() {
	}
	
	public int getPedidoProduto() {
		return pedidoProduto;
	}
	
	public int getSabor() {
		return sabor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pedidoProduto;
		result = prime * result + sabor;
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
		PedidoProdutoSaborPK other = (PedidoProdutoSaborPK) obj;
		if (pedidoProduto != other.pedidoProduto) {
			return false;
		}
		if (sabor != other.sabor) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PedidoProdutoSaborPK [pedidoProduto=" + pedidoProduto + ", sabor=" + sabor + "]";
	}
}