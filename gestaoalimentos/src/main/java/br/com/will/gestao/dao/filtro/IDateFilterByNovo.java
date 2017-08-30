package br.com.will.gestao.dao.filtro;

import java.util.Date;

public interface IDateFilterByNovo {

	IOrderByNovo filterDateBy(String filterBy, Date d1, Date d2);
	
}
