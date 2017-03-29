package br.com.nx.tickets.rest.retorno;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.Pedido;

@XmlType(propOrder = { "codigo_retorno", "mensagem", "ingressos_vendidos", "ingressos_cancelados", "total_vendido",
		"pedido" })
public class IngressoRetornoPedido extends IngressoRetorno {

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "pedido")
	private Pedido pedido;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingressos_vendidos")
	private Integer ingressosVendidos;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingressos_cancelados")
	private Integer ingressosCancelados;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "total_vendido")
	private Double totalVendido;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Integer getIngressosVendidos() {
		return ingressosVendidos;
	}

	public void setIngressosVendidos(Integer ingressosVendidos) {
		this.ingressosVendidos = ingressosVendidos;
	}

	public Double getTotalVendido() {
		return totalVendido;
	}

	public void setTotalVendido(Double totalVendido) {
		this.totalVendido = totalVendido;
	}

	public Integer getIngressosCancelados() {
		return ingressosCancelados;
	}

	public void setIngressosCancelados(Integer ingressosCancelados) {
		this.ingressosCancelados = ingressosCancelados;
	}

	@Override
	public String toString() {
		return "IngressoRetornoPedido [pedido=" + pedido + ", ingressosVendidos=" + ingressosVendidos
				+ ", ingressosCancelados=" + ingressosCancelados + ", totalVendido=" + totalVendido + "]";
	}
}
