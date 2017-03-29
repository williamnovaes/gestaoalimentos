package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Local;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.LocalServico;
import br.com.nx.tickets.servico.PortariaServico;

@Named
@ViewScoped
public class PortariaCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private PortariaServico portariaServico;
	@EJB
	private LocalServico localServico;
	@EJB
	private GuicheServico guicheServico;

	private Portaria portaria;

	private List<Local> locais;
	private Local local;
//	private List<Guiche> guichesAssociados;
//	private List<Guiche> guichesDisponiveis;
	private EBoolean[] padrao = EBoolean.values();

	private Integer idLocalSelecionado;
//	private Integer idGuicheSelecionado;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Portaria Cadastro Bean");
		try {
			if (portaria == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						portaria = portariaServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Portaria ");
					}
				}
				if (this.portaria == null) {
					this.portaria = new Portaria();
					this.portaria.setLocal(new Local());
//					this.portaria.setGuiches(new ArrayList<>());
				}
			}
			locais 				= localServico.obterTodos("descricao");
//			guichesDisponiveis 	= guicheServico.obterTodos("descricao");
//			guichesAssociados 	= new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			this.portaria.setLocal(localServico.obterPorId(this.idLocalSelecionado));
//			if (!this.guichesAssociados.isEmpty()) {
//				this.portaria.setGuiches(guichesAssociados);
//			}
			portariaServico.salvar(this.portaria);
			return "portarias?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
//	public void adicionarGuiche() {
//		try {
//			Guiche giche = new Guiche();
//			giche = guicheServico.obterPorId(idGuicheSelecionado);
//			guichesAssociados.add(giche);
//			guichesDisponiveis.remove(giche);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void removerGuiche(Guiche gi) {
//		guichesAssociados.remove(gi);
//		guichesDisponiveis.add(gi);
//	}

	public Portaria getPortaria() {
		return portaria;
	}

	public Local getLocal() {
		return local;
	}

	public List<Local> getLocais() {
		return locais;
	}

	public Integer getIdLocalSelecionado() {
		return idLocalSelecionado;
	}

	public void setIdLocalSelecionado(Integer idLocalSelecionado) {
		this.idLocalSelecionado = idLocalSelecionado;
	}
	
	public EBoolean[] getPadrao() {
		return padrao;
	}
	
//	public List<Guiche> getGuichesAssociados() {
//		return guichesAssociados;
//	}
	
//	public List<Guiche> getGuichesDisponiveis() {
//		return guichesDisponiveis;
//	}
	
//	public Integer getIdGuicheSelecionado() {
//		return idGuicheSelecionado;
//	}
	
//	public void setIdGuicheSelecionado(Integer idGuicheSelecionado) {
//		this.idGuicheSelecionado = idGuicheSelecionado;
//	}
}
