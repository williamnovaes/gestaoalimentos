package br.com.nx.tickets.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.PromocaoServico;

@Named
@ViewScoped
public class PromocaoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PromocaoServico promocaoServico;
	@EJB
	private EventoServico eventoServico;
	
	private Promocao promocao;
	
	private List<Evento> eventos;
	
	private Integer idEventoSelecionado;
	
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
					this.promocao.setEvento(new Evento());
					this.promocao.setUsuario(getLoginBean().getUsuarioLogado());
				} else {
					idEventoSelecionado = promocao.getEvento().getId();
					dataInicio 			= promocao.getDataInicio().getTime();
					dataFim				= promocao.getDataFim().getTime();
				}
			}
			eventos = eventoServico.obterTodos("descricao");
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			this.promocao.setEvento(eventoServico.obterPorId(this.idEventoSelecionado));
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
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public Integer getIdEventoSelecionado() {
		return idEventoSelecionado;
	}
	
	public void setIdEventoSelecionado(Integer idEventoSelecionado) {
		this.idEventoSelecionado = idEventoSelecionado;
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
