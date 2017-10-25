package br.com.will.gestao.entidade;

import java.math.BigDecimal;
import java.util.Comparator;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "sabor", schema = "gestao")
public class Sabor implements SituacaoAlteravel, Paginavel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "saborSeq", sequenceName = "sabor_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "saborSeq")
	@Column(unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@Column(name = "nome", length = SistemaConstantes.DESCRICAO, nullable = false)
	private String nome;
	
	@NotNull
	@Column(name = "index", nullable = false)
	private Integer index;

	@NotNull
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO)
	private String descricao;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto", foreignKey = @ForeignKey(name = "fk_produto"), nullable = false)
	private Produto produto;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@Column(name = "preco", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal preco;
	
	@NotNull
	@JoinColumn(name = "_empresa", foreignKey = @ForeignKey(name = "fk_empresa"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	
	@OneToMany(mappedBy = "sabor", fetch = FetchType.LAZY)
	private List<SaborIngrediente> ingredientes;
	
	@XmlTransient
	@Transient
	private boolean marcado;
	
	public Sabor() {
	}
	
	public Sabor(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Sabor(Integer id) {
		this.id = id;
	}

	public Sabor(String descricao) {
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
	
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getPreco() {
		if (preco == null) {
			return new BigDecimal(0);
		}
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
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
		return situacao;
	}
	
	public List<SaborIngrediente> getIngredientes() {
		return ingredientes;
	}
	
	public void setIngredientes(List<SaborIngrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
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
		Sabor other = (Sabor) obj;
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
		return "Sabor [id=" + id + ", descricao=" + descricao + "]";
	}

	@Override
	@XmlTransient
	public String getSqlSelect() {
		return "SELECT distinct(s) FROM Sabor s ";
	}

	@Override
	@XmlTransient
	public String getSqlCount() {
		return " SELECT count(distinct s) FROM Sabor s ";
	}

	@Override
	@XmlTransient
	public String getObjetoRetorno() {
		return " s ";
	}

	@Override
	@XmlTransient
	public String getJoin() {
		return " JOIN FETCH s.produto p ";
	}
	
	public static final Comparator<Sabor> COMPARAR_POR_DESCRICAO = new Comparator<Sabor>() {

		@Override
		public int compare(Sabor o1, Sabor o2) {
			if (o1.getDescricao().compareTo(o2.getDescricao()) > 0) {
				return 1;
			} else if (o1.getDescricao().compareTo(o2.getDescricao()) < 0) {
				return -1;
			}
			return 0;
		}
	};
}