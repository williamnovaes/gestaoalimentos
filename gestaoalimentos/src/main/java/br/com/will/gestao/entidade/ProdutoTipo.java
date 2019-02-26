package br.com.will.gestao.entidade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.Descritivel;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.EOrigemPreco;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "produto_tipo", schema = "gestao")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class ProdutoTipo implements SituacaoAlteravel, Descritivel, Paginavel {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "produtoTipoSeq", sequenceName = "produto_tipo_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "produtoTipoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@Column(length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricao;
	
	@Column(name = "preco", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal preco = new BigDecimal(0);
	
	@Column(name = "sequencia")
	private Integer sequencia;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "soma_preco_sabor", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean somaPrecoSabor = EBoolean.FALSE;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "origem_preco", columnDefinition = SistemaConstantes.E_ORIGEM_PRECO_DEFAULT_PRODUTO)
	private EOrigemPreco origemPreco  = EOrigemPreco.PRODUTO;
	
	@OneToMany(mappedBy = "produtoTipo", fetch = FetchType.LAZY)
	private List<Produto> produtos;
	
	@OneToMany(mappedBy = "produtoTipo", fetch = FetchType.LAZY)
	private List<Tamanho> tamanhos;
	
	public ProdutoTipo() {
	}
	
	public ProdutoTipo(Integer id) {
		this.id = id;
	}

	public ProdutoTipo(String descricao) {
		this.descricao = descricao;
	}
	
	public ProdutoTipo(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public ESituacao getSituacao() {
		return situacao;
	}
	
	public Integer getSequencia() {
		return this.sequencia;
	}
	
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	
	public EOrigemPreco getOrigemPreco() {
		return origemPreco;
	}
	
	public void setOrigemPreco(EOrigemPreco origemPreco) {
		this.origemPreco = origemPreco;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public EBoolean getSomaPrecoSabor() {
		return somaPrecoSabor;
	}
	
	public void setSomaPrecoSabor(EBoolean somaPrecoSabor) {
		this.somaPrecoSabor = somaPrecoSabor;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public List<Tamanho> getTamanhos() {
		if (tamanhos == null) {
			tamanhos = new ArrayList<>();
		}
		return tamanhos;
	}
	
	public void setTamanhos(List<Tamanho> tamanhos) {
		this.tamanhos = tamanhos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		ProdutoTipo other = (ProdutoTipo) obj;
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
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}

	@Override
	public String getSqlSelect() {
		StringBuilder str = new StringBuilder();
		str.append(" SELECT DISTINCT(pt) FROM ProdutoTipo pt ");
		return str.toString();
	}

	@Override
	public String getSqlCount() {
		StringBuilder str = new StringBuilder();
		str.append(" SELECT COUNT(DISTINCT pt.id) FROM ProdutoTipo pt ");
		return str.toString();
	}

	@Override
	public String getObjetoRetorno() {
		StringBuilder str = new StringBuilder();
		str.append(" pt ");
		return str.toString();
	}

	@Override
	public String getJoin() {
		StringBuilder str = new StringBuilder();
		str.append(" ");
		return str.toString();
	}
}