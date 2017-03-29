package br.com.nx.tickets.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.servico.IngressoTipoServico;

@Named
@ViewScoped
public class IngressoTipoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IngressoTipoServico ingressoTipoServico;

	private IngressoTipo ingressoTipo;
	
	private EBoolean[] cortesia = EBoolean.values();

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Ingresso Tipo Cadastro Bean");
		try {
			if (ingressoTipo == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						ingressoTipo = ingressoTipoServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Tipo de Ingresso ");
					}
				}
				if (this.ingressoTipo == null) {
					this.ingressoTipo = new IngressoTipo();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			ingressoTipoServico.salvar(this.ingressoTipo);
			return "ingressosTipos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public IngressoTipo getIngressoTipo() {
		return ingressoTipo;
	}
	
	public EBoolean[] getCortesia() {
		return cortesia;
	}
}