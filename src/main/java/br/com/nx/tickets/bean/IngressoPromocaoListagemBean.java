package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.IngressoPromocao;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.IngressoPromocaoServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class IngressoPromocaoListagemBean extends BaseListagemBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IngressoPromocaoServico ingressoPromocaoServico;
	@EJB
	private EventoServico eventoServico;
	private Evento evento;
	
	private IngressoPromocao ingressoPromocao;
	private List<IngressoPromocao> ingressosPromocoesSorteados;
	
	@Override
	@PostConstruct
	public void inicializar() {
		try {
			evento = eventoServico.obterAtivos().get(0);
			configurarPaginador(getFiltroPermissaoUsuario(), ingressoPromocaoServico, SistemaConstantes.DEZ);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscarRegistros() {
		try {
			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sortear() {
		try {
			ingressosPromocoesSorteados = ingressoPromocaoServico.sortear(evento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public IngressoPromocao getIngressoPromocao() {
		return ingressoPromocao;
	}
	
	public List<IngressoPromocao> getIngressosPromocoesSorteados() {
		return ingressosPromocoesSorteados;
	}
	
	public boolean isPar() {
		if (ingressosPromocoesSorteados != null) {
			return ingressosPromocoesSorteados.size() > 1;
		}
		return false;
	}
}
