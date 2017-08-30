package br.com.will.gestao.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.BaseDAOException;
import br.com.will.gestao.dao.EnderecoDAO;
import br.com.will.gestao.entidade.Endereco;
import br.com.will.gestao.servico.exception.BaseServicoException;


@Stateless
public class EnderecoServico extends BaseServico<Endereco> {

	private static final long serialVersionUID = 1L;
	@Inject
	private EnderecoDAO enderecoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(enderecoDao);
	}

	public Endereco obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return enderecoDao.obterCompletoPorId(id);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
}