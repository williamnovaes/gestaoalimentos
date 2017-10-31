package br.com.will.gestao.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.entidade.Empresa;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class CaixaDAO extends BaseDAO<Caixa> {

	private static final long serialVersionUID = 1L;

	public CaixaDAO() {
		super(Caixa.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(Caixa t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Caixa.class, getEm(), filtravel)
					.setupPaginador(paginador)
					.filterSimpleBy("cx.observacao")
					.orderBy("cx.id")
					.sortedBy(ESortedBy.ASC)
					.build()
					.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Caixa consultarPorData(Calendar dataInicioDia) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT cx FROM Caixa cx ");
			sql.append(" WHERE cx.dataCadastro >:_data ");
			sql.append(" AND cx.dataFechamento IS NULL ");
			sql.append(" ORDER BY cx.dataCadastro ");
			
			return getEm().createQuery(sql.toString(), Caixa.class)
						  .setParameter("_data", dataInicioDia)
						  .setMaxResults(1)
						  .getSingleResult();
		} catch (NoResultException nre) {
			getLog().info("Nao Existe Caixa cadastrado no periodo");
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void abrirCaixa(Caixa caixa) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE Caixa ");
			sql.append(" SET aberto =:_aberto ");
			sql.append(" WHERE id =:_idCaixa ");
			
			getEm().createQuery(sql.toString())
				   .setParameter("_aberto", EBoolean.TRUE)
				   .setParameter("_idCaixa", caixa.getId())
				   .executeUpdate();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Caixa consultarCaixaAberto(Empresa empresa) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT cx FROM Caixa cx ");
			sql.append(" JOIN FETCH cx.empresa em ");
			sql.append(" WHERE cx.aberto =:_aberto ");
			sql.append(" AND em = :_empresa ");
			sql.append(" ORDER BY cx.dataAbertura DESC ");
			
			return getEm().createQuery(sql.toString(), Caixa.class)
						  .setParameter("_aberto", EBoolean.TRUE)
						  .setParameter("_empresa", empresa)
						  .getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Caixa consultarCompletoPorId(Integer id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT cx FROM Caixa cx ");
			sql.append(" JOIN FETCH cx.empresa em ");
			sql.append(" JOIN FETCH cx.usuario us ");
			sql.append(" WHERE cx.id =:_id ");
			
			return getEm().createQuery(sql.toString(), Caixa.class).getSingleResult();
		} catch (NoResultException nre) {
			getLog().error("caixa completo por id nao encontrado");
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}