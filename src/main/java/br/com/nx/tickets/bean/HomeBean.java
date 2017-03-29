package br.com.nx.tickets.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.dto.ExtratoRetiradaDTO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Retirada;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.RetiradaServico;
import br.com.nx.tickets.servico.exception.SaldoIndisponivelException;

@Named
@ViewScoped
public class HomeBean extends BaseBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private EventoServico eventoServico;
	@EJB
	private GuicheServico guicheServico;
	@EJB
	private RetiradaServico retiradaServico;
	
	private List<ExtratoGuicheDTO> extratos;
	private ExtratoGuicheDTO extratoEventoTotal;
	private ExtratoGuicheDTO extratoGuiche;
	private List<ExtratoGuicheDTO> extratosGuiches;
	private ExtratoRetiradaDTO extratoRetirada;
	private List<Evento> eventos;
	private Integer idEventoSelecionado;
	private Evento evento;
	private Guiche guiche;
	private boolean exibirExtratoModal;
	private BigDecimal valorRetirada;

	@PostConstruct
	public void inicializar() {
		try {
			eventos = eventoServico.obterDisponiveisPorUsuario(getLoginBean().getUsuarioLogado());
			if (eventos.size() == 1) {
				idEventoSelecionado = eventos.get(0).getId(); 
				buscarExtrato();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarExtrato() {
		try {
			evento = eventoServico.obterCompletoPorId(idEventoSelecionado);
			if (evento != null) {
				extratos = eventoServico.obterExtratoVenda(evento);
				extratoEventoTotal = new ExtratoGuicheDTO();
				for (ExtratoGuicheDTO eg : extratos) {
					extratoEventoTotal.setQuantidade(extratoEventoTotal.getQuantidade() + eg.getQuantidade());
					extratoEventoTotal.setValorTotal(extratoEventoTotal.getValorTotal().add(eg.getValorTotal()));
					extratoEventoTotal.setTotal(extratoEventoTotal.getTotal().add(eg.getTotal()));
					extratoEventoTotal.setValorTotalTaxaAdministrativa(extratoEventoTotal
							.getValorTotalTaxaAdministrativa().add(eg.getValorTotalTaxaAdministrativa()));
					ExtratoRetiradaDTO exx = guicheServico.obterExtratoRetiradaPorGuicheEvento(new Guiche(eg.getId()), 
							this.evento);
					eg.setTotalRetirada(exx.getSaldoDisponivel());
					extratoEventoTotal.setTotalRetirada(extratoEventoTotal.getTotalRetirada().add(eg.getTotalRetirada()));
				}
			} else {
				extratos = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void detalharGuiche(Integer idGuiche) {
		try {
			if (idGuiche > 0) {
				this.guiche = guicheServico.obterPorId(idGuiche);
			} else {
				guiche = null;
			}
			extratosGuiches 	 	= guicheServico.obterExtratoPorGuicheEvento(guiche, evento);
			extratoGuiche    	= new ExtratoGuicheDTO();
			extratoGuiche.setNome("TOTAL");
			for (ExtratoGuicheDTO ex : extratosGuiches) {
				extratoGuiche.setQuantidade(extratoGuiche.getQuantidade() + ex.getQuantidade());
				extratoGuiche.setValorTotal(extratoGuiche.getValorTotal().add(ex.getValorTotal()));
				extratoGuiche.setValorTotalTaxaAdministrativa(extratoGuiche
						.getValorTotalTaxaAdministrativa().add(ex.getValorTotalTaxaAdministrativa()));
			}
			if (this.guiche != null) {
				extratoRetirada = guicheServico.obterExtratoRetiradaPorGuicheEvento(this.guiche, this.evento);
			}
			exibirExtratoModal = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarRetirada() {
		try {
			Retirada retirada = new Retirada();
			retirada.setUsuario(getLoginBean().getUsuarioLogado());
			retirada.setGuiche(this.guiche);
			retirada.setEvento(this.evento);
			retirada = retiradaServico.salvar(getLoginBean().getUsuarioLogado(), retirada, valorRetirada);
			valorRetirada = null;
			this.detalharGuiche(this.guiche.getId());
		} catch (SaldoIndisponivelException e) {
			adicionarError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}
	
	@Override
	public void fecharModal() {
		exibirExtratoModal = false;
	}
	
	public List<ExtratoGuicheDTO> getExtratos() {
		return extratos;
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
	
	public ExtratoGuicheDTO getExtratoEventoTotal() {
		return extratoEventoTotal;
	}

	public ExtratoGuicheDTO getExtratoGuiche() {
		return extratoGuiche;
	}
	
	public boolean isExibirExtratoModal() {
		return exibirExtratoModal;
	}
	
	public Guiche getGuiche() {
		return guiche;
	}
	
	public List<ExtratoGuicheDTO> getExtratosGuiches() {
		return extratosGuiches;
	}
	
	public BigDecimal getValorRetirada() {
		return valorRetirada;
	}
	
	public void setValorRetirada(BigDecimal valorRetirada) {
		this.valorRetirada = valorRetirada;
	}
	
	public ExtratoRetiradaDTO getExtratoRetirada() {
		return extratoRetirada;
	}
}