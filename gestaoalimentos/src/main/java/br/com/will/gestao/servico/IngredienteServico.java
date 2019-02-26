package br.com.will.gestao.servico;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.dao.IngredienteDAO;
import br.com.will.gestao.entidade.Ingrediente;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.entidade.SaborIngrediente;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class IngredienteServico extends BaseServico<Ingrediente> {

	private static final long serialVersionUID = 1L;
	@Inject
	private IngredienteDAO ingredienteDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(ingredienteDao);
	}

	public Ingrediente obterCompletoPorId(Integer id) throws BaseServicoException {
		try {
			return ingredienteDao.consultarCompletoPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Ingrediente> obterAssociadosPorSabor(Sabor sabor) throws BaseServicoException {
		try {
			List<SaborIngrediente> saborIngredientes = ingredienteDao.cconsultarAssociadosPorSabor(sabor);
			List<Ingrediente> ingredientes = new ArrayList<>();
			for (SaborIngrediente si : saborIngredientes) {
				Ingrediente i = si.getIngrediente();
				i.setQuantidade(si.getQuantidade());
				ingredientes.add(i);
			}
			
			return ingredientes;
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
