package br.com.nx.tickets.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@Table(name = "guiche_lote", schema = "public")
public class GuicheLote implements Serializable, SituacaoAlteravel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GuicheLotePK id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_guiche", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_guiche"))
	private Guiche guiche;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_lote", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_lote"))
	private Lote lote;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	public GuicheLote() {
	}

	public GuicheLote(Guiche guiche, Lote lote) {
		this.guiche = guiche;
		this.lote = lote;
		this.id = new GuicheLotePK(guiche.getId(), lote.getId());
	}

	public GuicheLotePK getId() {
		return id;
	}

	public Guiche getGuiche() {
		return guiche;
	}

	public Lote getLote() {
		return lote;
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
		GuicheLote other = (GuicheLote) obj;
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
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}

	@Override
	public ESituacao getSituacao() {
		return this.situacao;
	}
}