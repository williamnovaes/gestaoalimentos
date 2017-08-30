package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.BaseDAOException;
import br.com.will.gestao.dao.PedidoDAO;
import br.com.will.gestao.entidade.Pedido;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class PedidoServico extends BaseServico<Pedido> {

	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoDAO pedidoDao;
	@EJB
	private PromocaoServico promocaoServico;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(pedidoDao);
	}
	
	public Pedido salvar(Usuario usuario, Pedido pedido) throws BaseServicoException {
		try {
			pedido.anularId();
			pedido = super.salvar(pedido);
			return pedido;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Pedido obterPedidoCompletoPorId(Long id) throws BaseServicoException {
		try {
			return pedidoDao.consultarCompletoPorId(id);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
}