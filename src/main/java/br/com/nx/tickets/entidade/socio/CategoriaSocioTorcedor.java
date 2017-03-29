package br.com.nx.tickets.entidade.socio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@Table(name = "categoria_socio_torcedor", schema = "socio_torcedor")
public class CategoriaSocioTorcedor implements Serializable, SituacaoAlteravel, Comparable<CategoriaSocioTorcedor> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "categoriaSocioTorcedorSeq", sequenceName = "categoria_socio_torcedor_id_seq", 
	allocationSize = 1, schema = "socio_torcedor")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "categoriaSocioTorcedorSeq")
	private Integer id;

	@NotNull
	@Column(name = "descricao", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}

	@Override
	public ESituacao getSituacao() {
		return situacao;
	}

	public Integer getId() {
		return id;
	}

	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "CategoriaSocioTorcedor [id=" + id + "]";
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
		CategoriaSocioTorcedor other = (CategoriaSocioTorcedor) obj;
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
	public int compareTo(CategoriaSocioTorcedor o) {
		return this.descricao.compareTo(o.descricao);
	}
}