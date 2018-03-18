package br.com.will.gestao.dao.filtro;

import br.com.will.gestao.entidade.util.ENivel;

public enum ESQL {
	
	ADMINISTRADOR(ENivel.ADMINISTRADOR, "", " ", " 1 = 1 ");
	
	private final ENivel nivel;
	private final String join;
	private final String alias;
	private final String where;

	ESQL(ENivel eNivel,  String join, String alias, String where) {
		this.nivel	   = eNivel;
		this.alias 	   = alias;
		this.join 	   = join + " " + this.alias;
		this.where 	   =  (this.alias.trim().isEmpty() ? "" : (this.alias + ".")) + where;
	}

	public ENivel getNivel() {
		return nivel;
	}
	
	public String getJoin() {
		return join;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public String getWhere() {
		return where;
	}
//	public String getWhereHierarquia() {
//		return whereHierarquia;
//	}
}
