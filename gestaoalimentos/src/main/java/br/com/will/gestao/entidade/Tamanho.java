package br.com.will.gestao.entidade;

import java.math.BigDecimal;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "tamanho", schema = "gestao", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "descricao", "_produto" }) })
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Tamanho implements Comparable<Tamanho>, SituacaoAlteravel, Paginavel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "tamanhoSeq", sequenceName = "tamanho_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tamanhoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@Column(name = "tamanho", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String tamanho;

	@NotNull
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricao;
	
	@NotNull
	@Column(name = "sequencia", nullable = false)
	private Integer sequencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_tamanho_tipo", foreignKey = @ForeignKey(name = "fk_tamanho_tipo"))
	private TamanhoTipo tamanhoTipo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto", foreignKey = @ForeignKey(name = "fk_produto"))
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto_tipo", foreignKey = @ForeignKey(name = "fk_produto_tipo"))
	private ProdutoTipo produtoTipo;
	
	@Column(name = "preco", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal preco = new BigDecimal(0);
	
	@NotNull
	@Column(name = "limite_sabores", nullable = false)
	private Integer limiteSabores = 1;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	public Tamanho(Integer id) {
		this.id = id;
	}

	public Tamanho() {
	}

	public Tamanho(String descricao) {
		this.descricao = descricao;
	}

	public Tamanho(Integer id, String descricao) {
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
	
	public String getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	
	public TamanhoTipo getTamanhoTipo() {
		return tamanhoTipo;
	}
	
	public void setTamanhoTipo(TamanhoTipo tamanhoTipo) {
		this.tamanhoTipo = tamanhoTipo;
	}
	
	public BigDecimal getPreco() {
		return this.preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public Integer getLimiteSabores() {
		return limiteSabores;
	}
	
	public void setLimiteSabores(Integer limiteSabores) {
		this.limiteSabores = limiteSabores;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Integer getSequencia() {
		return sequencia;
	}
	
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	
	@Override
	public ESituacao getSituacao() {
		return situacao;
	}
	
	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}
	
	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}
	
	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}
	
	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}
	
	@Override
	public String toString() {
		return "Tamanho [id=" + id + "]";
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
		Tamanho other = (Tamanho) obj;
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
	@XmlTransient
	public String getSqlSelect() {
		return "SELECT distinct(t) FROM Tamanho t ";
	}

	@Override
	@XmlTransient
	public String getSqlCount() {
		return "SELECT COUNT(DISTINCT t.id) FROM Tamanho t ";
	}

	@Override
	@XmlTransient
	public String getObjetoRetorno() {
		return " t ";
	}

	@Override
	@XmlTransient
	public String getJoin() {
		return " LEFT JOIN FETCH t.produto p "
			 + " LEFT JOIN FETCH t.produtoTipo pt"
			 + " LEFT JOIN FETCH t.tamanhoTipo tt ";
	}

	@Override
	public int compareTo(Tamanho o) {
		return this.descricao.compareTo(o.getDescricao());
	}
}