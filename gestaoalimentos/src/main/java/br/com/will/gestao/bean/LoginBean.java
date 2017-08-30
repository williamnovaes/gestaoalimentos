package br.com.will.gestao.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.will.gestao.componente.Credencial;
import br.com.will.gestao.entidade.Empresa;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.entidade.permissao.Menu;
import br.com.will.gestao.entidade.permissao.Pagina;
import br.com.will.gestao.servico.PaginaServico;
import br.com.will.gestao.servico.UsuarioServico;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Named
@SessionScoped
public class LoginBean extends BaseBean {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private Credencial credencial;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private PaginaServico paginaServico;
	private Usuario usuarioLogado;
	private boolean logado;
	private List<Pagina> paginas;
	private HashMap<Menu, List<Pagina>> paginasPorMenu;
	private Empresa empresa;

	public String fazerLogin() {
		try {
			usuarioLogado = usuarioServico.logar(credencial);
			if (usuarioLogado != null) {
				configurarPermissao();
				logado = true;
				return "home?faces-redirect=true";
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}

	public String fazerLogout() throws BaseServicoException {
		try {
			logado = false;
			usuarioLogado = null;
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			return "login?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			// return NavegacaoBean.redirecionarParaLogin();
		}
		return "";
	}

	private void configurarPermissao() {
		try {
			List<Pagina> paginasAuxiliar = new ArrayList<>();
			paginasPorMenu = new HashMap<Menu, List<Pagina>>();
			paginas = paginaServico.obterPorNivel(this.usuarioLogado.getNivel());
			for (Pagina pagina : paginas) {
				if (paginasPorMenu.containsKey(pagina.getMenu())) {
					paginasAuxiliar = paginasPorMenu.get(pagina.getMenu());
				} else {
					paginasAuxiliar = new ArrayList<Pagina>();
				}
				paginasAuxiliar.add(pagina);
				paginasPorMenu.put(pagina.getMenu(), paginasAuxiliar);
			}
			this.empresa = usuarioLogado.getEmpresa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isExibirPagina(String pagina) {
		return termPermissaoPagina(pagina);
	}
	
	public boolean termPermissaoPagina(String pagina) {
		if (paginasPorMenu == null) {
			return true;
		}
		Collection<List<Pagina>> pgs = paginasPorMenu.values();
		for (List<Pagina> list : pgs) {
			for (Pagina pg : list) {
				if (pg.getNome().equals(pagina)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String irParaHome() {
		return "home?faces-redirect=true";
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public boolean isLogado() {
		return logado;
	}

	public List<Pagina> getPaginasPorMenu(Menu menu) {
		return paginasPorMenu.get(menu);
	}

	public List<Menu> getMenus() {
		if (paginasPorMenu != null) {
			return new ArrayList<>(paginasPorMenu.keySet());
		} else {
			return null;
		}
	}

	public boolean isAdministrador() {
		if (this.usuarioLogado != null && this.usuarioLogado.getNivel().getDescricao().equals("ADMINISTRADOR")) {
			return true;
		}
		return false;
	}
	
	public boolean isVendedor() {
		if (this.usuarioLogado != null && this.usuarioLogado.getNivel().getDescricao().equals("VENDEDOR")) {
			return true;
		}
		return false;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
}
