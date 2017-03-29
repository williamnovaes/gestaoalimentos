package br.com.nx.tickets.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Segmento;
import br.com.nx.tickets.servico.SegmentoServico;

@Named
@ViewScoped
public class SegmentoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private SegmentoServico segmentoServico;
	
	private Segmento segmento;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Segmento Cadastro Bean");
		try {
			if (segmento == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						segmento = segmentoServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Segmento ");
					}
				}
				if (this.segmento == null) {
					this.segmento = new Segmento();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			segmentoServico.salvar(this.segmento);
			return "segmentos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Segmento getSegmento() {
		return segmento;
	}
}
