package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.IngressoTipoDAO;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class IngressoTipoServico extends BaseServico<IngressoTipo> {

	private static final long serialVersionUID = 1L;
	@Inject
	private IngressoTipoDAO ingressoTipoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(ingressoTipoDao);
	}

	public List<IngressoTipo> obterPorCliente(Cliente cliente) throws BaseServicoException {
		try {
			return ingressoTipoDao.consultarPorCliente(cliente);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}