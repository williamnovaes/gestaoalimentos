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
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "forma_pagamento", schema = "gestao")
public class FormaPagamento implements Paginavel, SituacaoAlteravel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "formaPagamentoSeq", sequenceName = "forma_pagamento_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "formaPagamentoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricao;

	@Column(name = "icone", length = SistemaConstantes.DESCRICAO)
	private String icone;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao", columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	public FormaPagamento(Integer id) {
		this.id = id;
	}

	public FormaPagamento() {
	}

	public FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public FormaPagamento(String descricao, String icone) {
		this.descricao = descricao;
		this.icone = icone;
	}

	public FormaPagamento(Integer id, String descricao) {
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
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	@PrePersist
	public void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}
	
	@Override
	public ESituacao getSituacao() {
		return this.situacao;
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
		return "FormaPagamento [id=" + id + "]";
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
		FormaPagamento other = (FormaPagamento) obj;
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

	@Override
	public String getSqlSelect() {
		return "SELECT fp FROM FormaPagamento fp ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(distinct fp.id) FROM FormaPagamento fp ";
	}

	@Override
	public String getObjetoRetorno() {
		return " fp ";
	}

	@Override
	public String getJoin() {
		return "";
	}
}