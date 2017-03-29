package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.nx.tickets.componente.FiltroPermissaoUsuarioSocioTorcedor;
import br.com.nx.tickets.entidade.EventoSocioTorcedor;
import br.com.nx.tickets.entidade.socio.SocioTorcedor;
import br.com.nx.tickets.servico.SocioTorcedorServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class SocioTorcedorListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private SocioTorcedorServico socioTorcedorServico;
	@Inject
	private FiltroPermissaoUsuarioSocioTorcedor filtroPermissaoUsuarioSocioTorcedor;
	
	private SocioTorcedor socioTorcedor;
	private List<EventoSocioTorcedor> eventosSocioTorcedor;
	
	private boolean exibirModalParticipacoes;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(filtroPermissaoUsuarioSocioTorcedor, socioTorcedorServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}

	public void buscarRegistros() {
		try {
//			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obterEventosPorSocio(SocioTorcedor socio) {
		try {
			this.socioTorcedor = socio;
			exibirModalParticipacoes = true;
			eventosSocioTorcedor = socioTorcedorServico.obterParticipacoes(socio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void fecharModal() {
		exibirModalParticipacoes = false;
	}
	
	public SocioTorcedor getSocioTorcedor() {
		return socioTorcedor;
	}
	
	public boolean isExibirModalParticipacoes() {
		return exibirModalParticipacoes;
	}
	
	public List<EventoSocioTorcedor> getEventosSocioTorcedor() {
		return eventosSocioTorcedor;
	}
	
	public FiltroPermissaoUsuarioSocioTorcedor getFiltroPermissaoUsuarioSocioTorcedor() {
		return filtroPermissaoUsuarioSocioTorcedor;
	}
}
