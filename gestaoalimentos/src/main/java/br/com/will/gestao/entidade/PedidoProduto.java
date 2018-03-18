package br.com.will.gestao.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "pedido_produto", schema = "gestao")
public class PedidoProduto
		implements Serializable, Paginavel, Exportavel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pedidoProdutoSeq", sequenceName = "pedido_produto_id_multi_seq", 
	allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pedidoProdutoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_pedido", foreignKey = @ForeignKey(name = "fk_pedido"))
	private Pedido pedido;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto", foreignKey = @ForeignKey(name = "fk_produto"))
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_tamanho", foreignKey = @ForeignKey(name = "fk_tamanho"))
	private Tamanho tamanho;
	
	@Column(name = "referencia")
	private Integer referencia;
	
	@Column(name = "observacao", length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String observacao;
	
	@Column(name = "preco_final")
	private BigDecimal precoFinal;


	public PedidoProduto() {
	}

	public PedidoProduto(Pedido pedido, Produto produto) {
		this.produto = produto;
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public BigDecimal getPrecoFinal() {
		return precoFinal;
	}
	
	public void setPrecoFinal(BigDecimal precoFinal) {
		this.precoFinal = precoFinal;
	}
	
	public Integer getReferencia() {
		return referencia;
	}
	
	public void setReferencia(Integer referencia) {
		this.referencia = referencia;
	}
	
	public Tamanho getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
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
		PedidoProduto other = (PedidoProduto) obj;
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
		return " SELECT count(pp.id) FROM PedidoProduto pp ";
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
		sb.append(" JOIN FETCH pp.pedido pd ");
		sb.append(" JOIN FETCH pp.produto pr ");
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