package br.com.nx.tickets.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoPromocao;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.ETamanho;
import br.com.nx.tickets.servico.IngressoPromocaoServico;
import br.com.nx.tickets.servico.IngressoServico;
import br.com.nx.tickets.servico.PromocaoServico;
import br.com.nx.tickets.servico.exception.IngressoPromocaoDuplicadoException;
import br.com.nx.tickets.util.Util;

@Named
@ViewScoped
public class IngressoPromocaoCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IngressoServico ingressoServico;
	@EJB
	private IngressoPromocaoServico ingressoPromocaoServico;
	@EJB
	private PromocaoServico promocaoServico;
	
	private IngressoPromocao ingressoPromocao;
	private List<Promocao> promocoes;
	private ETamanho[] tamanhos = ETamanho.values();
	private Date dataNascimento;
	private Integer idPromocaoSelecionado;
	
	@PostConstruct
	public void inicializar() {
		getLog().info("Criando IngressoPromocaoCadastroBean");
		try {
			ingressoPromocao = new IngressoPromocao();
			ingressoPromocao.setPromocao(new Promocao());
			ingressoPromocao.setIngresso(new Ingresso());
			dataNascimento = null;
			promocoes = promocaoServico.obterTodos("descricao");
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			this.ingressoPromocao.setPromocao(promocaoServico.obterPorId(idPromocaoSelecionado));
			this.ingressoPromocao.setDataNascimento(DataUtil.getCalendar(this.dataNascimento));
			Ingresso ingresso = ingressoServico.obterParaPromocao(ingressoPromocao.getIngresso());
			if (ingresso != null) {
				if (!Util.isEmail(ingressoPromocao.getEmail())) {
					adicionarError("E-mail inválido!");
					return null;
				}
				if (!Util.isCPF(ingressoPromocao.getCpf())) {
					adicionarError("CPF inválido!");
					return null;
				}
				if (ingressoPromocao.getTelefoneResidencial() != null && !Util.isTelefone(ingressoPromocao.getTelefoneResidencial())) {
					adicionarError("Telefone Residencial inválido!");
					return null;
				}
				if (!Util.isTelefone(ingressoPromocao.getTelefoneCelular())) {
					adicionarError("Telefone Celular inválido!");
					return null;
				}
				Promocao promocao = ingressoPromocaoServico.obterPorIngresso(ingresso);
				ingressoPromocao.configurarPk(ingresso, promocao);
				ingressoPromocao.setDataNascimento(DataUtil.getCalendar(dataNascimento));
				ingressoPromocao = ingressoPromocaoServico.salvarIngressoPromocao(ingressoPromocao);
				adicionarInfo("Parabéns, " + ingressoPromocao.getNome() + "! Voce esta participando da promocao!");
				return "promocaoOk?faces-redirect=true";
			} else {
				adicionarError("INGRESSO NAO ENCONTRADO!");
				return null;
			}
		} catch (IngressoPromocaoDuplicadoException ipde) {
			adicionarError(ipde.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
			return null;
		}
	}
	
	public IngressoPromocao getIngressoPromocao() {
		return ingressoPromocao;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public List<Promocao> getPromocoes() {
		return promocoes;
	}
	
	public Integer getIdPromocaoSelecionado() {
		return idPromocaoSelecionado;
	}
	
	public void setIdPromocaoSelecionado(Integer idPromocaoSelecionado) {
		this.idPromocaoSelecionado = idPromocaoSelecionado;
	}
	
	public ETamanho[] getTamanhos() {
		return tamanhos;
	}
}
