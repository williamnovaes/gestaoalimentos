package br.com.will.gestao.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.entidade.Tamanho;

public class ProdutoPedidoDTO {
	private Produto produto;
	private List<Sabor> sabores;
	private Tamanho tamanho;
	private Integer quantidade;
	private String observacao;
	private BigDecimal preco;
	
	public ProdutoPedidoDTO() {}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Sabor> getSabores() {
		return sabores;
	}

	public void setSabores(List<Sabor> sabores) {
		this.sabores = sabores;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public String getLabelProdutos() {
		StringBuilder str = new StringBuilder();
		for (Sabor sabor : sabores) {
			str.append(sabor.getDescricao());
			if (sabores.indexOf(sabor) != (sabores.size() - 1)) {
				str.append(" / ");
			}
		}
		str.append(" (").append(sabores.size());
		str.append("/").append(tamanho.getLimiteSabores()).append(")");
		return str.toString();
	}
}