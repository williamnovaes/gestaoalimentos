package br.com.nx.tickets.rest.retorno;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.entidade.Retirada;

@XmlType(propOrder = { "codigo_retorno", "mensagem", "saldo_retirada", "saldo_disponivel", "retiradas",
		"extrato_guiche", "pedidos" })
public class IngressoRetornoExtratoGuiche extends IngressoRetorno {

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "saldo_retirada")
	private BigDecimal saldoRetirada = new BigDecimal(0);

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "saldo_disponivel")
	private BigDecimal saldoDisponivel = new BigDecimal(0);

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "retiradas")
	private List<Retirada> retiradas;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "extrato_guiche")
	private List<ExtratoGuicheDTO> extratoGuiche;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "pedidos")
	private List<Pedido> pedidos;

	public List<ExtratoGuicheDTO> getExtratoGuiche() {
		return extratoGuiche;
	}

	public void setExtratoGuiche(List<ExtratoGuicheDTO> extratoGuiche) {
		this.extratoGuiche = extratoGuiche;
	}

	public List<Retirada> getRetiradas() {
		return retiradas;
	}

	public void setRetiradas(List<Retirada> retiradas) {
		this.retiradas = retiradas;
		for (Retirada retirada : retiradas) {
			retirada.setGuiche(null);
		}
	}

	public BigDecimal getSaldoDisponivel() {
		return saldoDisponivel;
	}

	public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
		this.saldoDisponivel = saldoDisponivel;
	}

	public void setSaldoRetirada(BigDecimal saldoRetirada) {
		this.saldoRetirada = saldoRetirada;
	}

	public BigDecimal getSaldoRetirada() {
		return saldoRetirada;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public String toString() {
		return "IngressoRetornoExtratoGuiche [saldoRetirada=" + saldoRetirada + ", saldoDisponivel=" + saldoDisponivel
				+ ", retiradas=" + retiradas + ", extratoGuiche=" + extratoGuiche + ", pedidos=" + pedidos + "]";
	}
}