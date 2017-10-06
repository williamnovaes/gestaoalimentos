
package br.com.will.gestao.entidade;

import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.EFormaPagamento;
import br.com.will.gestao.entidade.util.EStatus;
import br.com.will.gestao.entidade.util.ETipoEntrega;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "pedido", schema = "gestao")
public class Pedido implements Paginavel {

//	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pedidoSeq", sequenceName = "pedido_id_multi_seq", allocationSize = 1, schema = "gestao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pedidoSeq")
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false)
	private Calendar dataCadastro;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "pagamento_tipo", columnDefinition = SistemaConstantes.E_PAGAMENTO_DEFAULT_DINHEIRO)
	private EFormaPagamento formaPagamento;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_entrega", columnDefinition = SistemaConstantes.E_PAGAMENTO_DEFAULT_DINHEIRO)
	private ETipoEntrega tipoEntrega;
	
	@NotNull
	@JoinColumn(name = "_cliente", foreignKey = @ForeignKey(name = "fk_cliente"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario cliente;
	
	@NotNull
	@Column(name = "valor_total", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal valorTotal = new BigDecimal("0");
	
	@NotNull
	@Column(name = "valor_cobrado", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal valorCobrado = new BigDecimal("0");
	
	@NotNull
	@Column(name = "valor_desconto", precision = SistemaConstantes.DEZESSETE, scale = SistemaConstantes.DOIS)
	private BigDecimal valorDesconto = new BigDecimal("0");
	
	@NotNull
	@JoinColumn(name = "_caixa", foreignKey = @ForeignKey(name = "fk_caixa"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Caixa caixa;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status_atendimento", columnDefinition = SistemaConstantes.E_STATUS_DEFAULT_PENDENTE)
	private EStatus statusAtendimento;
	

	public Pedido(Integer id) {
		this.id = id;
		this.dataCadastro = Calendar.getInstance();
	}
	
	public Pedido() {
	}
	
	public Integer getId() {
		return id;
	}

	public void anularId() {
		this.id = null;
		this.dataCadastro = null;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	@PrePersist
	protected void setDataCadastro() {
		if (this.dataCadastro == null) {
			this.dataCadastro = Calendar.getInstance();
		}
	}

	public EFormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(EFormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal total) {
		this.valorTotal = total;
	}
	
	public BigDecimal getValorCobrado() {
		return valorCobrado;
	}
	
	public void setValorCobrado(BigDecimal valorCobrado) {
		this.valorCobrado = valorCobrado;
	}
	
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	
	public ETipoEntrega getTipoEntrega() {
		return tipoEntrega;
	}
	
	public void setTipoEntrega(ETipoEntrega tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}
	
	public Caixa getCaixa() {
		return caixa;
	}
	
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	
	public EStatus getStatusAtendimento() {
		return statusAtendimento;
	}
	
	public void setStatusAtendimento(EStatus statusAtendimento) {
		this.statusAtendimento = statusAtendimento;
	}
	
	public Usuario getCliente() {
		return cliente;
	}
	
	public void setCliente(Usuario cliente) {
		this.cliente= cliente;
	}
	
	@Override
	public String getSqlSelect() {
		return " SELECT distinct(pd) FROM Pedido pd ";
	}

	@Override
	public String getSqlCount() {
		return " SELECT count(distinct pd.id) FROM Pedido pd ";
	}

	@Override
	public String getObjetoRetorno() {
		return " pd ";
	}

	@Override
	public String getJoin() {
		return "";
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", formaPagamento=" + formaPagamento  + "]";
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
		Pedido other = (Pedido) obj;
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