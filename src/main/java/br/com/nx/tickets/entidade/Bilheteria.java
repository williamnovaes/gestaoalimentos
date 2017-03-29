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

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "descricao", "portaria" })
@Table(name = "bilheteria", schema = "public")
public class Bilheteria implements Serializable, SituacaoAlteravel, Paginavel {
	private static final long serialVersionUID = 1L;

	@Id
	@XmlElement(name = "id")
	@SequenceGenerator(name = "bilheteriaSeq", sequenceName = "bilheteria_id_seq", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "bilheteriaSeq")
	private Integer id;

	@NotNull
	@XmlElement(name = "descricao")
	@Column(name = "descricao", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String descricao;

	@NotNull
	@XmlElement(name = "portaria")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_portaria", foreignKey = @ForeignKey(name = "fk_portaria"), nullable = false)
	private Portaria portaria;
	
	@NotNull
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ponto_venda", foreignKey = @ForeignKey(name = "fk_ponto_venda"), nullable = false)
	private PontoVenda pontoVenda;
	
	@NotAudited
	@XmlTransient
	@OneToMany(mappedBy = "bilheteria", fetch = FetchType.LAZY)
	private List<Guiche> guiches;
	
	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	public Bilheteria() {

	}
	
	public Bilheteria(Integer id) {
		this.id = id;
	}
	
	public Bilheteria(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
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
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return "Bilheteria [id=" + id + ", descricao=" + descricao + "]";
	}
	
	public PontoVenda getPontoVenda() {
		return pontoVenda;
	}
	
	public void setPontoVenda(PontoVenda pontoVenda) {
		this.pontoVenda = pontoVenda;
	}
	
	public Portaria getPortaria() {
		return portaria;
	}
	
	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
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
		Bilheteria other = (Bilheteria) obj;
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
		return " SELECT distinct(bi) FROM Bilheteria bi ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(distinct bi.id) FROM Bilheteria bi ";
	}

	@Override
	public String getObjetoRetorno() {
		return " bi ";
	}

	@Override
	public String getJoin() {
		return " JOIN FETCH bi.pontoVenda pv "
			 + " JOIN FETCH bi.portaria p ";
	}
}