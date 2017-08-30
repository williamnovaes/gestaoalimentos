package br.com.will.gestao.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-12T21:11:59.153-0300")
@StaticMetamodel(ProdutoIngrediente.class)
public class ProdutoIngrediente_ {
	public static volatile SingularAttribute<ProdutoIngrediente, ProdutoIngredientePK> id;
	public static volatile SingularAttribute<ProdutoIngrediente, Produto> produto;
	public static volatile SingularAttribute<ProdutoIngrediente, Ingrediente> ingrediente;
	public static volatile SingularAttribute<ProdutoIngrediente, String> observacao;
	public static volatile SingularAttribute<ProdutoIngrediente, Integer> quantidade;
}
