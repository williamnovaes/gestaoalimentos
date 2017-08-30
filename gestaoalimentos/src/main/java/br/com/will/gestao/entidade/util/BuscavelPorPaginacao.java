package br.com.will.gestao.entidade.util;

import java.io.Serializable;

import javax.ejb.Local;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.componente.Paginador;
import br.com.will.gestao.componente.Paginavel;
import br.com.will.gestao.servico.exception.BaseServicoException;

@Local
public interface BuscavelPorPaginacao extends Serializable {
	
	Paginador<Paginavel> obterPorFiltro(Paginador<Paginavel> paginador,
			Filtravel filtravel) throws BaseServicoException;
}
