package br.com.will.gestao.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-12T21:11:59.153-0300")
@StaticMetamodel(SaborIngrediente.class)
public class ProdutoIngrediente_ {
	public static volatile SingularAttribute<SaborIngrediente, SaborIngredientePK> id;
	public static volatile SingularAttribute<SaborIngrediente, Produto> produto;
	public static volatile SingularAttribute<SaborIngrediente, Ingrediente> ingrediente;
	public static volatile SingularAttribute<SaborIngrediente, String> observacao;
	public static volatile SingularAttribute<SaborIngrediente, Integer> quantidade;
}
