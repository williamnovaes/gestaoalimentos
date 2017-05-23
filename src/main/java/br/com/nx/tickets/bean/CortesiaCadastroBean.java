package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Categoria;
import br.com.nx.tickets.entidade.Cortesia;
import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.servico.CategoriaServico;
import br.com.nx.tickets.servico.CortesiaServico;
import br.com.nx.tickets.servico.IngressoTipoServico;

@Named
@ViewScoped
public class CortesiaCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CortesiaServico cortesiaServico;
	@EJB 
	private IngressoTipoServico ingressoTipoServico;
	@EJB
	private CategoriaServico categoriaServico;
	
	private Cortesia cortesia;
	private Categoria categoria;
	private List<Categoria> categorias;
	
	private Integer idCategoriaSelecionada;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Cortesia Cadastro Bean");
		try {
			if (cortesia == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("idCortesia");
				if (idParam != null && !idParam.equals("")) {
					try {
						cortesia = cortesiaServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Cortesia ");
					}
				}
				if (this.cortesia == null) {
					this.cortesia = new Cortesia();
				} else {
					categoria = this.cortesia.getCategoria();
					idCategoriaSelecionada = categoria.getId();
				}
				categorias = categoriaServico.obterTodos("descricao");
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			IngressoTipo ingressoTipo = ingressoTipoServico.obterPorDescricao("CORTESIA");
			cortesia.setIngressoTipo(ingressoTipo);
			categoria = categoriaServico.obterPorId(idCategoriaSelecionada);
			cortesia.setCategoria(categoria);
			cortesiaServico.salvar(this.cortesia);
			return "cortesias?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Cortesia getCortesia() {
		return cortesia;
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	public Integer getIdCategoriaSelecionada() {
		return idCategoriaSelecionada;
	}
	
	public void setIdCategoriaSelecionada(Integer idCategoriaSelecionada) {
		this.idCategoriaSelecionada = idCategoriaSelecionada;
	}
}
