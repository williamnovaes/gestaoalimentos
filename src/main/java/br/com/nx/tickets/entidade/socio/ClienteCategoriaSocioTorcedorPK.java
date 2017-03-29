package br.com.nx.tickets.entidade.socio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClienteCategoriaSocioTorcedorPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "_cliente")
	private int cliente;

	@Column(name = "_categoria_socio_torcedor")
	private int categoriaSocioTorcedor;

	public ClienteCategoriaSocioTorcedorPK() {
	}

	public ClienteCategoriaSocioTorcedorPK(int cliente, int categoriaSocioTorcedor) {
		this.cliente = cliente;
		this.categoriaSocioTorcedor = categoriaSocioTorcedor;
	}

	public int getCliente() {
		return cliente;
	}

	public int getCategoriaSocioTorcedor() {
		return categoriaSocioTorcedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cliente;
		result = prime * result + categoriaSocioTorcedor;
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
		ClienteCategoriaSocioTorcedorPK other = (ClienteCategoriaSocioTorcedorPK) obj;
		if (categoriaSocioTorcedor != other.categoriaSocioTorcedor) {
			return false;
		}
		if (cliente != other.cliente) {
			return false;
		}
		return true;
	}
}