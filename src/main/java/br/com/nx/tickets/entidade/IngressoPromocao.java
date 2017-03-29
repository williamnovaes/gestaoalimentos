package br.com.nx.tickets.entidade;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.rest.DateFormatterAdapter;
import br.com.nx.tickets.util.SistemaConstantes;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "ingresso", "nome", "cpf", "telefone_residencial", "telefone_celular", "email",
		"tamanho", "data_nascimento"})
@Table(name = "ingresso_promocao", schema = "public")
public class IngressoPromocao implements Serializable, Paginavel, Comparable<IngressoPromocao> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@XmlTransient
	private IngressoPromocaoPK id;

	@NotNull
	@XmlElement(name = "ingresso")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ingresso", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_ingresso"))
	private Ingresso ingresso;

	@NotNull
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_promocao", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_promocao"))
	private Promocao promocao;
	
	@NotNull
	@XmlTransient
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	private Calendar dataCadastro;
	
	@NotNull
	@Column(name = "nome", nullable = false, length = SistemaConstantes.CEM)
	private String nome;
	
	@NotNull
	@Column(name = "cpf", nullable = false, length = SistemaConstantes.ONZE)
	private String cpf;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "telefone_residencial")
	@Column(name = "telefone_residencial", length = SistemaConstantes.ONZE)
	private String telefoneResidencial;
	
	@NotNull
	@XmlElement(name = "telefone_celular")
	@Column(name = "telefone_celular", nullable = false, length = SistemaConstantes.ONZE)
	private String telefoneCelular;
	
	@NotNull
	@Column(name = "email", nullable = false, length = SistemaConstantes.CEM)
	private String email;
	
	@NotNull
	@Column(name = "tamanho", nullable = false, length = SistemaConstantes.DOIS)
	private String tamanho;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@XmlElement(name = "data_nascimento")
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@Column(name = "data_nascimento", nullable = false)
	private Calendar dataNascimento;
	
	@XmlTransient
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_sorteado")
	private Calendar dataSorteado;

	public IngressoPromocao() {
		ingresso = new Ingresso();
	}

	public IngressoPromocao(Ingresso ingresso, Promocao promocao) {
		this.ingresso = ingresso;
		this.promocao = promocao;
		this.id = new IngressoPromocaoPK(ingresso.getId(), promocao.getId());
	}
	
	public void configurarPk(Ingresso in, Promocao pr) {
		this.ingresso = in;
		this.promocao = pr;
		this.id = new IngressoPromocaoPK(in.getId(), pr.getId());
	}

	public IngressoPromocaoPK getId() {
		return id;
	}

	public Ingresso getIngresso() {
		return ingresso;
	}
	
	public void setIngresso(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
	public Promocao getPromocao() {
		return promocao;
	}
	
	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Calendar getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefoneCelular() {
		return telefoneCelular;
	}
	
	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}
	
	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}
	
	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}
	
	@PrePersist
	public void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}
	
	public Calendar getDataSorteado() {
		return dataSorteado;
	}
	
	public String getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
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
		IngressoPromocao other = (IngressoPromocao) obj;
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
		return "SELECT distinct(ip) FROM IngressoPromocao ip ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct ip.id) FROM IngressoPromocao ip ";
	}

	@Override
	public String getObjetoRetorno() {
		return "ip";
	}

	@Override
	public String getJoin() {
		return "JOIN FETCH ip.ingresso i "
			 + "JOIN FETCH ip.promocao pr "
			 + "JOIN FETCH pr.evento ev ";
	}

	@Override
	public int compareTo(IngressoPromocao o) {
		return this.nome.compareTo(o.nome);
	}
}