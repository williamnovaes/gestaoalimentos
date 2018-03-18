package br.com.will.gestao.entidade;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "status_atendimento", schema = "gestao")
public class StatusAtendimento implements Paginavel, SituacaoAlteravel {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "statusAtendimentoSeq", sequenceName = "status_atendimento_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "statusAtendimentoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricao;
	
	@Column(name = "timeline")
	private Integer timeline;

	@Column(name = "icone", length = SistemaConstantes.DESCRICAO)
	private String icone;
	
	@Column(name = "encerrado", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean encerrado = EBoolean.FALSE;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	public StatusAtendimento(Integer id) {
		this.id = id;
	}

	public StatusAtendimento() {
	}

	public StatusAtendimento(String descricao) {
		this.descricao = descricao;
	}

	public StatusAtendimento(String descricao, String icone) {
		this.descricao = descricao;
		this.icone = icone;
	}

	public StatusAtendimento(Integer id, String descricao) {
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
	
	public Integer getTimeline() {
		return timeline;
	}
	
	public void setTimeline(Integer timeline) {
		this.timeline = timeline;
	}
	
	public EBoolean getEncerrado() {
		return encerrado;
	}
	
	public void setEncerrado(EBoolean encerrado) {
		this.encerrado = encerrado;
	}
	
	public boolean isEncerrado() {
		return Util.converterENumBooleanToBoolean(this.encerrado);
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	@PrePersist
	public void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}
	
	@Override
	public ESituacao getSituacao() {
		return situacao;
	}
	
	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}
	
	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
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
		StatusAtendimento other = (StatusAtendimento) obj;
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
	public String getSqlSelect() {
		return " SELECT distinct(sa) FROM StatusAtendimento sa ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(distinct sa.id) FROM StatusAtendimento sa ";
	}

	@Override
	public String getObjetoRetorno() {
		return " sa ";
	}

	@Override
	public String getJoin() {
		return "";
	}
}