package br.com.nx.tickets.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.nx.tickets.componente.Credencial;
import br.com.nx.tickets.dto.ExtratoGuicheDTO;
import br.com.nx.tickets.entidade.AutenticacaoLog;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoPromocao;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.entidade.Retirada;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.EFormatoData;
import br.com.nx.tickets.rest.envio.IngressoEnvioDadosAplicativo;
import br.com.nx.tickets.rest.envio.IngressoEnvioExtratoEvento;
import br.com.nx.tickets.rest.envio.IngressoEnvioLogin;
import br.com.nx.tickets.rest.envio.IngressoEnvioRetirada;
import br.com.nx.tickets.rest.envio.IngressoEnvioSincronizacaoAplicativo;
import br.com.nx.tickets.rest.envio.IngressoEnvioSorteioPromocao;
import br.com.nx.tickets.rest.envio.IngressoEnvioVerificarConexao;
import br.com.nx.tickets.rest.retorno.IngressoRetornoDadosAplicativo;
import br.com.nx.tickets.rest.retorno.IngressoRetornoExtratoEvento;
import br.com.nx.tickets.rest.retorno.IngressoRetornoLogin;
import br.com.nx.tickets.rest.retorno.IngressoRetornoRetirada;
import br.com.nx.tickets.rest.retorno.IngressoRetornoSincronizacaoAplicativo;
import br.com.nx.tickets.rest.retorno.IngressoRetornoSorteioPromocao;
import br.com.nx.tickets.rest.retorno.IngressoRetornoVerificarConexao;
import br.com.nx.tickets.servico.AutenticacaoLogServico;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.IngressoPromocaoServico;
import br.com.nx.tickets.servico.IngressoServico;
import br.com.nx.tickets.servico.IngressoSituacaoServico;
import br.com.nx.tickets.servico.LoteServico;
import br.com.nx.tickets.servico.PedidoServico;
import br.com.nx.tickets.servico.PortariaServico;
import br.com.nx.tickets.servico.PromocaoServico;
import br.com.nx.tickets.servico.RetiradaServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.servico.exception.SaldoIndisponivelException;
import br.com.nx.tickets.util.Util;

@Path("/ingressoOffline")
public class IngressoOfflineRest implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private IngressoServico ingressoServico;
	@EJB
	private IngressoSituacaoServico ingressoSituacaoServico;
	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private PromocaoServico promocaoServico;
	@EJB
	private LoteServico loteServico;
	@EJB
	private PortariaServico portariaServico;
	@EJB
	private GuicheServico guicheServico;
	@EJB
	private AutenticacaoLogServico autenticacaoLogServico;
	@EJB
	private IngressoPromocaoServico ingressoPromocaoServico;
	@EJB
	private RetiradaServico retiradaServico;
	@Inject
	private transient Logger log;

	/**
	 * Clica em configurar
	 */
	@POST
	@Path("logar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logar(IngressoEnvioLogin ingressoEnviavel, @Context HttpServletRequest request) {
		IngressoRetornoLogin retorno = new IngressoRetornoLogin();
		retorno.setEnviarImpressao(EBoolean.TRUE);
		try {
			log.info("LOGAR: " + ingressoEnviavel);
			Credencial credencial = new Credencial(ingressoEnviavel.getLogin(), ingressoEnviavel.getSenha());
			Usuario usuario = usuarioServico.logar(credencial);
			if (usuario != null) {
				ingressoEnviavel.setIdUsuario(usuario.getId());
				usuario.setToken(gerarToken(usuario));
				retorno.setUsuario(usuario);
				if (usuario.getNivel().getDescricao().equals("VENDEDOR")) {
					Guiche guiche = guicheServico.obterPorUsuario(usuario);
					retorno.setGuiche(guiche);
					if (retorno.getGuiche() == null) {
						throw new Exception("Usuário não vinculado à guiches: " + usuario.getLogin() + "!");
					}
					retorno.setEventos(eventoServico.obterAtivosPorGuiche(guiche));
					if (retorno.getEventos() == null || retorno.getEventos().isEmpty()) {
						throw new Exception(
								"Não há eventos ativos disponíveis para o guiche: " + guiche.getDescricao() + "!");
					}
				}

				if (usuario.getNivel().getDescricao().equals("VALIDADOR")) {
					retorno.setEventos(eventoServico.obterAtivos());
					retorno.setPortaria(portariaServico.obterPorUsuario(usuario));
					retorno.setPortarias(portariaServico.obterTodos());
					if (retorno.getPortarias() == null || retorno.getPortarias().isEmpty()) {
						throw new Exception("Não há portarias válidas para o usuário: " + usuario.getLogin() + "!");
					}
					if (retorno.getEventos() == null || retorno.getEventos().isEmpty()) {
						throw new Exception("Não há eventos vigentes para o usuário: " + usuario.getLogin());
					}
				}
				retorno.setCodigoRetorno(ECodigoRetorno.OK);
				retorno.setMensagem("Ok. Autenticacao realizada com sucesso!");
				usuarioServico.atualizarToken(usuario);
			} else {
				retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
				retorno.setUsuario(null);
				retorno.setMensagem("ERROR: Problema na autentiacao!");
			}
			autenticacaoLogServico.salvar(new AutenticacaoLog(ingressoEnviavel));
			log.info(retorno.getMensagem());
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setUsuario(null);
			retorno.setMensagem("ERROR: " + e.getMessage());
			log.error(retorno.getMensagem());
			return Response.ok(retorno).build();
		}
	}

	@POST
	@Path("baixarDadosAplicativo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response baixarDadosAplicativo(IngressoEnvioDadosAplicativo ingressoEnviavel,
			@Context HttpServletRequest request) {
		IngressoRetornoDadosAplicativo retorno = new IngressoRetornoDadosAplicativo();
		try {
			log.info("BAIXAR DADOS DO APLICATIVO: " + ingressoEnviavel);
			Credencial credencial = new Credencial(ingressoEnviavel.getLoginTerminal(),
					ingressoEnviavel.getSenhaTerminal());
			Usuario usuario = usuarioServico.logar(credencial);
			if (usuario != null) {
				Evento evento = eventoServico.obterCompletoPorId(ingressoEnviavel.getIdEvento());
				if (evento == null) {
					throw new BaseServicoException("Evento não encontrado: " + ingressoEnviavel.getIdEvento());
				}
				retorno.setEvento(evento);
				if (usuario.getNivel().getDescricao().equals("VENDEDOR")) {
					retorno.setClienteNome(evento.getCliente().getNome());
					retorno.setClienteTelefoneComercial(Util.formatarTelefone(evento.getCliente().getTelefoneComercial()));
					retorno.setLocalCidade(evento.getLocal().getEndereco().getCidade().getNome() + "-"
							+ evento.getLocal().getEndereco().getCidade().getEstado().getUf());
					retorno.setLocalDescricao(evento.getLocal().getDescricao());
					retorno.setIngressosBilheteria(ingressoServico.obterOffLinePorEventoGuiche(evento, new Guiche(ingressoEnviavel.getIdGuiche())));
					retorno.setIngressosSituacao(ingressoSituacaoServico.obterTodos("id"));
					Promocao promocao = promocaoServico.obterPorEvento(evento);
					if (promocao != null) {
						retorno.setEventoObservacaoPromocao(promocao.getObservacao());
					}
					retorno.setLotes(loteServico.obterLotesValidosPorGuicheEvento(new Guiche(ingressoEnviavel.getIdGuiche()), 
							evento));
					if (retorno.getLotes() == null || retorno.getLotes().isEmpty()) {
						throw new Exception(
								"Não há LOTES vigentes para o evento " + evento.getDescricao() + "!");
					}

					if (retorno.getIngressosBilheteria() == null || retorno.getIngressosBilheteria().isEmpty()) {
						retorno.setLotes(null);
						throw new Exception("Não há ingressos disponíveis para o evento e guiche selecionado: "
								+ usuario.getLogin() + "!");
					}
					retorno.setMensagem("Ok. Dados do usuário " + usuario.getLogin() + " recuperados com sucesso. "
							+ retorno.getIngressosBilheteria().size() + " ingressos disponíveis para venda!");
				} else {
					List<Ingresso> obterValidosPorEvento = ingressoServico.obterValidosPorEvento(evento);
					for (Ingresso ingresso : obterValidosPorEvento) {
						ingresso.setIngressoTipoDescricao(
								ingresso.getGuicheLote().getLote().getIngressoTipo().getDescricao());
						ingresso.setIngressoSituacao(null);
					}
					retorno.setIngressosValidacao(obterValidosPorEvento);
					retorno.setMensagem("Ok. Dados do usuário " + usuario.getLogin() + " recuperados com sucesso. "
							+ retorno.getIngressosValidacao().size() + " ingressos disponíveis para validação!");
				}
				retorno.setUsuarioId(usuario.getId());
				retorno.setUsuarioNome(usuario.getNome());
				retorno.setUsuarioNivel(usuario.getNivel().getDescricao());
				retorno.setCodigoRetorno(ECodigoRetorno.OK);
			} else {
				retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
				retorno.setMensagem("ERROR: Não foi possível baixar os dados!");
			}
			log.info(retorno.getMensagem());
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}

	@POST
	@Path("sincronizarDadosAplicativo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sincronizarDadosAPlicativo(IngressoEnvioSincronizacaoAplicativo ingressoEnviavel,
			@Context HttpServletRequest request) {
		IngressoRetornoSincronizacaoAplicativo retorno = new IngressoRetornoSincronizacaoAplicativo();
		try {
			log.info("SINCRONIZAR: " + ingressoEnviavel);
			Evento evento = eventoServico.obterPorId(ingressoEnviavel.getIdEvento());
			if ((ingressoEnviavel.getPedidos() == null || ingressoEnviavel.getPedidos().size() == 0) 
					&& (ingressoEnviavel.getIngressosCancelados() == null || ingressoEnviavel.getIngressosCancelados().size() == 0)) {
				Integer quantidade = ingressoServico.atualizarIngressosValidacao(ingressoEnviavel.getIngressosValidacao());
				if (quantidade > 0) {
					retorno.setMensagem("Ok. " + quantidade + " ingressos validados atualizados com sucesso!");
				} else {
					retorno.setMensagem("Ok. Não há ingressos de validação para sincronizar!");
				}
				
				List<Ingresso> ingressos = ingressoServico.obterPorEventoSituacao(evento, 
						ingressoSituacaoServico.obterPorDescricao("VALIDADO"));
				for (Ingresso ingresso : ingressos) {
					ingresso.setIngressoSituacao(null);
					ingresso.setSincronizar(EBoolean.FALSE);
				}
				retorno.setIngressosValidados(ingressos);
				retorno.setIngressosCancelados(0);
				retorno.setIngressosValidos(0);
				retorno.setIngressosDisponiveis(0);
				log.info("INGRESSOS VALIDADOS: " + ingressos.size());
			} else {
				Guiche guiche = guicheServico.obterPorId(ingressoEnviavel.getIdGuiche());
				if (ingressoEnviavel.getPedidos() != null) {
					for (Pedido p : ingressoEnviavel.getPedidos()) {
						p.setEvento(evento);
						p.setGuiche(guiche);
					}
				}
				Integer ingressosSincronizados = 0;
				if (ingressoEnviavel.getPedidos() != null) {
					ingressosSincronizados += pedidoServico.sincronizarDadosAplicativo(ingressoEnviavel.getPedidos());
					log.info("SINCRONIZADOS: " + ingressosSincronizados);
				}
				if (ingressoEnviavel.getIngressosCancelados() != null) {
					for (Ingresso i : ingressoEnviavel.getIngressosCancelados()) {
						log.info("CANCELADOS: " + ingressoServico.cancelarIngresso(i));
					}
				}
				
				if (ingressosSincronizados > 0) {
					retorno.setMensagem("Ok. " + ingressosSincronizados + " ingressos sincronizados com sucesso!");
				} else {
					retorno.setMensagem("Ok. Não há pedidos no guiche " + guiche.getDescricao() + " para sincronizar!");
				}
				
				retorno.setIngressosValidos(ingressoServico.obterPorEventoGuicheSituacao(evento, guiche, 
						Arrays.asList(ingressoSituacaoServico.obterPorDescricao("DISPONIVEL"), 
								ingressoSituacaoServico.obterPorDescricao("VALIDADO"))));
				retorno.setIngressosCancelados(ingressoServico.obterPorEventoGuicheSituacao(evento, guiche, 
						Arrays.asList(ingressoSituacaoServico.obterPorDescricao("CANCELADO"))));
				retorno.setIngressosDisponiveis(ingressoServico.obterPorEventoGuicheSituacao(evento, guiche, 
						Arrays.asList(ingressoSituacaoServico.obterPorDescricao("LOTE"))));
			}
			retorno.setDataSincronizacao(Calendar.getInstance());
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			log.info(retorno.getMensagem());
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}

	private String gerarToken(Usuario us) {
		return Util.criptografarString(us.getId() + DataUtil.formatarData(Calendar.getInstance(), EFormatoData.PROTOCOLO));
	}

	@POST
	@Path("sortearPromocao")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sortearPromocao(IngressoEnvioSorteioPromocao ingressoEnviavel,
			@Context HttpServletRequest request) {
		IngressoRetornoSorteioPromocao retorno = new IngressoRetornoSorteioPromocao();
		try {
			log.info("SORTEAR PROMOCAO: " + ingressoEnviavel);
			Evento evento = eventoServico.obterPorId(ingressoEnviavel.getIdEvento());
			List<IngressoPromocao> sorteados = ingressoPromocaoServico.sortear(evento);
			for (IngressoPromocao ingressoPromocao : sorteados) {
				ingressoPromocao.getIngresso().setPortariaValidacao(null);
				ingressoPromocao.getIngresso().setIngressoSituacao(null);
				ingressoPromocao.getIngresso().setObservacaoPromocao(null);
			}
			retorno.setDataSorteio(Calendar.getInstance());
			retorno.setPromocao(promocaoServico.obterPorEvento(evento));
			Collections.sort(sorteados);
			retorno.setSorteados(sorteados);
			retorno.setMensagem("Ok. Sorteio realizado: " + sorteados.size() + " sorteados!");
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}

	@POST
	@Path("obterExtratoEvento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response obterExtratoEvento(IngressoEnvioExtratoEvento ingressoEnviavel,
			@Context HttpServletRequest request) {
		try {
			log.info("EXTRATO EVENTO: " + ingressoEnviavel);
			IngressoRetornoExtratoEvento retorno = new IngressoRetornoExtratoEvento();
			Evento evento = eventoServico.obterPorId(ingressoEnviavel.getIdEvento());
			List<ExtratoGuicheDTO> extratoPorIngressoTipo = guicheServico.obterExtratoPorGuicheEvento(null, evento);
			for (ExtratoGuicheDTO extratoGuicheDTO : extratoPorIngressoTipo) {
				retorno.setQuantidade(retorno.getQuantidade() + extratoGuicheDTO.getQuantidade());
				retorno.setValorTotal(retorno.getValorTotal().add(extratoGuicheDTO.getValorTotal()));
				retorno.setValorTotalTaxaAdministrativa(retorno.getValorTotalTaxaAdministrativa()
						.add(extratoGuicheDTO.getValorTotalTaxaAdministrativa()));
				retorno.setTotal(retorno.getTotal().add(extratoGuicheDTO.getTotal()));
			}
			Collections.sort(extratoPorIngressoTipo);
			retorno.setExtratoPorIngressoTipo(extratoPorIngressoTipo);
//			List<ExtratoGuicheDTO> extratos = eventoServico.obterExtratoVenda(evento);
//			Collections.sort(extratos);
//			retorno.setExtratoEvento(extratos);
			IngressoSituacao iss = ingressoSituacaoServico.obterPorDescricao("CANCELADO");
			List<Ingresso> cancelados = ingressoServico.obterPorEventoSituacao(evento, Arrays.asList(iss));
			HashMap<String, List<Ingresso>> ingressosPorGuiche = new HashMap<>();
			List<Ingresso> ingressos = null;
			for (Ingresso ingresso : cancelados) {
				ingressos = new ArrayList<>();
				ingresso.setIngressoSituacao(null);
				ingresso.setCodigoPromocao(null);
				ingresso.setObservacaoPromocao(null);
				ingresso.setPortariaValidacao(null);
				ingresso.setValor(ingresso.getGuicheLote().getLote().getValor().toString());
				ingresso.setTaxa(ingresso.getGuicheLote().getLote().getValorTaxaAdministrativa().toString());
				ingresso.setTotal(String.valueOf(ingresso.getGuicheLote().getLote().getValor()
						.add(ingresso.getGuicheLote().getLote().getValorTaxaAdministrativa())));
				if (ingressosPorGuiche.containsKey(ingresso.getGuicheLote().getGuiche().getDescricao())) {
					ingressos = ingressosPorGuiche.get(ingresso.getGuicheLote().getGuiche().getDescricao());
				}
				ingressos.add(ingresso);
				ingressosPorGuiche.put(ingresso.getGuicheLote().getGuiche().getDescricao(), ingressos);
			}
			retorno.setCancelados(ingressosPorGuiche);
			retorno.setDataExtrato(Calendar.getInstance());
			retorno.setMensagem("Ok. Retornando extrato!");
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			log.info(retorno.getMensagem());
			log.info("CANCELADOS: " + cancelados.size());
			log.info("INGRESSOS POR GUICHE: " + ingressosPorGuiche.size());
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			IngressoRetornoVerificarConexao retorno = new IngressoRetornoVerificarConexao();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}

	@POST
	@Path("verificarConexao")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response verificarConexao(IngressoEnvioVerificarConexao ingressoEnviavel,
			@Context HttpServletRequest request) {
		try {
			log.info("VERIFICAR CONEXAO: " + ingressoEnviavel);
			IngressoRetornoVerificarConexao retorno = new IngressoRetornoVerificarConexao();
			retorno.setMensagem("Ok. Conexao verificada!");
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			IngressoRetornoVerificarConexao retorno = new IngressoRetornoVerificarConexao();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}
	
	@POST
	@Path("salvarRetirada")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarRetirada(IngressoEnvioRetirada ingressoEnviavel, @Context HttpServletRequest request) {
		IngressoRetornoRetirada retorno = new IngressoRetornoRetirada();
		try {
			log.info("SALVAR RETIRADA : " + ingressoEnviavel);
			Usuario usuario = usuarioServico.logar(new Credencial(ingressoEnviavel.getLogin(), ingressoEnviavel.getSenha()));
			Evento evento   = eventoServico.obterPorId(ingressoEnviavel.getIdEvento());
			eventoServico.validarUsuarioRetirada(evento, usuario);
			Retirada retirada = new Retirada();
			retirada.setGuiche(guicheServico.obterPorId(ingressoEnviavel.getIdGuiche()));
			retirada.setEvento(eventoServico.obterPorId(ingressoEnviavel.getIdEvento()));
			retirada = retiradaServico.salvar(usuario, retirada, ingressoEnviavel.getValorRetirada());
			retorno.setRetirada(retirada);
			retorno.getRetirada().setGuiche(null);
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			retorno.setMensagem("Ok. Retirada informada com sucesso!");
			log.info(retorno.getMensagem());
			return Response.ok(retorno).build();
		} catch (SaldoIndisponivelException e) {
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();	
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}
}