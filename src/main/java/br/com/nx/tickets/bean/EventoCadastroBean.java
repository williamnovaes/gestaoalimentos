package br.com.nx.tickets.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Atracao;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.EventoTipo;
import br.com.nx.tickets.entidade.Local;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.servico.AtracaoServico;
import br.com.nx.tickets.servico.ClienteServico;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.EventoTipoServico;
import br.com.nx.tickets.servico.LocalServico;
import br.com.nx.tickets.servico.PontoVendaServico;
import br.com.nx.tickets.servico.UsuarioServico;

@Named
@ViewScoped
public class EventoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private LocalServico localServico;
	@EJB
	private ClienteServico clienteServico;
	@EJB
	private EventoTipoServico eventoTipoServico;
	@EJB
	private PontoVendaServico pontoVendaServico;
	@EJB
	private AtracaoServico atracaoServico;
	@EJB
	private UsuarioServico usuarioServico;

	private Evento evento;
	private Cliente cliente;
	private List<Local> locais;
	private List<Cliente> clientes;
	private List<Usuario> usuariosDisponiveis;
	private List<Usuario> usuariosAssociados;
	private List<EventoTipo> eventosTipos;
	private List<Atracao> atracoesAssociadas;
	private List<Atracao> atracoesDisponiveis;
	private List<PontoVenda> pontosVendasDisponiveis;
	private List<PontoVenda> pontosVendasAssociados;
	
	private Integer idClienteSelecionado;
	private Integer idEventoTipoSelecionado;
	private Integer idLocalSelecionado;
	private Integer idAtracaoSelecionada;
	private Integer idUsuarioSelecionado;
	private Integer idPontoVendaSelecionado;

	private Date dataInicioVendaIngresso;
	private Date dataFimVendaIngresso;
	private Date dataAberturaPortao;
	private Date dataEvento;

	@PostConstruct
	public void inicializar() {
		try {
			if (evento == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("idEvento");
				if (idParam != null && !idParam.equals("")) {
					try {
						evento = eventoServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Evento ");
					}
				}
				if (this.evento == null) {
					this.evento = new Evento();
					this.evento.setLocal(new Local());
					this.evento.setEventoTipo(new EventoTipo());
					this.evento.setCliente(new Cliente());
					pontosVendasAssociados 	= new ArrayList<>();
					pontosVendasDisponiveis = new ArrayList<>();
					clientes 				= clienteServico.obterTodos("nome");
					locais					= localServico.obterTodos("descricao");
					eventosTipos 			= eventoTipoServico.obterTodos("descricao");
					usuariosDisponiveis		= usuarioServico.obterTodos("nome");
					usuariosAssociados		= new ArrayList<>();
					atracoesDisponiveis	 	= atracaoServico.obterTodos("nome");
					atracoesAssociadas 		= new ArrayList<>();
				} else {
					pontosVendasAssociados 	= eventoServico.obterPontosVendas(evento);
					pontosVendasDisponiveis = new ArrayList<>();
					pontosVendasDisponiveis.removeAll(pontosVendasAssociados);
					clientes 				= new ArrayList<>();
					eventosTipos 			= new ArrayList<>();
					locais	 				= localServico.obterTodos("descricao");
					clientes.add(clienteServico.obterPorId(evento.getCliente().getId()));
					eventosTipos.add(evento.getEventoTipo());
					idClienteSelecionado 	= evento.getCliente().getId();
					idLocalSelecionado 		= evento.getLocal().getId();
					idEventoTipoSelecionado = evento.getEventoTipo().getId();
					usuariosAssociados 		= eventoServico.obterUsuariosRetirada(evento);
					usuariosDisponiveis 	= usuarioServico.obterTodos("nome");
					atracoesAssociadas 		= eventoServico.obterAtracoes(evento);
					atracoesDisponiveis	 	= atracaoServico.obterTodos("nome");
					dataEvento 				= evento.getDataEvento().getTime();
					dataAberturaPortao 		= evento.getDataAberturaPortao().getTime();
					dataFimVendaIngresso 	= evento.getDataFimVendaIngresso().getTime();
					dataInicioVendaIngresso = evento.getDataInicioVendaIngresso().getTime();
					usuariosDisponiveis.removeAll(usuariosAssociados);
					atracoesDisponiveis.removeAll(atracoesAssociadas);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (this.evento.getId() != null) {
				evento.setDataAberturaPortao(DataUtil.getCalendar(this.dataAberturaPortao));
				evento.setDataInicioVendaIngresso(DataUtil.getCalendar(this.dataInicioVendaIngresso));
				evento.setDataEvento(DataUtil.getCalendar(this.dataEvento));
				evento.setDataFimVendaIngresso(DataUtil.getCalendar(dataFimVendaIngresso));
				evento.setLocal(localServico.obterPorId(this.idLocalSelecionado));
				evento = eventoServico.alterar(evento);
				eventoServico.atualizarUsuarios(evento, usuariosAssociados);
				eventoServico.atualizarPontosVendas(evento, pontosVendasAssociados);
				eventoServico.atualizarAtracoes(evento, atracoesAssociadas);
				adicionarInfo("Evento Alterado com sucesso.");
			} else {
				evento.setDataAberturaPortao(DataUtil.getCalendar(this.dataAberturaPortao));
				evento.setDataInicioVendaIngresso(DataUtil.getCalendar(this.dataInicioVendaIngresso));
				evento.setDataEvento(DataUtil.getCalendar(this.dataEvento));
				evento.setDataFimVendaIngresso(DataUtil.getCalendar(dataFimVendaIngresso));
				evento.setCliente(clienteServico.obterPorId(this.idClienteSelecionado));
				evento.setLocal(localServico.obterPorId(this.idLocalSelecionado));
				evento.setEventoTipo(eventoTipoServico.obterPorId(this.idEventoTipoSelecionado));
				evento = eventoServico.salvar(evento);
				eventoServico.atualizarUsuarios(evento, usuariosAssociados);
				eventoServico.atualizarPontosVendas(evento, pontosVendasAssociados);
				eventoServico.atualizarAtracoes(evento, atracoesAssociadas);
				adicionarInfo("Evento salvo com sucesso.");
			}
			return "eventos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError("Erro ao Salvar Evento.");
			return null;
		}
	}

	public void obterPontosVendasPorCliente() {
		try {
			pontosVendasDisponiveis = new ArrayList<>();
			pontosVendasAssociados 	= new ArrayList<>();
			if (idClienteSelecionado != null && idClienteSelecionado > 0) {
				this.cliente = clienteServico.obterPorId(idClienteSelecionado);
				pontosVendasDisponiveis = clienteServico.obterPontosVendas(this.cliente);
				idPontoVendaSelecionado = null;
			} else {
				idPontoVendaSelecionado = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adicionarPontoVenda() {
		try {
			if (this.idPontoVendaSelecionado != null) {
				pontosVendasAssociados.add(pontoVendaServico.obterPorId(this.idPontoVendaSelecionado));
				pontosVendasDisponiveis.removeAll(pontosVendasAssociados);
				this.idPontoVendaSelecionado = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removerPontoVenda(PontoVenda pv) {
		try {
			pontosVendasAssociados.remove(pv);
			pontosVendasDisponiveis.add(pv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarAtracao() {
		try {
			if (this.idAtracaoSelecionada != null) {
				atracoesAssociadas.add(atracaoServico.obterPorId(this.idAtracaoSelecionada));
				atracoesDisponiveis.removeAll(atracoesAssociadas);
				this.idAtracaoSelecionada = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerAtracao(Atracao atracao) {
		try {
			atracoesAssociadas.remove(atracao);
			atracoesDisponiveis.add(atracao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarUsuario() {
		try {
			if (this.idUsuarioSelecionado != null) {
				usuariosAssociados.add(usuarioServico.obterPorId(this.idUsuarioSelecionado));
				usuariosDisponiveis.removeAll(usuariosAssociados);
				this.idUsuarioSelecionado = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerUsuairo(Usuario usuario) {
		try {
			usuariosAssociados.remove(usuario);
			usuariosDisponiveis.add(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<EventoTipo> getEventosTipos() {
		return eventosTipos;
	}

	public List<Local> getLocais() {
		return locais;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Integer getIdClienteSelecionado() {
		return idClienteSelecionado;
	}

	public void setIdClienteSelecionado(Integer idClienteSelecionado) {
		this.idClienteSelecionado = idClienteSelecionado;
	}

	public Integer getIdEventoTipoSelecionado() {
		return idEventoTipoSelecionado;
	}

	public void setIdEventoTipoSelecionado(Integer idEventoTipoSelecionado) {
		this.idEventoTipoSelecionado = idEventoTipoSelecionado;
	}

	public Integer getIdLocalSelecionado() {
		return idLocalSelecionado;
	}

	public void setIdLocalSelecionado(Integer idLocalSelecionado) {
		this.idLocalSelecionado = idLocalSelecionado;
	}

	public List<PontoVenda> getPontosVendasDisponiveis() {
		return pontosVendasDisponiveis;
	}

	public List<PontoVenda> getPontosVendasAssociados() {
		return pontosVendasAssociados;
	}

	public Integer getIdPontoVendaSelecionado() {
		return idPontoVendaSelecionado;
	}
	
	public void setIdPontoVendaSelecionado(Integer idPontoVendaSelecionado) {
		this.idPontoVendaSelecionado = idPontoVendaSelecionado;
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	
	public Integer getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}
	
	public void setIdUsuarioSelecionado(Integer idUsuarioSelecionado) {
		this.idUsuarioSelecionado = idUsuarioSelecionado;
	}
	
	public List<Atracao> getAtracoesAssociadas() {
		return atracoesAssociadas;
	}
	
	public List<Atracao> getAtracoesDisponiveis() {
		return atracoesDisponiveis;
	}
	
	public Integer getIdAtracaoSelecionada() {
		return idAtracaoSelecionada;
	}
	
	public void setIdAtracaoSelecionada(Integer idAtracaoSelecionada) {
		this.idAtracaoSelecionada = idAtracaoSelecionada;
	}
	
	public List<Usuario> getUsuarios() {
		return usuariosDisponiveis;
	}
	
	public List<Usuario> getUsuariosDisponiveis() {
		return usuariosDisponiveis;
	}
	
	public List<Usuario> getUsuariosAssociados() {
		return usuariosAssociados;
	}

	public Date getDataEvento() {
		return dataEvento;
	}
	
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	
	public Date getDataAberturaPortao() {
		return this.dataAberturaPortao;
	}
	
	public void setDataAberturaPortao(Date dataAberturaPortao) {
		this.dataAberturaPortao = dataAberturaPortao;
	}

	public Date getDataInicioVendaIngresso() {
		return this.dataInicioVendaIngresso;
	}
	
	public void setDataInicioVendaIngresso(Date dataInicioVendaIngresso) {
		this.dataInicioVendaIngresso = dataInicioVendaIngresso;
	}

	public Date getDataFimVendaIngresso() {
		return this.dataFimVendaIngresso;
	}
	
	public void setDataFimVendaIngresso(Date dataFimVendaIngresso) {
		this.dataFimVendaIngresso = dataFimVendaIngresso;
	}
}