package br.com.nx.tickets.rest.retorno;

import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.Ingresso;
import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EFormatoData;
import br.com.nx.tickets.rest.DateFormatterAdapter;

public class IngressoRetornoSincronizacaoAplicativo extends IngressoRetorno {

	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@XmlElement(name = "data_sincronizacao")
	private Calendar dataSincronizacao;
	@XmlElement(name = "ingressos_validos")
	private Integer ingressosValidos;
	@XmlElement(name = "ingressos_cancelados")
	private Integer ingressosCancelados;
	@XmlElement(name = "ingressos_disponiveis")
	private Integer ingressosDisponiveis;
	@JsonInclude(Include.NON_NULL)
	@XmlElement(name = "ingressos_validados")
	private List<Ingresso> ingressosValidados;

	public Calendar getDataSincronizacao() {
		return dataSincronizacao;
	}

	public void setDataSincronizacao(Calendar dataSincronizacao) {
		this.dataSincronizacao = dataSincronizacao;
	}

	public Integer getIngressosCancelados() {
		return ingressosCancelados;
	}

	public void setIngressosCancelados(Integer ingressosCancelados) {
		this.ingressosCancelados = ingressosCancelados;
	}

	public Integer getIngressosValidos() {
		return ingressosValidos;
	}

	public void setIngressosValidos(Integer ingressosValidos) {
		this.ingressosValidos = ingressosValidos;
	}
	
	public Integer getIngressosDisponiveis() {
		return ingressosDisponiveis;
	}
	
	public void setIngressosDisponiveis(Integer ingressosDisponiveis) {
		this.ingressosDisponiveis = ingressosDisponiveis;
	}
	
	public List<Ingresso> getIngressosValidados() {
		return ingressosValidados;
	}
	
	public void setIngressosValidados(List<Ingresso> ingressosValidados) {
		this.ingressosValidados = ingressosValidados;
	}

	@Override
	public String toString() {
		return "IngressoRetornoSincronizacaoAplicativo [dataSincronizacao="
				+ DataUtil.formatarData(dataSincronizacao, EFormatoData.BRASILEIRO_DATA_HORA) + ", ingressosValidos="
				+ ingressosValidos + ", ingressosCancelados=" + ingressosCancelados + "]";
	}
}
