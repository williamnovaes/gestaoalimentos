package br.com.nx.tickets.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.UsuarioPontoVenda;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class UsuarioPontoVendaDAO extends BaseDAO<UsuarioPontoVenda> {

	private static final long serialVersionUID = 1L;

	public UsuarioPontoVendaDAO() {
		super(UsuarioPontoVenda.class);
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(UsuarioPontoVenda t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(UsuarioPontoVenda.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("upv.id")
									.orderBy("upv.id")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public UsuarioPontoVenda consultarPontoVendaPorUsuario(Usuario usuario) {
		try {
			return getEm().createQuery("SELECT upv FROM UsuarioPontoVenda upv "
					+ " JOIN FETCH upv.pontoVenda pv "
					+ " JOIN FETCH upv.usuario us "
					+ " WHERE us.id =:_idUsuario", UsuarioPontoVenda.class)
						  .setParameter("_idUsuario", usuario.getId())
						  .getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Usuario> consultarPorPontoVendaNivel(PontoVenda pontoVenda, Nivel nivel) {
		try {
			return getEm().createQuery("SELECT us FROM UsuarioPontoVenda upv "
					+ " JOIN upv.pontoVenda pv "
					+ " JOIN upv.usuario us "
					+ " JOIN us.nivel n "
					+ " WHERE pv =:_pontoVenda AND n =:_nivel ", Usuario.class)
						  .setParameter("_pontoVenda", pontoVenda)
						  .setParameter("_nivel", nivel)
						  .getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}
