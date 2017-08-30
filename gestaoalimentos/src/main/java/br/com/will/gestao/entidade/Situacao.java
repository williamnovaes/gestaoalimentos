package br.com.will.gestao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "situacao", schema = "gestao")
public class Situacao {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@NotNull
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricao;

	@Column(name = "icone", length = SistemaConstantes.DESCRICAO)
	private String icone;

	public Situacao(Integer id) {
		this.id = id;
	}

	public Situacao() {
	}

	public Situacao(String descricao) {
		this.descricao = descricao;
	}

	public Situacao(String descricao, String icone) {
		this.descricao = descricao;
		this.icone = icone;
	}

	public Situacao(Integer id, String descricao) {
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

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
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
		Situacao other = (Situacao) obj;
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