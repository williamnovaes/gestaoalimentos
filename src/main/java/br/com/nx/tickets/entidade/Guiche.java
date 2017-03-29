package br.com.nx.tickets.entidade;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "descricao", "permitir_venda", "bilheteria" })
@Table(name = "guiche", schema = "public")
public class Guiche implements Serializable, SituacaoAlteravel, Paginavel, Comparable<Guiche> {
	private static final long serialVersionUID = 1L;

	@Id
	@XmlElement(name = "id")
	@SequenceGenerator(name = "guicheSeq", sequenceName = "guiche_id_seq", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "guicheSeq")
	private Integer id;

	@NotNull
	@XmlElement(name = "descricao")
	@Column(name = "descricao", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String descricao;
	
	@NotNull
	@XmlElement(name = "hora_limite_venda_ingresso_antes_evento")
	@Column(name = "hora_limite_venda_ingresso_antes_evento", nullable = false)
	private Integer horaLimiteVendaIngressoAntesEvento;
	
	@NotNull
	@XmlElement(name = "descricao_impressao")
	@Column(name = "descricao_impressao", length = SistemaConstantes.DESCRICAO, nullable = false)
	private String descricaoImpressao;

	@NotNull
	@XmlElement(name = "bilheteria")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_bilheteria", foreignKey = @ForeignKey(name = "fk_bilheteria"), nullable = false)
	private Bilheteria bilheteria;

	@XmlTransient
	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_usuario", foreignKey = @ForeignKey(name = "fk_usuario"), unique = true)
	private Usuario usuario;

	@NotAudited
	@XmlTransient
	@XmlElement(name = "retirada")
	@OneToMany(mappedBy = "guiche", fetch = FetchType.LAZY)
	private List<Retirada> retiradas;

	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	@NotNull
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "permitir_venda")
	@Column(name = "permitir_venda", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_TRUE, nullable = false)
	private EBoolean permitirVenda = EBoolean.TRUE;
	
	@NotNull
	@JsonInclude(Include.NON_NULL)
	@Column(name = "limite_ingresso_por_pedido")
	@XmlElement(name = "limite_ingresso_por_pedido")
	private Integer limiteIngressoPorPedido = SistemaConstantes.DEZ;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "imprimir_antes_confirmacao_pedido")
	@Column(name = "imprimir_antes_confirmacao_pedido", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE, nullable = false)
	private EBoolean imprimirAntesConfirmacaoPedido = EBoolean.TRUE;

	@NotNull
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "imprimir_cortesia")
	@Column(name = "imprimir_cortesia", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE, nullable = false)
	private EBoolean imprimirCortesia = EBoolean.FALSE;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "offline")
	@Column(name = "offline", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE, nullable = false)
	private EBoolean offline = EBoolean.FALSE;

    @Enumerated(EnumType.STRING)
    @XmlElement(name = "imprimir_recibo")
    @Column(name = "imprimir_recibo", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_TRUE)
    private EBoolean imprimirRecibo = EBoolean.TRUE;
    
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "picotar")
    @Column(name = "picotar", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_TRUE)
    private EBoolean picotar = EBoolean.TRUE;
    
	public Guiche() {

	}

	public Guiche(Integer id) {
		this.id = id;
	}

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}

	@Override
	public ESituacao getSituacao() {
		return situacao;
	}

	public Integer getId() {
		return id;
	}

	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Bilheteria getBilheteria() {
		return bilheteria;
	}

	public void setBilheteria(Bilheteria bilheteria) {
		this.bilheteria = bilheteria;
	}

	public EBoolean getPermitirVenda() {
		return permitirVenda;
	}

	public void setPermitirVenda(EBoolean permitirVenda) {
		this.permitirVenda = permitirVenda;
	}
	
	public EBoolean getImprimirAntesConfirmacaoPedido() {
		return imprimirAntesConfirmacaoPedido;
	}
	
	public void setImprimirAntesConfirmacaoPedido(EBoolean imprimirAntesConfirmacaoPedido) {
		this.imprimirAntesConfirmacaoPedido = imprimirAntesConfirmacaoPedido;
	}
	
	public EBoolean getImprimirCortesia() {
		return imprimirCortesia;
	}
	
	public void setImprimirCortesia(EBoolean imprimirCortesia) {
		this.imprimirCortesia = imprimirCortesia;
	}
	
	public EBoolean getOffline() {
		return offline;
	}
	
	public void setOffline(EBoolean offline) {
		this.offline = offline;
	}
	
	public Integer getLimiteIngressoPorPedido() {
		return limiteIngressoPorPedido;
	}
	
	public void setLimiteIngressoPorPedido(Integer limiteIngressoPorPedido) {
		this.limiteIngressoPorPedido = limiteIngressoPorPedido;
	}
	
	public String getDescricaoImpressao() {
		return descricaoImpressao;
	}
	
	public void setDescricaoImpressao(String descricaoImpressao) {
		this.descricaoImpressao = descricaoImpressao;
	}
	
	public Integer getHoraLimiteVendaIngressoAntesEvento() {
		return horaLimiteVendaIngressoAntesEvento;
	}
	
	public void setHoraLimiteVendaIngressoAntesEvento(Integer horaLimiteVendaIngressoAntesEvento) {
		this.horaLimiteVendaIngressoAntesEvento = horaLimiteVendaIngressoAntesEvento;
	}
	
	public EBoolean getImprimirRecibo() {
		return imprimirRecibo;
	}

	public void setImprimirRecibo(EBoolean imprimirRecibo) {
		this.imprimirRecibo = imprimirRecibo;
	}

	public EBoolean getPicotar() {
		return picotar;
	}

	public void setPicotar(EBoolean picotar) {
		this.picotar = picotar;
	}

	@Override
	public String toString() {
		return "Guiche [id=" + id + ", descricao=" + descricao + "]";
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
		Guiche other = (Guiche) obj;
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
	public String getSqlSelect() {
		return "SELECT distinct(g) FROM Guiche g ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct g.id) FROM Guiche g ";
	}

	@Override
	public String getObjetoRetorno() {
		return "g";
	}

	@Override
	public String getJoin() {
		return " LEFT JOIN FETCH g.usuario us " 
			 + " JOIN FETCH g.bilheteria b ";
	}

	@Override
	public int compareTo(Guiche o) {
		return this.descricao.compareTo(o.getDescricao());
	}
}