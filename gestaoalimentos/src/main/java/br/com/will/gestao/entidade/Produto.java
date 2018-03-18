package br.com.will.gestao.entidade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.util.SistemaConstantes;
import br.com.will.gestao.util.Util;

@Entity
@Table(name = "produto", schema = "gestao")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Produto implements SituacaoAlteravel, Paginavel, Comparable<Produto> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "produtoSeq", sequenceName = "produto_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "produtoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@Column(name = "nome", length = SistemaConstantes.DESCRICAO, nullable = false)
	private String nome;
	
	@NotNull
	@Column(name = "sequencia", nullable = false)
	private Integer sequencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto_tipo", foreignKey = @ForeignKey(name = "fk_produto_tipo"))
	private ProdutoTipo produtoTipo;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = SistemaConstantes.E_SITUACAO_DEFAULT_ATIVO)
	private ESituacao situacao = ESituacao.ATIVO;
	
	@Column(name = "preco", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal preco;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "permite_sabores", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean permiteSabores = EBoolean.FALSE;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tamanho", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean tamanho = EBoolean.FALSE;
	
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
	private List<Tamanho> tamanhos;
	
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
	private List<Sabor> sabores;
	
	@XmlTransient
	@Transient
	private String observacao;
	
	@XmlTransient
	@Transient
	private List<Sabor> saboresSelecionados;
	
	@XmlTransient
	@Transient
	private Tamanho tamanhoSelecionado;
	
	public Produto() {
	}
	
	public Produto(Integer id, String nome) {
		this.id = id;
	}
	
	public Produto(Integer id) {
		this.id = id;
	}

	public Produto(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return this.id;
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
	
	public Integer getSequencia() {
		return sequencia;
	}
	
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	
	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}

	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}

	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}
	
	public BigDecimal getPreco() {
		if (preco == null) {
			return new BigDecimal(0);
		}
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public EBoolean getPermiteSaboresEBool() {
		return permiteSabores;
	}
	
	public void setPermiteSaboresEBool(EBoolean ps) {
		this.permiteSabores = ps;
	}
	
	@Override
	public void alterarSituacao() {
		this.situacao = Util.alteraSituacao(this.situacao);
	}
	
	@Override
	public ESituacao getSituacao() {
		return situacao;
	}
	
	public List<Tamanho> getTamanhos() {
		return tamanhos;
	}
	
	public void setTamanhos(List<Tamanho> tamanhos) {
		this.tamanhos = tamanhos;
	}
	
	public List<Sabor> getSabores() {
		if (this.sabores == null) {
			this.sabores = new ArrayList<>();
		}
		return sabores;
	}
	
	public void setSabores(List<Sabor> sabores) {
		this.sabores = sabores;
	}
	
	public boolean isPermiteSabores() {
		return Util.converterENumBooleanToBoolean(this.permiteSabores);
	}
	
	public void permiteSabores(boolean permite) {
		this.permiteSabores = Util.converterBooleanToEnumBoolean(permite);
	}
	
	public EBoolean getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(EBoolean tamanho) {
		this.tamanho = tamanho;
	}
	
	public boolean isTamanho() {
		return Util.converterENumBooleanToBoolean(this.tamanho);
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public List<Sabor> getSaboresSelecionados() {
		return saboresSelecionados;
	}
	
	public void setSaboresSelecionados(List<Sabor> saboresSelecionados) {
		this.saboresSelecionados = saboresSelecionados;
	}
	
	public Tamanho getTamanhoSelecionado() {
		return tamanhoSelecionado;
	}
	
	public void setTamanhoSelecionado(Tamanho tamanhoSelecionado) {
		this.tamanhoSelecionado = tamanhoSelecionado;
	}
	
	public void adicionarSabor(Sabor sabor) {
		if (saboresSelecionados == null) {
			saboresSelecionados = new ArrayList<>();
		}
		saboresSelecionados.add(sabor);
	}
	
	public boolean isAdiconaSabor() {
		return tamanhoSelecionado != null && (tamanhoSelecionado.getLimiteSabores() > saboresSelecionados.size());
	}
	
	public Integer getLimiteSabores() {
		return getTamanhoSelecionado().getLimiteSabores();
	}
	
	public Integer getLimiteSaboresDisponivel() {
		return getTamanhoSelecionado().getLimiteSabores() - getSaboresSelecionados().size();
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
		Produto other = (Produto) obj;
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
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(" Produto [id=").append(id).append(", nome=").append(nome).append("] ");
		return str.toString();
	}

	@Override
	@XmlTransient
	public String getSqlSelect() {
		return " SELECT distinct(p) FROM Produto p ";
	}

	@Override
	@XmlTransient
	public String getSqlCount() {
		return " SELECT count(distinct p) FROM Produto p ";
	}

	@Override
	@XmlTransient
	public String getObjetoRetorno() {
		return " p ";
	}

	@Override
	@XmlTransient
	public String getJoin() {
		return " JOIN FETCH p.produtoTipo pt ";
	}
	
	public static final Comparator<Produto> COMPARAR_POR_NOME = new Comparator<Produto>() {
		@Override
		public int compare(Produto o1, Produto o2) {
			if (o1.getNome().compareTo(o2.getNome()) > 0) {
				return 1;
			} else if (o1.getNome().compareTo(o2.getNome()) < 0) {
				return -1;
			}
			return 0;
		}
	};

	@Override
	public int compareTo(Produto o) {
		return this.sequencia.compareTo(o.getSequencia());
	}
}