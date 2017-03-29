package br.com.nx.tickets.dao;

import java.util.List;

import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.ClienteIngressoTipo;
import br.com.nx.tickets.entidade.ClientePontoVenda;
import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.UsuarioCliente;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class ClienteDAO extends BaseDAO<Cliente> {

	private static final long serialVersionUID = 1L;

	public ClienteDAO() {
		super(Cliente.class);
	}

	@Override
	public void verificarDuplicidade(Cliente t) throws ViolacaoDeConstraintException {
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Cliente.class, getEm(), filtravel)
									.setupPaginador(paginador)
									.filterSimpleBy("cl.nome")
									.orderBy("cl.nome")
									.sortedBy(ESortedBy.ASC).build()
									.dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	public List<PontoVenda> consultarPontosVendas(Cliente cliente) {
		try {
			return getEm().createQuery(" SELECT pdv.pontoVenda "
									 + " FROM Cliente cl "
									 + " JOIN cl.clientesPontosVendas pdv "
									 + " WHERE cl =:_cliente "
									 + " ORDER BY pdv.pontoVenda.nome ", PontoVenda.class)
						  .setParameter("_cliente", cliente)
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarPontosVendas(Cliente cliente, List<PontoVenda> pontosVendas) {
		try {
			removerPontoVendaPorCliente(cliente);
			for (PontoVenda pontoVenda : pontosVendas) {
				getEm().persist(new ClientePontoVenda(cliente, pontoVenda));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removerPontoVendaPorCliente(Cliente cliente) {
		try {
			getEm().createNativeQuery("DELETE FROM cliente_ponto_venda where _cliente =:_cliente ")
			.setParameter("_cliente", cliente)
			.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void atualizarIngressoTipo(Cliente cliente, List<IngressoTipo> it) {
		try {
			removerIngressoTipoPorCliente(cliente);
			for (IngressoTipo ingressoTipo : it) {
				getEm().persist(new ClienteIngressoTipo(cliente, ingressoTipo));
			}
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	private void removerIngressoTipoPorCliente(Cliente cliente) {
		try {
			getEm().createNativeQuery("DELETE FROM cliente_ingresso_tipo where _cliente =:_cliente ")
			.setParameter("_cliente", cliente)
			.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<IngressoTipo> consultarIngressosTipos(Cliente cliente) {
		try {
			return getEm()
					.createQuery(" SELECT cit.ingressoTipo "
							   + " FROM Cliente cl "
							   + " JOIN cl.clientesIngressosTipos cit "
							   + " WHERE cl =:_cliente "
							   + " ORDER BY cit.ingressoTipo.descricao ", IngressoTipo.class)
						  .setParameter("_cliente", cliente)
						  .getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Cliente> consultarClientesPorUsuario(Usuario usuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ucl.cliente ");
			sql.append(" FROM Usuario us ");
			sql.append(" JOIN us.usuariosClientes ucl ");
			sql.append(" WHERE us =:_usuario ");
			sql.append(" ORDER BY ucl.cliente.nome ");
			
			return getEm()
					.createQuery(sql.toString(), Cliente.class)
					.setParameter("_usuario", usuario)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarUsuarios(Cliente cliente, List<Usuario> usuarios) {
		try {
			removerUsuariosPorCliente(cliente);
			for (Usuario usuario : usuarios) {
				getEm().persist(new UsuarioCliente(usuario, cliente));
			}
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
	
	private void removerUsuariosPorCliente(Cliente cliente) {
		try {
			getEm().createNativeQuery("DELETE FROM usuario_cliente where _cliente =:_cliente ")
			.setParameter("_cliente", cliente)
			.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}