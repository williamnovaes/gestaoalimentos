package br.com.will.gestao.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.FiltroUsuarioPedido;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.dao.filtro.ESortedBy;
import br.com.will.gestao.dao.filtro.SQLFilter;
import br.com.will.gestao.entidade.Pedido;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class PedidoDAO extends BaseDAO<Pedido> {

	private static final long serialVersionUID = 1L;

	public PedidoDAO() {
		super(Pedido.class);
	}

	@Override
	public void verificarDuplicidade(Pedido t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			FiltroUsuarioPedido filtroPedido = (FiltroUsuarioPedido) filtravel;
			return new SQLFilter.SQLFilterBuilder(Pedido.class, getEm(), filtravel)
					.filterDateBy("pd.dataCadastro", filtroPedido.getDataInicio(), filtroPedido.getDataFim())
					.setupPaginador(paginador)
					.filterSimpleBy("")
					.orderBy("pd.dataCadastro")
					.sortedBy(ESortedBy.DESC).build()
					.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Pedido consultarPorId(BigInteger id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ped FROM Pedido ped ");
			sql.append("WHERE ped.id =:_id");

			return getEm().createQuery(sql.toString(), Pedido.class).setParameter("_id", id).getSingleResult();
		} catch (NoResultException nr) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public Pedido consultarCompletoPorId(Long id) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ped FROM Pedido ped ");
			sql.append("LEFT JOIN FETCH lo.endereco ed ");
			sql.append("LEFT JOIN FETCH ed.cidade cd ");
			sql.append("LEFT JOIN FETCH cd.estado est ");
			sql.append("WHERE ped.id =:_id");

			return getEm().createQuery(sql.toString(), Pedido.class).setParameter("_id", id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
}