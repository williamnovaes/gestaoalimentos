package br.com.will.gestao.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.BaseDAOException;
import br.com.will.gestao.dao.PromocaoDAO;
import br.com.will.gestao.entidade.Promocao;
import br.com.will.gestao.servico.exception.BaseServicoException;

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
}