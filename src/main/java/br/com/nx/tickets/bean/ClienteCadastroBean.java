package br.com.nx.tickets.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Cidade;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.Endereco;
import br.com.nx.tickets.entidade.Estado;
import br.com.nx.tickets.entidade.Segmento;
import br.com.nx.tickets.servico.CidadeServico;
import br.com.nx.tickets.servico.ClienteServico;
import br.com.nx.tickets.servico.EstadoServico;
import br.com.nx.tickets.servico.SegmentoServico;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Named
@ViewScoped
public class ClienteCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	@EJB
	private ClienteServico clienteServico;
	@EJB
	private SegmentoServico segmentoServico;
	@EJB
	private EstadoServico estadoServico;
	@EJB
	private CidadeServico cidadeServico;

	private Cliente cliente;
	private Integer idSegmentoSelecionado;
	private String ufEstadoSelecionado;
	private Integer idCidadeSelecionada;
	private List<Cidade> cidades;
	private List<Estado> estados;
	private List<Segmento> segmentos;
	
	private Date dataFundacao;
	private Date dataNascimento;
	
	@PostConstruct
	public void inicializar() {
		try {
			if (cliente == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("id");

				if (idParam != null && !idParam.equals("")) {
					try {
						cliente = clienteServico.obterPorId(Integer.parseInt(idParam));
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao Buscar Cliente ");
					}
				}
				if (this.cliente == null) {
					this.cliente = new Cliente();
					this.cliente.setEndereco(new Endereco());
					this.cliente.setSegmento(new Segmento());
				}
			}
			estados = estadoServico.obterTodos("nome");
			segmentos = segmentoServico.obterTodos("descricao");
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			cliente.setSegmento(segmentoServico.obterPorId(idSegmentoSelecionado));
			cliente.getEndereco().setCidade(cidadeServico.obterPorId(idCidadeSelecionada));
			if (cliente.getId() != null) {
				clienteServico.alterar(cliente);
			} else {
				clienteServico.salvar(cliente);
			}
			adicionarInfo("Cliente salvo com sucesso.");
			return "clientes?faces-redirect=true";
		} catch (BaseServicoException e) {
			e.printStackTrace();
			adicionarError("Erro ao Salvar Cliente.");
			return null;
		}
	}

	public void carregarCidades() {
		try {
			cidades = cidadeServico.obterPorUf(this.ufEstadoSelecionado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Cliente getCliente() {
		return cliente;
	}

	public Integer getIdSegmentoSelecionado() {
		return idSegmentoSelecionado;
	}

	public void setIdSegmentoSelecionado(Integer idSegmentoSelecionado) {
		this.idSegmentoSelecionado = idSegmentoSelecionado;
	}

	public String getUfEstadoSelecionado() {
		return ufEstadoSelecionado;
	}

	public void setUfEstadoSelecionado(String ufEstado) {
		this.ufEstadoSelecionado = ufEstado;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public Integer getIdCidadeSelecionada() {
		return idCidadeSelecionada;
	}

	public void setIdCidadeSelecionada(Integer idCidade) {
		this.idCidadeSelecionada = idCidade;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public List<Segmento> getSegmentos() {
		return segmentos;
	}

	public Date getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}