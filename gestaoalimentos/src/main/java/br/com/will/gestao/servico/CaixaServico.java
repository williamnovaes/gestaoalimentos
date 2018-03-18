package br.com.will.gestao.servico;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.CaixaDAO;
import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class CaixaServico extends BaseServico<Caixa> {

	private static final long serialVersionUID = 1L;
	@Inject
	private CaixaDAO caixaDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(caixaDao);
	}

	public Caixa obterPorData(Calendar dataInicioDia) throws BaseServicoException {
		try {
			return caixaDao.consultarPorData(dataInicioDia);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void abrirCaixa(Caixa caixa) throws BaseServicoException {
		try {
			caixaDao.abrirCaixa(caixa);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Caixa obterCaixaAberto() throws BaseServicoException {
		try {
			return caixaDao.consultarCaixaAberto();
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Caixa obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return caixaDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
