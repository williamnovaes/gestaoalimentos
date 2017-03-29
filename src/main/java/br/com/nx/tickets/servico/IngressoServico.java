package br.com.nx.tickets.servico;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.componente.ESeparador;
import br.com.nx.tickets.dao.BaseDAOException;
import br.com.nx.tickets.dao.IngressoDAO;
import br.com.nx.tickets.entidade.Arquivo;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.util.LeitorArquivo;

@Stateless
public class IngressoServico extends BaseServico<Ingresso> {

	private static final long serialVersionUID = 1L;
	@Inject
	private IngressoDAO ingressoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(ingressoDao);
	}

	public List<Ingresso> obterPorIdPedido(BigInteger id) throws BaseServicoException {
		try {
			return ingressoDao.consultarPorIdPedido(id);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Ingresso alterarIngressoValidacao(Ingresso ingresso) throws BaseServicoException {
		try {
			return ingressoDao.alterarIngressoValidacao(ingresso);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Ingresso obterPorCodigo(String codigo, Integer idEvento) throws BaseServicoException {
		try {
			return ingressoDao.consultarPorCodigoEvento(codigo, idEvento);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Ingresso atualizarCodigoBarra(Ingresso ingresso) throws BaseServicoException {
		try {
			return ingressoDao.atualizarCodigoBarra(ingresso);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Integer cancelarIngresso(Ingresso ingresso) throws BaseServicoException {
		try {
			return ingressoDao.cancelarIngresso(ingresso);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void cancelarTodosIngressosPorPedido(Pedido pedido, String observacao)
			throws BaseServicoException {
		try {
			ingressoDao.cancelarTodosIngressosPorPedido(pedido, observacao);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Ingresso obterParaPromocao(Ingresso ingresso) throws BaseServicoException {
		try {
			return ingressoDao.consultarParaPromocao(ingresso);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Ingresso> obterOffLinePorEventoGuiche(Evento evento, Guiche guiche) throws BaseServicoException {
		try {
			return ingressoDao.consultarOffLinePorEventoGuiche(evento, guiche);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Ingresso> obterValidosPorEvento(Evento evento) throws BaseServicoException {
		try {
			return ingressoDao.consultarValidosPorEvento(evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Integer atualizarIngressosValidacao(List<Ingresso> ingressos) throws BaseServicoException {
		try {
			return ingressoDao.atualizarIngressosValidacao(ingressos);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Ingresso> processar(Arquivo arquivo) throws Exception {
		try {
			List<Ingresso> ingressos = new ArrayList<>();
			List<String[]> obj = LeitorArquivo.processarCSV(new FileInputStream(arquivo.getCaminhoArquivo()), ESeparador.PONTO_VIRGULA);
//			List<String[]> obj = LeitorArquivo.processarXLS(new FileInputStream(arquivo.getCaminhoArquivo()), 1);
			for (String[] strings : obj) {
				Ingresso st = new Ingresso(strings);
				if (st.getId() != null) {
					ingressos.add(st);
				} else {
					getLog().error(strings[2]);
				}
			}
			return ingressos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public List<Ingresso> obterPorEventoSituacao(Evento evento, List<IngressoSituacao> situacoes) throws BaseServicoException {
		try {
			return ingressoDao.consultarPorEventoSituacao(evento, situacoes);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Integer obterPorEventoGuicheSituacao(Evento evento, Guiche guiche, List<IngressoSituacao> situacoes) throws BaseServicoException {
		try {
			return ingressoDao.consultarPorEventoGuicheSituacao(evento, guiche, situacoes);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<Ingresso> obterPorEventoSituacao(Evento evento, IngressoSituacao situacao) throws BaseServicoException {
		try {
			return ingressoDao.consultarPorEventoSituacao(evento, situacao);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public Ingresso obterPorId(Long id) throws BaseServicoException {
		try {
			return ingressoDao.consultarPorId(id);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public void solicitarCancelamento(Guiche guiche, Ingresso ingresso) throws BaseServicoException {
		try {
			ingressoDao.solicitarCancelamento(guiche, ingresso);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}