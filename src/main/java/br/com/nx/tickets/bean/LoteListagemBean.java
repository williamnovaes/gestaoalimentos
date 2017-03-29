package br.com.nx.tickets.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.GuicheLote;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.servico.GuicheLoteServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.LoteServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class LoteListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private LoteServico loteServico;
	@EJB
	private GuicheServico guicheServico;
	@EJB
	private GuicheLoteServico guicheLoteServico;
	
	private Lote lote;
	private List<Guiche> guichesDisponiveis;
	private List<Guiche> guichesNovos;
	private List<GuicheLote> guichesLotes;
	
	private boolean exibirModalData;
	private boolean exibirModalGuiche;
	
	private Date dataInicio;
	private Date dataFim;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), loteServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}

	public void buscarRegistros() {
		try {
			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abrirModalGuiche(Lote lt) {
		try {
			exibirModalGuiche 	= true;
			this.lote 			= lt;
			guichesNovos 		= new ArrayList<>();
			guichesDisponiveis 	= guicheServico.obterTodosCompletos();
			guichesLotes		= guicheLoteServico.obterPorLote(this.lote);
			
			for (GuicheLote guiches : guichesLotes) {
				if (guichesDisponiveis.contains(guiches.getGuiche())) {
					guichesDisponiveis.remove(guiches.getGuiche());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abrirModalData(Lote lt) {
		exibirModalData = true;
		this.lote 		= lt;
		dataInicio 		= this.lote.getDataInicio().getTime();
		dataFim			= this.lote.getDataFim().getTime();
	}
	
	public void alterarDataVigente() {
		try {
			Lote lt = this.lote;
			lt.setDataInicio(DataUtil.getCalendar(this.dataInicio));
			lt.setDataFim(DataUtil.getCalendar(this.dataFim));
			
			loteServico.alterar(lt);
			fecharModal();
			dataInicio = null;
			dataFim = null;
		} catch (Exception e) {
			adicionarError("Erro ao alterar data!");
			e.printStackTrace();
		}
	}
	
	public void adicionarGuiches(Guiche gi) {
		guichesNovos.add(gi);
		guichesDisponiveis.remove(gi);
	}
	
	public void removerGuiches(Guiche gi) {
		guichesNovos.remove(gi);
		guichesDisponiveis.add(gi);
	}
	
	public void alterarSituacao(Lote lt) {
		try {
			lt.alterarSituacao();
			loteServico.alterar(lt);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situacao do lote!");
			e.printStackTrace();
		}
	}
	
	public void alterarSituacaoGuicheLote(Guiche g) {
		try {
			GuicheLote guicheLote = guicheLoteServico.obterPorGuicheLote(g, this.lote);
			guicheLote.alterarSituacao();
			guicheLoteServico.alterar(guicheLote);
			abrirModalGuiche(this.lote);
		} catch (Exception e) {
			adicionarError("Erro ao alterar situacao do lote!");
			e.printStackTrace();
		}
	}
	
	@Override
	public void fecharModal() {
		exibirModalData 	= false;
		exibirModalGuiche 	= false;
	}
	
	public void salvar() {
		try {
			if (!this.guichesNovos.isEmpty()) {
				loteServico.atualizarGuiches(this.lote, guichesNovos);
			}
			fecharModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Lote getLote() {
		return lote;
	}
	
	public boolean isExibirModalData() {
		return exibirModalData;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public boolean isExibirModalGuiche() {
		return exibirModalGuiche;
	}
	
	public List<Guiche> getGuichesDisponiveis() {
		return guichesDisponiveis;
	}
	
	public List<Guiche> getGuichesNovos() {
		return guichesNovos;
	}
	
	public List<GuicheLote> getGuichesLotes() {
		return guichesLotes;
	}
}