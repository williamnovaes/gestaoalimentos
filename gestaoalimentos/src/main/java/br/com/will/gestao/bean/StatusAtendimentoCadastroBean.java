package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.StatusAtendimento;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.servico.StatusAtendimentoServico;

@Named
@ViewScoped
public class StatusAtendimentoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private StatusAtendimentoServico statusAtendimentoServico;
	
	private StatusAtendimento statusAtendimento;
	private EBoolean[] ebooleans = EBoolean.values();
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Status Atendimento Cadastro Bean");
		try {
			if (statusAtendimento == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						statusAtendimento = statusAtendimentoServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.statusAtendimento == null) {
					this.statusAtendimento = new StatusAtendimento();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (this.statusAtendimento.getId() != null) {
				statusAtendimentoServico.alterar(this.statusAtendimento);
				adicionarInfo("Status de atendimento " + this.statusAtendimento.getDescricao() + " alterado com sucesso");
			} else {
				statusAtendimentoServico.salvar(this.statusAtendimento);
				adicionarInfo("Status de atendimento cadastrado com sucesso");
			}
			
			return "statusAtendimentos?faces-redirect=true";
		} catch (Exception e) {
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public StatusAtendimento getStatusAtendimento() {
		return statusAtendimento;
	}
	
	public void setStatusAtendimento(StatusAtendimento statusAtendimento) {
		this.statusAtendimento = statusAtendimento;
	}
	
	public EBoolean[] getEbooleans() {
		return ebooleans;
	}
}