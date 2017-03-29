package br.com.nx.tickets.entidade;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.envers.NotAudited;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.rest.DateFormatterAdapter;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "numero", "valor", "valor_taxa_administrativa", "data_inicio", "data_fim" })
@Table(name = "lote", schema = "public")
public class Lote implements SituacaoAlteravel, Paginavel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "loteSeq", sequenceName = "lote_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "loteSeq")
	@Column(unique = true, nullable = false)
	@XmlElement(name = "id")
	private Integer id;

	@NotNull
	@XmlElement(name = "numero")
	@Column(name = "numero", nullable = false, updatable = false)
	private Integer numero;

	@NotNull
	@XmlElement(name = "quantidade_ingressos")
	@Column(name = "quantidade_ingressos", nullable = false)
	private Integer quantidadeIngressos;

	@NotNull
	@XmlElement(name = "valor")
	@Column(name = "valor", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS, nullable = false, updatable = false)
	private BigDecimal valor = new BigDecimal(0);

	@NotNull
	@XmlElement(name = "valor_taxa_administrativa")
	@Column(name = "valor_taxa_administrativa", precision = SistemaConstantes.DEZESSETE, 
	scale = SistemaConstantes.DOIS, nullable = false, updatable = false)
	private BigDecimal valorTaxaAdministrativa = new BigDecimal(0);

	@NotNull
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_inicio", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@XmlElement(name = "data_inicio")
	private Calendar dataInicio;

	@NotNull
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_fim", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@XmlElement(name = "data_fim")
	private Calendar dataFim;

	@NotNull
	@XmlTransient
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_cadastro", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro;

	@NotNull
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_evento", foreignKey = @ForeignKey(name = "fk_evento"), nullable = false)
	private Evento evento;

	@NotNull
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_usuario", foreignKey = @ForeignKey(name = "fk_usuario"), nullable = false)
	private Usuario usuario;

	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	@NotNull
	@XmlElement(name = "ingresso_tipo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ingresso_tipo", foreignKey = @ForeignKey(name = "fk_ingresso_tipo"), nullable = false)
	private IngressoTipo ingressoTipo;
	
	@XmlTransient
	@NotAudited
	@OneToMany(mappedBy = "lote", fetch = FetchType.LAZY)
	private List<GuicheLote> guichesLotes;

	public Lote() {
		this.situacao = ESituacao.ATIVO;
	}

	public Lote(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getQuantidadeIngressos() {
		return quantidadeIngressos;
	}

	public void setQuantidadeIngressos(Integer quantidadeIngressos) {
		this.quantidadeIngressos = quantidadeIngressos;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	@PrePersist
	protected void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public IngressoTipo getIngressoTipo() {
		return ingressoTipo;
	}

	public void setIngressoTipo(IngressoTipo ingressoTipo) {
		this.ingressoTipo = ingressoTipo;
	}

	public BigDecimal getValorTaxaAdministrativa() {
		return valorTaxaAdministrativa;
	}

	public void setValorTaxaAdministrativa(BigDecimal valorTaxaAdministrativa) {
		this.valorTaxaAdministrativa = valorTaxaAdministrativa;
	}

	public BigDecimal getTotal() {
		return valor.add(valorTaxaAdministrativa);
	}

	@Override
	public String getSqlSelect() {
		return "SELECT distinct(lt) FROM Lote lt ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct lt) FROM Lote lt ";
	}

	@Override
	public String getObjetoRetorno() {
		return "lt";
	}

	@Override
	public String getJoin() {
		return "JOIN FETCH lt.evento ev " + "JOIN FETCH lt.usuario usu " 
				+ "JOIN FETCH lt.ingressoTipo it ";
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
		Lote other = (Lote) obj;
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
		return "Lote [id=" + id + ", numero=" + numero + ", quantidadeIngressos=" + quantidadeIngressos + ", valor="
				+ valor + "]";
	}

	@Override
	public void alterarSituacao() {
		situacao = Util.alteraSituacao(situacao);
	}

	@Override
	public ESituacao getSituacao() {
		return situacao;
	}
}