package br.com.will.gestao.entidade;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "cidade", schema = "gestao")
public class Cidade implements SituacaoAlteravel, Paginavel, Comparable<Cidade> {
	
	@XmlTransient
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "cidadeSeq", sequenceName = "cidade_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "cidadeSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@Column(name = "nome", length = SistemaConstantes.CENTO_CINQUENTA, nullable = false)
	private String nome;

	@Column(name = "codigo", length = SistemaConstantes.DEZ)
	private String codigo;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_estado", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_estado"))
	private Estado estado;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@Transient
	@XmlTransient
	private String distrito;
	
	public Cidade() {
	}
	
	public Cidade(Integer id, String nome, String uf) {
		this.id = id;
		this.nome = nome;
		this.estado = new Estado(uf);
	}
	
	public Cidade(Integer id, String nome, String uf, String nomeEstado) {
		this.id = id;
		this.nome = nome;
		this.estado = new Estado(uf, nomeEstado);
	}
	
	public Cidade(Integer id, String nome, String uf, String nomeEstado, String distrito) {
		this.id = id;
		this.nome = nome;
		this.estado = new Estado(uf, nomeEstado);
		this.distrito = distrito;
	}
	
	public Cidade(String nome, String uf) {
		this.nome = nome;
		this.estado = new Estado(uf);
	}
	
	public Cidade(Integer id) {
		this.id = id;
	}
	
	public Cidade(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getDistrito() {
		return distrito;
	}
	
	@Override
	public ESituacao getSituacao() {
		return situacao;
	}

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(situacao);
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String getSqlSelect() {
		return  "SELECT distinct(ci) FROM Cidade ci ";
	}
	
	public String getSqlCount() {
		return "SELECT count(distinct ci.id) FROM Cidade ci ";
	}
	
	public String getObjetoRetorno() {
		return "ci";
	}

	public String toPrevisaoTempo() {
		return nome + "," + estado.getUf();
	}
	
	@Override
	public String toString() {
		return "Cidade [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public String getJoin() {
		return  " JOIN FETCH ci.estado e ";
	}
	
	@Override
	public int compareTo(Cidade c) {
		return this.nome.compareTo(c.nome);
	}
	
	public static final Comparator<Cidade> COMPARAR_POR_NOME = new Comparator<Cidade>() {

		@Override
		public int compare(Cidade o1, Cidade o2) {
			if (o1.getNome().compareTo(o2.getNome()) > 0) {
				return 1;
			} else if (o1.getNome().compareTo(o2.getNome()) < 0) {
				return -1;
			}
			return 0;
		}
	};
}