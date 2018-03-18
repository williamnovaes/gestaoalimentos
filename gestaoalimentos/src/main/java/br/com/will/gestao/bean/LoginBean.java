package br.com.will.gestao.bean;

import java.math.BigDecimal;
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
import br.com.will.gestao.dto.ProdutoPedidoDTO;
import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.entidade.Produto;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.entidade.permissao.Menu;
import br.com.will.gestao.entidade.permissao.Pagina;
import br.com.will.gestao.entidade.util.ETipoNivel;
import br.com.will.gestao.servico.CaixaServico;
import br.com.will.gestao.servico.PaginaServico;
import br.com.will.gestao.servico.UsuarioServico;
import br.com.will.gestao.servico.exception.BaseServicoException;
import br.com.will.gestao.util.ConfiguracaoSistemaConstantes;

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
	@EJB
	private CaixaServico caixaServico;

	private Usuario usuarioLogado;
	private boolean logado;
	private List<Pagina> paginas;
	private HashMap<Menu, List<Pagina>> paginasPorMenu;
	private List<Produto> carrinho = new ArrayList<>();
	private List<ProdutoPedidoDTO> carrinhoDto = new ArrayList<>();
	private boolean exibirModalLogin = false;
	private Caixa caixa;

	public String fazerLogin() {
		try {
			exibirModalLogin = false;
			if (credencial == null || credencial.getPasswordDescriptografado() == null
					|| credencial.getPasswordDescriptografado().isEmpty() || credencial.getUsername() == null
					|| credencial.getUsername().isEmpty()) {
				adicionarInfo("Login e Senha obrigatorios");
				return null;
			}
			usuarioLogado = usuarioServico.logar(credencial, getConfiguracaoApplication()
					.obterConfiguracaoSistema(ConfiguracaoSistemaConstantes.SENHA_CORINGA).getValor());
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

	public String fazerLoginPosCadastro() {
		try {
			if (usuarioLogado != null) {
				configurarPermissao();
				logado = true;
				return "/pages/home.ppd?faces-redirect=true";
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
			return "home.ppd?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			// return NavegacaoBean.redirecionarParaLogin();
		}
		return "";
	}

	public void carregarCaixa() {
		try {
			caixa = caixaServico.obterCaixaAberto();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void configurarPermissao() {
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
		return "/pages/home.ppd?faces-redirect=true";
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
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
		if (this.usuarioLogado != null && (this.usuarioLogado.getNivel().getDescricao().equals("ADMINISTRADOR")
				|| this.usuarioLogado.getTipoNivel().equals(ETipoNivel.ADMINISTRADOR))) {
			return true;
		}
		return false;
	}

	public boolean isSuperUser() {
		if (this.usuarioLogado != null && this.usuarioLogado.getTipoNivel().equals(ETipoNivel.ADMINISTRADOR)) {
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

	public BigDecimal valorTotal() {
		return null;
	}

	public BigDecimal valorPorProduto(Produto produto) {
		return null;
	}

	public boolean isCliente() {
		return this.usuarioLogado != null && this.usuarioLogado.getTipoNivel().equals(ETipoNivel.CLIENTE);
	}

	public void abrirModalLogin() {
		exibirModalLogin = true;
	}

	public boolean isExibirModalLogin() {
		return exibirModalLogin;
	}

	public void fecharModalLogin() {
		exibirModalLogin = false;
	}

	public List<Produto> getCarrinho() {
		return carrinho;
	}

	public void adicionarProduto(Produto produto) {
		if (this.carrinho == null) {
			this.carrinho = new ArrayList<>();
		}
		this.carrinho.add(produto);
	}

	public void setCarrinho(List<Produto> carrinho) {
		this.carrinho = carrinho;
	}

	public Credencial getCredencial() {
		return credencial;
	}

	public List<ProdutoPedidoDTO> getCarrinhoDto() {
		if (this.carrinhoDto == null) {
			this.carrinhoDto = new ArrayList<>();
		}
		return carrinhoDto;
	}

	public void setCarrinhoDto(List<ProdutoPedidoDTO> carrinhoDto) {
		this.carrinhoDto = carrinhoDto;
	}

	public void adicionarProdutoDto(ProdutoPedidoDTO produto) {
		this.carrinhoDto.add(produto);
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	
	public boolean isAberto() {
		return this.caixa != null && this.caixa.isAberto();
	}

	public boolean isFuncionario() {
		if (this.usuarioLogado != null && this.usuarioLogado.getTipoNivel().equals(ETipoNivel.FUNCIONARIO)) {
			return true;
		}
		return false;
	}
}