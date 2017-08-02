package br.com.will.gestao.entidade;

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

import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "tamanho", schema = "gestao")
public class Tamanho {
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_tamanho_tipo", foreignKey = @ForeignKey(name = "fk_tamanho_tipo"))
	private TamanhoTipo tamanhoTipo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto_tipo", foreignKey = @ForeignKey(name = "fk_produto_tipo"))
	private ProdutoTipo produtoTipo;

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
	
	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}
	
	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}

	@Override
	public String toString() {
		return "Situacao [id=" + id + "]";
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}