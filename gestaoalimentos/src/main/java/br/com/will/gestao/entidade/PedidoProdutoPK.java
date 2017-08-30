package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PedidoProdutoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "_pedido")
	private int pedido;

	@Column(name = "_produto")
	private int produto;

	public PedidoProdutoPK(int pedido, int produto) {
		this.pedido = pedido;
		this.produto = produto;
	}

	public PedidoProdutoPK() {
	}
	
	public int getProduto() {
		return produto;
	}
	
	public int getPedido() {
		return pedido;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pedido;
		result = prime * result + produto;
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
		PedidoProdutoPK other = (PedidoProdutoPK) obj;
		if (pedido != other.pedido) {
			return false;
		}
		if (produto != other.produto) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PedidoProdutoPK [pedido=" + pedido + ", produto=" + produto + "]";
	}
}