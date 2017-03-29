package br.com.nx.tickets.servico;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.componente.ESeparador;
import br.com.nx.tickets.dao.BaseDAOException;
import br.com.nx.tickets.dao.IngressoDAO;
import br.com.nx.tickets.dao.IngressoSituacaoDAO;
import br.com.nx.tickets.dao.PedidoDAO;
import br.com.nx.tickets.dto.ExtratoPedidoDTO;
import br.com.nx.tickets.entidade.Arquivo;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.GuicheLote;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EFormatoData;
import br.com.nx.tickets.entidade.util.EPagamentoTipo;
import br.com.nx.tickets.entidade.util.ESituacao;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.servico.exception.ForaHorarioPontoVendaException;
import br.com.nx.tickets.util.LeitorArquivo;
import br.com.nx.tickets.util.SistemaConstantes;
import br.com.nx.tickets.util.Util;

@Stateless
public class PedidoServico extends BaseServico<Pedido> {

	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoDAO pedidoDao;
	@Inject
	private IngressoDAO ingressoDao;
	@Inject
	private IngressoSituacaoDAO ingressoSituacaoDao;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private GuicheServico guicheServico;
	@EJB
	private PromocaoServico promocaoServico;
	@EJB
	private GuicheLoteServico guicheLoteServico;
	@EJB
	private LoteServico loteServico;
	
	@Override
	@PostConstruct
	public void inicializar() {
		setDao(pedidoDao);
	}
	
	public Pedido salvar(Usuario usuario, Pedido pedido) throws ForaHorarioPontoVendaException, BaseServicoException {
		try {
			Evento evento 		  = pedido.getEvento();
			Calendar dataFimVenda = evento.getDataEvento();
			Integer limite 		  = pedido.getGuiche().getHoraLimiteVendaIngressoAntesEvento();
			Calendar dataAlterada = DataUtil.alterarDataOriginal(Calendar.HOUR_OF_DAY, -limite, dataFimVenda);
			if (dataAlterada.before(Calendar.getInstance())) {
				throw new ForaHorarioPontoVendaException(pedido.getGuiche().getDescricao() 
						+ " fora do horário permitido para o evento: " + evento.getDescricao() + "!");
			}
			pedido.setGuiche(guicheServico.obterPorUsuario(usuario));
			IngressoSituacao is = ingressoSituacaoDao.consultarPorDescricao("DISPONIVEL");
			List<Ingresso> ingressosRetorno = new ArrayList<Ingresso>();
			pedido.anularId();
			pedido = super.salvar(pedido);
			Promocao promocao = promocaoServico.obterPorEvento(pedido.getEvento());
			ExtratoPedidoDTO extrato = new ExtratoPedidoDTO(pedido);
			for (Ingresso ingresso : pedido.getIngressos()) {
				if (ingresso.getGuicheLote() == null) {
					Lote lote = loteServico.obterCompletoPorId(ingresso.getLote().getId());
					if (!lote.getSituacao().equals(ESituacao.ATIVO)) {
						throw new BaseServicoException("Lote " + lote.getIngressoTipo().getDescricao() 
								+ " a R$ " + lote.getTotal()  + ", INATIVO. Clique em 'ATUALIZAR' para continuar!");
					}
					if (lote.getDataFim().before(Calendar.getInstance())) {
						throw new BaseServicoException("Lote " + lote.getIngressoTipo().getDescricao() 
								+ " a R$ " + lote.getTotal()  + " expirado em: " 
								+ DataUtil.formatarData(lote.getDataFim(), EFormatoData.BRASILEIRO_DATA_HORA) 
								+ ". Clique em 'ATUALIZAR' para continuar!");
					}
					GuicheLote gl = guicheLoteServico.obterPorGuicheLote(pedido.getGuiche(), ingresso.getLote());
					if (gl == null) {
						gl = guicheLoteServico.salvar(new GuicheLote(pedido.getGuiche(), ingresso.getLote()));
					}
					ingresso.setGuicheLote(gl);
				}
				ingresso.anularId();
				ingresso.setPedido(pedido);
				ingresso.setIngressoSituacao(is);
				if (promocao != null) {
					ingresso.setCodigoPromocao(Util.gerarCodigoAleatorio(SistemaConstantes.SEIS));
					ingresso.setObservacaoPromocao(promocao.getObservacao().replace("[CODIGO]", ingresso.getCodigoPromocao()));
				}
				if (pedido.getCortesia() != null) {
					ingresso.getLote().getIngressoTipo().setDescricaoImpressao(pedido.getCortesia().getDescricaoImpressao());
					ingresso.getLote().getIngressoTipo().setDescricao("CORTESIA " + pedido.getCortesia().getDescricao());
					ingresso.setObservacaoPromocao(null);
				}
				extrato.adicionarExtratoIngresso(ingresso.getLote(), 1);
				ingressosRetorno.add(ingresso);
			}
			ingressoDao.salvar(ingressosRetorno);
			pedido.setIngressos(ingressosRetorno);
			pedido.setExtratoPedidoDTO(extrato);
			return pedido;
		} catch (ForaHorarioPontoVendaException e) {
			throw new ForaHorarioPontoVendaException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Pedido obterPedidoCompletoPorId(Long id) throws BaseServicoException {
		try {
			return pedidoDao.consultarCompletoPorId(id);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Integer sincronizarDadosAplicativo(List<Pedido> pedidos) throws BaseServicoException {
		try {
			return pedidoDao.sincronizarDadosAplicativo(pedidos);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Pedido> processar(Arquivo arquivo, Evento evento, Guiche guiche) throws Exception {
		try {
			List<Pedido> pedidos = new ArrayList<>();
			List<String[]> obj = LeitorArquivo.processarCSV(new FileInputStream(arquivo.getCaminhoArquivo()), ESeparador.PONTO_VIRGULA);
			Pedido st = new Pedido();
			for (String[] strings : obj) {
				if (st.getId() == null) {
					st = new Pedido(strings);
					st.setEvento(evento);
					st.setGuiche(guiche);
					st.setPagamentoTipo(EPagamentoTipo.DINHEIRO);
					continue;
				} 
				if (st.getId().equals(new BigInteger(strings[SistemaConstantes.ZERO]))) {
					st.getIngressos().add(new Ingresso(Long.parseLong(strings[SistemaConstantes.DOIS])));
				} else {
					pedidos.add(st);
					st = new Pedido(strings);
					st.setEvento(evento);
					st.setGuiche(guiche);
					st.setPagamentoTipo(EPagamentoTipo.DINHEIRO);
				}
			}
			pedidos.add(st);
			return pedidos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public List<Pedido> obterPorGuicheEvento(Guiche guiche, Evento evento) throws BaseServicoException {
		try {
			return pedidoDao.consultarPorGuicheEvento(guiche, evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}