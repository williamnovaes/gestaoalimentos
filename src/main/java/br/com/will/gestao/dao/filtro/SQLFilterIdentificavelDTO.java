package br.com.will.gestao.dao.filtro;

import java.util.List;

import br.com.will.gestao.entidade.util.Identificavel;
import br.com.will.gestao.entidade.util.ValueGenerator;
import br.com.will.gestao.util.SistemaConstantes;

public class SQLFilterIdentificavelDTO {

	private String alias;
	private String attribute;
	private List<Identificavel> identificaveis;
	
	public SQLFilterIdentificavelDTO(String attribute, List<Identificavel> identificaveis) {
		this.alias			= "_" + ValueGenerator.sqlAlias(SistemaConstantes.DEZ);
		this.attribute 		= attribute;
		this.identificaveis = identificaveis;
	}
	
	public String getAlias() {
		return alias;
	}

	public String getAttribute() {
		return attribute;
	}
	
	public List<Identificavel> getIdentificaveis() {
		return identificaveis;
	}

	@Override
	public String toString() {
		return "SQLFilterIdentificavelDTO [alias=" + alias + ", attribute="
				+ attribute + ", identificaveis=" + identificaveis + "]";
	}
}
