package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "produto_ingrediente", schema = "gestao")
public class ProdutoIngrediente
		implements Serializable, Paginavel, Exportavel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProdutoIngredientePK id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_produto"))
	private Produto produto;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ingrediente", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_pedido"))
	private Ingrediente ingrediente;
	
	@Column(name = "observacao", length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String observacao;
	
	@Column(name = "quantidade")
	private Integer quantidade;


	public ProdutoIngrediente() {
	}

	public ProdutoIngrediente(Produto produto, Ingrediente ingrediente) {
		this.id = new ProdutoIngredientePK(produto.getId(), ingrediente.getId());
		this.produto = produto;
		this.ingrediente = ingrediente;
	}

	public ProdutoIngredientePK getId() {
		return this.id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	
	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
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
		ProdutoIngrediente other = (ProdutoIngrediente) obj;
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
		return "PedidoProduto [id=" + id + "]";
	}

	@Override
	public String getSqlSelect() {
		return " ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(pp.id) FROM PedidoProduto pp ";
	}

	@Override
	public String getObjetoRetorno() {
		return " pp ";
	}

	@Override
	public String getJoin() {
		StringBuilder sb = new StringBuilder();
		sb.append(" JOIN pp.pedido pd ");
		sb.append(" JOIN pp.produto pr ");
		return sb.toString();
	}

	public static String cabecalhoExportacao() {
		return "";
	}

	@Override
	public String exportar() {
		StringBuilder retorno = new StringBuilder();
		return retorno.toString();
	}
}