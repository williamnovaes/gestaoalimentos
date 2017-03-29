package br.com.nx.tickets.entidade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.EFormatoData;
import br.com.nx.tickets.rest.DateFormatterAdapter;
import br.com.nx.tickets.util.SistemaConstantes;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "codigo", "lote_id", "ingresso_tipo_descricao", "valor", "taxa", "total",
		"codigo_promocao", "observacao_promocao" })
@Table(name = "ingresso", schema = "public")
public class Ingresso implements Paginavel, Comparable<Ingresso> {

	@Id
	@XmlElement(name = "id")
	@SequenceGenerator(name = "ingressoSeq", sequenceName = "ingresso_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ingressoSeq")
	@Column(unique = true, nullable = false)
	private Long id;

	@NotNull
	@XmlElement(name = "codigo")
	@Column(name = "codigo", length = SistemaConstantes.TREZE, nullable = false, unique = true, updatable = false)
	private String codigo;

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_pedido", foreignKey = @ForeignKey(name = "fk_pedido"))
	private Pedido pedido;

	@NotNull
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "_guiche", referencedColumnName = "_guiche", nullable = false, updatable = false),
			@JoinColumn(name = "_lote", referencedColumnName = "_lote", nullable = false, updatable = false) })
	private GuicheLote guicheLote;

	@NotNull
	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.LAZY)
	@XmlElement(name = "ingresso_situacao")
	@JoinColumn(name = "_ingresso_situacao", foreignKey = @ForeignKey(name = "fk_ingresso_situacao"), nullable = false)
	private IngressoSituacao ingressoSituacao;

	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.LAZY)
	@XmlElement(name = "portaria_validacao")
	@JoinColumn(name = "_portaria_validacao", foreignKey = @ForeignKey(name = "fk_portaria_leitura"))
	private Portaria portariaValidacao;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "data_validacao")
	@Temporal(TemporalType.TIMESTAMP)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_validacao", updatable = false)
	private Calendar dataValidacao;

	@XmlElement(name = "observacao")
	@JsonInclude(Include.NON_NULL)
	@Column(name = "observacao", length = SistemaConstantes.DUZENTOS)
	private String observacao;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "codigo_promocao")
	@Column(name = "codigo_promocao", length = SistemaConstantes.NOVE)
	private String codigoPromocao;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "observacao_promocao")
	@Column(name = "observacao_promocao", length = SistemaConstantes.DUZENTOS)
	private String observacaoPromocao;

	@Transient
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "lote_id")
	private String idLote;

	@Transient
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "valor")
	private String valor;

	@Transient
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "taxa")
	private String taxa;

	@Transient
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "total")
	private String total;

	@Transient
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "lote")
	private Lote lote;

	@Transient
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingresso_tipo_descricao")
	private String ingressoTipoDescricao;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "sincronizar")
	private EBoolean sincronizar = EBoolean.FALSE;

	public Ingresso() {
	}

	public Ingresso(Object[] objects) {
		int coluna = 0;
		this.idLote = ((Integer) objects[coluna++]).toString();
		this.id = ((BigInteger) objects[coluna++]).longValue();
		this.codigo = (String) objects[coluna++];
		this.valor = ((BigDecimal) objects[coluna++]).toString();
		this.taxa = ((BigDecimal) objects[coluna++]).toString();
		this.total = ((BigDecimal) objects[coluna++]).toString();
		this.codigoPromocao = (String) objects[coluna++];
		this.ingressoSituacao = new IngressoSituacao(((Integer) objects[coluna++]));
	}

	public Ingresso(String[] strings) {
		try {
			this.id = Long.parseLong(strings[SistemaConstantes.ZERO]);
			this.portariaValidacao = new Portaria(Integer.parseInt(strings[SistemaConstantes.UM]));
			this.dataValidacao = DataUtil.converterStringParaCalendar(strings[SistemaConstantes.DOIS],
					EFormatoData.BRASILEIRO_DATA_HORA_SEGUNDOS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Ingresso(Long id) {
		this.id = id;
	}

	public void anularId() {
		this.id = null;
	}

	public Long getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public IngressoSituacao getIngressoSituacao() {
		return ingressoSituacao;
	}

	public void setIngressoSituacao(IngressoSituacao ingressoSituacao) {
		this.ingressoSituacao = ingressoSituacao;
	}

	public Calendar getDataValidacao() {
		return dataValidacao;
	}

	public void setDataValidacao(Calendar dataValidacao) {
		this.dataValidacao = dataValidacao;
	}

	public Portaria getPortariaValidacao() {
		return portariaValidacao;
	}

	public void setPortariaValidacao(Portaria portariaValidacao) {
		this.portariaValidacao = portariaValidacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getCodigoPromocao() {
		return codigoPromocao;
	}

	public void setCodigoPromocao(String codigoPromocao) {
		this.codigoPromocao = codigoPromocao;
	}

	public GuicheLote getGuicheLote() {
		return guicheLote;
	}

	public void setGuicheLote(GuicheLote guicheLote) {
		this.guicheLote = guicheLote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public String getObservacaoPromocao() {
		return observacaoPromocao;
	}

	public void setObservacaoPromocao(String observacaoPromocao) {
		this.observacaoPromocao = observacaoPromocao;
	}

	public Lote getLote() {
		return lote;
	}

	public String getIngressoTipoDescricao() {
		return ingressoTipoDescricao;
	}

	public void setIngressoTipoDescricao(String ingressoTipoDescricao) {
		this.ingressoTipoDescricao = ingressoTipoDescricao;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setTaxa(String taxa) {
		this.taxa = taxa;
	}

	public void setTotal(String total) {
		this.total = total;
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
		Ingresso other = (Ingresso) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	public EBoolean getSincronizar() {
		return sincronizar;
	}
	
	public void setSincronizar(EBoolean sincronizar) {
		this.sincronizar = sincronizar;
	}

	@Override
	public String getSqlSelect() {
		return "SELECT distinct(i) FROM Ingresso i ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct i.id) FROM Ingresso i ";
	}

	@Override
	public String toString() {
		return "Ingresso [id=" + id + ", codigo=" + codigo + ", observacao=" + observacao + ", codigoPromocao="
				+ codigoPromocao + ", observacaoPromocao=" + observacaoPromocao + ", idLote=" + idLote + ", valor="
				+ valor + ", taxa=" + taxa + ", total=" + total + ", lote=" + lote + ", ingressoTipoDescricao="
				+ ingressoTipoDescricao + "]";
	}

	@Override
	public String getObjetoRetorno() {
		return "i";
	}

	@Override
	public String getJoin() {
		return "JOIN FETCH i.guicheLote glt " + "JOIN FETCH glt.lote lt " + "JOIN FETCH glt.guiche g "
				+ "JOIN FETCH lt.ingressoTipo it " + "JOIN FETCH i.ingressoSituacao st " + "LEFT JOIN FETCH i.pedido p "
				+ "LEFT JOIN FETCH i.portariaValidacao pl ";
	}

	public boolean isValido() {
		return ingressoSituacao.getAtivo().equals(EBoolean.TRUE);
	}

	@Override
	public int compareTo(Ingresso o) {
		return this.guicheLote.getGuiche().getDescricao().compareTo(o.getGuicheLote().getGuiche().getDescricao());
	}
}