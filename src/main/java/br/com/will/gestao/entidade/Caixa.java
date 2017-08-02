package br.com.will.gestao.entidade;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "caixa", schema = "gestao")
public class Caixa implements Exportavel, SituacaoAlteravel {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "caixaSeq", sequenceName = "caixa_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "caixaSeq")
	@Column(unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@JoinColumn(name = "_empresa", foreignKey = @ForeignKey(name = "fk_empresa"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "aberto", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean aberto = EBoolean.TRUE;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "entraga", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_TRUE)
	private EBoolean entrega = EBoolean.TRUE;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_abertura")
	private Calendar dataAbertura;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fechamento")
	private Calendar dataFechamento;
	
	@Column(name = "observacao", length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String observacao;

	
	@Override
	public String toString() {
		return "Caixa [id=" + id + "]";
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caixa other = (Caixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String exportar() {
		return null;
	}

	@Override
	public void alterarSituacao() {
	}

	@Override
	public ESituacao getSituacao() {
		return null;
	}
}