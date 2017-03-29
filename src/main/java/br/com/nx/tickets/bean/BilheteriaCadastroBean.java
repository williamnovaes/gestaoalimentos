package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Bilheteria;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.servico.BilheteriaServico;
import br.com.nx.tickets.servico.PontoVendaServico;
import br.com.nx.tickets.servico.PortariaServico;

@Named
@ViewScoped
public class BilheteriaCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private BilheteriaServico bilheteriaServico;
	@EJB
	private PortariaServico portariaServico;
	@EJB
	private PontoVendaServico pontoVendaServico;

	private Bilheteria bilheteria;
	
	private List<Portaria> portarias;
	private List<PontoVenda> pontosVendas;
	
	private Integer idPortariaSelecionado;
	private Integer idPontoVendaSelecionado;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Lote Cadastro Bean");
		try {
			if (bilheteria == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						bilheteria = bilheteriaServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Lote ");
					}
				}
				pontosVendas = pontoVendaServico.obterTodos("nome");
				portarias	 = portariaServico.obterTodos("descricao");
				if (this.bilheteria == null) {
					this.bilheteria = new Bilheteria();
					this.bilheteria.setPontoVenda(new PontoVenda());
					this.bilheteria.setPortaria(new Portaria());
				} else {
					idPortariaSelecionado 	= bilheteria.getPortaria().getId();
					idPontoVendaSelecionado = bilheteria.getPontoVenda().getId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String salvar() {
		try {
			Bilheteria bi = new Bilheteria();
			this.bilheteria.setPontoVenda(pontoVendaServico.obterPorId(idPontoVendaSelecionado));
			this.bilheteria.setPortaria(portariaServico.obterPorId(idPortariaSelecionado));
			
			bi = this.bilheteria;
			if (this.bilheteria.getId() != null) {
				bilheteriaServico.alterar(bi);
				adicionarInfo("Bilheteria Alterado com Sucesso!");
			} else {
				bilheteriaServico.salvar(bi);
				adicionarInfo("Bilheteria Salvo com Sucesso!");
			}
			return "bilheterias?faces-redirect=true";
		} catch (Exception e) {
			adicionarError("Erro ao salvar Bilheteria!");
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PontoVenda> getPontosVendas() {
		return pontosVendas;
	}
	
	public List<Portaria> getPortarias() {
		return portarias;
	}
	
	public Integer getIdPontoVendaSelecionado() {
		return idPontoVendaSelecionado;
	}
	
	public void setIdPontoVendaSelecionado(Integer idPontoVendaSelecionado) {
		this.idPontoVendaSelecionado = idPontoVendaSelecionado;
	}
	
	public Integer getIdPortariaSelecionado() {
		return idPortariaSelecionado;
	}
	
	public void setIdPortariaSelecionado(Integer idPortariaSelecionado) {
		this.idPortariaSelecionado = idPortariaSelecionado;
	}
	
	public Bilheteria getBilheteria() {
		return bilheteria;
	}
}