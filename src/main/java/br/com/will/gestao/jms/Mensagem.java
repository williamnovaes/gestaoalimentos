package br.com.will.gestao.jms;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Mensagem implements Serializable {
	
	private static final long serialVersionUID = 7965364831663576968L;
	
	private List<String> destinatarios;
	private String conteudo;
	private String assunto;
	private List<File> anexos;
	private List<String> destinatarioCC;
	private List<String> replyTo;
	
	public List<String> getDestinatarios() {
		return destinatarios;
	}
	
	public void setDestinatarios(List<String> destinatarios) {
		this.destinatarios = destinatarios;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	public List<File> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<File> anexos) {
		this.anexos = anexos;
	}

	public List<String> getReplyTo() {
		return replyTo;
	}
	
	public void setReplyTo(List<String> replyTo) {
		this.replyTo = replyTo;
	}
	
	public List<String> getDestinatarioCC() {
		return destinatarioCC;
	}
	
	public void setDestinatarioCC(List<String> destinatarioCC) {
		this.destinatarioCC = destinatarioCC;
	}
	
}
