package br.com.nx.tickets.rest.retorno;

import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nx.tickets.entidade.IngressoPromocao;
import br.com.nx.tickets.entidade.Promocao;
import br.com.nx.tickets.rest.DateFormatterAdapter;

public class IngressoRetornoSorteioPromocao extends IngressoRetorno {

	@JsonInclude(Include.NON_NULL)
	@XmlJavaTypeAdapter(DateFormatterAdapter.class)
	@XmlElement(name = "data_sorteio")
	private Calendar dataSorteio;
	@XmlElement(name = "promocao")
	private Promocao promocao;
	@XmlElement(name = "sorteados")
	private List<IngressoPromocao> sorteados;

	public Calendar getDataSorteio() {
		return dataSorteio;
	}

	public void setDataSorteio(Calendar dataSorteio) {
		this.dataSorteio = dataSorteio;
	}

	public Promocao getPromocao() {
		return promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	public List<IngressoPromocao> getSorteados() {
		return sorteados;
	}

	public void setSorteados(List<IngressoPromocao> sorteados) {
		this.sorteados = sorteados;
	}

	@Override
	public String toString() {
		return "IngressoRetornoSorteioPromocao [promocao=" + promocao + ", sorteados=" + sorteados + "]";
	}
}
