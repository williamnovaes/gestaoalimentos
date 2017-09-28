package br.com.will.gestao.entidade;

import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.permissao.PaginaNivel;
import br.com.will.gestao.entidade.util.Descritivel;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ENivel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.Identificavel;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "nivel", schema = "gestao")
public class Nivel implements SituacaoAlteravel, Descritivel, Comparable<Nivel>, Paginavel, Identificavel {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@NotNull
	@Column(name = "descricao", length = SistemaConstantes.DESCRICAO, nullable = false, unique = true)
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean chat = EBoolean.TRUE;

	@OneToMany(mappedBy = "nivel")
	private List<Usuario> usuarios;

	@Column(name = "icone", length = SistemaConstantes.DESCRICAO)
	private String icone;

	@Column(name = "alias", length = SistemaConstantes.DESCRICAO)
	private String alias;

	@JoinColumn(name = "_nivel_tipo", foreignKey = @ForeignKey(name = "fk_nivel_tipo"))
	@ManyToOne(fetch = FetchType.LAZY)
	private NivelTipo nivelTipo;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean editarView = EBoolean.FALSE;

	@OneToMany(mappedBy = "nivel")
	private List<PaginaNivel> paginaNivel;

	public Nivel(Integer id) {
		this.id = id;
	}

	public Nivel() {
	}

	public Nivel(String descricao) {
		this.descricao = descricao;
	}

	public Nivel(String descricao, String icone) {
		this.descricao = descricao;
		this.icone = icone;
	}

	public Nivel(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public ESituacao getSituacao() {
		return this.situacao;
	}

	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}

	public EBoolean getChat() {
		return chat;
	}

	public void setChat(EBoolean chat) {
		this.chat = chat;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public NivelTipo getNivelTipo() {
		return nivelTipo;
	}

	public void setNivelTipo(NivelTipo nivelTipo) {
		this.nivelTipo = nivelTipo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public EBoolean getEditarView() {
		return editarView;
	}
	
	public void setEditarView(EBoolean editarView) {
		this.editarView = editarView;
	}
	
	public List<PaginaNivel> getPaginaNivel() {
		return paginaNivel;
	}
	
	public void setPaginaNivel(List<PaginaNivel> paginaNivel) {
		this.paginaNivel = paginaNivel;
	}

	public ENivel getEnum() {
		if (this.getDescricao() != null && !this.getDescricao().equals("")) {
			return ENivel.valueOf(this.getDescricao().replaceAll(" ", "_"));
		}
		return null;
	}

	public static final Comparator<Nivel> COMPARAR_POR_DESCRICAO = new Comparator<Nivel>() {

		@Override
		public int compare(Nivel o1, Nivel o2) {
			if (o1.getDescricao().compareTo(o2.getDescricao()) > 0) {
				return 1;
			} else if (o1.getDescricao().compareTo(o2.getDescricao()) < 0) {
				return -1;
			}
			return 0;
		}
	};

	@Override
	public String toString() {
		return "Nivel [id=" + id + "]";
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
		Nivel other = (Nivel) obj;
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
	public int compareTo(Nivel n) {
		return this.descricao.compareTo(n.descricao);
	}

	@Override
	public String getSqlSelect() {

		return " SELECT distinct(n) FROM Nivel n ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(distinct n.id) FROM Nivel n ";
	}

	@Override
	public String getObjetoRetorno() {
		return " n ";
	}

	@Override
	public String getJoin() {
		return " JOIN FETCH n.nivelTipo nt "
			 + " JOIN FETCH n.paginaNivel pn ";
	}
}