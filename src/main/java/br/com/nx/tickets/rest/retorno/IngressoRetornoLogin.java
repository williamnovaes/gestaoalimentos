package br.com.nx.tickets.rest.retorno;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.EBoolean;

public class IngressoRetornoLogin extends IngressoRetorno {

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "usuario")
	private Usuario usuario;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "guiche")
	private Guiche guiche;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "portaria")
	private Portaria portaria;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "portarias")
	private List<Portaria> portarias;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "eventos")
	private List<Evento> eventos;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "token")
	private String token;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "enviar_impressao")
	private EBoolean enviarImpressao;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Guiche getGuiche() {
		return guiche;
	}
	
	public void setGuiche(Guiche guiche) {
		this.guiche = guiche;
	}

	public List<Portaria> getPortarias() {
		return portarias;
	}

	public void setPortarias(List<Portaria> portarias) {
		this.portarias = portarias;
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
	public Portaria getPortaria() {
		return portaria;
	}
	
	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
	}
	
	public EBoolean getEnviarImpressao() {
		return enviarImpressao;
	}
	
	public void setEnviarImpressao(EBoolean enviarImpressao) {
		this.enviarImpressao = enviarImpressao;
	}

	@Override
	public String toString() {
		return "IngressoRetornoLogin [usuario=" + usuario + ", guiche=" + guiche + ", portaria=" + portaria
				+ ", portarias=" + portarias + ", eventos=" + eventos + ", token=" + token + ", enviarImpressao="
				+ enviarImpressao + "]";
	}
}