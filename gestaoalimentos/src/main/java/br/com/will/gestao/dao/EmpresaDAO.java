package br.com.will.gestao.dao;

import java.util.List;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.Empresa;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class EmpresaDAO extends BaseDAO<Empresa> {

	private static final long serialVersionUID = 1L;

	public EmpresaDAO() {
		super(Empresa.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(Empresa t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
//			return new SQLFilter.SQLFilterBuilder(Produto.class, getEm(), filtravel)
//							    .setupPaginador(paginador)
//							    .filterSimpleBy("em.nomeFantasia")
//							    .orderBy("em.nomeFantasia")
//							    .sortedBy(ESortedBy.ASC)
//							    .build()
//							    .dadosPaginados();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Empresa consultarEmpresa() {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT em FROM Empresa em ");
			sql.append(" WHERE em.situaoca =:_situacao ");
			sql.append(" ORDER BY em.id ");
			return getEm().createQuery(sql.toString(), Empresa.class)
						  .setParameter("_situacao", ESituacao.ATIVO)
						  .setMaxResults(1)
						  .getSingleResult();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}