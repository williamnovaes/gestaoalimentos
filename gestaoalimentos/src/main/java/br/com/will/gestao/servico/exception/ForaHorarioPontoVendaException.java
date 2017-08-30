package br.com.will.gestao.servico.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ForaHorarioPontoVendaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ForaHorarioPontoVendaException(String mensagem) {
		super(mensagem);
	}
}