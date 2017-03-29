package br.com.nx.tickets.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.IngressoPromocaoDAO;
import br.com.nx.tickets.dao.PromocaoDAO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoPromocao;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.servico.exception.IngressoPromocaoDuplicadoException;

@Stateless
public class IngressoPromocaoServico extends BaseServico<IngressoPromocao> {

	private static final long serialVersionUID = 1L;

	@Inject
	private IngressoPromocaoDAO ingressoPromocaoDao;
	@Inject
	private PromocaoDAO promocaoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(ingressoPromocaoDao);
	}
	
	public IngressoPromocao salvarIngressoPromocao(IngressoPromocao t) throws IngressoPromocaoDuplicadoException, BaseServicoException {
		try {
			IngressoPromocao ip = ingressoPromocaoDao.consultarPorId(t.getId());
			if (ip == null) {
				return super.salvar(t);
			} else {
				throw new IngressoPromocaoDuplicadoException("Ingresso já cadastrado nesta promoçao!");
			}
		} catch (IngressoPromocaoDuplicadoException e) {
			throw new IngressoPromocaoDuplicadoException(e.getMessage());
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Promocao obterPorIngresso(Ingresso ingresso) throws BaseServicoException {
		try {
			return ingressoPromocaoDao.consultarPorIngresso(ingresso);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public void atualizarIngressoPromocaoSorteado(IngressoPromocao ingressoPromocao) throws BaseServicoException {
		try {
			ingressoPromocaoDao.atualizarIngressoPromocaoSorteado(ingressoPromocao);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public List<IngressoPromocao> sortear(Evento evento) throws BaseServicoException {
		try {
			List<IngressoPromocao> sorteados = new ArrayList<>();
			Promocao promocao = promocaoDao.consultarPorEvento(evento);
			ingressoPromocaoDao.limparPromocao(promocao);
			if (promocao != null) {
				List<IngressoPromocao> ingressosPromocoes = ingressoPromocaoDao.consultarParaSorteioPorEvento(evento);
				if (!ingressosPromocoes.isEmpty()) {
					for (int i = 0; i < promocao.getQuantidade(); i++) {
						sorteados.add(ingressosPromocoes.get(new Random().nextInt(ingressosPromocoes.size())));
					}
					for (IngressoPromocao ingressoPromocao : sorteados) {
						ingressoPromocaoDao.atualizarIngressoPromocaoSorteado(ingressoPromocao);
					}
				}
				return sorteados;
			} else {
				throw new BaseServicoException("Não há promoção para o evento informado!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}
	
}