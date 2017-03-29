package br.com.nx.tickets.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EventoUsuarioRetiradaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "_evento")
	private int evento;

	@Column(name = "_usuario")
	private int usuario;

	public EventoUsuarioRetiradaPK() {
	}

	public EventoUsuarioRetiradaPK(int evento, int usuario) {
		this.evento = evento;
		this.usuario = usuario;
	}

	public int getEvento() {
		return this.evento;
	}

	public int getUsuairo() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + usuario;
		result = prime * result + evento;
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
		EventoUsuarioRetiradaPK other = (EventoUsuarioRetiradaPK) obj;
		if (usuario != other.usuario) {
			return false;
		}
		if (evento != other.evento) {
			return false;
		}
		return true;
	}
}