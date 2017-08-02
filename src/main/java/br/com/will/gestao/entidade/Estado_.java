package br.com.will.gestao.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-27T21:59:49.812-0300")
@StaticMetamodel(Estado.class)
public class Estado_ {
	public static volatile SingularAttribute<Estado, String> uf;
	public static volatile SingularAttribute<Estado, String> nome;
	public static volatile ListAttribute<Estado, Cidade> cidades;
}
