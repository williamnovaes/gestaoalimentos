package br.com.nx.tickets.rest.retorno;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.Evento;
import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.IngressoSituacao;
import br.com.nx.tickets.entidade.Lote;
import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.socio.SocioTorcedor;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EFormatoData;

@XmlType(propOrder = { "codigo_retorno", "mensagem", "usuario_id", "usuario_nome", "usuario_nivel", "evento_id",
		"evento_descricao", "evento_impressao", "evento_data", "evento_abertura_portao", "evento_observacao",
		"evento_observacao_promocao", "local_descricao", "local_cidade", "cliente_nome", "cliente_telefone_comercial",
		"lotes", "ingressos_bilheteria", "ingressos_validacao", "ingressos_situacao",
		"portarias", "socios_torcedores" })
public class IngressoRetornoDadosAplicativo extends IngressoRetorno {

	@XmlElement(name = "usuario_id")
	private Integer usuarioId;

	@XmlElement(name = "usuario_nome")
	private String usuarioNome;

	@XmlElement(name = "usuario_nivel")
	private String usuarioNivel;

	@XmlElement(name = "evento_id")
	private Integer eventoId;

	@XmlElement(name = "evento_descricao")
	private String eventoDescricao;

	@XmlElement(name = "evento_impressao")
	private String eventoImpressao;

	@XmlElement(name = "evento_data")
	private String eventoData;

	@XmlElement(name = "evento_abertura_portao")
	private String eventoAberturaPortao;

	@XmlElement(name = "evento_observacao")
	private String eventoObservacao;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "evento_observacao_promocao")
	private String eventoObservacaoPromocao;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "local_descricao")
	private String localDescricao;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "local_cidade")
	private String localCidade;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "cliente_nome")
	private String clienteNome;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "cliente_telefone_comercial")
	private String clienteTelefoneComercial;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "lotes")
	private List<Lote> lotes;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingressos_bilheteria")
	private List<Ingresso> ingressosBilheteria;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingressos_validacao")
	private List<Ingresso> ingressosValidacao;
	
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingressos_situacao")
	private List<IngressoSituacao> ingressosSituacao;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "portarias")
	private List<Portaria> portarias;

	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "socios_torcedores")
	private List<SocioTorcedor> sociosTorcedores;

	public IngressoRetornoDadosAplicativo() {

	}

	public void setEvento(Evento evento) {
		this.eventoId = evento.getId();
		this.eventoDescricao = evento.getDescricao();
		this.eventoImpressao = evento.getDescricaoImpressao();
		this.eventoAberturaPortao = DataUtil.formatarData(evento.getDataAberturaPortao(),
				EFormatoData.AMERICANO_INGRESSO);
		this.eventoData = DataUtil.formatarData(evento.getDataEvento(), EFormatoData.AMERICANO_INGRESSO);
		this.eventoObservacao = evento.getObservacao();
	}

	public Integer getEventoId() {
		return eventoId;
	}

	public String getEventoDescricao() {
		return eventoDescricao;
	}

	public String getEventoImpressao() {
		return eventoImpressao;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getUsuarioNivel() {
		return usuarioNivel;
	}

	public void setUsuarioNivel(String usuarioNivel) {
		this.usuarioNivel = usuarioNivel;
	}

	public String getEventoAberturaPortao() {
		return eventoAberturaPortao;
	}

	public void setEventoAberturaPortao(String eventoAberturaPortao) {
		this.eventoAberturaPortao = eventoAberturaPortao;
	}

	public String getLocalDescricao() {
		return localDescricao;
	}

	public void setLocalDescricao(String localDescricao) {
		this.localDescricao = localDescricao;
	}

	public String getLocalCidade() {
		return localCidade;
	}

	public void setLocalCidade(String localCidade) {
		this.localCidade = localCidade;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public String getClienteTelefoneComercial() {
		return clienteTelefoneComercial;
	}

	public void setClienteTelefoneComercial(String clienteTelefoneComercial) {
		this.clienteTelefoneComercial = clienteTelefoneComercial;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}

	public List<Ingresso> getIngressosBilheteria() {
		return ingressosBilheteria;
	}

	public void setIngressosBilheteria(List<Ingresso> ingressosBilheteria) {
		this.ingressosBilheteria = ingressosBilheteria;
	}

	public List<SocioTorcedor> getSociosTorcedores() {
		return sociosTorcedores;
	}

	public void setSociosTorcedores(List<SocioTorcedor> sociosTorcedores) {
		this.sociosTorcedores = sociosTorcedores;
	}

	public void setEventoObservacaoPromocao(String eventoObservacaoPromocao) {
		this.eventoObservacaoPromocao = eventoObservacaoPromocao;
	}

	public void setPortarias(List<Portaria> portarias) {
		this.portarias = portarias;
	}

	public List<Ingresso> getIngressosValidacao() {
		return ingressosValidacao;
	}

	public void setIngressosValidacao(List<Ingresso> ingressosValidacao) {
		this.ingressosValidacao = ingressosValidacao;
	}

	public List<IngressoSituacao> getIngressosSituacao() {
		return ingressosSituacao;
	}
	
	public void setIngressosSituacao(List<IngressoSituacao> ingressosSituacao) {
		this.ingressosSituacao = ingressosSituacao;
	}
	
	@Override
	public String toString() {
		return "IngressoRetornoDadosAplicativo [usuarioId=" + usuarioId + ", usuarioNome=" + usuarioNome
				+ ", usuarioNivel=" + usuarioNivel + ", eventoId=" + eventoId + ", eventoDescricao=" + eventoDescricao
				+ ", eventoImpressao=" + eventoImpressao + ", lotes=" + lotes + ", ingressos=" + ingressosBilheteria
				+ ", sociosTorcedores=" + sociosTorcedores + "]";
	}
}
