package br.com.nx.tickets.rest;

import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import br.com.nx.tickets.entidade.util.DataUtil;
import br.com.nx.tickets.entidade.util.EFormatoData;

public class DateFormatterAdapter extends XmlAdapter<String, Calendar> {

	@Override
	public String marshal(Calendar v) throws Exception {
		synchronized (EFormatoData.AMERICANO_INGRESSO) {
			return DataUtil.formatarData(v, EFormatoData.AMERICANO_INGRESSO);
		}
	}

	@Override
	public Calendar unmarshal(String v) throws Exception {
		synchronized (EFormatoData.AMERICANO_INGRESSO) {
			return DataUtil.converterStringParaCalendar(v, EFormatoData.AMERICANO_INGRESSO);
		}
	}
}