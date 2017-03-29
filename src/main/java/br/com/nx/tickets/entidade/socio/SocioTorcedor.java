package br.com.nx.tickets.entidade.socio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.entidade.Arquivo;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "nome", "codigo", "carteirinha", "cpf", "plano", "situacao" })
@Table(name = "socio_torcedor", schema = "public", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "carteirinha", "_cliente" }) })
public class SocioTorcedor implements Serializable, Paginavel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "socioTorcedorSeq", sequenceName = "socio_torcedor_id_seq", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "socioTorcedorSeq")
	private Integer id;

	@NotNull
	@Column(name = "nome", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String nome;

	@NotNull
	@Column(name = "codigo", nullable = false, length = SistemaConstantes.CINQUENTA)
	private String codigo;

	@NotNull
	@Column(name = "carteirinha", nullable = false, length = SistemaConstantes.CINQUENTA)
	private String carteirinha;

	@NotNull
	@Column(name = "cpf", nullable = false, length = SistemaConstantes.CPF)
	private String cpf;

	@NotNull
	@Column(name = "plano", nullable = false, length = SistemaConstantes.VINTE)
	private String plano;

	@NotNull
	@Column(name = "situacao", nullable = false, length = SistemaConstantes.VINTE)
	private String situacao;

	@NotNull
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_cliente", foreignKey = @ForeignKey(name = "fk_cliente"), nullable = false)
	private Cliente cliente;

	@Transient
	@XmlTransient
	private Arquivo arquivo;

	public SocioTorcedor() {

	}

	public SocioTorcedor(String nome, String codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

	public SocioTorcedor(String[] strings) {
		this.carteirinha = strings[SistemaConstantes.DOIS];
		this.codigo = strings[SistemaConstantes.TRES];
		this.nome = Util.removerCaracteresSpeciaisAcentuacoes(strings[SistemaConstantes.QUATRO].toUpperCase()) + " "
				+ Util.removerCaracteresSpeciaisAcentuacoes(strings[SistemaConstantes.CINCO].toUpperCase());
		this.plano = strings[SistemaConstantes.SETE].toUpperCase();
		this.cpf = Util.removerNaoDigitos(strings[SistemaConstantes.ONZE]);
		if (this.cpf.length() > SistemaConstantes.ONZE) {
			this.cpf = cpf.substring(0, SistemaConstantes.DEZ);
		}
		if (this.cpf.length() != SistemaConstantes.ONZE) {
			this.cpf = Util.configurarCpfComZeros(this.cpf);
		}
		this.situacao = strings[SistemaConstantes.DEZENOVE].toUpperCase();
	}

	public String getNome() {
		return nome;
	}
	
	public String getNomeAbreviado() {
		return Util.abreviar(this.nome);
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

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getPlano() {
		return plano;
	}

	public String getCarteirinha() {
		return carteirinha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "SocioTorcedor [id=" + id + ", nome=" + nome + ", codigo=" + codigo + ", carteirinha=" + carteirinha
				+ ", cpf=" + cpf + ", plano=" + plano + ", situacao=" + situacao + "]";
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
		SocioTorcedor other = (SocioTorcedor) obj;
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
		return "SELECT distinct(st) FROM SocioTorcedor st ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct st.id) FROM SocioTorcedor st ";
	}

	@Override
	public String getObjetoRetorno() {
		return "st";
	}

	@Override
	public String getJoin() {
		return "";
	}
}