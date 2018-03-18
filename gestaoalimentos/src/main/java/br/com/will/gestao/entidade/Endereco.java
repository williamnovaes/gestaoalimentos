package br.com.will.gestao.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "endereco", schema = "gestao")
public class Endereco implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "enderecoSeq", sequenceName = "endereco_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "enderecoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "bairro", length = SistemaConstantes.CEM)
	private String bairro;

	@Column(name = "bloco", length = SistemaConstantes.CINQUENTA)
	private String bloco;

	@Column(name = "cep", length = SistemaConstantes.OITO)
	private String cep;

	@Column(name = "complemento", length = SistemaConstantes.CEM)
	private String complemento;

	@Column(name = "tipo_logradouro", length = SistemaConstantes.CENTO_CINQUENTA)
	private String tipoLogradouro;

	@Column(name = "logradouro", length = SistemaConstantes.CENTO_CINQUENTA)
	private String logradouro;

	@Column(name = "numero", length = SistemaConstantes.DEZ)
	private String numero;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_cidade", nullable = false, foreignKey = @ForeignKey(name = "fk_cidade"))
	private Cidade cidade;
	
	public Endereco() {

	}

	public Endereco(String cidade, String uf) {
		this.cidade = new Cidade(cidade, uf);
	}
	
	public Endereco(Integer id) {
		this.id = id;
	}

	public Endereco(String tipoLogradouro, String logradouro, String numero, 
			String bairro, String cep, String cidade, String uf) {
		this.tipoLogradouro = tipoLogradouro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = new Cidade(cidade, uf);
	}
	
	public Endereco(String tipoLogradouro, String logradouro, String numero,
			String complemento, String bairro, String cep, String cidade, String uf) {
		this.tipoLogradouro = tipoLogradouro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = new Cidade(cidade, uf);
	}

	public Integer getId() {
		return this.id;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getBloco() {
		return this.bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getTipoLogradouro() {
		if (tipoLogradouro == null) {
			return "";
		}
		if (tipoLogradouro.equals("R")) {
			return "RUA";
		}
		if (tipoLogradouro.equals("AL")) {
			return "ALAMEDA";
		}
		if (tipoLogradouro.equals("AV")) {
			return "AVENIDA";
		}
		if (tipoLogradouro.equals("VLA")) {
			return "VILA";
		}
		if (tipoLogradouro.equals("ROD")) {
			return "RODOVIA";
		}
		if (tipoLogradouro.equals("EST")) {
			return "ESTRADA";
		}
		if (tipoLogradouro.equals("TV")) {
			return "TRAVESSA";
		}
		return tipoLogradouro;
	}

	public String getEnderecoCompleto() {
		return new StringBuilder(getTipoLogradouro()).append(" ")
				.append(this.logradouro).append(", ").append(this.numero)
				.append(", ").append(this.bairro).append(", ").append(this.cep)
				.append(", ").append(this.cidade.getNome()).append("/")
				.append(this.cidade.getEstado().getUf()).toString();
	}

	public String getLogradouroCompleto() {
		return new StringBuilder(getTipoLogradouro()).append(" ")
				.append(this.logradouro).append(", ").append(this.numero)
				.append(", ").toString();
	}

	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getHashcodeTabulacao() {
		String hash = "";
		if (bairro != null) {
			hash += bairro;
		}

		if (cep != null) {
			hash += cep;
		}

		if (complemento != null) {
			hash += complemento;
		}

		if (logradouro != null) {
			hash += logradouro;
		}

		if (numero != null) {
			hash += numero;
		}
		return hash;
	}

	public String getNomeCidade() {
		if (this.cidade != null) {
			return this.cidade.getNome();
		}
		return null;
	}
	
	public String getUf() {
		if (this.cidade != null && this.cidade.getEstado() != null) {
			return this.cidade.getEstado().getUf();
		}
		return null;
	}
	
	public Endereco getClone() {
		Endereco endereco = new Endereco();
		endereco.setBairro(this.bairro);
		endereco.setBloco(this.bloco);
		endereco.setCep(Util.removerFormatacaoCep(this.cep));
		endereco.setComplemento(this.complemento);
		endereco.setTipoLogradouro(this.tipoLogradouro);
		endereco.setLogradouro(this.logradouro);
		endereco.setNumero(this.numero);
		endereco.setCidade(this.cidade);
		return endereco;
	}
}