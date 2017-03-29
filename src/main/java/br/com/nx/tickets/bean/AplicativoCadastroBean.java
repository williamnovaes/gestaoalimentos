package br.com.nx.tickets.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import br.com.nx.tickets.componente.Upload;
import br.com.nx.tickets.entidade.ArquivoAplicativo;
import br.com.nx.tickets.servico.ArquivoAplicativoServico;
import br.com.nx.tickets.servico.ArquivoTipoServico;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.util.Util;

@Named
@ViewScoped
public class AplicativoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ArquivoAplicativoServico arquivoAplicativoServico;
	@EJB
	private ArquivoTipoServico arquivoTipoServico;
	
	private ArquivoAplicativo arquivo;
	private Part arquivoFile;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Arquivo Aplicativo Cadastro Bean");
		try {
			this.arquivo = new ArquivoAplicativo();
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			this.arquivo.setArquivoTipo(arquivoTipoServico.obterPorDescricao("APLICATIVO"));
			Upload upload = Upload.getInstance();
			this.arquivo.setCaminhoArquivo(upload.write(arquivoFile));
			this.arquivo.setNomeArquivo("APLICATIVO");
			arquivoAplicativoServico.salvar(arquivo);
			return "aplicativos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public void download(Integer id) {
		try {
			Util.download(arquivoAplicativoServico.obterPorId(id));
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}
	
	public Part getArquivoFile() {
		return arquivoFile;
	}
	
	public void setArquivoFile(Part arquivoFile) {
		this.arquivoFile = arquivoFile;
	}
	
	public ArquivoAplicativo getArquivo() {
		return arquivo;
	}
}
