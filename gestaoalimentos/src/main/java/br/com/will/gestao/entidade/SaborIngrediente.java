package br.com.will.gestao.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "sabor_ingrediente", schema = "gestao")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class SaborIngrediente implements Serializable, Paginavel, Exportavel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SaborIngredientePK id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_sabor", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_sabor"))
	private Sabor sabor;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ingrediente", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_pedido"))
	private Ingrediente ingrediente;
	
	@Column(name = "observacao", length = SistemaConstantes.DUZENTOS_CINQUENTA)
	private String observacao;
	
	@Column(name = "quantidade")
	private Integer quantidade;


	public SaborIngrediente() {
	}

	public SaborIngrediente(Sabor sabor, Ingrediente ingrediente) {
		this.id = new SaborIngredientePK(sabor.getId(), ingrediente.getId());
		this.sabor = sabor;
		this.ingrediente = ingrediente;
		this.quantidade = ingrediente.getQuantidade();
	}

	public SaborIngredientePK getId() {
		return this.id;
	}

	public Sabor getSabor() {
		return sabor;
	}

	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}
	
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	
	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
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
		SaborIngrediente other = (SaborIngrediente) obj;
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
		return "SaborIngrediente [id=" + id + "]";
	}

	@Override
	public String getSqlSelect() {
		return " ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(si.id) FROM SaborIngrediente si ";
	}

	@Override
	public String getObjetoRetorno() {
		return " si ";
	}

	@Override
	public String getJoin() {
		StringBuilder sb = new StringBuilder();
		sb.append(" JOIN si.sabor sb ");
		sb.append(" JOIN si.ingrediente i ");
		return sb.toString();
	}

	public static String cabecalhoExportacao() {
		return "";
	}

	@Override
	public String exportar() {
		StringBuilder retorno = new StringBuilder();
		return retorno.toString();
	}
}