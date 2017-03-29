package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import br.com.nx.tickets.componente.Upload;
import br.com.nx.tickets.entidade.Arquivo;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.servico.ArquivoTipoServico;
import br.com.nx.tickets.servico.ClienteServico;
import br.com.nx.tickets.servico.SocioTorcedorServico;

@Named
@ViewScoped
public class SocioTorcedorCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ClienteServico clienteServico;
	@EJB
	private ArquivoTipoServico arquivoTipoServico;
	@EJB
	private SocioTorcedorServico socioTorcedorServico;
	
	private Arquivo arquivo;
	private Part socioTorcedorFile;
	private List<Cliente> clientes;
	private Integer idClienteSelecionado;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Socio Torcedor Cadastro Bean");
		try {
			this.arquivo = new Arquivo();
			clientes = clienteServico.obterTodos("nome");
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			Upload upload = Upload.getInstance();
			this.arquivo.setCaminhoArquivo(upload.write(socioTorcedorFile));
			socioTorcedorServico.salvar(arquivo, clienteServico.obterPorId(idClienteSelecionado));
			return "sociosTorcedores?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public Integer getIdClienteSelecionado() {
		return idClienteSelecionado;
	}
	
	public void setIdClienteSelecionado(Integer idClienteSelecionado) {
		this.idClienteSelecionado = idClienteSelecionado;
	}
	
	public Part getSocioTorcedorFile() {
		return socioTorcedorFile;
	}
	
	public void setSocioTorcedorFile(Part socioTorcedorFile) {
		this.socioTorcedorFile = socioTorcedorFile;
	}
}
