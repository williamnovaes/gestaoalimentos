package br.com.will.gestao.dao.filtro;

import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;

public interface IPaginavelNovo {

	IFilterByNovo setupPaginador(Paginador<Paginavel> paginador);
	
}
