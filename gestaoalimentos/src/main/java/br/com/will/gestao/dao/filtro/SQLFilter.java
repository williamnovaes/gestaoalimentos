package br.com.will.gestao.dao.filtro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.FiltroPermissao;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.BaseDAOException;
import br.com.will.gestao.entidade.util.DataUtil;
import br.com.will.gestao.entidade.util.Identificavel;
import br.com.will.gestao.util.SistemaConstantes;

public final class SQLFilter {

	private Class<? extends Paginavel> clazz;
	private EntityManager em;
	private FiltroPermissao filtroPermissao;
	private ESQL eSQL;
	private Set<String> simpleTextFilter;
	private Set<String> simpleLongFilter;
	private Set<String> simpleIntegerFilter;
	private Set<SQLFilterEmbeddedClause> filterEmbeddedClause;
	private Set<SQLFilterComplexDTO> complexFilter;
	private Set<SQLFilterDateDTO> dateFilter;
	private Set<SQLFilterIdentificavelDTO> identificaveis;

	private Paginador<Paginavel> paginador;
	private Paginavel paginavel;
	private String orderBy;
	private ESortedBy sortedBy;
	private boolean filtroHierarquia;

	private SQLFilter(SQLFilterBuilder filterBuilder) {
		try {
			
			this.clazz = filterBuilder.clazz;
			this.em = filterBuilder.em;
			this.filtroPermissao = (FiltroPermissao) filterBuilder.filtravel;
			this.paginador = filterBuilder.paginador;
			this.simpleTextFilter = filterBuilder.simpleTextFilter;
			this.simpleLongFilter = filterBuilder.simpleLongFilter;
			this.simpleIntegerFilter = filterBuilder.simpleIntegerFilter;
			this.complexFilter = filterBuilder.complexFilter;
			this.identificaveis = filterBuilder.identificaveis;
			this.filterEmbeddedClause = filterBuilder.filterEmbeddedClause;
			this.orderBy = filterBuilder.orderBy;
			this.sortedBy = filterBuilder.sortedBy;
			this.dateFilter = filterBuilder.dateFilter;
			this.eSQL = ESQL.valueOf(filtroPermissao.getNivel().name());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private StringBuilder montarCabecalho(ETipoSQL eTipoSQL) {
		StringBuilder select = new StringBuilder();
		try {

			if (!filtroHierarquia
					|| !(ETipoSQL.HIERARQUIA.equals(eTipoSQL) || ETipoSQL.COUNT_HIERARQUIA.equals(eTipoSQL))) {
				paginavel = this.clazz.newInstance();
			} 
			if (eTipoSQL.isCount()) {
				select.append(paginavel.getSqlCount());
			} else if (eTipoSQL.isSelect()) {
				select.append(paginavel.getSqlSelect());
			}
			if (!filtroHierarquia || !(ETipoSQL.HIERARQUIA.equals(eTipoSQL) || ETipoSQL.COUNT_HIERARQUIA.equals(eTipoSQL))) {
				select.append(paginavel.getJoin());
//				select.append(eSQL.getJoin());
			}
			select.append(" WHERE ");
			select.append(eSQL.getWhere());
			return select;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	private String monstarSQL(ETipoSQL eTipoSQL) {
		StringBuilder select = montarCabecalho(eTipoSQL);

		String sql = "";
		if (!eTipoSQL.equals(ETipoSQL.HIERARQUIA) && !eTipoSQL.equals(ETipoSQL.COUNT_HIERARQUIA)
				&& !eTipoSQL.equals(ETipoSQL.GRAFICO_HIERARQUIA)) {
			sql = montarSqlReal(eTipoSQL, select);
		} else {
			sql = montarSqlHierarquia(eTipoSQL, select);
			if (eTipoSQL.equals(ETipoSQL.COUNT_HIERARQUIA)) {
				sql = sql.replaceAll("FETCH", " ");
			}
		}
		return sql;
	}

	private String montarSqlHierarquia(ETipoSQL eTipoSQL, StringBuilder select) {

		if (identificaveis != null && !identificaveis.isEmpty()) {
			for (SQLFilterIdentificavelDTO identificavel : identificaveis) {
				select.append(" AND ").append(identificavel.getAttribute()).append(" in (:")
						.append(identificavel.getAlias()).append(") ");
			}
		}

		if (temValorValido(filtroPermissao.getEntradaFiltro())) {
			if (simpleTextFilter != null && !simpleTextFilter.isEmpty()) {
				select.append("AND ( ");
				int contador = 0;
				for (String filtro : simpleTextFilter) {
					select.append(filtro).append(ESQLOperator.LIKE.getOperator()).append(" :_filtro ");
					contador++;
					if (contador < simpleTextFilter.size()) {
						select.append(" OR ");
					}
				}
				select.append(") ");
			}

			if (simpleIntegerFilter != null && !simpleIntegerFilter.isEmpty() && filtroPermissao.isIntegerEntradaFiltro()) {
				select.append("OR ( ");
				int contador = 0;
				for (String filtro : simpleIntegerFilter) {
					select.append(filtro).append(ESQLOperator.EQUALS.getOperator()).append(" :_integerFiltro ");
					contador++;
					if (contador < simpleIntegerFilter.size()) {
						select.append(" OR ");
					}
				}
				select.append(") ");
			}
			if (simpleLongFilter != null && !simpleLongFilter.isEmpty()  && filtroPermissao.isLongEntradaFiltro()) {
				select.append("OR ( ");
				int contador = 0;
				for (String filtro : simpleLongFilter) {
					select.append(filtro).append(ESQLOperator.EQUALS.getOperator()).append(" :_longFiltro ");
					contador++;
					if (contador < simpleLongFilter.size()) {
						select.append(" OR ");
					}
				}
				select.append(") ");
			}
		}

		if (complexFilter != null && !complexFilter.isEmpty()) {
			select.append("AND ( ");
			int contador = 0;
			for (SQLFilterComplexDTO filtro : complexFilter) {
				select.append(filtro.getAttribute()).append(" =:").append(filtro.getAlias());
				contador++;
				if (contador < complexFilter.size()) {
					select.append(" AND ");
				}
			}
			select.append(") ");
		}

		List<Calendar> datas = new ArrayList<Calendar>();
		if (dateFilter != null && !dateFilter.isEmpty()) {
			select.append("AND ( ");
			Iterator<SQLFilterDateDTO> iterator = dateFilter.iterator();
			while (iterator.hasNext()) {
				SQLFilterDateDTO next = iterator.next();
				select.append(next.getAttribute()).append(" between :").append(next.getAliasDate1()).append(" and :")
						.append(next.getAliasDate2()).append(" ");
				datas.add(next.getDate1());
				datas.add(next.getDate2());
			}
			select.append(") ");
		}

		if (datas.size() == 2) {
			int dias = DataUtil.getDiferencaEmDias(datas.get(0), datas.get(1));
			int maximoDias = SistemaConstantes.SESSENTA;
			if (dias > maximoDias) {
				throw new BaseDAOException("Período informado maior que " + maximoDias + " dias (" + dias + "). "
						+ "Envie e-mail para help@nxmultiservicos.com.br para "
						+ "que lhe seja disponibilizado este relatório no período desejado.");
			}
		}

		if (filterEmbeddedClause != null && !filterEmbeddedClause.isEmpty()) {
			Iterator<SQLFilterEmbeddedClause> iterator = filterEmbeddedClause.iterator();
			while (iterator.hasNext()) {
				select.append(" AND (").append(iterator.next().getSql()).append(") ");
			}
		}
		if (!eTipoSQL.equals(ETipoSQL.COUNT_HIERARQUIA)) {
			select.append(" ORDER BY ").append(orderBy).append(" ").append(sortedBy);
		}
		String sql = select.toString();
		return sql;
	}

	private String montarSqlReal(ETipoSQL eTipoSQL, StringBuilder select) {
		if (temValorValido(filtroPermissao.getEntradaFiltro())) {
			select.append("AND ( ");
			if (simpleTextFilter != null && !simpleTextFilter.isEmpty()) {
				int contador = 0;
				for (String filtro : simpleTextFilter) {
					select.append(filtro).append(ESQLOperator.LIKE.getOperator()).append(" :_filtro ");
					contador++;
					if (contador < simpleTextFilter.size()) {
						select.append(" OR ");
					}
				}
			}

			if (simpleIntegerFilter != null && !simpleIntegerFilter.isEmpty() && filtroPermissao.isIntegerEntradaFiltro()) {
				select.append("OR ( ");
				int contador = 0;
				for (String filtro : simpleIntegerFilter) {
					select.append(filtro).append(ESQLOperator.EQUALS.getOperator()).append(" :_integerFiltro ");
					contador++;
					if (contador < simpleIntegerFilter.size()) {
						select.append(" OR ");
					}
				}
				select.append(") ");
			}

			if (simpleLongFilter != null && !simpleLongFilter.isEmpty() && filtroPermissao.isLongEntradaFiltro()) {
				select.append("OR ( ");
				int contador = 0;
				for (String filtro : simpleLongFilter) {
					select.append(filtro).append(ESQLOperator.EQUALS.getOperator()).append(" :_longFiltro ");
					contador++;
					if (contador < simpleLongFilter.size()) {
						select.append(" OR ");
					}
				}
				select.append(") ");
			}
			select.append(") ");
		}

		if (complexFilter != null && !complexFilter.isEmpty()) {
			select.append("AND ( ");
			int contador = 0;
			for (SQLFilterComplexDTO filtro : complexFilter) {
				select.append(filtro.getAttribute()).append(" =:").append(filtro.getAlias());
				contador++;
				if (contador < complexFilter.size()) {
					select.append(" AND ");
				}
			}
			select.append(") ");
		}

		List<Calendar> datas = new ArrayList<Calendar>();
		if (dateFilter != null && !dateFilter.isEmpty()) {
			select.append("AND ( ");
			Iterator<SQLFilterDateDTO> iterator = dateFilter.iterator();
			while (iterator.hasNext()) {
				SQLFilterDateDTO next = iterator.next();
				select.append(next.getAttribute()).append(" between :").append(next.getAliasDate1()).append(" and :")
						.append(next.getAliasDate2()).append(" ");
				datas.add(next.getDate1());
				datas.add(next.getDate2());
			}
			select.append(") ");
		}

		if (datas.size() == 2) {
			int dias = DataUtil.getDiferencaEmDias(datas.get(0), datas.get(1));
			int maximoDias = SistemaConstantes.SESSENTA;
			if (dias > maximoDias) {
				throw new BaseDAOException("Período informado maior que " + maximoDias + " dias (" + dias + "). "
						+ "Envie e-mail para help@nxmultiservicos.com.br para "
						+ "que lhe seja disponibilizado este relatório no período desejado.");
			}
		}

		if (filterEmbeddedClause != null && !filterEmbeddedClause.isEmpty()) {
			Iterator<SQLFilterEmbeddedClause> iterator = filterEmbeddedClause.iterator();
			while (iterator.hasNext()) {
				select.append(" AND (").append(iterator.next().getSql()).append(") ");
			}
		}

		/**
		 * Nao sabia o impacto do agrupamento com grafico
		 */
		boolean queryComOrderBy = false;
		if ((!eTipoSQL.isCount() && orderBy != null && !eTipoSQL.isGrafico()) || queryComOrderBy) {
			select.append(" ORDER BY ").append(orderBy).append(" ").append(sortedBy);
		}

		String sql = select.toString();
		if (!eTipoSQL.isSelect()) {
			sql = select.toString().replace("FETCH", "");
		}
		return sql;
	}

	private boolean temValorValido(String texto) {
		return texto != null && !texto.isEmpty();
	}

	private Query createTypedQuery(String select, ETipoSQL eTipoSQL) {
		Query query = em.createQuery(select);

		if (temValorValido(filtroPermissao.getEntradaFiltro())) {
			if (simpleTextFilter != null && !simpleTextFilter.isEmpty()) {
				query.setParameter("_filtro", "%" + filtroPermissao.getEntradaFiltro() + "%");
			}
			if (simpleIntegerFilter != null && !simpleIntegerFilter.isEmpty()) {
				query.setParameter("_integerFiltro", Integer.parseInt(filtroPermissao.getEntradaFiltro()));
			}
			if (simpleLongFilter != null && !simpleLongFilter.isEmpty()) {
				query.setParameter("_longFiltro", Long.parseLong(filtroPermissao.getEntradaFiltro()));
			}
		}

		if (complexFilter != null && !complexFilter.isEmpty()) {
			Iterator<SQLFilterComplexDTO> iterator = complexFilter.iterator();
			while (iterator.hasNext()) {
				SQLFilterComplexDTO next = iterator.next();
				query.setParameter(next.getAlias(), next.getValue());
			}
		}

		if (identificaveis != null && !identificaveis.isEmpty()) {
			for (SQLFilterIdentificavelDTO identificavel : identificaveis) {
				query.setParameter(identificavel.getAlias(), identificavel.getIdentificaveis());
			}
		}

		if (dateFilter != null && !dateFilter.isEmpty()) {
			Iterator<SQLFilterDateDTO> iterator = dateFilter.iterator();
			while (iterator.hasNext()) {
				SQLFilterDateDTO next = iterator.next();
				query.setParameter(next.getAliasDate1(), next.getDate1(), TemporalType.TIMESTAMP);
				query.setParameter(next.getAliasDate2(), next.getDate2(), TemporalType.TIMESTAMP);
			}
		}

		if (filterEmbeddedClause != null && !filterEmbeddedClause.isEmpty()) {
			Iterator<SQLFilterEmbeddedClause> iterator = filterEmbeddedClause.iterator();
			while (iterator.hasNext()) {
				List<SQLFilterEmbeddedParameters> filterEmbeddedParameters = iterator.next()
						.getFilterEmbeddedParameters();
				for (SQLFilterEmbeddedParameters sqlFilterEmbeddedParameters : filterEmbeddedParameters) {
					query.setParameter(sqlFilterEmbeddedParameters.getKey(), sqlFilterEmbeddedParameters.getValue());
				}
			}
		}
		return query;
	}

	public Integer totalRegistrosPaginador() {
		try {
			return ((Long) createTypedQuery(monstarSQL(ETipoSQL.COUNT), ETipoSQL.COUNT).getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Integer totalRegistrosPaginadorHierarquia() {
		try {
			return ((Long) createTypedQuery(monstarSQL(ETipoSQL.COUNT_HIERARQUIA), ETipoSQL.COUNT).getSingleResult())
					.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Query getQuery() {
		try {
			Query query = createTypedQuery(monstarSQL(ETipoSQL.SELECT), ETipoSQL.SELECT);
			return query;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Query getHierarquia() {
		try {
			Query query = createTypedQuery(monstarSQL(ETipoSQL.HIERARQUIA), ETipoSQL.HIERARQUIA);
			return query;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Query getQueryGrafico() {
		try {
			Query query = createTypedQuery(monstarSQL(ETipoSQL.GRAFICO), ETipoSQL.GRAFICO);
			return query;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Query getQueryGraficoHierarquia() {
		try {
			Query query = createTypedQuery(monstarSQL(ETipoSQL.GRAFICO_HIERARQUIA), ETipoSQL.GRAFICO);
			return query;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Paginador<Paginavel> dadosPaginados() {
		try {
			paginador.setTotalRegistros(totalRegistrosPaginador());
			Query q = getQuery();
			q.setMaxResults(paginador.getTotalRegistrosPorPagina()).setFirstResult(paginador.getOffset());
			paginador.setRegistros(q.getResultList());
			if (paginador.getTotalRegistros() <= paginador.getTotalRegistrosPorPagina()) {
				paginador.paginar(1);
			}
			paginador.setDadosDTO(null);
			return paginador;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Paginador<Paginavel> dadosPaginadosHierarquia() {
		try {
			paginador.setTotalRegistros(totalRegistrosPaginadorHierarquia());
			Query q = getHierarquia();
			q.setMaxResults(paginador.getTotalRegistrosPorPagina()).setFirstResult(paginador.getOffset());
			paginador.setRegistros(q.getResultList());
			if (paginador.getTotalRegistros() <= paginador.getTotalRegistrosPorPagina()) {
				paginador.paginar(1);
			}
			paginador.setDadosDTO(null);
			return paginador;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public static class SQLFilterBuilder
			implements IPaginavelNovo, IFilterByNovo, IFilterByIntegerNovo, IFilterByLongNovo, IDateFilterByNovo, IOrderByNovo, ISortedByNovo, IBuildNovo {
		private Class<? extends Paginavel> clazz;
		private EntityManager em;
		private Filtravel filtravel;
		private Paginador<Paginavel> paginador;
		private Set<SQLFilterEmbeddedClause> filterEmbeddedClause;
		private Set<String> simpleTextFilter;
		private Set<String> simpleLongFilter;
		private Set<String> simpleIntegerFilter;
		private Set<SQLFilterComplexDTO> complexFilter;
		private Set<SQLFilterIdentificavelDTO> identificaveis;
		private Set<SQLFilterDateDTO> dateFilter;
		private String orderBy;
		private ESortedBy sortedBy = ESortedBy.ASC;

		public SQLFilterBuilder(Class<? extends Paginavel> clazz, EntityManager em, Filtravel filtravel) {
			this.clazz = clazz;
			this.em = em;
			this.filtravel = filtravel;
		}

		public IFilterByNovo setupPaginador(Paginador<Paginavel> pg) {
			this.paginador = pg;
			return this;
		}

		public IOrderByNovo filterSimpleBy(String... filterBy) {
			if (this.simpleTextFilter == null) {
				this.simpleTextFilter = new LinkedHashSet<String>();
			}
			this.simpleTextFilter.addAll(Arrays.asList(filterBy));
			return this;
		}

		public SQLFilterBuilder filterLongBy(String... filterBy) {
			if (this.simpleLongFilter == null) {
				this.simpleLongFilter = new LinkedHashSet<String>();
			}
			
			this.simpleLongFilter.addAll(Arrays.asList(filterBy));
			return this;
		}
		
		public SQLFilterBuilder filterIntegerBy(String... filterBy) {
			if (this.simpleIntegerFilter == null) {
				this.simpleIntegerFilter = new LinkedHashSet<String>();
			}
			this.simpleIntegerFilter.addAll(Arrays.asList(filterBy));
			return this;
		}

		public SQLFilterBuilder filterByIdentificavel(String campo, List<Identificavel> ivs) {
			if (this.identificaveis == null) {
				this.identificaveis = new LinkedHashSet<SQLFilterIdentificavelDTO>();
			}
			if (!ivs.isEmpty()) {
				this.identificaveis.add(new SQLFilterIdentificavelDTO(campo, ivs));
			}
			return this;
		}

		public SQLFilterBuilder filterBy(String filterBy, Object by) {
			if (this.complexFilter == null) {
				this.complexFilter = new LinkedHashSet<SQLFilterComplexDTO>();
			}
			if (by != null) {
				if (by instanceof Integer) {
					if (((Integer) by) > 0) {
						this.complexFilter.add(new SQLFilterComplexDTO(filterBy, by));
					}
				} else {
					this.complexFilter.add(new SQLFilterComplexDTO(filterBy, by));
				}
			}
			return this;
		}

		public SQLFilterBuilder filterDateBy(String filterBy, Date d1, Date d2) {
			if (d1 != null && d2 != null) {
				if (this.dateFilter == null) {
					this.dateFilter = new LinkedHashSet<SQLFilterDateDTO>();
				}
				this.dateFilter.add(new SQLFilterDateDTO(filterBy, d1, d2));
			}
			return this;
		}

		public SQLFilterBuilder filterEmbebbedClause(String clause,
				SQLFilterEmbeddedParameters... filterEmbeddedParameters) {
			if (filterEmbeddedClause == null) {
				filterEmbeddedClause = new LinkedHashSet<SQLFilterEmbeddedClause>();
			}
			filterEmbeddedClause.add(new SQLFilterEmbeddedClause(clause, Arrays.asList(filterEmbeddedParameters)));
			return this;
		}

		public ISortedByNovo orderBy(String ob) {
			this.orderBy = ob;
			return this;
		}

		public IBuildNovo sortedBy(ESortedBy sb) {
			this.sortedBy = sb;
			return this;
		}

		public SQLFilter build() {
			return new SQLFilter(this);
		}
	}
}
