package br.com.will.gestao.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Nivel;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.entidade.util.ENivel;
import br.com.will.gestao.servico.NivelServico;
import br.com.will.gestao.servico.UsuarioServico;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Named
@ViewScoped
public class UsuarioCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private NivelServico nivelServico;

	private Usuario usuario;
	private List<Nivel> niveis;

	private Integer idNivelSelecionado;

	@PostConstruct
	public void inicializar() {
		try {
			if (usuario == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						usuario = usuarioServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Usuario");
					}
				}
				if (this.usuario == null) {
					this.usuario = new Usuario();
					this.usuario.setNivel(new Nivel());
				}
			}
			niveis = nivelServico.obterTodos("descricao");
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			Nivel nivel = null;
			if (getLoginBean().getUsuarioLogado() != null) {
				nivel = nivelServico.obterPorId(this.idNivelSelecionado);
			} else {
				nivel = nivelServico.obterPorId(3);
			}
			usuario.setNivel(nivel);
			if (usuario.getId() != null) {
				usuarioServico.alterar(usuario);
			} else {
				usuario = usuarioServico.salvar(usuario);
			}
			adicionarInfo("Usuario Salvo com Sucesso.");
			if (nivel.getDescricao().equals(ENivel.CLIENTE.getDescricao())
					&& (getLoginBean().getUsuarioLogado() == null || getLoginBean().isCliente())) {
				getLoginBean().getCredencial().setUsername(usuario.getLogin());
				getLoginBean().getCredencial().setPassword(usuario.getSenha());
				getLoginBean().fazerLogin();
				return "/pages/home.ppd?faces-redirect=true";
			}
			return "usuarios.ppd?faces-redirect=true";
		} catch (BaseServicoException e) {
			e.printStackTrace();
			adicionarError("Erro ao Salvar Usuario.");
			return null;
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public List<Nivel> getNiveis() {
		return niveis;
	}

	public void setNiveis(List<Nivel> niveis) {
		this.niveis = niveis;
	}

	public Integer getIdNivelSelecionado() {
		return idNivelSelecionado;
	}

	public void setIdNivelSelecionado(Integer idNivelSelecionado) {
		this.idNivelSelecionado = idNivelSelecionado;
	}
}