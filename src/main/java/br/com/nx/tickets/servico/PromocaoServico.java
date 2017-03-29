package br.com.nx.tickets.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.BaseDAOException;
import br.com.nx.tickets.dao.PromocaoDAO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class PromocaoServico extends BaseServico<Promocao> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PromocaoDAO promocaoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(promocaoDao);
	}
	
	public List<Promocao> obterPorUsuario(Integer idUsuario) throws BaseServicoException {
		try {
			return promocaoDao.consultarCompletoPorUsuario(idUsuario);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Promocao obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return promocaoDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Promocao obterPorEvento(Evento evento) throws BaseServicoException {
		try {
			return promocaoDao.consultarPorEvento(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}