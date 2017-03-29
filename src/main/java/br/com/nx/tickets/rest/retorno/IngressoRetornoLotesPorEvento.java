package br.com.nx.tickets.rest.retorno;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Portaria;

@XmlType(propOrder = { "codigo_retorno", "mensagem", "ingressos_cancelados", "ingressos_vendidos", "total_vendido",
		"versao_lote", "lotes", "portarias" })
public class IngressoRetornoLotesPorEvento extends IngressoRetorno {

	@XmlElement(name = "lotes")
	private List<Lote> lotes;

	@XmlElement(name = "portarias")
	private List<Portaria> portarias;

	@XmlElement(name = "ingressos_cancelados")
	private Integer ingressosCancelados;

	@XmlElement(name = "ingressos_vendidos")
	private Integer ingressosVendidos;

	@XmlElement(name = "total_vendido")
	private BigDecimal totalVendido;

	@XmlElement(name = "versao_lote")
	private float versaoLote;

	public void setVersaoLote(float versaoLote) {
		this.versaoLote = versaoLote;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}

	public List<Portaria> getPortarias() {
		return portarias;
	}

	public void setPortarias(List<Portaria> portarias) {
		this.portarias = portarias;
	}

	public Integer getIngressosVendidos() {
		return ingressosVendidos;
	}

	public void setIngressosVendidos(Integer ingressosVendidos) {
		this.ingressosVendidos = ingressosVendidos;
	}

	public BigDecimal getTotalVendido() {
		return totalVendido;
	}

	public void setTotalVendido(BigDecimal totalVendido) {
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
		return "IngressoRetornoLotesPorEvento [lotes=" + lotes + ", portarias=" + portarias + ", ingressosVendidos="
				+ ingressosVendidos + ", versaoLote=" + versaoLote + "]";
	}
}
