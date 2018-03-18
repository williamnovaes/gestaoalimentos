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

@Entity
@Table(name = "pedido_produto_adicional", schema = "gestao")
public class PedidoProdutoAdicional
		implements Serializable, Paginavel, Exportavel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PedidoProdutoAdicionalPK id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_pedido_produto", updatable = false, insertable = false, foreignKey = @ForeignKey(name = "fk_pedido_produto"))
	private PedidoProduto pedidoProduto;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ingrediente",  updatable = false, insertable = false, foreignKey = @ForeignKey(name = "fk_ingrediente"))
	private Ingrediente ingrediente;
	
	@Column(name = "quantidade")
	private Integer quantidade;
	
	public PedidoProdutoAdicional() {
	}

	public PedidoProdutoAdicional(PedidoProduto pedidoProduto, Ingrediente ingrediente) {
		this.id = new PedidoProdutoAdicionalPK(pedidoProduto.getId(), ingrediente.getId());
		this.pedidoProduto = pedidoProduto;
		this.ingrediente = ingrediente;
	}

	public PedidoProdutoAdicionalPK getId() {
		return this.id;
	}

	public PedidoProduto getPedidoProduto() {
		return pedidoProduto;
	}
	
	public void setPedidoProduto(PedidoProduto pedidoProduto) {
		this.pedidoProduto = pedidoProduto;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	
	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
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
		PedidoProdutoAdicional other = (PedidoProdutoAdicional) obj;
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
		return "PedidoProdutoAdicional [id=" + id + "]";
	}

	@Override
	public String getSqlSelect() {
		return " SELECT ppa FROM PedidoProdutoAdicional ppa ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(ppa.id) FROM PedidoProdutoAdicional ppa ";
	}

	@Override
	public String getObjetoRetorno() {
		return " ppa ";
	}

	@Override
	public String getJoin() {
		StringBuilder sb = new StringBuilder();
		sb.append(" JOIN FETCH ppa.pedidoProduto pp ");
		sb.append(" JOIN FETCH pp.pedido pe ");
		sb.append(" JOIN FETCH pp.produto pr ");
		sb.append(" JOIN FETCH ppa.ingrediente ig ");
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