package br.com.nx.tickets.rest.envio;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.Pedido;

public class IngressoEnvioSincronizacaoAplicativo extends IngressoEnvio {

	@XmlElement(name = "guiche_id")
	private Integer idGuiche;
	
	@XmlElement(name = "evento_id")
	private Integer idEvento;
	
	@XmlElement(name = "pedidos")
	private List<Pedido> pedidos;
	
	@XmlElement(name = "ingressos_validacao")
	private List<Ingresso> ingressosValidacao;
	
	@XmlElement(name = "ingressos_cancelados")
	private List<Ingresso> ingressosCancelados;

	public IngressoEnvioSincronizacaoAplicativo() {
	}

	public Integer getIdGuiche() {
		return idGuiche;
	}

	public void setIdGuiche(Integer idGuiche) {
		this.idGuiche = idGuiche;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Integer getIdEvento() {
		return idEvento;
	}
	
	public List<Ingresso> getIngressosValidacao() {
		return ingressosValidacao;
	}
	
	public void setIngressosValidacao(List<Ingresso> ingressosValidacao) {
		this.ingressosValidacao = ingressosValidacao;
	}
	
	public List<Ingresso> getIngressosCancelados() {
		return ingressosCancelados;
	}
	
	public void setIngressosCancelados(List<Ingresso> ingressosCancelados) {
		this.ingressosCancelados = ingressosCancelados;
	}

	@Override
	public String toString() {
		return "IngressoEnvioSincronizacaoAplicativo [idGuiche=" + idGuiche + ", idEvento=" + idEvento + ", pedidos="
				+ pedidos + ", ingressosValidacao=" + ingressosValidacao + "]";
	}
}