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
@Table(name = "pedido_produto_sabor", schema = "gestao")
public class PedidoProdutoSabor
		implements Serializable, Paginavel, Exportavel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PedidoProdutoSaborPK id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_pedido_produto", updatable = false, insertable = false, foreignKey = @ForeignKey(name = "fk_pedido_produto"))
	private PedidoProduto pedidoProduto;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_sabor",  updatable = false, insertable = false, foreignKey = @ForeignKey(name = "fk_sabor"))
	private Sabor sabor;
	
	public PedidoProdutoSabor() {
	}

	public PedidoProdutoSabor(PedidoProduto pedidoProduto, Sabor sabor) {
		this.id = new PedidoProdutoSaborPK(pedidoProduto.getId(), sabor.getId());
		this.pedidoProduto = pedidoProduto;
		this.sabor = sabor;
	}

	public PedidoProdutoSaborPK getId() {
		return this.id;
	}

	public PedidoProduto getPedidoProduto() {
		return pedidoProduto;
	}
	
	public void setPedidoProduto(PedidoProduto pedidoProduto) {
		this.pedidoProduto = pedidoProduto;
	}
	
	public Sabor getSabor() {
		return sabor;
	}
	
	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
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
		PedidoProdutoSabor other = (PedidoProdutoSabor) obj;
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
		return "PedidoProdutoSabor [id=" + id + "]";
	}

	@Override
	public String getSqlSelect() {
		return " SELECT pps FROM PedidoProdutoSabor pps";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(pps.id) FROM PedidoProdutoSabor pps ";
	}

	@Override
	public String getObjetoRetorno() {
		return " pps ";
	}

	@Override
	public String getJoin() {
		StringBuilder sb = new StringBuilder();
		sb.append(" JOIN FETCH pps.pedidoProduto pd ");
		sb.append(" JOIN FETCH pd.pedido pe ");
		sb.append(" JOIN FETCH pd.produto pr ");
		sb.append(" JOIN FETCH pps.sabor sb ");
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