package br.com.nx.tickets.entidade.socio;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.nx.tickets.entidade.Cliente;

@Entity
@Table(name = "cliente_categoria_socio_torcedor", schema = "socio_torcedor")
public class ClienteCategoriaSocioTorcedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ClienteCategoriaSocioTorcedorPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_cliente", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_cliente"))
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_categoria_socio_torcedor", insertable = false, updatable = false, 
	foreignKey = @ForeignKey(name = "fk_categoria_socio_torcedor"))
	private CategoriaSocioTorcedor categoriaSocioTorcedor;

	public ClienteCategoriaSocioTorcedor() {
	}

	public ClienteCategoriaSocioTorcedor(Cliente cliente, CategoriaSocioTorcedor categoriaSocioTorcedor) {
		this.cliente = cliente;
		this.categoriaSocioTorcedor = categoriaSocioTorcedor;
		this.id = new ClienteCategoriaSocioTorcedorPK(cliente.getId(), categoriaSocioTorcedor.getId());
	}

	public ClienteCategoriaSocioTorcedorPK getId() {
		return this.id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public CategoriaSocioTorcedor getCategoriaSocioTorcedor() {
		return categoriaSocioTorcedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ClienteCategoriaSocioTorcedor other = (ClienteCategoriaSocioTorcedor) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}