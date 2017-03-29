package br.com.nx.tickets.entidade;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class ImpressaoTesteLogPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "_guiche")
	private int guiche;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro;

	public ImpressaoTesteLogPK() {
	}

	public ImpressaoTesteLogPK(Guiche guiche) {
		this.guiche = guiche.getId();
		this.dataCadastro = Calendar.getInstance();
	}
	
	public int getGuiche() {
		return guiche;
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro; 
	}
	
	protected void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + guiche;
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
		ImpressaoTesteLogPK other = (ImpressaoTesteLogPK) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null) {
				return false;
			}
		} else if (!dataCadastro.equals(other.dataCadastro)) {
			return false;
		}
		if (guiche != other.guiche) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ImpressaoTesteLogPK [guiche=" + guiche + ", dataCadastro=" + dataCadastro + "]";
	}
}