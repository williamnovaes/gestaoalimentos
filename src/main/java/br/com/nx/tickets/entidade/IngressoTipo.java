package br.com.nx.tickets.entidade;

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
import br.com.nx.tickets.entidade.util.Descritivel;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.Identificavel;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "descricao", "descricao_impressao", "imprimir_comprovante_retirada", "cortesia" })
@Table(name = "ingresso_tipo", schema = "public")
public class IngressoTipo
		implements SituacaoAlteravel, Descritivel, Comparable<IngressoTipo>, Paginavel, Identificavel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ingressoTipoSeq", sequenceName = "ingresso_tipo_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ingressoTipoSeq")
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

	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	@NotNull
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "imprimir_comprovante_retirada")
	@Column(name = "imprimir_comprovante_retirada", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean imprimirComprovanteRetirada = EBoolean.FALSE;

	@NotNull
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "cortesia")
	@Column(name = "cortesia", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean cortesia = EBoolean.FALSE;
	
	@XmlTransient
//	@XmlElement(name = "categoria")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_categoria", foreignKey = @ForeignKey(name = "fk_categoria"))
	private Categoria categoria;

	@NotAudited
	@XmlTransient
	@OneToMany(mappedBy = "ingressoTipo", fetch = FetchType.LAZY)
	private List<ClienteIngressoTipo> clientesIngressosTipos;

	public IngressoTipo(Integer id) {
		this.id = id;
	}

	public IngressoTipo() {
	}

	public IngressoTipo(String descricao) {
		this.descricao = descricao;
	}

	public IngressoTipo(Integer id, String descricao) {
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
		this.descricaoImpressao = descricaoImpressao.toUpperCase();
	}

	@Override
	public ESituacao getSituacao() {
		return this.situacao;
	}

	public EBoolean getImprimirComprovanteRetirada() {
		return imprimirComprovanteRetirada;
	}

	public void setImprimirComprovanteRetirada(EBoolean imprimirComprovanteRetirada) {
		this.imprimirComprovanteRetirada = imprimirComprovanteRetirada;
	}

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}

	public EBoolean getCortesia() {
		return cortesia;
	}

	public void setCortesia(EBoolean cortesia) {
		this.cortesia = cortesia;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		IngressoTipo other = (IngressoTipo) obj;
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
		return "IngressoTipo [descricao=" + descricao + "]";
	}

	@Override
	public int compareTo(IngressoTipo n) {
		return this.descricao.compareTo(n.descricao);
	}

	@Override
	public String getSqlSelect() {
		return "SELECT distinct(it) FROM IngressoTipo it ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct it.id) FROM IngressoTipo it ";
	}

	@Override
	public String getObjetoRetorno() {
		return "it";
	}

	@Override
	public String getJoin() {
		return "";
	}
}