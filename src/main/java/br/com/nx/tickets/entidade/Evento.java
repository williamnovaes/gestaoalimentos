package br.com.nx.tickets.entidade;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.util.Descritivel;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.Identificavel;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.rest.DateFormatterAdapter;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "descricao", "descricao_impressao", 
		"classificacao_etaria", "data_evento", "data_abertura_portao", 
		"data_inicio_venda_ingresso", "data_fim_venda_ingresso",
		"observacao"})
@Table(name = "evento", schema = "public")
public class Evento implements Descritivel, Comparable<Evento>, Paginavel, Identificavel, Serializable, SituacaoAlteravel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "eventoSeq", sequenceName = "evento_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "eventoSeq")
	@Column(unique = true, nullable = false)
	@XmlElement(name = "id")
	private Integer id;

	@NotNull
	@XmlElement(name = "descricao")
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO, nullable = false)
	private String descricao;
	
	@NotNull
	@XmlElement(name = "descricao_selecao")
	@Column(name = "descricao_selecao", length = SistemaConstantes.DESCRICAO, nullable = false)
	private String descricaoSelecao;
	
	@NotNull
	@XmlElement(name = "descricao_impressao")
	@Column(name = "descricao_impressao", length = SistemaConstantes.DESCRICAO, nullable = false)
	private String descricaoImpressao;

	@NotNull
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "classificacao_etaria")
	@Column(name = "classificacao_etaria")
	private Integer classificacaoEtaria;
	
	@NotNull
	@XmlTransient
	@Column(name = "capacidade_maxima", nullable = false)
	private Integer capacidadeMaxima;
	
	@NotNull
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_evento_tipo", foreignKey = @ForeignKey(name = "fk_evento_tipo"), nullable = false)
	private EventoTipo eventoTipo;

	@NotNull
	@JsonInclude(Include.NON_NULL)
	@Temporal(TemporalType.TIMESTAMP)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_evento", nullable = false)
	@XmlElement(name = "data_evento")
	private Calendar dataEvento;
	
	@NotNull
	@JsonInclude(Include.NON_NULL)
	@Temporal(TemporalType.TIMESTAMP)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_abertura_portao", nullable = false)
	@XmlElement(name = "data_abertura_portao")
	private Calendar dataAberturaPortao;
	
	@NotNull
	@JsonInclude(Include.NON_NULL)
	@Temporal(TemporalType.TIMESTAMP)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_inicio_venda_ingresso", nullable = false)
	@XmlElement(name = "data_inicio_venda_ingresso")
	private Calendar dataInicioVendaIngresso;
	
	@NotNull
	@JsonInclude(Include.NON_NULL)
	@Temporal(TemporalType.TIMESTAMP)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_fim_venda_ingresso", nullable = false)
	@XmlElement(name = "data_fim_venda_ingresso")
	private Calendar dataFimVendaIngresso;

	@NotNull
	@XmlTransient
	@Temporal(TemporalType.TIMESTAMP)
	@JsonInclude(Include.NON_NULL)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_cadastro", nullable = false)
	private Calendar dataCadastro;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "observacao")
	private String observacao = "ITAU SEGURADORA: 00.82.005970052-2";
	
	@XmlElement(name = "local")
	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_local", foreignKey = @ForeignKey(name = "fk_local"))
	private Local local;
	
	@XmlElement(name = "cliente")
	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_cliente", foreignKey = @ForeignKey(name = "fk_cliente"))
	private Cliente cliente;
	
	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@XmlTransient
	@OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
	private List<Pedido> pedidos;
	
	@XmlTransient
	@OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
	private List<Promocao> promocoes;
	
	@XmlTransient
	@NotAudited
	@OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
	private List<EventoPontoVenda> eventosPontosVendas;
	
	@XmlTransient
	@NotAudited
	@OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
	private List<EventoAtracao> eventosAtracoes;
	
	@XmlTransient
	@NotAudited
	@OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
	private List<EventoUsuarioRetirada> eventosUsuariosRetirada;

	public Evento(Integer id) {
		this.id = id;
	}

	public Evento() {
	}

	public Evento(String descricao) {
		this.descricao = descricao;
	}

	public Evento(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricaoImpressao() {
		return descricaoImpressao;
	}
	
	public void setDescricaoImpressao(String descricaoImpressao) {
		this.descricaoImpressao = descricaoImpressao;
	}
 
	public Integer getClassificacaoEtaria() {
		return classificacaoEtaria;
	}

	public void setClassificacaoEtaria(Integer classificacaoEtaria) {
		this.classificacaoEtaria = classificacaoEtaria;
	}

	public Integer getCapacidadeMaxima() {
		return capacidadeMaxima;
	}

	public void setCapacidadeMaxima(Integer capacidadeMaxima) {
		this.capacidadeMaxima = capacidadeMaxima;
	}

	public EventoTipo getEventoTipo() {
		return eventoTipo;
	}

	public void setEventoTipo(EventoTipo eventoTipo) {
		this.eventoTipo = eventoTipo;
	}

	public Calendar getDataInicioVendaIngresso() {
		return dataInicioVendaIngresso;
	}

	public void setDataInicioVendaIngresso(Calendar dataInicioVendaIngresso) {
		this.dataInicioVendaIngresso = dataInicioVendaIngresso;
	}
	
	public Calendar getDataFimVendaIngresso() {
		return dataFimVendaIngresso;
	}
	
	public void setDataFimVendaIngresso(Calendar dataFimVendaIngresso) {
		this.dataFimVendaIngresso = dataFimVendaIngresso;
	}
	
	public Calendar getDataAberturaPortao() {
		return dataAberturaPortao;
	}

	public void setDataAberturaPortao(Calendar dataAberturaPortao) {
		this.dataAberturaPortao = dataAberturaPortao;
	}

	public Calendar getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Calendar dataEvento) {
		this.dataEvento = dataEvento;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	@PrePersist
	protected void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}
	
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Override
	public void alterarSituacao() {
		situacao = Util.alteraSituacao(situacao);
	}

	@Override
	public ESituacao getSituacao() {
		return situacao;
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
		Evento other = (Evento) obj;
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
		return "Evento [id=" + id + ", descricao=" + descricao + ", descricaoImpressao=" + descricaoImpressao
				+ ", classificacaoEtaria=" + classificacaoEtaria + ", capacidadeMaxima=" + capacidadeMaxima + "]";
	}

	@Override
	public int compareTo(Evento n) {
		return this.descricao.compareTo(n.descricao);
	}

	@Override
	public String getSqlSelect() {
		return "SELECT distinct(ev) FROM Evento ev ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct ev.id) FROM Evento ev ";
	}

	@Override
	public String getObjetoRetorno() {
		return "ev";
	}

	@Override
	public String getJoin() {
		return "JOIN FETCH ev.cliente cl "
			 + "JOIN FETCH ev.eventoTipo et "
			 + "JOIN FETCH ev.local lc ";
	}
}