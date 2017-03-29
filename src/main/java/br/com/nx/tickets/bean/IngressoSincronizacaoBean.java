package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import br.com.nx.tickets.componente.Upload;
import br.com.nx.tickets.entidade.Arquivo;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.servico.ArquivoServico;
import br.com.nx.tickets.servico.ArquivoTipoServico;
import br.com.nx.tickets.servico.IngressoServico;

@Named
@ViewScoped
public class IngressoSincronizacaoBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IngressoServico ingressoServico;
	@EJB
	private ArquivoServico arquivoServico;
	@EJB
	private ArquivoTipoServico arquivoTipoServico;
	
	private Arquivo arquivo;
	private Part arquivoFile;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Ingresso Sincronização Bean");
		try {
			this.arquivo = new Arquivo();
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			Upload upload = Upload.getInstance();
			this.arquivo.setCaminhoArquivo(upload.write(arquivoFile));
			List<Ingresso> ingressos = ingressoServico.processar(arquivo);
			Integer quantidade = ingressoServico.atualizarIngressosValidacao(ingressos);
			getLog().info("Ok. " + quantidade + " ingressos validados atualizados com sucesso!");
			return "ingressos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public Part getArquivoFile() {
		return arquivoFile;
	}
	
	public void setArquivoFile(Part arquivoFile) {
		this.arquivoFile = arquivoFile;
	}
}
