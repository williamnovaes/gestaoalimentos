package br.com.nx.tickets.rest.retorno;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.rest.DateFormatterAdapter;

@XmlType(propOrder = { "codigo_retorno", "mensagem", "extrato_data", "quantidade", "valor_total",
		"valor_total_taxa_administrativa", "total", "cancelados", "extrato_evento", "extrato_ingresso_tipo" })
public class IngressoRetornoExtratoEvento extends IngressoRetorno {

	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@XmlElement(name = "extrato_data")
	private Calendar dataExtrato;

	@XmlElement(name = "quantidade")
	private Integer quantidade = 0;

	@XmlElement(name = "valor_total")
	private BigDecimal valorTotal = new BigDecimal(0);

	@XmlElement(name = "valor_total_taxa_administrativa")
	private BigDecimal valorTotalTaxaAdministrativa = new BigDecimal(0);

	@XmlElement(name = "total")
	private BigDecimal total = new BigDecimal(0);

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "extrato_evento")
	private List<ExtratoGuicheDTO> extratoEvento;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "cancelados")
	private HashMap<String, List<Ingresso>> cancelados;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "extrato_ingresso_tipo")
	private List<ExtratoGuicheDTO> extratoPorIngressoTipo;

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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<ExtratoGuicheDTO> getExtratoEvento() {
		return extratoEvento;
	}

	public void setExtratoEvento(List<ExtratoGuicheDTO> extratoEvento) {
		this.extratoEvento = extratoEvento;
	}

	public List<ExtratoGuicheDTO> getExtratoPorIngressoTipo() {
		return extratoPorIngressoTipo;
	}

	public void setExtratoPorIngressoTipo(List<ExtratoGuicheDTO> extratoPorIngressoTipo) {
		this.extratoPorIngressoTipo = extratoPorIngressoTipo;
	}

	public void setCancelados(HashMap<String, List<Ingresso>> cancelados) {
		this.cancelados = cancelados;
	}
	
	public void setDataExtrato(Calendar dataExtrato) {
		this.dataExtrato = dataExtrato;
	}

	@Override
	public String toString() {
		return "IngressoRetornoExtratoEvento [quantidade=" + quantidade + ", valorTotal=" + valorTotal
				+ ", valorTotalTaxaAdministrativa=" + valorTotalTaxaAdministrativa + ", total=" + total
				+ ", extratoEvento=" + extratoEvento + ", extratoPorIngressoTipo=" + extratoPorIngressoTipo
				+ ", cancelados=" + cancelados + "]";
	}
}