package br.com.nx.tickets.rest.envio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class IngressoEnvio {

    @XmlElement(name = "usuario_id")
    private Integer idUsuario;
    
    @JsonInclude(Include.NON_NULL)
    @XmlElement(name = "token")
    private String token;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    protected void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
    
    public String getToken() {
		return token;
	}
    
    public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "IngressoEnvio [idUsuario=" + idUsuario + ", token=" + token + "]";
	}
}