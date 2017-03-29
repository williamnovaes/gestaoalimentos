package br.com.nx.tickets.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Nivel;
import br.com.nx.tickets.entidade.permissao.Pagina;
import br.com.nx.tickets.servico.NivelServico;
import br.com.nx.tickets.servico.PaginaServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class PaginaListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PaginaServico paginaServico;
	@EJB
	private NivelServico nivelServico;
	
	private Pagina pagina;
	private List<Nivel> niveisAssociados;
	private List<Nivel> niveisDisponiveis;
	private List<Integer> posicoes;

	private boolean exibirModalNiveis;
	private boolean exibirModalPosicao;
		
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), paginaServico, SistemaConstantes.DEZ);
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
	
	public void obterNiveisPorPagina(Pagina pg) {
		try {
			this.pagina = pg;
			exibirModalNiveis = true;
			niveisDisponiveis = nivelServico.obterTodos("descricao");
			niveisAssociados = paginaServico.obterNiveisPorPagina(this.pagina);
			niveisDisponiveis.removeAll(niveisAssociados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarNivel(Nivel nivel) {
		try {
			niveisAssociados.add(nivel);
			niveisDisponiveis.remove(nivel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerNivel(Nivel nivel) {
		try {
			niveisAssociados.remove(nivel);
			niveisDisponiveis.add(nivel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarNiveisAssociados() {
		try {
			paginaServico.atualizarNiveis(this.pagina, this.niveisAssociados);
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abriModalPosicao(Pagina pg) {
		this.pagina 			= pg;
		this.exibirModalPosicao = true;
		posicoes = new ArrayList<>();
		for (int i = 1; i <= SistemaConstantes.CINQUENTA; i++) {
			posicoes.add(i);
		}
	}
	
	public void salvarPosicaoPagina() {
		try {
			getLog().info("Posicao Atualizada: " + pagina.getSequencia());
			paginaServico.alterar(pagina);
			fecharModal();
			buscarRegistros();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSituacao(Pagina pg) {
		try {
			pg.alterarSituacao();
			paginaServico.alterar(pg);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}
	
	@Override
	public void fecharModal() {
		exibirModalNiveis  = false;
		exibirModalPosicao = false;
	}
	
	public Pagina getPagina() {
		return pagina;
	}
	
	public boolean isExibirModalNiveis() {
		return exibirModalNiveis;
	}
	
	public List<Nivel> getNiveisAssociados() {
		return niveisAssociados;
	}
	
	public List<Nivel> getNiveisDisponiveis() {
		return niveisDisponiveis;
	}
	
	public boolean isExibirModalPosicao() {
		return exibirModalPosicao;
	}
	
	public List<Integer> getPosicoes() {
		return posicoes;
	}
}
