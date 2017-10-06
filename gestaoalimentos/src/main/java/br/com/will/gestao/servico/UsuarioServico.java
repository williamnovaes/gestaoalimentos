package br.com.will.gestao.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.will.gestao.componente.Credencial;
import br.com.will.gestao.dao.BaseDAOException;
import br.com.will.gestao.dao.UsuarioDAO;
import br.com.will.gestao.entidade.Nivel;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Stateless
public class UsuarioServico extends BaseServico<Usuario> {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioDAO usuarioDao;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(usuarioDao);
	}
	
	public Usuario logar(Credencial credencial, String senhaCoringa) throws BaseServicoException {
		try {
			return usuarioDao.logar(credencial, senhaCoringa);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Usuario logar(Credencial credencial) throws BaseServicoException {
		try {
			return usuarioDao.logar(credencial);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	@Override
	public Usuario salvar(Usuario usuario) throws BaseServicoException {
		try {
			return usuarioDao.salvar(usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Usuario> obterPorNivel(Nivel nivel) throws BaseServicoException {
		try {
			return usuarioDao.conultarPorNivel(nivel);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
