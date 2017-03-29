package br.com.nx.tickets.rest.retorno;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.Ingresso;

public class IngressoRetornoValidacao extends IngressoRetorno {
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingresso")
	private Ingresso ingresso;
	
	@XmlElement(name = "cor")
	private String cor;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "portaria_validacao")
	private String portariaValidacao;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "data_validacao")
	private String dataValidacao;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingresso_tipo")
	private String ingressoTipo;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "codigo_socio_torcedor")
	private String codigoSocioTorcedor;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "observacao")
	private String observacao;

	public Ingresso getIngresso() {
		return ingresso;
	}

	public void setIngresso(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
	public String getCor() {
		return cor;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public String getPortariaValidacao() {
		return portariaValidacao;
	}
	
	public void setPortariaValidacao(String portariaValidacao) {
		this.portariaValidacao = portariaValidacao;
	}
	
	public String getDataValidacao() {
		return dataValidacao;
	}
	public void setDataValidacao(String dataValidacao) {
		this.dataValidacao = dataValidacao;
	}
	
	public String getIngressoTipo() {
		return ingressoTipo;
	}
	
	public void setIngressoTipo(String ingressoTipo) {
		this.ingressoTipo = ingressoTipo;
	}
	
	public String getCodigoSocioTorcedor() {
		return codigoSocioTorcedor;
	}
	
	public void setCodigoSocioTorcedor(String codigoSocioTorcedor) {
		this.codigoSocioTorcedor = codigoSocioTorcedor;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public String toString() {
		return "IngressoRetornoValidacao [ingresso=" + ingresso + ", cor=" + cor + ", portariaValidacao="
				+ portariaValidacao + ", dataValidacao=" + dataValidacao + ", ingressoTipo=" + ingressoTipo
				+ ", codigoSocioTorcedor=" + codigoSocioTorcedor + ", observacao=" + observacao + "]";
	}
}
