package br.com.nx.tickets.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.EventoSocioTorcedor;
import br.com.nx.tickets.entidade.socio.SocioTorcedor;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;
import br.com.nx.tickets.util.SistemaConstantes;

public class SocioTorcedorDAO extends BaseDAO<SocioTorcedor> {

	private static final long serialVersionUID = 1L;

	public SocioTorcedorDAO() {
		super(SocioTorcedor.class);
	}

	@Override
	public void verificarDuplicidade(SocioTorcedor t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(SocioTorcedor.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("st.codigo", "st.nome", "st.carteirinha", "st.cpf")
									.orderBy("st.nome")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void salvar(List<SocioTorcedor> socioTorcedores) {
		try {
			int indice = 0;
			for (SocioTorcedor socioTorcedor : socioTorcedores) {
				getEm().persist(socioTorcedor);
				if (indice % SistemaConstantes.CINQUENTA == SistemaConstantes.ZERO) {
					getEm().flush();
					getEm().clear();
					getLog().info("LIBERANDO SOCIO TORCEDOR: " + indice);
				}
				indice++;
			}
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public SocioTorcedor consultarPorCodigo(String codigo) {
		try {
			return getEm().createQuery("SELECT st FROM SocioTorcedor st "
							+ "WHERE st.carteirinha =:_codigo", SocioTorcedor.class)
						  .setParameter("_codigo", codigo)
						  .getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<EventoSocioTorcedor> consultarParticipacoes(SocioTorcedor socio) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT est FROM EventoSocioTorcedor est "
					 + " JOIN FETCH est.evento ev "
					 + " JOIN FETCH est.socioTorcedor st "
					 + " WHERE st =:_socioTorcedor ");			
			
			return getEm().createQuery(sql.toString(), EventoSocioTorcedor.class)
					.setParameter("_socioTorcedor", socio)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<SocioTorcedor> consultarPorCliente(Cliente cliente) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT st FROM SocioTorcedor st ");
			sql.append(" JOIN FETCH st.cliente cl ");
			sql.append(" WHERE cl =:_cliente ");
			sql.append(" ORDER BY st.carteirinha ");
			
			return getEm().createQuery(sql.toString(), SocioTorcedor.class)
						  .setParameter("_cliente", cliente)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}