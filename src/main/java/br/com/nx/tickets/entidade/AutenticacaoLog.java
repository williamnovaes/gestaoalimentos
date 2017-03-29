package br.com.nx.tickets.entidade;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.nx.tickets.rest.envio.IngressoEnvioLogin;
import br.com.nx.tickets.util.SistemaConstantes;

@Entity
@Table(name = "autenticacao_log", schema = "public")
public class AutenticacaoLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "autenticacaoLogSeq", sequenceName = "autenticacao_log_id_seq", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "autenticacaoLogSeq")
	private Integer id;

	@NotNull
	@Column(name = "login", nullable = false, length = SistemaConstantes.CEM)
	private String login;

	@NotNull
	@Column(name = "senha", nullable = false, length = SistemaConstantes.CEM)
	private String senha;

	@Column(name = "terminal", length = SistemaConstantes.CEM)
	private String terminal;

	@NotNull
	@Column(name = "data_cadastro", nullable = false)
	private Calendar dataCadastro;
	
	public AutenticacaoLog() {
	}

	public AutenticacaoLog(String login, String senha, String terminal) {
		this.login = login;
		this.senha = senha;
		this.terminal = terminal;
	}
	
	public AutenticacaoLog(IngressoEnvioLogin ingressoEnvio) {
		this.login = ingressoEnvio.getLogin();
		this.senha = ingressoEnvio.getSenha();
		this.terminal = ingressoEnvio.getTerminal();
	}

	public Integer getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public String getTerminal() {
		return terminal;
	}

	@PrePersist
	protected void setDataCadastro() {
		this.dataCadastro = Calendar.getInstance();
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
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
		AutenticacaoLog other = (AutenticacaoLog) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}