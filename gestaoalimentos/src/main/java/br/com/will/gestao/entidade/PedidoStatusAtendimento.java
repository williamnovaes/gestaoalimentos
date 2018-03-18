package br.com.will.gestao.entidade;

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
import javax.validation.constraints.NotNull;

import br.com.will.gestao.componente.Paginavel;

@Entity
@Table(name = "pedido_status_atendimento", schema = "gestao")
public class PedidoStatusAtendimento
		implements Serializable, Paginavel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pedidoStatusAtendimentoSeq", sequenceName = "pedido_status_atendimento_id_multi_seq", 
	allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pedidoStatusAtendimentoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_pedido", foreignKey = @ForeignKey(name = "fk_pedido"))
	private Pedido pedido;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_status_atendimento", foreignKey = @ForeignKey(name = "fk_statusAtendimento"))
	private StatusAtendimento statusAtendimento;
	
	public PedidoStatusAtendimento() {
	}

	public PedidoStatusAtendimento(Pedido pedido, StatusAtendimento statusAtendimento) {
		this.pedido = pedido;
		this.statusAtendimento = statusAtendimento;
	}

	public Integer getId() {
		return id;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public StatusAtendimento getStatusAtendimento() {
		return statusAtendimento;
	}
	
	public void setStatusAtendimento(StatusAtendimento statusAtendimento) {
		this.statusAtendimento = statusAtendimento;
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
		PedidoStatusAtendimento other = (PedidoStatusAtendimento) obj;
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
		return "PedidoStatusAtendimento [id=" + id + "]";
	}

	@Override
	public String getSqlSelect() {
		return " SELECT count(ps.id) FROM PedidoStatusAtendimento pa ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(ps.id) FROM PedidoStatusAtendimento ps ";
	}

	@Override
	public String getObjetoRetorno() {
		return " ps ";
	}

	@Override
	public String getJoin() {
		StringBuilder sb = new StringBuilder();
		sb.append(" JOIN FETCH ps.pedido pd ");
		sb.append(" JOIN FETCH ps.statusAtendimento sa ");
		return sb.toString();
	}

	public static String cabecalhoExportacao() {
		return "";
	}
}