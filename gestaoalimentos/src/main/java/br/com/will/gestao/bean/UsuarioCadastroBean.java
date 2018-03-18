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
import br.com.will.gestao.util.SistemaConstantes;

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
	private String senhaConfirmacao;
	private boolean senhaValida;

	@PostConstruct
	public void inicializar() {
		try {
			if (usuario == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						usuario = usuarioServico.obterCompletoPorId(Integer.parseInt(idParam));
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
			senhaValida = false;
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
				nivel = nivelServico.obterPorDescricao("CLIENTE");
				usuario.setLogin(usuario.getEmail());
			}
			
			
			//valida campos obrigatorios
			usuario.setNivel(nivel);
			
			if (usuario.getId() != null) {
				usuarioServico.alterar(usuario);
			} else {
				usuario = usuarioServico.salvar(usuario);
			}
			
			adicionarInfo("Usuario Salvo com Sucesso.");
			if (nivel.getDescricao().equals(ENivel.CLIENTE.getDescricao())
					&& getLoginBean().getUsuarioLogado() == null) {
				this.usuario = usuarioServico.obterCompletoPorId(this.usuario.getId());
				getLoginBean().setUsuarioLogado(this.usuario);
				return getLoginBean().fazerLoginPosCadastro();
			}
			return "usuarios.ppd?faces-redirect=true";
		} catch (BaseServicoException e) {
			e.printStackTrace();
			adicionarError("Erro ao Salvar Usuario.");
			return null;
		}
	}
	
	public void validarSenha() {
		if (this.usuario.getSenha() == null) {
			adicionarWarn("Digite um senha");
			senhaValida = false;
			return;
		}
		
		if (this.usuario.getSenha().length() < SistemaConstantes.CINCO) {
			adicionarWarn("A senha deve conter mais que 5 caracteres");
			senhaValida = false;
			return;
		}
		senhaValida = true;
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
	
	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}
	
	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}
	
	public boolean isSenhasIguais() {
		return this.senhaConfirmacao != null 
				&& this.usuario.getSenha() != null 
				&& this.senhaConfirmacao.equals(this.usuario.getSenha())
				&& this.senhaValida;
	}
	
	public boolean isSenhaValida() {
		return senhaValida;
	}
}