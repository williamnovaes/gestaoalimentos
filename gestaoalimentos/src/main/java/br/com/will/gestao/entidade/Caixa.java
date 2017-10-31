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
@Table(name = "caixa", schema = "gestao")
public class Caixa implements Exportavel, SituacaoAlteravel, Paginavel {

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

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro;

	@Column(name = "observacao", length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String observacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	@NotNull
	@JoinColumn(name = "_usuario", foreignKey = @ForeignKey(name = "fk_usuario"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	public Caixa() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public EBoolean getAberto() {
		return aberto;
	}

	public void setAberto(EBoolean aberto) {
		this.aberto = aberto;
	}

	public boolean isAberto() {
		return Util.converterENumBooleanToBoolean(this.aberto);
	}

	public EBoolean getEntrega() {
		return entrega;
	}

	public void setEntrega(EBoolean entrega) {
		this.entrega = entrega;
	}

	public Calendar getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Calendar dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Calendar getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Calendar dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	@PrePersist
	public void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
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
		this.situacao = Util.alteraSituacao(this.situacao);
	}

	@Override
	public ESituacao getSituacao() {
		return this.situacao;
	}

	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}

	@Override
	public String getSqlSelect() {
		return " SELECT DISTINCT(cx) FROM Caixa cx ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(distinct cx) FROM Caixa cx ";
	}

	@Override
	public String getObjetoRetorno() {
		return " cx ";
	}

	@Override
	public String getJoin() {
		return " JOIN FETCH cx.empresa em " 
			 + " JOIN FETCH cx.usuario us ";
	}
}