package br.com.nx.tickets.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import br.com.nx.tickets.componente.Upload;
import br.com.nx.tickets.entidade.Arquivo;
import br.com.nx.tickets.entidade.Atracao;
import br.com.nx.tickets.servico.ArquivoServico;
import br.com.nx.tickets.servico.ArquivoTipoServico;
import br.com.nx.tickets.servico.AtracaoServico;

@Named
@ViewScoped
public class AtracaoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private AtracaoServico atracaoServico;
	@EJB
	private ArquivoTipoServico arquivoTipoServico;
	@EJB
	private ArquivoServico arquivoServico;
	
	private Atracao atracao;
	private Arquivo arquivo;
	private Part arquivoFile;

	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Atração Cadastro Bean");
		try {
			if (atracao == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");
				if (idParam != null && !idParam.equals("")) {
					try {
						atracao = atracaoServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Atracao ");
					}
				}
				if (this.atracao == null) {
					this.atracao = new Atracao();
					this.atracao.setArquivo(new Arquivo());
				}
				arquivo = new Arquivo();
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			this.arquivo.setArquivoTipo(arquivoTipoServico.obterPorDescricao("BANNER"));
			Upload upload = Upload.getInstance();
			this.arquivo.setCaminhoArquivo(upload.write(arquivoFile));
			this.arquivo.setNomeArquivo("BANNER ATRACAO");
			arquivoServico.salvar(arquivo);
			
			this.atracao.setArquivo(arquivo);
			atracaoServico.salvar(this.atracao);
			return "atracoes?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Atracao getAtracao() {
		return atracao;
	}
	
	public Part getArquivoFile() {
		return arquivoFile;
	}
	
	public void setArquivoFile(Part arquivoFile) {
		this.arquivoFile = arquivoFile;
	}
}
