package br.com.will.gestao.entidade;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;

@Generated(value="Dali", date="2017-06-27T22:01:15.475-0300")
@StaticMetamodel(Ingrediente.class)
public class Ingrediente_ {
	public static volatile SingularAttribute<Ingrediente, Integer> id;
	public static volatile SingularAttribute<Ingrediente, String> nome;
	public static volatile SingularAttribute<Ingrediente, String> descricao;
	public static volatile SingularAttribute<Ingrediente, ESituacao> situacao;
	public static volatile SingularAttribute<Ingrediente, EBoolean> adicional;
	public static volatile SingularAttribute<Ingrediente, BigDecimal> valorAdicional;
}
