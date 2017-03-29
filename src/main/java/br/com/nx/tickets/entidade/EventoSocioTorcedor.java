package br.com.nx.tickets.entidade;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.socio.SocioTorcedor;
import br.com.nx.tickets.rest.DateFormatterAdapter;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "evento_socio_torcedor", schema = "public")
public class EventoSocioTorcedor implements Serializable, Paginavel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EventoSocioTorcedorPK id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@XmlElement(name = "evento")
	@JoinColumn(name = "_evento", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_evento"))
	private Evento evento;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@XmlElement(name = "socio_torcedor")
	@JoinColumn(name = "_socio_torcedor", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_socio_torcedor"))
	private SocioTorcedor socioTorcedor;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@JsonInclude(Include.NON_NULL)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_cadastro", nullable = false)
	@XmlElement(name = "data_cadastro")
	private Calendar dataCadastro;
	
	@NotNull
	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.LAZY)
	@XmlElement(name = "portaria")
	@JoinColumn(name = "_portaria", foreignKey = @ForeignKey(name = "fk_portaria"), nullable = false)
	private Portaria portaria;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_sorteado")
	private Calendar dataSorteado;

	public EventoSocioTorcedor() {
	}

	public EventoSocioTorcedor(Evento evento, SocioTorcedor socioTorcedor) {
		this.evento = evento;
		this.socioTorcedor = socioTorcedor;
		this.id = new EventoSocioTorcedorPK(evento.getId(), socioTorcedor.getId());
	}

	public EventoSocioTorcedorPK getId() {
		return id;
	}

	public Evento getEvento() {
		return evento;
	}
	
	public SocioTorcedor getSocioTorcedor() {
		return socioTorcedor;
	}
	
	@PrePersist
	public void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	public Portaria getPortaria() {
		return portaria;
	}
	
	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
	}
	
	public Calendar getDataSorteado() {
		return dataSorteado;
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
		EventoSocioTorcedor other = (EventoSocioTorcedor) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EventoSocioTorcedor [id=" + id + "]";
	}

	@Override
	public String getSqlSelect() {
		return "SELECT distinct(est) FROM EventoSocioTorcedor est ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct est.id) FROM EventoSocioTorcedor est ";
	}

	@Override
	public String getObjetoRetorno() {
		return "est";
	}

	@Override
	public String getJoin() {
		return "JOIN FETCH est.evento ev "
			 + "JOIN FETCH est.socioTorcedor st ";
	}
}