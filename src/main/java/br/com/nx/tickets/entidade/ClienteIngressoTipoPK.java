package br.com.nx.tickets.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClienteIngressoTipoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "_cliente")
	private int cliente;

	@Column(name = "_ingresso_tipo")
	private int ingressoTipo;

	public ClienteIngressoTipoPK() {
	}

	public ClienteIngressoTipoPK(int cliente, int ingressoTipo) {
		this.cliente = cliente;
		this.ingressoTipo = ingressoTipo;
	}

	public int getCliente() {
		return cliente;
	}

	public int getIngressoTipo() {
		return ingressoTipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cliente;
		result = prime * result + ingressoTipo;
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
		ClienteIngressoTipoPK other = (ClienteIngressoTipoPK) obj;
		if (ingressoTipo != other.ingressoTipo) {
			return false;
		}
		if (cliente != other.cliente) {
			return false;
		}
		return true;
	}
}