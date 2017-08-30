package br.com.will.gestao.servico.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class IngressoPromocaoDuplicadoException extends Exception {

	private static final long serialVersionUID = 1L;

	public IngressoPromocaoDuplicadoException(String mensagem) {
		super(mensagem);
	}
}