package br.com.will.gestao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import br.com.will.gestao.entidade.util.Descritivel;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "tamanho_tipo", schema = "gestao")
public class TamanhoTipo implements Descritivel {

	@Id
	@SequenceGenerator(name = "tamanhoTipoSeq", sequenceName = "tamanho_tipo_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tamanhoTipoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@Column(length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricao;
	
	@Column(name = "quantidade")
	private Integer quantidade;


	public TamanhoTipo() {

	}
	
	public TamanhoTipo(Integer id) {
		this.id = id;
	}
	
	public TamanhoTipo(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		TamanhoTipo other = (TamanhoTipo) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}