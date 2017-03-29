package br.com.nx.tickets.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.util.SistemaConstantes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "nome", "ingresso_tipo", 
		"quantidade", "valor_total", "valor_total_taxa_administrativa", 
		"data_inicio_venda_ingresso", "data_fim_venda_ingresso",
		"total", "total_retirada"})
public class ExtratoGuicheDTO implements Serializable, Comparable<ExtratoGuicheDTO> {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private Integer id;

	@XmlElement(name = "nome")
	private String nome;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingresso_tipo")
	private String ingressoTipo;

	@XmlElement(name = "quantidade")
	private Integer quantidade = 0;

	@XmlElement(name = "valor_total")
	private BigDecimal valorTotal = new BigDecimal(0);

	@XmlElement(name = "valor_total_taxa_administrativa")
	private BigDecimal valorTotalTaxaAdministrativa = new BigDecimal(0);

	@XmlElement(name = "total")
	private BigDecimal total = new BigDecimal(0);
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "total_retirada")
	private BigDecimal totalRetirada;

	public ExtratoGuicheDTO() {
	}

	public ExtratoGuicheDTO(Object[] objects) {
		int coluna = 0;
		if (objects.length == SistemaConstantes.SEIS) {
			this.id 				= ((Integer) objects[coluna++]);
			this.nome 				= (String) objects[coluna++];
			this.ingressoTipo 		= (String) objects[coluna++];
			this.quantidade 		= ((BigInteger) objects[coluna++]).intValue();
			this.valorTotal 		= ((BigDecimal) objects[coluna++]);
			this.valorTotalTaxaAdministrativa = ((BigDecimal) objects[coluna++]);
			this.total 				= valorTotal.add(valorTotalTaxaAdministrativa);
		} else {
			this.id 				= ((Integer) objects[coluna++]);
			this.nome 				= (String) objects[coluna++];
			this.quantidade 		= ((BigInteger) objects[coluna++]).intValue();
			this.valorTotal 		= ((BigDecimal) objects[coluna++]);
			this.valorTotalTaxaAdministrativa = ((BigDecimal) objects[coluna++]);
			this.total 				= valorTotal.add(valorTotalTaxaAdministrativa);
		}
	}

	public ExtratoGuicheDTO(Long quantidade, BigDecimal total) {
		this.quantidade = quantidade.intValue();
		this.total	    = total;
	}
	
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorTotalTaxaAdministrativa() {
		return valorTotalTaxaAdministrativa;
	}

	public void setValorTotalTaxaAdministrativa(BigDecimal valorTotalTaxaAdministrativa) {
		this.valorTotalTaxaAdministrativa = valorTotalTaxaAdministrativa;
	}

	public String getIngressoTipo() {
		return ingressoTipo;
	}

	public void setIngressoTipo(String ingressoTipo) {
		this.ingressoTipo = ingressoTipo;
	}

	public BigDecimal getTotal() {
		if (total == null) {
			return new BigDecimal("0");
		}
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public void setTotalRetirada(BigDecimal totalRetirada) {
		this.totalRetirada = totalRetirada;
	}
	
	public BigDecimal getTotalRetirada() {
		if (totalRetirada == null) {
			return new BigDecimal("0");
		}
		return totalRetirada;
	}

	@Override
	public String toString() {
		return "ExtratoGuicheDTO [id=" + id + ", nome=" + nome + ", ingressoTipo=" + ingressoTipo + ", quantidade="
				+ quantidade + ", valorTotal=" + valorTotal + ", valorTotalTaxaAdministrativa="
				+ valorTotalTaxaAdministrativa + ", total=" + total + ", totalRetirada=" + totalRetirada + "]";
	}

	@Override
	public int compareTo(ExtratoGuicheDTO o) {
		return this.nome.compareTo(o.nome);
	}
}