package br.com.nx.tickets.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Atracao;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.AtracaoServico;
import br.com.nx.tickets.servico.ClienteServico;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.PontoVendaServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class EventoListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private EventoServico eventoServico;
	@EJB
	private PontoVendaServico pontoVendaServico;
	@EJB
	private ClienteServico clienteServico;
	@EJB
	private AtracaoServico atracaoServico;
	@EJB
	private UsuarioServico usuarioServico;
	
	private Evento evento;
	private List<PontoVenda> pontosVendasAssociados;
	private List<PontoVenda> pontosVendasDisponiveis;
	private List<Atracao> atracoesAssociadas;
	private List<Atracao> atracoesDisponiveis;
	private List<Usuario> usuariosRetiradaAssociados;
	private List<Usuario> usuariosRetiradaDisponiveis;
	
	private boolean exibirModalPontoVenda;
	private boolean exibirModalEvento;
	private boolean exibirModalAtracao;
	private boolean exibirModalUsuariosRetirada;
	
	private BigDecimal totalPorIngresso;
	private BigDecimal totalTaxaAdministrativa;
	private Integer totalQuantidade;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), eventoServico, SistemaConstantes.DEZ);
		buscarRegistros();
		totalPorIngresso 		= new BigDecimal(0);
		totalTaxaAdministrativa = new BigDecimal(0);
		totalQuantidade 		= 0;
	}

	public void buscarRegistros() {
		try {
			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obterPontosVendasPorEvento(Evento ev) {
		try {
			this.evento = ev;
			exibirModalPontoVenda = true;
			pontosVendasDisponiveis = clienteServico.obterPontosVendas(ev.getCliente());
			pontosVendasAssociados = eventoServico.obterPontosVendas(this.evento);
			pontosVendasDisponiveis.removeAll(pontosVendasAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarPontoVenda(PontoVenda pv) {
		try {
			pontosVendasAssociados.add(pv);
			pontosVendasDisponiveis.remove(pv);
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
	
	public void salvarPontosVendasAssociados() {
		try {
			eventoServico.atualizarPontosVendas(this.evento, this.pontosVendasAssociados);
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void obterAtracoesPorEvento(Evento ev) {
		try {
			this.evento = ev;
			exibirModalAtracao = true;
			atracoesDisponiveis = atracaoServico.obterTodos("nome");
			atracoesAssociadas = eventoServico.obterAtracoes(this.evento);
			atracoesDisponiveis.removeAll(atracoesAssociadas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarAtracao(Atracao atracao) {
		try {
			atracoesAssociadas.add(atracao);
			atracoesDisponiveis.remove(atracao);
			
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
	
	public void salvarAtracoesAssociadas() {
		try {
			getLog().info("EVENTO: " + evento.getId());
			eventoServico.atualizarAtracoes(this.evento, this.atracoesAssociadas);
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Usuarios retirada 
	 * */
	public void obterUsuariosRetiradaPorEvento(Evento ev) {
		try {
			this.evento = ev;
			exibirModalUsuariosRetirada = true;
			usuariosRetiradaDisponiveis = usuarioServico.obterTodos("nome");
			usuariosRetiradaAssociados = eventoServico.obterUsuariosRetirada(this.evento);
			usuariosRetiradaDisponiveis.removeAll(usuariosRetiradaAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarUsuarioRetirada(Usuario usuario) {
		try {
			usuariosRetiradaAssociados.add(usuario);
			usuariosRetiradaDisponiveis.remove(usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerUsuarioRetirada(Usuario usuario) {
		try {
			usuariosRetiradaAssociados.remove(usuario);
			usuariosRetiradaDisponiveis.add(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarUsuariosRetiradaAssociados() {
		try {
			getLog().info("EVENTO: " + evento.getId());
			eventoServico.atualizarUsuarios(this.evento, this.usuariosRetiradaAssociados);
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exibirDetalhesEvento(Evento ev) {
		exibirModalEvento = true;
		this.evento = ev;
	}
	
	@Override
	public void fecharModal() {
		exibirModalEvento 			= false;
		exibirModalPontoVenda 		= false;
		exibirModalAtracao 			= false;
		exibirModalUsuariosRetirada = false;
		totalPorIngresso 			= new BigDecimal(0);
		totalTaxaAdministrativa 	= new BigDecimal(0);
		totalQuantidade 			= 0;
	}
	
	public void alterarSituacao(Evento ev) {
		try {
			ev.alterarSituacao();
			eventoServico.alterar(ev);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situacao do Evento!");
			e.printStackTrace();
		}
	}
	
	public boolean isExibirModalPontoVenda() {
		return exibirModalPontoVenda;
	}
	
	public boolean isExibirModalEvento() {
		return exibirModalEvento;
	}
	
	public List<PontoVenda> getPontosVendasAssociados() {
		return pontosVendasAssociados;
	}
	
	public List<PontoVenda> getPontosVendasDisponiveis() {
		return pontosVendasDisponiveis;
	}
	
	public BigDecimal getTotalPorIngresso() {
		return totalPorIngresso;
	}
	
	public Integer getTotalQuantidade() {
		return totalQuantidade;
	}
	
	public BigDecimal getTotalTaxaAdministrativa() {
		return totalTaxaAdministrativa;
	}

	public Evento getEvento() {
		return evento;
	}
	
	public List<Atracao> getAtracoesAssociadas() {
		return atracoesAssociadas;
	}
	
	public List<Atracao> getAtracoesDisponiveis() {
		return atracoesDisponiveis;
	}
	
	public List<Usuario> getUsuariosRetiradaAssociados() {
		return usuariosRetiradaAssociados;
	}
	
	public List<Usuario> getUsuariosRetiradaDisponiveis() {
		return usuariosRetiradaDisponiveis;
	}
	
	public boolean isExibirModalAtracao() {
		return exibirModalAtracao;
	}
	
	public boolean isExibirModalUsuariosRetirada() {
		return exibirModalUsuariosRetirada;
	}
}
