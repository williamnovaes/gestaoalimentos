package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.PedidoProdutoDAO;
import br.com.will.gestao.entidade.PedidoProduto;

@Stateless
public class PedidoProdutoServico extends BaseServico<PedidoProduto> {

	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoProdutoDAO pedidoProdutoDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(pedidoProdutoDao);
	}
}