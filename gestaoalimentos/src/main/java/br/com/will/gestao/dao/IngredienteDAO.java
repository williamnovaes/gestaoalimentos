package br.com.will.gestao.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.Ingrediente;
import br.com.will.gestao.entidade.Sabor;
import br.com.will.gestao.entidade.SaborIngrediente;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class IngredienteDAO extends BaseDAO<Ingrediente> {

	private static final long serialVersionUID = 1L;

	public IngredienteDAO() {
		super(Ingrediente.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(Ingrediente t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Ingrediente.class, getEm(), filtravel)
							    .setupPaginador(paginador)
							    .filterSimpleBy("i.descricao")
							    .orderBy("i.descricao")
							    .sortedBy(ESortedBy.ASC)
							    .build()
							    .dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Ingrediente consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT i FROM Ingrediente i ");
			sql.append(" WHERE i.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Ingrediente.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre) {
			getLog().error("INGREDIENTE NAO ENCONTRADO");
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<SaborIngrediente> cconsultarAssociadosPorSabor(Sabor sabor) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT si FROM SaborIngrediente si ");
			sql.append(" JOIN si.sabor s ");
			sql.append(" JOIN FETCH si.ingrediente i ");
			sql.append(" WHERE s =:_sabor ");
			sql.append(" AND i.situacao =:_situacao ");
			sql.append(" ORDER BY i.sequencia ");
			
			return getEm().createQuery(sql.toString(), SaborIngrediente.class)
						  .setParameter("_sabor", sabor)
						  .setParameter("_situacao", ESituacao.ATIVO)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}