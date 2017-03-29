package br.com.nx.tickets.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Pedido;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id_pedido", "valor_total", "valor_total_taxa_administrativa", "quantidade", "forma_pagamento",
		"extrato_ingressos" })
public class ExtratoPedidoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "id_pedido")
	private BigInteger idPedido;

	@XmlElement(name = "valor_total")
	private BigDecimal valorTotal = new BigDecimal(0);

	@XmlElement(name = "valor_total_taxa_administrativa")
	private BigDecimal valorTotalTaxaAdministrativa = new BigDecimal(0);

	@XmlElement(name = "quantidade")
	private Integer quantidade;

	@XmlElement(name = "extrato_ingressos")
	private List<ExtratoIngressoDTO> extratoIngressos;

	@XmlElement(name = "forma_pagamento")
	private String formaPagamento;

	@XmlTransient
	private HashMap<Lote, Integer> quantidadePorLote;

	public ExtratoPedidoDTO(Pedido pedido) {
		this.idPedido = pedido.getId();
		this.formaPagamento = pedido.getPagamentoTipo().getLabel();
	}

	public ExtratoPedidoDTO(Pedido pedido, BigDecimal valorTotal, BigDecimal valorTotalTaxaAdministrativa) {
		this.idPedido = pedido.getId();
		this.formaPagamento = pedido.getPagamentoTipo().getLabel();
		this.valorTotal = valorTotal;
		this.valorTotalTaxaAdministrativa = valorTotalTaxaAdministrativa;
		this.quantidade = pedido.getIngressos().size();
	}

	public ExtratoPedidoDTO() {
		quantidadePorLote = new HashMap<>();
	}

	public List<ExtratoIngressoDTO> getExtratoIngressos() {
		return extratoIngressos;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public BigDecimal getValorTotalTaxaAdministrativa() {
		return valorTotalTaxaAdministrativa;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public BigDecimal getTotalPedido() {
		return valorTotal.add(valorTotalTaxaAdministrativa);
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public void adicionarExtratoIngresso(Lote lote, Integer qtd) {
		if (quantidadePorLote == null) {
			quantidadePorLote = new HashMap<>();
		}
		if (quantidadePorLote.containsKey(lote)) {
			qtd += quantidadePorLote.get(lote);
		}
		quantidadePorLote.put(lote, qtd);
		atualizarIngressos();
	}

	private void atualizarIngressos() {
		Set<Lote> keys = quantidadePorLote.keySet();
		extratoIngressos = new ArrayList<ExtratoIngressoDTO>();
		for (Lote lt : keys) {
			extratoIngressos.add(new ExtratoIngressoDTO(lt, quantidadePorLote.get(lt)));
		}
		this.calcularTotal();
	}

	private void calcularTotal() {
		valorTotal = new BigDecimal(0);
		valorTotalTaxaAdministrativa = new BigDecimal(0);
		quantidade = 0;
		for (ExtratoIngressoDTO extratoIngresso : extratoIngressos) {
			valorTotal = valorTotal.add(extratoIngresso.getValorTotal());
			valorTotalTaxaAdministrativa = valorTotalTaxaAdministrativa.add(extratoIngresso.getValorTotalTaxa());
			quantidade += extratoIngresso.getQuantidade();
		}
	}

	public void removerIngresso(ExtratoIngressoDTO extratoIngressso) {
		quantidadePorLote.remove(extratoIngressso.getLote());
		atualizarIngressos();
	}

	public List<Ingresso> toIngressoList() {
		List<Ingresso> ingressos = new ArrayList<>();
		for (ExtratoIngressoDTO extratoIngresso : extratoIngressos) {
			for (int i = 0; i < extratoIngresso.getQuantidade(); i++) {
				// ingressos.add(new Ingresso(extratoIngresso.getLote()));
			}
		}
		return ingressos;
	}

	@Override
	public String toString() {
		return "ExtratoPedido [idPedido=" + idPedido + ", valorTotal=" + valorTotal + ", valorTotalTaxaAdministrativa="
				+ valorTotalTaxaAdministrativa + ", quantidade=" + quantidade + ", extratoIngressos=" + extratoIngressos
				+ "]";
	}
}