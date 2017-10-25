package br.com.will.gestao.entidade;

import java.math.BigDecimal;
import java.util.Comparator;

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
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.Descritivel;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "ingrediente", schema = "gestao")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Ingrediente implements SituacaoAlteravel, Descritivel, Paginavel {
	
	@XmlTransient
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ingredienteSeq", sequenceName = "ingrediente_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ingredienteSeq")
	@Column(unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@Column(name = "nome", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String nome;

	@NotNull
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO)
	private String descricao;
	
	@NotNull
	@Column(name = "sequencia", nullable = false)
	private Integer sequencia;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao", columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "adicional", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean adicional = EBoolean.FALSE;
	
	@Column(name = "valor_adicional", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal valorAdicional;
	
	@NotNull
	@JoinColumn(name = "_empresa", foreignKey = @ForeignKey(name = "fk_empresa"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	
	public Ingrediente() {
	}
	
	public Ingrediente(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Ingrediente(Integer id) {
		this.id = id;
	}

	public Ingrediente(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}
	
	public EBoolean getAdicional() {
		return adicional;
	}
	
	public void setAdicional(EBoolean adicional) {
		this.adicional = adicional;
	}

	public BigDecimal getValorAdicional() {
		if (valorAdicional == null) {
			return new BigDecimal(0);
		}
		return valorAdicional;
	}

	public void setValorAdicional(BigDecimal valor) {
		this.valorAdicional = valor;
	}
	
	public Integer getSequencia() {
		return sequencia;
	}
	
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}
	
	@Override
	public ESituacao getSituacao() {
		return this.situacao;
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
		Ingrediente other = (Ingrediente) obj;
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
		return "Ingrediente [id=" + id + ", descricao=" + descricao + "]";
	}

	@Override
	@XmlTransient
	public String getSqlSelect() {
		return "SELECT distinct(i) FROM Ingrediente i ";
	}

	@Override
	@XmlTransient
	public String getSqlCount() {
		return "SELECT COUNT(DISTINCT i) FROM Ingrediente i ";
	}

	@Override
	@XmlTransient
	public String getObjetoRetorno() {
		return " i ";
	}

	@Override
	@XmlTransient
	public String getJoin() {
		return " JOIN FETCH i.empresa em ";
	}
	
	public static final Comparator<Ingrediente> COMPARAR_POR_DESCRICAO = new Comparator<Ingrediente>() {

		@Override
		public int compare(Ingrediente o1, Ingrediente o2) {
			if (o1.getDescricao().compareTo(o2.getDescricao()) > 0) {
				return 1;
			} else if (o1.getDescricao().compareTo(o2.getDescricao()) < 0) {
				return -1;
			}
			return 0;
		}
	};
}