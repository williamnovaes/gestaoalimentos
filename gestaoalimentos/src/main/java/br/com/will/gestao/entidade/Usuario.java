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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.ETipoNivel;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "usuario", schema = "gestao")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Usuario implements Exportavel, SituacaoAlteravel, Paginavel {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "usuarioSeq", sequenceName = "usuario_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuarioSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "nome", length = SistemaConstantes.CENTO_CINQUENTA, nullable = false)
	private String nome;

	@Column(name = "cpf", length = SistemaConstantes.ONZE, nullable = false)
	private String cpf;

	@Email(message = "Email inválido")
	@Column(name = "email", length = SistemaConstantes.EMAIL, nullable = false)
	private String email;

	@Column(name = "login", length = SistemaConstantes.CINQUENTA, nullable = false)
	private String login;

	@Column(name = "senha", length = SistemaConstantes.CENTO_CINQUENTA, nullable = false)
	private String senha;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_nivel", foreignKey = @ForeignKey(name = "fk_nivel"), nullable = false)
	private Nivel nivel;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao", columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	@JoinColumn(name = "_empresa", foreignKey = @ForeignKey(name = "fk_empresa"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;

	@NotAudited
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ultima_interacao")
	private Calendar dataUltimaInteracao;

	@NotAudited
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", updatable = false)
	private Calendar dataCadastro;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Calendar dataNascimento;

	@Transient
	private String chaveSessao;

	@Transient
	private boolean senhaCoringa;

	@Transient
	private Boolean selecionado;
	
	public Usuario() {

	}
	
	public Usuario(Integer id) {
		this.id = id;
	}
	
	public Usuario(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public ESituacao getSituacao() {
		return situacao;
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

	public Calendar getDataUltimaInteracao() {
		return dataUltimaInteracao;
	}

	public void setDataUltimaInteracao(Calendar dataUltimaInteracao) {
		this.dataUltimaInteracao = dataUltimaInteracao;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	@PrePersist
	public void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getChaveSessao() {
		return chaveSessao;
	}

	public void setChaveSessao(String chaveSessao) {
		this.chaveSessao = chaveSessao;
	}

	public boolean isSenhaCoringa() {
		return senhaCoringa;
	}

	public void setSenhaCoringa(boolean senhaCoringa) {
		this.senhaCoringa = senhaCoringa;
	}

	public Boolean getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
	}
	
	public ETipoNivel getTipoNivel() {
		return ETipoNivel.valueOf(this.nivel.getNivelTipo().getDescricao());
	}

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}
	
	@Override
	public String exportar() {
		return null;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", nivel=" + nivel + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
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
		Usuario other = (Usuario) obj;
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
		return "SELECT distinct(u) FROM Usuario u ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct u) FROM Usuario u ";
	}

	@Override
	public String getObjetoRetorno() {
		return " u ";
	}

	@Override
	public String getJoin() {
		StringBuilder str = new StringBuilder();
		str.append(" JOIN FETCH u.nivel n ");
		str.append(" JOIN FETCH u.empresa e ");
		return str.toString();
	}
}