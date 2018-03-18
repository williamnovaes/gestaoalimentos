package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "produto_tipo_ingrediente", schema = "gestao")
public class ProdutoTipoIngrediente implements Serializable, Paginavel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProdutoTipoIngredientePK id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto_tipo", updatable = false, insertable = false, foreignKey = @ForeignKey(name = "fk_produto_tipo"))
	private ProdutoTipo produtoTipo;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ingrediente",  updatable = false, insertable = false, foreignKey = @ForeignKey(name = "fk_ingrediente"))
	private Ingrediente ingrediente;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	public ProdutoTipoIngrediente() {
	}

	public ProdutoTipoIngrediente(ProdutoTipo produtoTipo, Ingrediente ingrediente) {
		this.id = new ProdutoTipoIngredientePK(produtoTipo.getId(), ingrediente.getId());
		this.produtoTipo = produtoTipo;
		this.ingrediente = ingrediente;
	}
	
	public ProdutoTipoIngredientePK getId() {
		return id;
	}
	
	public void setId(ProdutoTipoIngredientePK id) {
		this.id = id;
	}
	
	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}
	
	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}
	
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	
	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	public ESituacao getSituacao() {
		return situacao;
	}
	
	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
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
		ProdutoTipoIngrediente other = (ProdutoTipoIngrediente) obj;
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
		return "ProdutoTipoIngrediente [id=" + id + "]";
	}

	@Override
	public String getSqlSelect() {
		return " SELECT pti FROM ProdutoTipoIngrediente pti ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(pti.id) FROM ProdutoTipoIngrediente pti ";
	}

	@Override
	public String getObjetoRetorno() {
		return " pti ";
	}

	@Override
	public String getJoin() {
		StringBuilder sb = new StringBuilder();
		sb.append(" JOIN FETCH pti.produtoTipo pt ");
		sb.append(" JOIN FETCH pti.ingrediente i ");
		return sb.toString();
	}
}