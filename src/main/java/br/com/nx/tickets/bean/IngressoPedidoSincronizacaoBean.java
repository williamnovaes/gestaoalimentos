package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import br.com.nx.tickets.componente.Upload;
import br.com.nx.tickets.entidade.Arquivo;
import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Guiche;
import br.com.nx.tickets.entidade.Pedido;
import br.com.nx.tickets.servico.ArquivoServico;
import br.com.nx.tickets.servico.ArquivoTipoServico;
import br.com.nx.tickets.servico.EventoServico;
import br.com.nx.tickets.servico.GuicheServico;
import br.com.nx.tickets.servico.PedidoServico;

@Named
@ViewScoped
public class IngressoPedidoSincronizacaoBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private ArquivoServico arquivoServico;
	@EJB
	private ArquivoTipoServico arquivoTipoServico;
	@EJB
	private EventoServico eventoServico;
	@EJB
	private GuicheServico guicheServico;
	
	private Arquivo arquivo;
	private Part arquivoFile;
	
	private List<Evento> eventos;
	private List<Guiche> guiches;
	
	private Integer idEventoSelecionado;
	private Integer idGuicheSelecionado;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando Ingresso Pedido Sincronização Bean");
		try {
			this.arquivo = new Arquivo();
			eventos = eventoServico.obterTodos("descricao");
			guiches = guicheServico.obterTodos("descricao");
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			Upload upload = Upload.getInstance();
			this.arquivo.setCaminhoArquivo(upload.write(arquivoFile));
			Evento evento = eventoServico.obterPorId(idEventoSelecionado);
			Guiche guiche = guicheServico.obterPorId(idGuicheSelecionado);
			List<Pedido> pedidos = pedidoServico.processar(arquivo, evento, guiche);
			Integer quantidade = pedidoServico.sincronizarDadosAplicativo(pedidos);
			getLog().info("Ok. " + quantidade + " pedidos validados atualizados com sucesso!");
			return "pedidos?faces-redirect=true";
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
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public List<Guiche> getGuiches() {
		return guiches;
	}
	
	public Integer getIdEventoSelecionado() {
		return idEventoSelecionado;
	}
	
	public void setIdEventoSelecionado(Integer idEventoSelecionado) {
		this.idEventoSelecionado = idEventoSelecionado;
	}
	
	public Integer getIdGuicheSelecionado() {
		return idGuicheSelecionado;
	}
	
	public void setIdGuicheSelecionado(Integer idGuicheSelecionado) {
		this.idGuicheSelecionado = idGuicheSelecionado;
	}
}
