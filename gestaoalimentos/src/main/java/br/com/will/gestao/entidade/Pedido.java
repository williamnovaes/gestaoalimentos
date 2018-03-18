
package br.com.will.gestao.entidade;

import java.math.BigDecimal;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.util.SistemaConstantes;

@Entity
@Table(name = "pedido", schema = "gestao")
public class Pedido implements Paginavel {

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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_forma_pagamento", foreignKey = @ForeignKey(name = "fk_forma_pagamento"))
	private FormaPagamento formaPagamento;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_modo_entrega", foreignKey = @ForeignKey(name = "fk_modo_entrega"))
	private ModoEntrega modoEntrega;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_cliente", foreignKey = @ForeignKey(name = "fk_cliente"))
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_caixa", foreignKey = @ForeignKey(name = "fk_caixa"))
	private Caixa caixa;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_ultimo_status", foreignKey = @ForeignKey(name = "fk_ultimo_status"))
	private StatusAtendimento ultimoStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "cpf_nota_fiscal", columnDefinition = SistemaConstantes.E_BOOLEAN_DEFAULT_FALSE)
	private EBoolean cpfNotaFiscal;
	
	@Column(name = "cpf_cnpj", length = SistemaConstantes.QUINZE)
	private String cpfCnpj;
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<PedidoProduto> pedidosProdutos;
	

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

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	
	public void setFormaPagamento(FormaPagamento formaPagamento) {
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
	
	public Caixa getCaixa() {
		return caixa;
	}
	
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	
	public StatusAtendimento getUltimoStatus() {
		return ultimoStatus;
	}
	
	public void setUltimoStatus(StatusAtendimento ultimoStatus) {
		this.ultimoStatus = ultimoStatus;
	}
	
	public Usuario getCliente() {
		return cliente;
	}
	
	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	
	public EBoolean getCpfNotaFiscal() {
		return cpfNotaFiscal;
	}
	
	public void setCpfNotaFiscal(EBoolean cpfNotaFiscal) {
		this.cpfNotaFiscal = cpfNotaFiscal;
	}
	
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	
	public List<PedidoProduto> getPedidosProdutos() {
		return pedidosProdutos;
	}
	
	public void setPedidosProdutos(List<PedidoProduto> pedidosProdutos) {
		this.pedidosProdutos = pedidosProdutos;
	}
	
	public ModoEntrega getModoEntrega() {
		return modoEntrega;
	}
	
	public void setModoEntrega(ModoEntrega modoEntrega) {
		this.modoEntrega = modoEntrega;
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
		StringBuilder str = new StringBuilder();
		str.append(" JOIN FETCH pd.formaPagamento fp ");
		str.append(" JOIN FETCH pd.modoEntrega me ");
		str.append(" JOIN FETCH pd.ultimoStatus sa ");
		str.append(" JOIN FETCH pd.cliente cl ");
		str.append(" JOIN pd.caixa cx ");
		
		return str.toString();
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