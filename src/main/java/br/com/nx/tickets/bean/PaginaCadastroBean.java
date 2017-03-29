package br.com.nx.tickets.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.permissao.Menu;
import br.com.nx.tickets.entidade.permissao.Pagina;
import br.com.nx.tickets.entidade.util.EPaginaTipo;
import br.com.nx.tickets.servico.MenuServico;
import br.com.nx.tickets.servico.NivelServico;
import br.com.nx.tickets.servico.PaginaServico;

@Named
@ViewScoped
public class PaginaCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private PaginaServico paginaServico;
	@EJB
	private NivelServico nivelServico;
	@EJB
	private MenuServico menuServico;

	private Pagina pagina;
	private Integer idNivelSelecionado;
	private Integer idMenuSelecionado;
	
	private List<Nivel> niveisDisponiveis;
	private List<Nivel> niveisAssociados;
	private List<Menu> menus;
	
	private EPaginaTipo[] paginaTipo = EPaginaTipo.values();
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Página Cadastro Bean");
		try {
			if (pagina == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						pagina = paginaServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.pagina == null) {
					this.pagina = new Pagina();
					this.pagina.setMenu(new Menu());
					niveisAssociados  = new ArrayList<>();
					niveisDisponiveis = nivelServico.obterTodos("descricao");
					menus = menuServico.obterTodos("nome");
				} else {
					menus = menuServico.obterTodos("nome");
					idMenuSelecionado = pagina.getMenu().getId();
					niveisAssociados  = paginaServico.obterNiveisPorPagina(pagina);
					niveisDisponiveis = nivelServico.obterTodos("descricao");
					niveisDisponiveis.removeAll(niveisAssociados);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (this.pagina.getId() != null) {
				this.pagina.setMenu(menuServico.obterPorId(this.idMenuSelecionado));
				paginaServico.alterar(this.pagina);
				if (!niveisAssociados.isEmpty()) {
					paginaServico.atualizarNiveis(pagina, niveisAssociados);
				}
			} else {
				this.pagina.setMenu(menuServico.obterPorId(this.idMenuSelecionado));
				paginaServico.salvar(this.pagina);
				if (!niveisAssociados.isEmpty()) {
					paginaServico.atualizarNiveis(pagina, niveisAssociados);
				}
			}
			return "paginas?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public void adicionarNivel() {
		try {
			if (this.idNivelSelecionado != null) {
				niveisAssociados.add(nivelServico.obterPorId(this.idNivelSelecionado));
				niveisDisponiveis.removeAll(niveisAssociados);
				this.idNivelSelecionado = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerNivel(Nivel nivel) {
		try {
			niveisAssociados.remove(nivel);
			niveisDisponiveis.add(nivel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Pagina getPagina() {
		return pagina;
	}
	
	public List<Nivel> getNiveisAssociados() {
		return niveisAssociados;
	}
	
	public List<Nivel> getNiveisDisponiveis() {
		return niveisDisponiveis;
	}

	public List<Menu> getMenus() {
		return menus;
	}
	
	public Integer getIdNivelSelecionado() {
		return idNivelSelecionado;
	}
	
	public void setIdNivelSelecionado(Integer idNivelSelecionado) {
		this.idNivelSelecionado = idNivelSelecionado;
	}
	
	public Integer getIdMenuSelecionado() {
		return idMenuSelecionado;
	}
	
	public void setIdMenuSelecionado(Integer idMenuSelecionado) {
		this.idMenuSelecionado = idMenuSelecionado;
	}
	
	public EPaginaTipo[] getPaginaTipo() {
		return paginaTipo;
	}
}
