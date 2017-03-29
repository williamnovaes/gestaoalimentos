package br.com.nx.tickets.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.EFormatoData;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.entidade.util.ETipoGuiche;
import br.com.nx.tickets.servico.BilheteriaServico;
import br.com.nx.tickets.servico.ClienteServico;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.IngressoTipoServico;
import br.com.nx.tickets.servico.LoteServico;
import br.com.nx.tickets.servico.PontoVendaServico;
import br.com.nx.tickets.servico.UsuarioServico;

@Named
@ViewScoped
public class LoteCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private LoteServico loteServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private GuicheServico guicheServico;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private ClienteServico clienteServico;
	@EJB
	private PontoVendaServico pontoVendaServico;
	@EJB
	private BilheteriaServico bilheteriaServico;
	@EJB
	private IngressoTipoServico ingressoTipoServico;

	private Lote lote;
	private Evento evento;
	private IngressoTipo ingressoTipo;
	private List<Evento> eventos;
	private ETipoGuiche[] tiposGuiches;
	private List<IngressoTipo> lotesTipos;
	private List<Guiche> guichesAssociados;
	private List<Guiche> guichesDisponiveis;
	private List<IngressoTipo> ingressosTipos;
	// private List<Usuario> usuarios;

	private String valor;
	private Integer idEventoSelecionado;
//	private Integer idGuicheSelecionado;
	private String tipoGuicheSelecionado;
	private Integer[] guichesSelecionados;
	private Integer idIngressoTipoSelecionado;
	// private Integer idUsuarioSelecionado;

	private Date dataFim;
	private Date dataInicio;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Lote Cadastro Bean");
		try {
			if (lote == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						lote = loteServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Lote ");
					}
				}
				if (this.lote == null) {
					this.lote = new Lote();
					this.lote.setEvento(new Evento());
					this.lote.setUsuario(new Usuario());
					this.lote.setIngressoTipo(new IngressoTipo());
					ingressosTipos 		= new ArrayList<>();
					guichesAssociados 	= new ArrayList<>();
					guichesDisponiveis 	= new ArrayList<>();
				} else {
					valor 						= lote.getValor().toString();
					evento 						= lote.getEvento();
					dataFim 					= lote.getDataFim().getTime();
					dataInicio 					= lote.getDataInicio().getTime();
					ingressosTipos 				= ingressoTipoServico.obterPorCliente(evento.getCliente());
					guichesAssociados 			= guicheServico.obterPorLote(lote);
					guichesDisponiveis 			= guicheServico.obterPorEvento(evento, null);
					idEventoSelecionado 		= lote.getEvento().getId();
					idIngressoTipoSelecionado 	= lote.getIngressoTipo().getId();
					guichesDisponiveis.removeAll(guichesAssociados);
				}
			}
			eventos = eventoServico.obterAtivos();
			lotesTipos = ingressoTipoServico.obterTodos("descricao");
			tiposGuiches = ETipoGuiche.values();
			tipoGuicheSelecionado = ETipoGuiche.AMBOS.getLabel();
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			Lote lt = new Lote();
			lote.setDataInicio(DataUtil.getCalendar(this.dataInicio));
			lote.setDataFim(DataUtil.getCalendar(this.dataFim));
			lote.setValor(new BigDecimal(this.valor));

			if (lt.getId() != null) {
				lt = this.lote;
//				loteServico.alterar(lt);
			} else {
				lote.setUsuario(getLoginBean().getUsuarioLogado());
				lote.setEvento(eventoServico.obterPorId(this.idEventoSelecionado));
				lote.setIngressoTipo(ingressoTipoServico.obterPorId(this.idIngressoTipoSelecionado));
				List<Lote> lotes = loteServico.obterLotePorEvento(this.lote.getEvento());
				lt = this.lote;
				if (verificaDataVigente(lotes)) {
					loteServico.salvar(lt);
					if (!guichesAssociados.isEmpty()) {
						loteServico.atualizarGuiches(lt, guichesAssociados);
					}
				} else {
					return null;
				}
			}
			adicionarInfo("Cadastrado com sucesso.");
			return "lotes?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	private boolean verificaDataVigente(List<Lote> lotes) {
		for (Lote ltAux : lotes) {
			if (ltAux.getIngressoTipo().equals(this.lote.getIngressoTipo())
					&& ltAux.getSituacao().equals(ESituacao.ATIVO)) {
				if (this.lote.getId() != ltAux.getId()) {
					if (this.lote.getDataInicio().compareTo(ltAux.getDataFim()) <= 0) {
						adicionarError("Já existe um lote de mesmo tipo e vigencia cadastrado! "
								+ DataUtil.formatarData(ltAux.getDataFim(), EFormatoData.BRASILEIRO_DATA_HORA));
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void buscarIngressoTipo() {
		try {
			ingressoTipo = ingressoTipoServico.obterPorId(idIngressoTipoSelecionado);
			obterGuiches();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obterIngressosTiposPorCliente() {
		try {
			this.evento = eventoServico.obterPorIdComCliente(idEventoSelecionado);
			ingressosTipos = ingressoTipoServico
					.obterPorCliente(clienteServico.obterPorId(this.evento.getCliente().getId()));
			limparCombos();
			obterGuiches();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void limparCombos() {
		try {
			guichesDisponiveis = new ArrayList<>();
			guichesAssociados = new ArrayList<>();
			tipoGuicheSelecionado = ETipoGuiche.AMBOS.getLabel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obterGuiches() {
		try {
			guichesSelecionados = null;
			if (tipoGuicheSelecionado.equals("AMBOS")) {
				guichesDisponiveis = guicheServico.obterPorEvento(this.evento, null);
			} else if (tipoGuicheSelecionado.equals("ONLINE")) {
				guichesDisponiveis = guicheServico.obterPorEvento(this.evento, EBoolean.FALSE);
			} else if (tipoGuicheSelecionado.equals("OFFLINE")) {
				guichesDisponiveis = guicheServico.obterPorEvento(this.evento, EBoolean.TRUE);
			}
			if (!guichesAssociados.isEmpty()) {
				guichesDisponiveis.removeAll(guichesAssociados);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void associarGuiches() {
//		try {
//			Guiche g = new Guiche();
//			if (idGuicheSelecionado != null) {
//				g = guicheServico.obterPorId(idGuicheSelecionado);
//				guichesAssociados.add(g);
//				guichesDisponiveis.remove(g);
//				this.idGuicheSelecionado = null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void associarGuiches() {
		try {
			if (guichesSelecionados != null && guichesSelecionados.length > 0) {
				for (Integer id : guichesSelecionados) {
					Guiche gi = guicheServico.obterCompletoPorId(id);
					if (gi != null && !guichesAssociados.contains(gi)) {
						guichesAssociados.add(gi);
					}
				}
				guichesDisponiveis.removeAll(guichesAssociados);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removerGuiches(Guiche gi) {
		guichesAssociados.remove(gi);
		guichesDisponiveis.add(gi);
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public List<IngressoTipo> getLotesTipos() {
		return lotesTipos;
	}

	public List<IngressoTipo> getIngressosTipos() {
		return ingressosTipos;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public Integer getIdIngressoTipoSelecionado() {
		return idIngressoTipoSelecionado;
	}

	public void setIdIngressoTipoSelecionado(Integer idIngressoTipoSelecionado) {
		this.idIngressoTipoSelecionado = idIngressoTipoSelecionado;
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public List<Guiche> getGuichesDisponiveis() {
		return guichesDisponiveis;
	}

	public List<Guiche> getGuichesAssociados() {
		return guichesAssociados;
	}

//	public Integer getIdGuicheSelecionado() {
//		return idGuicheSelecionado;
//	}
//
//	public void setIdGuicheSelecionado(Integer idGuicheSelecionado) {
//		this.idGuicheSelecionado = idGuicheSelecionado;
//	}
	
	public Integer[] getGuichesSelecionados() {
		return guichesSelecionados;
	}
	
	public void setGuichesSelecionados(Integer[] guichesSelecionados) {
		this.guichesSelecionados = guichesSelecionados;
	}
	
	public ETipoGuiche[] getTiposGuiches() {
		return tiposGuiches;
	}
	
	public String getTipoGuicheSelecionado() {
		return tipoGuicheSelecionado;
	}
	
	public void setTipoGuicheSelecionado(String tipoGuicheSelecionado) {
		this.tipoGuicheSelecionado = tipoGuicheSelecionado;
	}
}