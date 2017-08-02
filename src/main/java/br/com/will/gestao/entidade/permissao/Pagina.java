package br.com.will.gestao.entidade.permissao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.EPaginaTipo;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "pagina", schema = "permissao")
public class Pagina implements Serializable, SituacaoAlteravel, Paginavel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "paginaSeq", sequenceName = "pagina_id_seq", allocationSize = 1, schema = "permissao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "paginaSeq")
	private Integer id;

	@NotNull
	@Column(name = "nome", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA, unique = true)
	private String nome;
	
	@NotNull
	@Column(name = "rotulo", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String rotulo;
	
	@NotNull
	@Column(name = "bean", nullable = false, length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String bean;
	
	@NotNull
	@Column(name = "sequencia", nullable = false)
	private Integer sequencia;

	@NotNull
	@Column(name = "data_cadastro", nullable = false)
	private Calendar dataCadastro;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "pagina_tipo",columnDefinition = SistemaConstantes.E_PAGINA_TIPO_DEFAULT_LISTAGEM)
	private EPaginaTipo paginaTipo;
	
	@OneToMany(mappedBy = "pagina")
	private List<PaginaNivel> paginaNivel;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_menu", foreignKey = @ForeignKey(name = "fk_menu"), nullable = false)
	private Menu menu;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;

	public Pagina() {

	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pagina(String caminhoArquivo) {
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
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public String getBean() {
		return bean;
	}
	
	public void setBean(String bean) {
		this.bean = bean;
	}
	
	public String getRotulo() {
		return rotulo;
	}
	
	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
	
	public Integer getSequencia() {
		return sequencia;
	}
	
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	
	public EPaginaTipo getPaginaTipo() {
		return paginaTipo;
	}
	
	public void setPaginaTipo(EPaginaTipo paginaTipo) {
		this.paginaTipo = paginaTipo;
	}
	
	@Override
	public String toString() {
		return "Pagina [id=" + id + "]";
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
		Pagina other = (Pagina) obj;
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
		return "SELECT distinct(pg) FROM Pagina pg ";
	}

	@Override
	public String getSqlCount() {
		return "SELECT count(distinct pg.id) FROM Pagina pg ";
	}

	@Override
	public String getObjetoRetorno() {
		return "pg";
	}

	@Override
	public String getJoin() {
		return "LEFT JOIN FETCH pg.paginaNivel pn "
			 + "LEFT JOIN FETCH pn.nivel nv "
			 + "JOIN FETCH pg.menu mn ";
	}
}