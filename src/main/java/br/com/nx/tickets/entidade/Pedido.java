
package br.com.nx.tickets.entidade;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dto.ExtratoPedidoDTO;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EFormatoData;
import br.com.nx.tickets.entidade.util.EPagamentoTipo;
import br.com.nx.tickets.rest.DateFormatterAdapter;
import br.com.nx.tickets.util.SistemaConstantes;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "pedido", schema = "public")
public class Pedido implements Paginavel {

	@Id
	@Column(name = "id", precision = SistemaConstantes.VINTE, scale = SistemaConstantes.ZERO)
	private BigInteger id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_cadastro", nullable = false)
	@XmlElement(name = "data_cadastro")
	private Calendar dataCadastro;

	@NotNull
	@JsonInclude(Include.NON_NULL)
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "pagamento_tipo")
	@Column(name = "pagamento_tipo", columnDefinition = SistemaConstantes.E_PAGAMENTO_DEFAULT_DINHEIRO)
	private EPagamentoTipo pagamentoTipo;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "portaria")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_portaria", foreignKey = @ForeignKey(name = "fk_portaria"))
	private Portaria portaria;
	
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
	@JoinColumn(name = "_evento", foreignKey = @ForeignKey(name = "fk_evento"), nullable = false, updatable = false)
	private Evento evento;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingressos")
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<Ingresso> ingressos;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "responsavel", length = SistemaConstantes.DESCRICAO)
	private String responsavel;
	
	@XmlElement(name = "cortesia")
	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_cortesia", foreignKey = @ForeignKey(name = "fk_cortesia"))
	private Cortesia cortesia;

	@Transient
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingressosOffline")
	private List<Ingresso> ingressosOffline;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "extrato_pedido")
	private ExtratoPedidoDTO extratoPedidoDTO;
	
	public Pedido(BigInteger id) {
		this.id = id;
		this.dataCadastro = Calendar.getInstance();
	}
	
	public Pedido(String[] strings) {
		try {
			this.id	= new BigInteger(strings[SistemaConstantes.ZERO]);
			this.dataCadastro = DataUtil.converterStringParaCalendar(
					strings[SistemaConstantes.UM], EFormatoData.BRASILEIRO_DATA_HORA);
			this.ingressos = new ArrayList<>();
			this.ingressos.add(new Ingresso(Long.parseLong(strings[SistemaConstantes.DOIS])));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pedido() {
	}
	
	public BigInteger getId() {
		return id;
	}

	public void anularId() {
		this.id = null;
		this.dataCadastro = null;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public List<Ingresso> getIngressos() {
		return ingressos;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Guiche getGuiche() {
		return guiche;
	}

	public void setGuiche(Guiche guiche) {
		this.guiche = guiche;
	}
	
	@PrePersist
	protected void setDataCadastro() {
		if (this.dataCadastro == null) {
			this.dataCadastro = Calendar.getInstance();
		}
		if (this.id == null || this.id.equals(0)) {
			this.id = new BigInteger(this.guiche.getId() + "" + this.dataCadastro.getTimeInMillis());
		}
	}

	public void setIngressos(List<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}

	public EPagamentoTipo getPagamentoTipo() {
		return pagamentoTipo;
	}

	public void setPagamentoTipo(EPagamentoTipo pagamentoTipo) {
		this.pagamentoTipo = pagamentoTipo;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public ExtratoPedidoDTO getExtratoPedidoDTO() {
		return extratoPedidoDTO;
	}
	
	public void setExtratoPedidoDTO(ExtratoPedidoDTO extratoPedidoDTO) {
		this.extratoPedidoDTO = extratoPedidoDTO;
	}
	
	public Cortesia getCortesia() {
		return cortesia;
	}
	
	public void setCortesia(Cortesia cortesia) {
		this.cortesia = cortesia;
	}
	
	public Portaria getPortaria() {
		return portaria;
	}
	
	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
	}
	
	@Override
	public String getSqlSelect() {
		return "SELECT distinct(pd) FROM Pedido pd ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct pd.id) FROM Pedido pd ";
	}

	@Override
	public String getObjetoRetorno() {
		return "pd";
	}

	@Override
	public String getJoin() {
		return "JOIN FETCH pd.evento e "
			 + "JOIN FETCH pd.guiche g " 
			 + "JOIN FETCH g.bilheteria b " 
			 + "JOIN FETCH b.pontoVenda pv ";
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", pagamentoTipo=" + pagamentoTipo + ", guiche=" + guiche + ", portaria=" + portaria
				+ ", evento=" + evento + ", ingressos=" + ingressos + ", responsavel=" + responsavel
				+ ", ingressosOffline=" + ingressosOffline + ", extratoPedidoDTO=" + extratoPedidoDTO + "]";
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
		Pedido other = (Pedido) obj;
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