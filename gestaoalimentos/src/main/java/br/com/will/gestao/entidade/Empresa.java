package br.com.will.gestao.entidade;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "empresa", schema = "gestao")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Empresa implements Exportavel, SituacaoAlteravel {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "empresaSeq", sequenceName = "empresa_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "empresaSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@Column(name = "cnpj", length = SistemaConstantes.QUATORZE, nullable = false)
	private String cnpj;

	@NotNull
	@Column(name = "nome_fantasia", length = SistemaConstantes.CENTO_CINQUENTA, nullable = false)
	private String nomeFantasia;

	@NotNull
	@Column(name = "razao_social", length = SistemaConstantes.CENTO_CINQUENTA, nullable = false)
	private String razaoSocial;

	@Column(name = "telefone", length = SistemaConstantes.ONZE)
	private String telefone;

	@Column(name = "telefone2", length = SistemaConstantes.ONZE)
	private String telefone2;

	@Column(name = "email", length = SistemaConstantes.EMAIL)
	private String email;

	@NotAudited
	@JoinColumn(name = "_endereco", foreignKey = @ForeignKey(name = "fk_endereco"))
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Endereco endereco;

	@NotAudited
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
	private List<Usuario> usuariosCadastro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Usuario> getUsuariosCadastro() {
		return usuariosCadastro;
	}

	public void setUsuariosCadastro(List<Usuario> usuariosCadastro) {
		this.usuariosCadastro = usuariosCadastro;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Override
	public String toString() {
		return "Empresa [id=" + id + ", cnpj=" + cnpj + ", nomeFantasia=" + nomeFantasia + ", razaoSocial="
				+ razaoSocial + ", telefone=" + telefone + ", telefone2=" + telefone2 + ", email=" + email
				+ ", endereco=" + endereco + ", usuariosCadastro=" + usuariosCadastro + ", dataCadastro=" + dataCadastro
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeFantasia == null) ? 0 : nomeFantasia.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
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
		Empresa other = (Empresa) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeFantasia == null) {
			if (other.nomeFantasia != null)
				return false;
		} else if (!nomeFantasia.equals(other.nomeFantasia))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
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