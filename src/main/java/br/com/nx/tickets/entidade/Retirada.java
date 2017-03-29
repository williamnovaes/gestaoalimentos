package br.com.nx.tickets.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.rest.DateFormatterAdapter;
import br.com.nx.tickets.util.SistemaConstantes;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "retirada", schema = "public")
public class Retirada implements Paginavel, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@XmlElement(name = "id")
	@SequenceGenerator(name = "retiradaSeq", sequenceName = "retirada_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "retiradaSeq")
	@Column(unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_usuario", foreignKey = @ForeignKey(name = "fk_usuario"))
	private Usuario usuario;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@XmlElement(name = "data_cadastro")
	@Column(name = "data_cadastro", nullable = false)
	private Calendar dataCadastro;
	
	@XmlElement(name = "valor")
	@Column(name = "valor", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal valor = new BigDecimal(0);
	
	@NotNull
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "guiche")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_guiche", foreignKey = @ForeignKey(name = "fk_guiche"))
	private Guiche guiche;
	
	@NotNull
	@XmlTransient
	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_evento", foreignKey = @ForeignKey(name = "fk_evento"))
	private Evento evento;

	public Retirada() {
	}
	
	@PrePersist
	protected void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Guiche getGuiche() {
		return guiche;
	}
	
	public void setGuiche(Guiche guiche) {
		this.guiche = guiche;
	}
	
	public Evento getEvento() {
		return evento;
	}
	
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	@Override
	public String getSqlSelect() {
		return null;
	}

	@Override
	public String getSqlCount() {
		return null;
	}

	@Override
	public String getObjetoRetorno() {
		return null;
	}

	@Override
	public String getJoin() {
		return null;
	}
}