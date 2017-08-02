package br.com.will.gestao.entidade;

import java.io.Serializable;
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

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "promocao", schema = "gestao")
public class Promocao implements Serializable, SituacaoAlteravel, Paginavel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "promocaoSeq", sequenceName = "promocao_id_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "promocaoSeq")
	private Integer id;

	@NotNull
	@Column(name = "descricao", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String descricao;
	
	@NotNull
	@Column(name = "observacao", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String observacao = 
			"Cadastre o código [CODIGO] em;www.smingressos.com.br;e concorra a uma camisa oficial.";
	
	@NotNull
	@Column(name = "quantidade", nullable = false, updatable = false)
	private Integer quantidade;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inicio", nullable = false)
	private Calendar dataInicio;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fim", nullable = false)
	private Calendar dataFim;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_usuario", foreignKey = @ForeignKey(name = "fk_usuario"), nullable = false)
	private Usuario usuario;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	public Promocao() {

	}

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
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Calendar getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Calendar getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		return "Promocao [id=" + id + ", descricao=" + descricao + "]";
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
		Promocao other = (Promocao) obj;
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
		return "SELECT distinct(pr) FROM Promocao pr ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct pr.id) FROM Promocao pr ";
	}

	@Override
	public String getObjetoRetorno() {
		return "pr";
	}

	@Override
	public String getJoin() {
		return " LEFT JOIN FETCH pr.usuario us ";
	}
}