package br.com.will.gestao.entidade;

import java.math.BigDecimal;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_empresa", foreignKey = @ForeignKey(name = "fk_empresa"))
	private Empresa empresa;
	
	@Column(name = "preco", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal preco;
	
	@Column(name = "sequencia")
	private Integer sequencia;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean somaPrecoSabor = EBoolean.FALSE;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "origem_preco", columnDefinition = SistemaConstantes.E_ORIGEM_PRECO_DEFAULT_PRODUTO)
	private EOrigemPreco origemPreco  = EOrigemPreco.PRODUTO;
	
	@OneToMany(mappedBy = "produtoTipo", fetch = FetchType.LAZY)
	private List<Produto> produtos;
	
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
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoTipo other = (ProdutoTipo) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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
		str.append(" LEFT JOIN FETCH pt.empresa em ");
		str.append(" LEFT JOIN FETCH pt.tamanhos t ");
		return str.toString();
	}
}