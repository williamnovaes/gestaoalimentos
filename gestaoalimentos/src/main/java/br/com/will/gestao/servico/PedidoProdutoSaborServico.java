package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.PedidoProdutoSaborDAO;
import br.com.will.gestao.entidade.PedidoProdutoSabor;

@Stateless
public class PedidoProdutoSaborServico extends BaseServico<PedidoProdutoSabor> {

	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoProdutoSaborDAO pedidoProdutoSaborDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(pedidoProdutoSaborDao);
	}
}