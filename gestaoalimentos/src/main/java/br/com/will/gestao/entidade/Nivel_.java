package br.com.will.gestao.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.will.gestao.entidade.permissao.PaginaNivel;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;

@Generated(value="Dali", date="2017-07-24T22:48:50.464-0300")
@StaticMetamodel(Nivel.class)
public class Nivel_ {
	public static volatile SingularAttribute<Nivel, Integer> id;
	public static volatile SingularAttribute<Nivel, String> descricao;
	public static volatile SingularAttribute<Nivel, ESituacao> situacao;
	public static volatile SingularAttribute<Nivel, EBoolean> chat;
	public static volatile ListAttribute<Nivel, Usuario> usuarios;
	public static volatile SingularAttribute<Nivel, String> icone;
	public static volatile SingularAttribute<Nivel, String> alias;
	public static volatile SingularAttribute<Nivel, NivelTipo> nivelTipo;
	public static volatile ListAttribute<Nivel, PaginaNivel> paginaNivel;
}
