package br.com.nx.tickets.rest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
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
import br.com.nx.tickets.dto.ExtratoPedidoDTO;
import br.com.nx.tickets.entidade.AutenticacaoLog;
import br.com.nx.tickets.entidade.Bilheteria;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.EventoSocioTorcedor;
import br.com.nx.tickets.entidade.EventoTipo;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.GuicheLote;
import br.com.nx.tickets.entidade.ImpressaoTesteLog;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoTipo;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.entidade.PontoVenda;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Retirada;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.socio.SocioTorcedor;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EBoolean;
import br.com.nx.tickets.entidade.util.EFormatoData;
import br.com.nx.tickets.entidade.util.EPagamentoTipo;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.rest.envio.IngressoEnvioCortesia;
import br.com.nx.tickets.rest.envio.IngressoEnvioEventos;
import br.com.nx.tickets.rest.envio.IngressoEnvioExtratoGuiche;
import br.com.nx.tickets.rest.envio.IngressoEnvioImpressaoTeste;
import br.com.nx.tickets.rest.envio.IngressoEnvioLogin;
import br.com.nx.tickets.rest.envio.IngressoEnvioLotesPorEvento;
import br.com.nx.tickets.rest.envio.IngressoEnvioPedido;
import br.com.nx.tickets.rest.envio.IngressoEnvioRetirada;
import br.com.nx.tickets.rest.envio.IngressoEnvioSolicitacaoCancelamento;
import br.com.nx.tickets.rest.envio.IngressoEnvioValidacao;
import br.com.nx.tickets.rest.retorno.IngressoRetornoCortesia;
import br.com.nx.tickets.rest.retorno.IngressoRetornoEventos;
import br.com.nx.tickets.rest.retorno.IngressoRetornoExtratoGuiche;
import br.com.nx.tickets.rest.retorno.IngressoRetornoLogin;
import br.com.nx.tickets.rest.retorno.IngressoRetornoLotesPorEvento;
import br.com.nx.tickets.rest.retorno.IngressoRetornoPedido;
import br.com.nx.tickets.rest.retorno.IngressoRetornoRetirada;
import br.com.nx.tickets.rest.retorno.IngressoRetornoSolicitacaoCancelamento;
import br.com.nx.tickets.rest.retorno.IngressoRetornoValidacao;
import br.com.nx.tickets.servico.AutenticacaoLogServico;
import br.com.nx.tickets.servico.ClienteServico;
import br.com.nx.tickets.servico.CortesiaServico;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.EventoSocioTorcedorServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.ImpressaoTesteLogServico;
import br.com.nx.tickets.servico.IngressoServico;
import br.com.nx.tickets.servico.IngressoSituacaoServico;
import br.com.nx.tickets.servico.LocalServico;
import br.com.nx.tickets.servico.LoteServico;
import br.com.nx.tickets.servico.PedidoServico;
import br.com.nx.tickets.servico.PontoVendaServico;
import br.com.nx.tickets.servico.PortariaServico;
import br.com.nx.tickets.servico.PromocaoServico;
import br.com.nx.tickets.servico.RetiradaServico;
import br.com.nx.tickets.servico.SocioTorcedorServico;
import br.com.nx.tickets.servico.UsuarioPontoVendaServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.servico.exception.ForaHorarioPontoVendaException;
import br.com.nx.tickets.servico.exception.SaldoIndisponivelException;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Path("/ingressoOnline")
public class IngressoOnlineRest implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private PontoVendaServico pontoVendaServico;
	@EJB
	private IngressoServico ingressoServico;
	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private RetiradaServico retiradaServico;
	@EJB
	private PromocaoServico promocaoServico;
	@EJB
	private LoteServico loteServico;
	@EJB
	private UsuarioPontoVendaServico usuarioPontoVendaServico;
	@EJB
	private PortariaServico portariaServico;
	@EJB
	private LocalServico localServico;
	@EJB
	private IngressoSituacaoServico ingressoSituacaoServico;
	@EJB
	private SocioTorcedorServico socioTorcedorServico;
	@EJB
	private EventoSocioTorcedorServico eventoSocioTorcedorServico;
	@EJB
	private GuicheServico guicheServico;
	@EJB
	private ClienteServico clienteServico;
	@EJB
	private AutenticacaoLogServico autenticacaoLogServico;
	@EJB
	private CortesiaServico cortesiaServico;
	@EJB
	private ImpressaoTesteLogServico impressaoTesteLogServico;
	@Inject
	private transient Logger log;

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
				retorno.setEventos(eventoServico.obterDisponiveisPorUsuario(usuario));
				if (retorno.getEventos() == null || retorno.getEventos().isEmpty()) {
					throw new Exception("Não há eventos ativos para o usuário: " + usuario.getLogin());
				}
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
				} else if (usuario.getNivel().getDescricao().equals("VALIDADOR")) {
					retorno.setEventos(eventoServico.obterAtivos());
					retorno.setPortaria(portariaServico.obterPorUsuario(usuario));
					retorno.setPortarias(portariaServico.obterTodos());
					if (retorno.getPortarias() == null || retorno.getPortarias().isEmpty()) {
						throw new Exception("Não há portarias válidas para o usuário: " + usuario.getLogin() + "!");
					}
					if (retorno.getEventos() == null || retorno.getEventos().isEmpty()) {
						throw new Exception("Não há eventos vigentes para o usuário: " + usuario.getLogin());
					}
					log.info(retorno);
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
	@Path("obterEventosAtivosPorGuiche")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response obterEventosAtivosPorGuiche(IngressoEnvioEventos ingressoEnviavel,
			@Context HttpServletRequest request) {
		IngressoRetornoEventos retorno = new IngressoRetornoEventos();
		try {
			log.info("OBTER EVENTOS ATIVOS POR GUICHE: " + ingressoEnviavel);
			if (ingressoEnviavel.getIdGuiche() != null) {
				retorno.setEventos(eventoServico.obterAtivosPorGuiche(new Guiche(ingressoEnviavel.getIdGuiche())));
			} else {
				retorno.setEventos(eventoServico.obterAtivos());
			}
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			retorno.setMensagem("Ok. Eventos por guiche obtidos com sucesso!");
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			log.error(retorno.getMensagem());
			return Response.ok(retorno).build();
		}
	}

	@POST
	@Path("obterLotesPorEvento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response obterLotesPorEvento(IngressoEnvioLotesPorEvento ingressoEnviavel,
			@Context HttpServletRequest request) {
		IngressoRetornoLotesPorEvento retorno = new IngressoRetornoLotesPorEvento();
		try {
			log.info("OBTER LOTES POR EVENTO : " + ingressoEnviavel);
			Evento evento = eventoServico.obterCompletoPorId(ingressoEnviavel.getIdEvento());
			Guiche guiche = guicheServico.obterPorId(ingressoEnviavel.getIdGuiche());
			retorno.setPortarias(portariaServico.obterPorEvento(evento));
			retorno.setLotes(loteServico.obterLotesValidosPorGuicheEvento(guiche, evento));
			ExtratoGuicheDTO edto = guicheServico.obterExtratoTotalGuiche(guiche, evento);
			retorno.setIngressosVendidos(edto.getQuantidade());
			retorno.setTotalVendido(edto.getTotal());
			retorno.setIngressosCancelados(ingressoServico.obterPorEventoGuicheSituacao(evento, guiche,
					Arrays.asList(ingressoSituacaoServico.obterPorDescricao("CANCELADO"))));
			retorno.setMensagem("Ok. Lotes obtidos com sucesso!");
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			log.info(retorno);
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			log.error(retorno.getMensagem());
			return Response.ok(retorno).build();
		}
	}

	@POST
	@Path("obterCortesias")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response obterCortesias(IngressoEnvioCortesia ingressoEnviavel, @Context HttpServletRequest request) {
		IngressoRetornoCortesia retorno = new IngressoRetornoCortesia();
		try {
			log.info("OBTER CORTESIAS: " + ingressoEnviavel);
			retorno.setLote(loteServico
					.obterLoteCortesiaValidoPorEvento(eventoServico.obterPorId(ingressoEnviavel.getIdEvento())));
			retorno.setCortesias(cortesiaServico.obterTodos("descricao"));
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			retorno.setMensagem("Ok. Cortesias!");
			log.info(retorno.getMensagem());
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			log.error(retorno.getMensagem());
			return Response.ok(retorno).build();
		}
	}

	// TODO: Validar se o lote ainda está valido para o guiche em questao
	@POST
	@Path("salvarPedido")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarPedido(IngressoEnvioPedido ingressoEnviavel, @Context HttpServletRequest request) {
		IngressoRetornoPedido retorno = new IngressoRetornoPedido();
		try {
			log.info("SALVAR PEDIDO : " + ingressoEnviavel);
			Pedido pedido = ingressoEnviavel.getPedido();
			pedido.anularId();
			Evento evento = eventoServico.obterPorId(ingressoEnviavel.getIdEvento());
			Guiche guiche = guicheServico.obterPorId(ingressoEnviavel.getIdGuiche());
			pedido.setEvento(evento);
			pedido.setGuiche(guiche);
			retorno.setPedido(pedidoServico.salvar(new Usuario(ingressoEnviavel.getIdUsuario()), pedido));
			ExtratoGuicheDTO ex = guicheServico.obterExtratoTotalGuiche(new Guiche(ingressoEnviavel.getIdGuiche()),
					evento);
			retorno.setIngressosVendidos(ex.getQuantidade());
			retorno.setIngressosCancelados(ingressoServico.obterPorEventoGuicheSituacao(evento, guiche,
					Arrays.asList(ingressoSituacaoServico.obterPorDescricao("CANCELADO"))));
			retorno.setTotalVendido(ex.getTotal().doubleValue());
			retorno.setMensagem("Ok. Pedido salvo com sucesso!");
			log.info(retorno);
			return Response.ok(retorno).build();
		} catch (ForaHorarioPontoVendaException e) {
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem(e.getMessage());
			log.error(e.getMessage());
			return Response.ok(retorno).build();
		} catch (BaseServicoException e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem(e.getMessage());
			log.error(e.getMessage());
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}

	@POST
	@Path("obterExtratoGuiche")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response obterExtratoGuiche(IngressoEnvioExtratoGuiche ingressoEnviavel,
			@Context HttpServletRequest request) {
		IngressoRetornoExtratoGuiche retorno = new IngressoRetornoExtratoGuiche();
		try {
			log.info("OBTER EXTRATO GUICHE: " + ingressoEnviavel);
			Guiche guiche = guicheServico.obterPorId(ingressoEnviavel.getIdGuiche());
			Evento evento = eventoServico.obterPorId(ingressoEnviavel.getIdEvento());

			List<ExtratoGuicheDTO> extratosGuiches = guicheServico.obterExtratoPorGuicheEvento(guiche, evento);
			for (ExtratoGuicheDTO extratoGuicheDTO : extratosGuiches) {
				retorno.setSaldoDisponivel(retorno.getSaldoDisponivel().add(extratoGuicheDTO.getTotal()));
			}
			retorno.setExtratoGuiche(extratosGuiches);

			List<Retirada> retiradas = retiradaServico.obterPorGuicheEvento(guiche, evento);
			for (Retirada retirada : retiradas) {
				retorno.setSaldoRetirada(retorno.getSaldoRetirada().add(retirada.getValor()));
				retirada.setEvento(null);

			}
			retorno.setRetiradas(retiradas);
			List<Pedido> pedidos = pedidoServico.obterPorGuicheEvento(guiche, evento);
			for (Pedido pedido : pedidos) {
				pedido.setPortaria(null);
				pedido.setEvento(null);
				pedido.setGuiche(null);
				pedido.setCortesia(null);
				for (Ingresso i : pedido.getIngressos()) {
					i.setIngressoTipoDescricao(i.getGuicheLote().getLote().getIngressoTipo().getDescricao());
					i.setLote(i.getGuicheLote().getLote());
					i.setPortariaValidacao(null);
				}
			}
			retorno.setPedidos(pedidos);
			retorno.setMensagem("Ok. Extrato do Guiche!");
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
	@Path("salvarRetirada")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarRetirada(IngressoEnvioRetirada ingressoEnviavel, @Context HttpServletRequest request) {
		IngressoRetornoRetirada retorno = new IngressoRetornoRetirada();
		try {
			log.info("SALVAR RETIRADA : " + ingressoEnviavel);
			Usuario usuario = usuarioServico
					.logar(new Credencial(ingressoEnviavel.getLogin(), ingressoEnviavel.getSenha()));
			Evento evento = eventoServico.obterPorId(ingressoEnviavel.getIdEvento());
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

	@POST
	@Path("solicitarCancelamento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response solicitarIngressoCancelamento(IngressoEnvioSolicitacaoCancelamento ingressoEnviavel,
			@Context HttpServletRequest request) {
		IngressoRetornoSolicitacaoCancelamento retorno = new IngressoRetornoSolicitacaoCancelamento();
		try {
			log.info("SOLICITAR CANCELAMENTO: " + ingressoEnviavel);
			ingressoServico.solicitarCancelamento(new Guiche(ingressoEnviavel.getIdGuiche()),
					new Ingresso(ingressoEnviavel.getIdIngresso()));
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			retorno.setMensagem("Ok. Solicitacão realizada com sucesso!");
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
	@Path("validarEntrada")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validarEntrada(IngressoEnvioValidacao ingressoEnviavel, @Context HttpServletRequest request) {
		IngressoRetornoValidacao retorno = new IngressoRetornoValidacao();
		try {
			log.info("VALIDACAO INGRESSO: " + ingressoEnviavel);
			Ingresso ingresso = ingressoServico.obterPorCodigo(ingressoEnviavel.getCodigo(),
					ingressoEnviavel.getIdEvento());
			if (ingresso != null) {
				return validarIngresso(ingressoEnviavel, retorno, ingresso);
			} else {
				return validarSocioTorcedor(ingressoEnviavel, retorno);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setIngresso(null);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}

	@POST
	@Path("imprimirTeste")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response imprimirTeste(IngressoEnvioImpressaoTeste ingressoEnviavel, @Context HttpServletRequest request) {
		IngressoRetornoPedido retorno = new IngressoRetornoPedido();
		try {
			log.info("IMPRIMIR TESTE: " + ingressoEnviavel);
			ImpressaoTesteLog impressaoTesteLog = new ImpressaoTesteLog(new Guiche(ingressoEnviavel.getIdGuiche()),
					ingressoEnviavel.getTesteTipo());
			retorno.setPedido(gerarPedidoTeste(usuarioServico.obterPorId(ingressoEnviavel.getIdUsuario())));
			impressaoTesteLogServico.salvar(impressaoTesteLog);
			retorno.setMensagem("Ok. Enviando teste para impressão!");
			log.info(retorno.getMensagem());
			return Response.ok(retorno).build();
		} catch (Exception e) {
			e.printStackTrace();
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setMensagem("ERROR: " + e.getMessage());
			return Response.ok(retorno).build();
		}
	}

	private Pedido gerarPedidoTeste(Usuario usuario) {
		try {
			Evento evento = new Evento(0, "EVENTO XXXX");
			evento.setDescricaoImpressao("AAA X BBB");
			evento.setDataAberturaPortao(Calendar.getInstance());
			evento.setObservacao("abcd1234 - sm ingressos - 1234abcd");
			evento.setLocal(localServico.obterCompletoPorId(1));
			EventoTipo eventoTipo = new EventoTipo(0);
			eventoTipo.setDescricao("FUTEBOL");

			evento.setEventoTipo(eventoTipo);
			Cliente c = new Cliente(0);
			c.setNome("Cliente de Teste");
			c.setTelefoneComercial("99-9999-9999");
			evento.setCliente(c);
			usuario.setNivel(new Nivel(0));

			PontoVenda pontoVenda = new PontoVenda(0);
			pontoVenda.setNome("SM INGRESSOS - GUICHE");
			pontoVenda.setCpfCnpj("00000000000001");
			pontoVenda.setTaxaAdministrativa(new BigDecimal("1.99"));
			pontoVenda.setVersaoAplicativo(new Float("0.01"));

			Pedido pedido = new Pedido(new BigInteger("1000000000000"));
			pedido.setEvento(evento);
			pedido.setPagamentoTipo(EPagamentoTipo.DINHEIRO);

			IngressoTipo ingressoTipo = new IngressoTipo(0);
			ingressoTipo.setDescricao("INGRESSO TESTE");
			ingressoTipo.setDescricaoImpressao("INGRESSO;TESTE");

			Lote lote = new Lote(0);
			lote.setValor(new BigDecimal("00.00"));
			lote.setValorTaxaAdministrativa(new BigDecimal("0.00"));
			lote.setNumero(1);
			lote.setIngressoTipo(ingressoTipo);
			lote.setDataInicio(Calendar.getInstance());
			lote.setDataFim(Calendar.getInstance());
			lote.setQuantidadeIngressos(1);

			Bilheteria bilheteria = new Bilheteria(0, "BILHETERIA 0");
			bilheteria.setPortaria(new Portaria(0, "PORTARIA 0"));

			Guiche guiche = new Guiche(0);
			guiche.setDescricao("GUICHE TESTE");
			guiche.setBilheteria(bilheteria);

			Ingresso ingresso = new Ingresso(1L);
			ingresso.setCodigo("0000000000000");
			ingresso.setIngressoSituacao(ingressoSituacaoServico.obterPorDescricao("DISPONIVEL"));
			ingresso.setGuicheLote(new GuicheLote(guiche, lote));
			ingresso.setLote(lote);
			ingresso.setObservacao("TESTE DE IMPRESSAO");
			pedido.setIngressos(Arrays.asList(ingresso));
			List<Portaria> portarias = portariaServico.obterTodos();
			if (!portarias.isEmpty()) {
				pedido.setPortaria(portarias.get(0));
			}
			pedido.setExtratoPedidoDTO(
					new ExtratoPedidoDTO(pedido, lote.getValor(), lote.getValorTaxaAdministrativa()));
			return pedido;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Response validarIngresso(IngressoEnvioValidacao ingressoEnviavel, IngressoRetornoValidacao retorno,
			Ingresso ingresso) throws BaseServicoException {
		if (ingresso.getPortariaValidacao() != null) {
			ingresso.getPortariaValidacao().setLocal(null);
		}
		/**
		 * Validar evento - abertura dos portoes - portaria (se o ingresso esta
		 * na portaria correta)
		 */
		retorno.setIngresso(ingresso);
		if (ingresso.isValido()) {
			// Deve vir na requisição esses dados
			ingresso.setPortariaValidacao(portariaServico.obterPorId(ingressoEnviavel.getIdPortaria()));
			ingresso.setIngressoSituacao(ingressoSituacaoServico.obterPorDescricao("VALIDADO"));
			retorno.setIngresso(ingressoServico.alterarIngressoValidacao(ingresso));
			retorno.setCodigoRetorno(ECodigoRetorno.OK);
			retorno.setCor("green");
			retorno.setMensagem("OK: INGRESSO VÁLIDO!");
			retorno.setObservacao("SEJA BEM-VINDO!");
			retorno.setIngressoTipo(ingresso.getGuicheLote().getLote().getIngressoTipo().getDescricao());
			retorno.getIngresso().setPedido(null);
			retorno.getIngresso().setPortariaValidacao(null);
			log.info(retorno.getMensagem());
			return Response.ok(retorno).build();
		} else {
			if (ingresso.getDataValidacao() != null) {
				retorno.setPortariaValidacao(ingresso.getPortariaValidacao().getDescricao());
				retorno.setDataValidacao(
						DataUtil.formatarData(ingresso.getDataValidacao(), EFormatoData.BRASILEIRO_DATA_HORA));
				retorno.setCor(ingresso.getIngressoSituacao().getCor());
				retorno.setIngressoTipo(ingresso.getGuicheLote().getLote().getIngressoTipo().getDescricao());
				retorno.setObservacao(ingresso.getPortariaValidacao().getDescricao() + " - " + " "
						+ DataUtil.formatarData(ingresso.getDataValidacao(), EFormatoData.BRASILEIRO_DATA) + " ÀS "
						+ DataUtil.formatarData(ingresso.getDataValidacao(), EFormatoData.BRASILEIRO_HORA_MINUTO));
				retorno.setMensagem("ERRO: INGRESSO JÁ VALIDADO!");
			} else {
				retorno.setCor(ingresso.getIngressoSituacao().getCor());
				retorno.setObservacao(ingresso.getObservacao());
				retorno.setIngressoTipo(ingresso.getGuicheLote().getLote().getIngressoTipo().getDescricao());
				retorno.setMensagem("ERRO: " + ingresso.getIngressoSituacao().getMensagem());
			}
			retorno.getIngresso().setIngressoSituacao(null);
			retorno.getIngresso().setGuicheLote(null);
			retorno.getIngresso().setPedido(null);
			retorno.getIngresso().setPortariaValidacao(null);
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			log.info(retorno.getMensagem());
			return Response.ok(retorno).build();
		}
	}

	private Response validarSocioTorcedor(IngressoEnvioValidacao ingressoEnviavel, IngressoRetornoValidacao retorno)
			throws BaseServicoException {
		String codigo = ingressoEnviavel.getCodigo();
		if (codigo.length() == SistemaConstantes.OITO) {
			codigo = codigo.substring(0, SistemaConstantes.SETE);
		}
		log.info("CARTEIRINHA SOCIO TORCEDOR: " + codigo);
		SocioTorcedor socioTorcedor = socioTorcedorServico.obterPorCodigo(codigo);
		if (socioTorcedor == null) {
			retorno.setCor("red");
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setIngresso(null);
			retorno.setMensagem("ERRO: CÓDIGO NÃO ENCONTRADO!");
			log.error(retorno.getMensagem());
			return Response.ok(retorno).build();
		}
		retorno.setCodigoSocioTorcedor(socioTorcedor.getCarteirinha());
		retorno.setIngressoTipo(socioTorcedor.getPlano());
		if (socioTorcedor.getSituacao().equals(ESituacao.ATIVO.getTexto())) {
			Evento evento = eventoServico.obterPorId(ingressoEnviavel.getIdEvento());
			EventoSocioTorcedor evst = new EventoSocioTorcedor(evento, socioTorcedor);
			EventoSocioTorcedor est = eventoSocioTorcedorServico.obterPorEventoSocioTorcedor(evst);
			if (est == null) {
				retorno.setCor("blue");
				retorno.setCodigoRetorno(ECodigoRetorno.OK);
				retorno.setMensagem("OK: SEJA BEM-VINDO!");
				retorno.setObservacao(socioTorcedor.getNome() + " - " + socioTorcedor.getSituacao());
				evst.setPortaria(portariaServico.obterPorId(ingressoEnviavel.getIdPortaria()));
				eventoSocioTorcedorServico.salvar(evst);
			} else {
				retorno.setPortariaValidacao(est.getPortaria().getDescricao());
				retorno.setDataValidacao(
						DataUtil.formatarData(est.getDataCadastro(), EFormatoData.BRASILEIRO_DATA_HORA));
				retorno.setCor("orange");
				retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
				retorno.setMensagem("ERRO: SÓCIO TORCEDOR JÁ VALIDADO!");
				retorno.setObservacao(socioTorcedor.getNomeAbreviado() + " - " + est.getPortaria().getDescricao()
						+ " - " + " " + DataUtil.formatarData(est.getDataCadastro(), EFormatoData.BRASILEIRO_DATA)
						+ " ÀS " + DataUtil.formatarData(est.getDataCadastro(), EFormatoData.BRASILEIRO_HORA_MINUTO));
			}
		} else {
			retorno.setCor("red");
			retorno.setCodigoRetorno(ECodigoRetorno.ERROR);
			retorno.setIngresso(null);
			retorno.setMensagem("ERRO: ACESSO NÃO AUTORIZADO!");
			retorno.setObservacao("SÓCIO TORCEDOR - " + socioTorcedor.getNome() + " - " + socioTorcedor.getSituacao());
		}
		log.info(retorno);
		return Response.ok(retorno).build();
	}

	private String gerarToken(Usuario us) {
		return Util
				.criptografarString(us.getId() + DataUtil.formatarData(Calendar.getInstance(), EFormatoData.PROTOCOLO));
	}
}