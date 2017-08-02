package br.com.will.gestao.entidade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

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
import javax.validation.constraints.NotNull;

import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "arquivo", schema = "gestao")
public class Arquivo implements Serializable, SituacaoAlteravel,
		Comparable<Arquivo> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "arquivoSeq", sequenceName = "arquivo_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "arquivoSeq")
	private Integer id;

	@NotNull
	@Column(name = "nome_arquivo", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String nomeArquivo;

	@NotNull
	@Column(name = "data_cadastro", nullable = false)
	private Calendar dataCadastro;

	@NotNull
	@Column(name = "caminho_arquivo", length = SistemaConstantes.DUZENTOS_CINQUENTA, unique = true)
	private String caminhoArquivo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	@NotNull
	@JoinColumn(name = "_arquivo_tipo", foreignKey = @ForeignKey(name = "fk_arquivo_tipo"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private ArquivoTipo arquivoTipo;
	
	@NotNull
	@JoinColumn(name = "_empresa", foreignKey = @ForeignKey(name = "fk_empresa"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	
	public Arquivo() {

	}

	public Arquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
		this.situacao = ESituacao.ATIVO;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
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

	public void setArquivoTipo(ArquivoTipo arquivoTipo) {
		this.arquivoTipo = arquivoTipo;
	}

	public ArquivoTipo getArquivoTipo() {
		return arquivoTipo;
	}

	@PrePersist
	public void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	@Override
	public String toString() {
		return "Arquivo [id=" + id + "]";
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
		Arquivo other = (Arquivo) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public static final Comparator<Arquivo> COMPARAR_POR_NOME_ARQUIVO = new Comparator<Arquivo>() {

		@Override
		public int compare(Arquivo o1, Arquivo o2) {
			if (o1.getNomeArquivo().compareTo(o2.getNomeArquivo()) > 0) {
				return 1;
			} else if (o1.getNomeArquivo().compareTo(o2.getNomeArquivo()) < 0) {
				return -1;
			}
			return 0;
		}
	};

	@Override
	public int compareTo(Arquivo o) {
		return this.nomeArquivo.compareTo(o.nomeArquivo);
	}
}