package br.com.nx.tickets.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EventoSocioTorcedorPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "_evento")
	private int evento;

	@Column(name = "_socio_torcedor")
	private int socioTorcedor;

	public EventoSocioTorcedorPK() {
	}

	public EventoSocioTorcedorPK(int evento, int socioTorcedor) {
		this.evento = evento;
		this.socioTorcedor = socioTorcedor;
	}

	public int getEvento() {
		return evento;
	}
	
	public int getSocioTorcedor() {
		return socioTorcedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + evento;
		result = prime * result + socioTorcedor;
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
		EventoSocioTorcedorPK other = (EventoSocioTorcedorPK) obj;
		if (evento != other.evento) {
			return false;
		}
		if (socioTorcedor != other.socioTorcedor) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EventoSocioTorcedorPK [evento=" + evento + ", socioTorcedor=" + socioTorcedor + "]";
	}
}
