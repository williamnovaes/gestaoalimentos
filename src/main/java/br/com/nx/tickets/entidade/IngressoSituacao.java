package br.com.nx.tickets.entidade;

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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.util.Descritivel;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@XmlRootElement
@Table(name = "ingresso_situacao")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "descricao", "situacao"})
public class IngressoSituacao implements SituacaoAlteravel, Descritivel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ingressoSituacaoSeq", sequenceName = "ingresso_situacao_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ingressoSituacaoSeq")
	@Column(unique = true, nullable = false)
	@XmlElement(name = "id")
	private Integer id;

	@NotNull
	@JsonInclude(Include.NON_NULL)
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	@XmlElement(name = "descricao")
	private String descricao;
	
	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(name = "ativo", length = SistemaConstantes.DEZ, nullable = false)
	private EBoolean ativo;
	
	@NotNull
	@XmlTransient
	@Enumerated(EnumType.STRING)
	@Column(name = "considerar_entrada_caixa", length = SistemaConstantes.DEZ, nullable = false)
	private EBoolean considerarEntradaCaixa;

	@XmlTransient
	@Column(name = "icone", length = SistemaConstantes.CINQUENTA, unique = true)
	private String icone;
	
	@NotNull
	@XmlTransient
	@Column(name = "cor", length = SistemaConstantes.DEZ, nullable = false)
	@XmlElement(name = "cor")
	private String cor;
	
	@NotNull
	@XmlTransient
	@Column(name = "mensagem", length = SistemaConstantes.DESCRICAO, nullable = false)
	@XmlElement(name = "mensagem")
	private String mensagem;
	
	public IngressoSituacao() {
	}

	public IngressoSituacao(Integer id) {
		this.id = id;
	}
	
	public IngressoSituacao(Integer id, String descricao, String icone) {
		this.id = id;
		this.descricao = descricao;
		this.icone = icone;
	}
	
	public IngressoSituacao(String descricao) {
		this.descricao = descricao;
	}

	public IngressoSituacao(String descricao, EBoolean ativo, String icone) {
		this.descricao = descricao;
		this.ativo = ativo;
		this.icone = icone;
	}

	public Integer getId() {
		return this.id;
	}

	@Override
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(situacao);
	}

	public EBoolean getAtivo() {
		return ativo;
	}
	
	public String getIcone() {
		return icone;
	}
	
	public String getCor() {
		return cor;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public EBoolean getConsiderarEntradaCaixa() {
		return considerarEntradaCaixa;
	}
	
	public void setConsiderarEntradaCaixa(EBoolean considerarEntradaCaixa) {
		this.considerarEntradaCaixa = considerarEntradaCaixa;
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
		IngressoSituacao other = (IngressoSituacao) obj;
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
	public ESituacao getSituacao() {
		return situacao;
	}
}