package br.com.nx.tickets.servico;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.RetiradaDAO;
import br.com.nx.tickets.dto.ExtratoRetiradaDTO;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Retirada;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.servico.exception.SaldoIndisponivelException;

@Stateless
public class RetiradaServico extends BaseServico<Retirada> {

	private static final long serialVersionUID = 1L;
	@Inject
	private RetiradaDAO retiradaDao;
	@EJB
	private GuicheServico guicheServico;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(retiradaDao);
	}

	public List<Retirada> obterPorUsuario(Usuario usuario) throws BaseServicoException {
		try {
			return retiradaDao.consultarPorCliente(usuario);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Retirada salvar(Usuario usuario, Retirada t, BigDecimal valorRetirada) throws BaseServicoException, SaldoIndisponivelException {
		ExtratoRetiradaDTO extrato = guicheServico.obterExtratoRetiradaPorGuicheEvento(t.getGuiche(), t.getEvento());
		if (extrato.getSaldoDisponivel().compareTo(valorRetirada) == -1) {
			throw new SaldoIndisponivelException("Retirada maior que o disponível. Saldo disponível: " 
		+ extrato.getSaldoDisponivel());
		}
		t.setUsuario(usuario);
		t.setValor(valorRetirada);
		return super.salvar(t);
	}
	
	public List<Retirada> obterPorGuicheEvento(Guiche guiche, Evento evento) throws BaseServicoException {
		try {
			return retiradaDao.consultarPorGuicheEvento(guiche, evento);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}