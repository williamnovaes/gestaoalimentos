package br.com.will.gestao.rest;

import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import br.com.will.gestao.entidade.util.DataUtil;
import br.com.will.gestao.entidade.util.EFormatoData;

public class DateFormatterAdapter extends XmlAdapter<String, Calendar> {

	@Override
	public String marshal(Calendar v) throws Exception {
		synchronized (EFormatoData.AMERICANO_TIME_ZONE) {
			return DataUtil.formatarData(v, EFormatoData.AMERICANO_TIME_ZONE);
		}
	}

	@Override
	public Calendar unmarshal(String v) throws Exception {
		synchronized (EFormatoData.AMERICANO_TIME_ZONE) {
			return DataUtil.converterStringParaCalendar(v, EFormatoData.AMERICANO_TIME_ZONE);
		}
	}
}