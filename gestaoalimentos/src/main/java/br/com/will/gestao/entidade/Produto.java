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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.Descritivel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "produto", schema = "gestao", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "index", "_produto_tipo", "_empresa" }) })
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Produto implements SituacaoAlteravel, Descritivel, Paginavel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "produtoSeq", sequenceName = "produto_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "produtoSeq")
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

	@ManyToOne
	@JoinColumn(name = "_produto_pai")
	private Produto produtoPai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto_tipo", foreignKey = @ForeignKey(name = "fk_produto_tipo"))
	private ProdutoTipo produtoTipo;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@Column(name = "valor", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal valor;
	
	@NotNull
	@JoinColumn(name = "_empresa", foreignKey = @ForeignKey(name = "fk_empresa"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	
	@XmlTransient
	@Transient
	private Tamanho tamanhoSelecionado;
	
	public Produto() {
	}
	
	public Produto(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Produto(Integer id) {
		this.id = id;
	}

	public Produto(String descricao) {
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

	public Produto getProdutoPai() {
		return produtoPai;
	}

	public void setProdutoPai(Produto produtoPai) {
		this.produtoPai = produtoPai;
	}

	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}

	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}
	
	public BigDecimal getValor() {
		if (valor == null) {
			return new BigDecimal(0);
		}
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
//	public Tamanho getTamanho() {
//		return tamanho;
//	}
	
//	public void setTamanho(Tamanho tamanho) {
//		this.tamanho = tamanho;
//	}
	
	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}
	
	@Override
	public ESituacao getSituacao() {
		return situacao;
	}
	
	public Tamanho getTamanhoSelecionado() {
		return tamanhoSelecionado;
	}
	
	public void setTamanhoSelecionado(Tamanho tamanhoSelecionado) {
		this.tamanhoSelecionado = tamanhoSelecionado;
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
		Produto other = (Produto) obj;
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
		return "Produto [id=" + id + ", descricao=" + descricao + "]";
	}

	@Override
	@XmlTransient
	public String getSqlSelect() {
		return "SELECT distinct(p) FROM Produto p ";
	}

	@Override
	@XmlTransient
	public String getSqlCount() {
		return " SELECT count(distinct p) FROM Produto p ";
	}

	@Override
	@XmlTransient
	public String getObjetoRetorno() {
		return " p ";
	}

	@Override
	@XmlTransient
	public String getJoin() {
		return " JOIN FETCH p.produtoTipo pt "
				+ "LEFT JOIN p.produtoPai pp ";
	}
	
	public static final Comparator<Produto> COMPARAR_POR_DESCRICAO = new Comparator<Produto>() {

		@Override
		public int compare(Produto o1, Produto o2) {
			if (o1.getDescricao().compareTo(o2.getDescricao()) > 0) {
				return 1;
			} else if (o1.getDescricao().compareTo(o2.getDescricao()) < 0) {
				return -1;
			}
			return 0;
		}
	};
}