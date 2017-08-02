package br.com.will.gestao.entidade.permissao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "menu", schema = "permissao")
public class Menu implements Serializable, SituacaoAlteravel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "menuSeq", sequenceName = "menu_id_seq", allocationSize = 1, schema = "permissao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "menuSeq")
	private Integer id;

	@NotNull
	@Column(name = "nome", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String nome;
	
	@NotNull
	@Column(name = "sequencia", nullable = false)
	private Integer sequencia;

	@NotNull
	@Column(name = "data_cadastro", nullable = false)
	private Calendar dataCadastro;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "visivel", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_TRUE)
	private EBoolean visivel;
	
	@OneToMany(mappedBy = "menu")
	private List<Pagina> paginas;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	public Menu() {

	}

	public Menu(String caminhoArquivo) {
		this.situacao = ESituacao.ATIVO;
	}

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}

	@Override
	public ESituacao getSituacao() {
		return situacao;
	}
	
	public EBoolean getVisivel() {
		return visivel;
	}
	
	public void setVisivel(EBoolean visivel) {
		this.visivel = visivel;
	}
	
	public boolean isVisivel() {
		return getVisivel().equals(EBoolean.TRUE);
	}

	public Integer getId() {
		return id;
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
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getSequencia() {
		return sequencia;
	}
	
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	
	@Override
	public String toString() {
		return "Menu [id=" + id + "]";
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
		Menu other = (Menu) obj;
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