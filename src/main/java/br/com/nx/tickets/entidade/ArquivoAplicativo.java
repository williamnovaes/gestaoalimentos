package br.com.nx.tickets.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.nx.tickets.componente.Paginavel;

@Entity
@Table(name = "arquivo_aplicativo", schema = "public")
public class ArquivoAplicativo extends Arquivo implements Paginavel {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "versao_aplicativo", nullable = false, updatable = false)
	private Float versaoAplicativo;
	
	public Float getVersaoAplicativo() {
		return versaoAplicativo;
	}
	
	public void setVersaoAplicativo(Float versaoAplicativo) {
		this.versaoAplicativo = versaoAplicativo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		ArquivoAplicativo other = (ArquivoAplicativo) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String getSqlSelect() {
		return "SELECT distinct(aa) FROM ArquivoAplicativo aa ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct aa.id) FROM ArquivoAplicativo aa ";
	}

	@Override
	public String getObjetoRetorno() {
		return "aa";
	}

	@Override
	public String getJoin() {
		return "JOIN FETCH aa.arquivoTipo at ";
	}
}