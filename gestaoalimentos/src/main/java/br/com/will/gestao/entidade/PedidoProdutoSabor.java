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
	private PedidoProdutoPK id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_pedido", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_pedido"))
	private Pedido pedido;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_produto"))
	private Produto produto;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_saobor", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_sabor"))
	private Sabor sabor;
	
	@Column(name = "referencia")
	private Integer referencia;
	
	public PedidoProdutoSabor() {
	}

	public PedidoProdutoSabor(Pedido pedido, Produto produto) {
		this.id = new PedidoProdutoPK(pedido.getId(), produto.getId());
		this.produto = produto;
		this.pedido = pedido;
	}

	public PedidoProdutoPK getId() {
		return this.id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Sabor getSabor() {
		return sabor;
	}
	
	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}
	
	public Integer getReferencia() {
		return referencia;
	}
	
	public void setReferencia(Integer referencia) {
		this.referencia = referencia;
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
		sb.append(" JOIN pps.pedido pd ");
		sb.append(" JOIN pps.produto pr ");
		sb.append(" JOIN pps.sabor sb ");
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