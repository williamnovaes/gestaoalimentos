package br.com.will.gestao.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.Nivel;
import br.com.will.gestao.entidade.permissao.Pagina;
import br.com.will.gestao.entidade.permissao.PaginaNivel;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class PaginaDAO extends BaseDAO<Pagina> {

	private static final long serialVersionUID = 1L;

	public PaginaDAO() {
		super(Pagina.class);
	}

	@Override
	public void verificarDuplicidade(Pagina t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Pagina.class, getEm(), filtravel)
								.setupPaginador(paginador)
								.filterSimpleBy("pg.nome")
								.orderBy("pg.nome")
								.sortedBy(ESortedBy.ASC)
								.build()
								.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public void atualizarNiveis(Pagina pagina, List<Nivel> niveis) {
		try {
			removerNivelPorPagina(pagina);
			for (Nivel nivel : niveis) {
				getEm().persist(new PaginaNivel(pagina, nivel));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removerNivelPorPagina(Pagina pagina) {
		try {
			getEm().createQuery("DELETE FROM PaginaNivel pn "
							  + "WHERE pn.pagina =:_pagina ")
					.setParameter("_pagina", pagina)
					.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Nivel> consultarNiveisPorPagina(Pagina pagina) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT nv FROM Nivel nv ");
			sql.append(" JOIN FETCH nv.paginaNivel pn ");
			sql.append(" JOIN FETCH pn.pagina pg ");
			sql.append(" WHERE pg =:_pagina ");
			sql.append(" ORDER BY nv.id ");
			
			return getEm()
					.createQuery(sql.toString(), Nivel.class)
					.setParameter("_pagina", pagina)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Pagina> consultarPorNivel(Nivel nivel) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pg FROM Pagina pg  ");
			sql.append(" JOIN FETCH pg.menu mn ");
			sql.append(" JOIN FETCH pg.paginaNivel pn ");
			sql.append(" JOIN FETCH pn.nivel n ");
			sql.append(" WHERE n =:_nivel and pg.situacao =:_ativo and mn.situacao =:_ativo ");
			sql.append(" ORDER BY mn.sequencia, pg.sequencia");
			return getEm()
					.createQuery(sql.toString(), Pagina.class)
					.setParameter("_nivel", nivel)
					.setParameter("_ativo", ESituacao.ATIVO)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Pagina consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT pg FROM Pagina pg ");
			sql.append(" JOIN FETCH pg.menu m ");
			sql.append(" WHERE pg.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Pagina.class)
						  .setParameter("_id", id)
						  .getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}