package br.com.nx.tickets.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.Retirada;

@XmlRootElement
public class ExtratoRetiradaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private BigInteger quantidade = new BigInteger("0");
	
	@XmlElement(name = "total")
	private BigDecimal total = new BigDecimal(0);
	
	@XmlElement(name = "total_taxa_administrativa")
	private BigDecimal totalTaxaAdministrativa = new BigDecimal(0);
	
	@XmlElement(name = "saldo_retirada")
	private BigDecimal saldoRetirada = new BigDecimal(0);
	
	@XmlElement(name = "saldo_disponivel")
	private BigDecimal saldoDisponivel = new BigDecimal(0);

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "retiradas")
	private List<Retirada> retiradas;
	
	public ExtratoRetiradaDTO() {
	}
	
	public ExtratoRetiradaDTO(Object[] objects) {
		int coluna = 0;
		this.quantidade				    = ((BigInteger) objects[coluna++]);
		this.total    	     			= ((BigDecimal) objects[coluna++]);
		this.totalTaxaAdministrativa    = ((BigDecimal) objects[coluna++]);
	}

	public BigDecimal getTotal() {
		return total;
	}
	
	public BigDecimal getTotalTaxaAdministrativa() {
		return totalTaxaAdministrativa;
	}
	
	public BigDecimal getSaldoRetirada() {
		return saldoRetirada;
	}
	
	public BigDecimal getSaldoDisponivel() {
		return saldoDisponivel;
	}
	
	public List<Retirada> getRetiradas() {
		return retiradas;
	}
	
	public void setRetiradas(List<Retirada> retiradas) {
		this.retiradas 	   = retiradas;
		this.saldoRetirada = new BigDecimal(0);
		for (Retirada retirada : retiradas) {
			this.saldoRetirada = saldoRetirada.add(retirada.getValor());
		}
		if (total != null) {
			this.saldoDisponivel = total.add(totalTaxaAdministrativa).subtract(saldoRetirada);
		}
	}

	@Override
	public String toString() {
		return "ExtratoRetiradaDTO [quantidade=" + quantidade + ", total=" + total + ", totalTaxaAdministrativa="
				+ totalTaxaAdministrativa + ", saldoRetirada=" + saldoRetirada + ", saldoDisponivel=" + saldoDisponivel
				+ ", retiradas=" + retiradas + "]";
	}
}