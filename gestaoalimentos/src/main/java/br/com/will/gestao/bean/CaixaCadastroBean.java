package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.entidade.util.DataUtil;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.EFormatoData;
import br.com.will.gestao.servico.CaixaServico;

@Named
@ViewScoped
public class CaixaCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private CaixaServico caixaServico;
	
	private Caixa caixa;
	
	private String dataAbertura;
	private String dataFechamento;
	
	private EBoolean[] booleans = EBoolean.values();
	private EBoolean aberto = EBoolean.FALSE;
	private EBoolean entrega = EBoolean.TRUE;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Página Cadastro Bean");
		try {
			if (caixa == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.isEmpty()) {
					try {
						this.caixa = caixaServico.obterCompletoPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Página ");
					}
				}
				if (this.caixa == null) {
					this.caixa= new Caixa();
				} else {
					this.aberto = this.caixa.getAberto();
					this.entrega = this.caixa.getEntrega();
					this.dataAbertura = DataUtil.formatarData(this.caixa.getDataAbertura(),
							EFormatoData.BRASILEIRO_DATA);
					this.dataFechamento = DataUtil.formatarData(this.caixa.getDataFechamento(),
							EFormatoData.BRASILEIRO_DATA);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			this.caixa.setEmpresa(getLoginBean().getEmpresa());
			this.caixa.setUsuario(getLoginBean().getUsuarioLogado());
			this.caixa.setAberto(this.aberto);
			this.caixa.setEntrega(this.entrega);
			this.caixa.setDataAbertura(
					DataUtil.converterStringParaCalendar(this.dataAbertura, EFormatoData.BRASILEIRO_DATA_HORA));
			this.caixa.setDataFechamento(
					DataUtil.converterStringParaCalendar(this.dataFechamento, EFormatoData.BRASILEIRO_DATA_HORA));
			
			if (this.caixa.getId() != null) {
				caixaServico.alterar(this.caixa);
				adicionarInfo("Caixa alterado com sucesso");
			} else {
				caixaServico.salvar(this.caixa);
				adicionarInfo("Caixa cadastrado com sucesso");
			}
			return "caixas";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Caixa getCaixa() {
		return caixa;
	}
	
	public EBoolean[] getBooleans() {
		return booleans;
	}
	
	public EBoolean getAberto() {
		return aberto;
	}
	
	public void setAberto(EBoolean aberto) {
		this.aberto = aberto;
	}
	
	public EBoolean getEntrega() {
		return entrega;
	}
	
	public void setEntrega(EBoolean entrega) {
		this.entrega = entrega;
	}
	
	public String getDataAbertura() {
		return dataAbertura;
	}
	
	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public String getDataFechamento() {
		return dataFechamento;
	}
	
	public void setDataFechamento(String dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
}