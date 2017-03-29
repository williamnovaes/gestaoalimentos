package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Bilheteria;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.servico.BilheteriaServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class GuicheCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private GuicheServico guicheServico;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private BilheteriaServico bilheteriaServico;
	
	private Guiche guiche;
	private Usuario usuario;
	
	private List<Usuario> usuarios;
	private List<Bilheteria> bilheterias;
	private EBoolean[] offline = EBoolean.values();
	private EBoolean[] imprimirConfirmacao = EBoolean.values();
	private EBoolean[] imprimirCortesia = EBoolean.values();
	
	private Integer idBilheteriaSelecionado;
	private Integer idUsuarioSelecionado;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Guichê Cadastro Bean");
		try {
			if (guiche == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						guiche = guicheServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Guichê ");
					}
				}
				if (this.guiche == null) {
					this.guiche = new Guiche();
					this.guiche.setBilheteria(new Bilheteria());
				} else {
					usuario 				= guiche.getUsuario();
					idBilheteriaSelecionado = guiche.getBilheteria().getId();
				}
				bilheterias = bilheteriaServico.obterTodos("descricao");
				usuarios	= usuarioServico.obterDisponiveisPorGuiche();
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			Guiche gi = new Guiche();
			this.guiche.setBilheteria(bilheteriaServico.obterPorId(idBilheteriaSelecionado));
			if (this.usuario != null) {
				this.guiche.setUsuario(this.usuario);
			}
			this.guiche.setHoraLimiteVendaIngressoAntesEvento(SistemaConstantes.SEIS);
			gi = this.guiche;
			guicheServico.salvar(gi);
			adicionarInfo("Cadastrado com sucesso." + gi);
			return "guiches?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public void associarUsuario() {
		try {
			if (usuario != null) {
				removerUsuario(this.usuario);
			}
			usuario = usuarioServico.obterPorId(idUsuarioSelecionado);
			usuarios.remove(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerUsuario(Usuario us) {
		try {
			usuarios = usuarioServico.obterDisponiveisPorGuiche();
			usuario = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Guiche getGuiche() {
		return guiche;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public List<Bilheteria> getBilheterias() {
		return bilheterias;
	}
	
	public Integer getIdBilheteriaSelecionado() {
		return idBilheteriaSelecionado;
	}
	
	public void setIdBilheteriaSelecionado(Integer idBilheteriaSelecionado) {
		this.idBilheteriaSelecionado = idBilheteriaSelecionado;
	}
	
	public Integer getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}
	
	public void setIdUsuarioSelecionado(Integer idUsuarioSelecionado) {
		this.idUsuarioSelecionado = idUsuarioSelecionado;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public EBoolean[] getOffline() {
		return offline;
	}
	
	public EBoolean[] getImprimirConfirmacao() {
		return imprimirConfirmacao;
	}
	
	public EBoolean[] getImprimirCortesia() {
		return imprimirCortesia;
	}
}