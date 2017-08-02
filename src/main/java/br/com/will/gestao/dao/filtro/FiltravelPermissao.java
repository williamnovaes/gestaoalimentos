package br.com.will.gestao.dao.filtro;

import java.util.Set;

import br.com.will.gestao.componente.Filtravel;
import br.com.will.gestao.servico.exception.BaseServicoException;

public interface FiltravelPermissao<T> {

	Set<T> consularPorFiltro(Filtravel filtravel) throws BaseServicoException;
}