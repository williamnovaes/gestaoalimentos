package br.com.nx.tickets.entidade;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cliente_ingresso_tipo", schema = "public")
public class ClienteIngressoTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ClienteIngressoTipoPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_cliente", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_cliente"))
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ingresso_tipo", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_ingresso_tipo"))
	private IngressoTipo ingressoTipo;

	public ClienteIngressoTipo() {
	}

	public ClienteIngressoTipo(Cliente cliente, IngressoTipo ingressoTIpo) {
		this.cliente = cliente;
		this.ingressoTipo = ingressoTIpo;
		this.id = new ClienteIngressoTipoPK(cliente.getId(), ingressoTIpo.getId());
	}

	public ClienteIngressoTipoPK getId() {
		return this.id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public IngressoTipo getIngressoTipo() {
		return ingressoTipo;
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
		ClienteIngressoTipo other = (ClienteIngressoTipo) obj;
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