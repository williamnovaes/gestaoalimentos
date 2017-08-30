package br.com.will.gestao.servico;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.BaseDAOException;
import br.com.will.gestao.dao.InterfaceDAO;
import br.com.will.gestao.entidade.util.BuscavelPorPaginacao;
import br.com.will.gestao.entidade.util.Descritivel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;
import br.com.will.gestao.servico.exception.BaseServicoException;

public abstract class BaseServico<T> implements InterfaceServico<T>, BuscavelPorPaginacao {

	private static final long serialVersionUID = 1L;

	private InterfaceDAO<T> dao;
	@Inject
    private transient Logger log;

	protected abstract void inicializar();

	@Override
	public int obterQuantidade() throws BaseServicoException {
		try {
			return dao.consultarQuantidade();
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());	
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseServicoException(ex.getMessage());
		}
	}

	@Override
	public T salvar(T t) throws BaseServicoException {
		try {
			 T f = dao.salvar(t);
			 return f;
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());	
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BaseServicoException("Erro ao salvar: " + t + ex.getMessage());
		}
	}

	@Override
	public T alterar(T t) throws BaseServicoException {
		try {
			return dao.alterar(t);
		} catch (BaseDAOException bde) {
			throw new BaseServicoException(bde.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	@Override
	public void deletar(T t, Integer id) throws BaseServicoException {
		try {
			dao.deletar(t, id);
		} catch (BaseDAOException e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	@Override
	public List<T> obterTodos() throws BaseServicoException {
		try {
			return dao.consultarTodos();
		} catch (BaseDAOException e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	@Override
	public List<T> obterTodos(String coluna) throws BaseServicoException {
		try {
			return dao.consultarTodos(coluna);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	@Override
	public T obterPorId(Integer id) throws BaseServicoException {
		try {
			return dao.consultarPorId(id);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	@Override
	public <K extends Descritivel> T obterPorDescricao(String descricao) throws BaseServicoException {
		try {
			return dao.consultarPorDescricao(descricao);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	@Override
	public List<? extends SituacaoAlteravel> obterPorSituacao(ESituacao situacao) throws BaseServicoException {
		try {
			return dao.consultarPorSituacao(situacao);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	@Override
	public Paginador<Paginavel> obterPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) throws BaseServicoException {
		try {
			return dao.consultarPorFiltro(paginador, filtravel);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	protected InterfaceDAO<T> getDao() {
		return dao;
	}

	protected void setDao(InterfaceDAO<T> dao) {
		this.dao = dao;
	}
	
	public Logger getLog() {
		return log;
	}
}
