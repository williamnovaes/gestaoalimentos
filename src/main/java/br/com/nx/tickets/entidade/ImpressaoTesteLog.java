package br.com.nx.tickets.entidade;

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
import javax.xml.bind.annotation.XmlElement;

import br.com.nx.tickets.util.SistemaConstantes;

@Entity
@Table(name = "impressao_teste_log", schema = "public")
public class ImpressaoTesteLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ImpressaoTesteLogPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_guiche", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_guiche"))
	private Guiche guiche;
	
	@NotNull
	@XmlElement(name = "teste_tipo")
	@Column(name = "teste_tipo", length = SistemaConstantes.DESCRICAO, nullable = false)
	private String testeTipo;

	public ImpressaoTesteLog() {
	}

	public ImpressaoTesteLog(Guiche guiche, String testeTipo) {
		this.guiche = guiche;
		this.testeTipo = testeTipo;
		this.id = new ImpressaoTesteLogPK(guiche);
	}

	public ImpressaoTesteLogPK getId() {
		return this.id;
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
		ImpressaoTesteLog other = (ImpressaoTesteLog) obj;
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