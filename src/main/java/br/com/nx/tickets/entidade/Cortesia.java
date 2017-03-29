package br.com.nx.tickets.entidade;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.util.Descritivel;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.Identificavel;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "descricao", "descricao_impressao" })
@Table(name = "cortesia", schema = "public")
public class Cortesia implements SituacaoAlteravel, Descritivel, Comparable<Cortesia>, Paginavel, Identificavel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "cortesiaSeq", sequenceName = "cortesia_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "cortesiaSeq")
	@Column(unique = true, nullable = false)
	@XmlElement(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricao;

	@NotNull
	@XmlElement(name = "descricao_impressao")
	@Column(name = "descricao_impressao", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricaoImpressao;
	
	@XmlTransient
//	@XmlElement(name = "categoria")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_categoria", foreignKey = @ForeignKey(name = "fk_categoria"))
	private  Categoria categoria;

	@NotNull
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ingresso_tipo", foreignKey = @ForeignKey(name = "fk_ingresso_tipo"))
	private IngressoTipo ingressoTipo;

	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	public Cortesia(Integer id) {
		this.id = id;
	}

	public Cortesia() {
	}

	public Cortesia(String descricao) {
		this.descricao = descricao;
	}

	public Cortesia(Integer id, String descricao) {
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
	
	public IngressoTipo getIngressoTipo() {
		return ingressoTipo;
	}
	
	public void setIngressoTipo(IngressoTipo ingressoTipo) {
		this.ingressoTipo = ingressoTipo;
	}

	public String getDescricaoImpressao() {
		return descricaoImpressao;
	}

	public void setDescricaoImpressao(String descricaoImpressao) {
		this.descricaoImpressao = descricaoImpressao.toUpperCase();
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public ESituacao getSituacao() {
		return this.situacao;
	}

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
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
		Cortesia other = (Cortesia) obj;
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
		return "Cortesia [descricao=" + descricao + "descricaoImpressao=" + descricaoImpressao + "]";
	}

	@Override
	public int compareTo(Cortesia n) {
		return this.descricao.compareTo(n.descricao);
	}

	@Override
	public String getSqlSelect() {
		return "SELECT distinct(ct) FROM Cortesia ct ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct ct.id) FROM Cortesia ct ";
	}

	@Override
	public String getObjetoRetorno() {
		return " ct ";
	}

	@Override
	public String getJoin() {
		return " JOIN FETCH ct.ingressoTipo it "
			 + " JOIN FETCH ct.categoria ca ";
	}
}