package br.com.nx.tickets.servico.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class SaldoIndisponivelException extends Exception {

	private static final long serialVersionUID = 1L;

	public SaldoIndisponivelException(String mensagem) {
		super(mensagem);
	}
}