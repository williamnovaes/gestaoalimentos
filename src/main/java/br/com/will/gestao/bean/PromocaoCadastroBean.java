package br.com.will.gestao.bean;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Promocao;
import br.com.will.gestao.entidade.util.DataUtil;
import br.com.will.gestao.servico.PromocaoServico;

@Named
@ViewScoped
public class PromocaoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PromocaoServico promocaoServico;
	
	private Promocao promocao;
	
	private Date dataInicio;
	private Date dataFim;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Promoção Cadastro Bean");
		try {
			if (promocao == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						promocao = promocaoServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Promoção ");
					}
				}
				if (this.promocao == null) {
					this.promocao = new Promocao();
					this.promocao.setUsuario(getLoginBean().getUsuarioLogado());
				} else {
					dataInicio 			= promocao.getDataInicio().getTime();
					dataFim				= promocao.getDataFim().getTime();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			this.promocao.setDataInicio(DataUtil.getCalendar(this.dataInicio));
			this.promocao.setDataFim(DataUtil.getCalendar(this.dataFim));
			if (this.promocao.getId() != null) {
				promocaoServico.alterar(this.promocao);
				adicionarInfo("Promoção alterada com sucesso!");
			} else {
				promocaoServico.salvar(this.promocao);
				adicionarInfo("Promoção salva com sucesso!");
			}
			return "promocoes?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Promocao getPromocao() {
		return promocao;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
}
