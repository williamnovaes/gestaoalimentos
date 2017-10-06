package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PedidoProdutoSaborPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "_pedido")
	private int pedido;

	@Column(name = "_produto")
	private int produto;
	
	@Column(name = "_sabor")
	private int sabor;

	public PedidoProdutoSaborPK(int pedido, int produto, int sabor) {
		this.pedido = pedido;
		this.produto = produto;
		this.sabor = sabor;
	}

	public PedidoProdutoSaborPK() {
	}
	
	public int getProduto() {
		return produto;
	}
	
	public int getPedido() {
		return pedido;
	}
	
	public int getSabor() {
		return sabor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pedido;
		result = prime * result + produto;
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
		if (pedido != other.pedido) {
			return false;
		}
		if (produto != other.produto) {
			return false;
		}
		if (sabor != other.sabor) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PedidoProdutoPK [pedido=" + pedido + ", produto=" + produto + ", sabor=" + sabor + "]";
	}
}