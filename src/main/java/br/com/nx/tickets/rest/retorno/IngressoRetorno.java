package br.com.nx.tickets.rest.retorno;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.nx.tickets.rest.ECodigoRetorno;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class IngressoRetorno implements IngressoRetornavel {

	@XmlElement(name = "mensagem")
	private String mensagem;
	@XmlElement(name = "codigo_retorno")
	private String codigoRetorno = ECodigoRetorno.OK.getCodigo();
	
	public IngressoRetorno() {
		
	}
	
	public IngressoRetorno(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@Override
	public String getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(ECodigoRetorno codigoRetorno) {
		this.codigoRetorno = codigoRetorno.getCodigo();
	}
}
