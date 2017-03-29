package br.com.nx.tickets.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.com.nx.tickets.entidade.Lote;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "lote", "valor", "valor_total_taxa_administrativa", "quantidade" })
public class ExtratoIngressoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "lote")
	private Lote lote;

	@XmlElement(name = "valor")
	private BigDecimal valorTotal = new BigDecimal(0);

	@XmlElement(name = "valor_total_taxa_administrativa")
	private BigDecimal valorTotalTaxaAdministrativa = new BigDecimal(0);

	@XmlElement(name = "quantidade")
	private Integer quantidade;

	public ExtratoIngressoDTO(Lote lote, Integer quantidade) {
		this.lote = lote;
		this.quantidade = quantidade;
		this.calcularTotal();
	}

	public Lote getLote() {
		return lote;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public BigDecimal getValorTotalTaxa() {
		return valorTotalTaxaAdministrativa;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	private void calcularTotal() {
		valorTotal = new BigDecimal(0);
		valorTotalTaxaAdministrativa = new BigDecimal(0);
		valorTotal = valorTotal.add(lote.getValor()).multiply(new BigDecimal(quantidade));
		valorTotalTaxaAdministrativa = valorTotalTaxaAdministrativa
				.add(lote.getValorTaxaAdministrativa().multiply(new BigDecimal(quantidade)));
	}

	public BigDecimal getTotal() {
		return valorTotal.add(valorTotalTaxaAdministrativa);

	}

	@Override
	public String toString() {
		return "ExtratoIngresso [lote=" + lote + ", valorTotal=" + valorTotal
				+ ", valorTotalTaxaAdministrativa=" + valorTotalTaxaAdministrativa + ", quantidade=" + quantidade + "]";
	}
}