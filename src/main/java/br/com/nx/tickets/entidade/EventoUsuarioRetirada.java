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
@Table(name = "evento_usuario_retirada", schema = "public")
public class EventoUsuarioRetirada implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EventoUsuarioRetiradaPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_evento", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_evento"))
	private Evento evento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_usuario", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_usuario"))
	private Usuario usuario;

	public EventoUsuarioRetirada() {
	}

	public EventoUsuarioRetirada(Evento evento, Usuario usuario) {
		this.evento = evento;
		this.usuario = usuario;
		this.id = new EventoUsuarioRetiradaPK(evento.getId(), usuario.getId());
	}

	public EventoUsuarioRetiradaPK getId() {
		return this.id;
	}

	public Evento getEvento() {
		return evento;
	}

	public Usuario getUsuario() {
		return usuario;
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
		EventoUsuarioRetirada other = (EventoUsuarioRetirada) obj;
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