package br.com.will.gestao.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-12T20:32:08.638-0300")
@StaticMetamodel(PedidoProduto.class)
public class PedidoProduto_ {
	public static volatile SingularAttribute<PedidoProduto, PedidoProdutoPK> id;
	public static volatile SingularAttribute<PedidoProduto, Pedido> pedido;
	public static volatile SingularAttribute<PedidoProduto, Produto> produto;
	public static volatile SingularAttribute<PedidoProduto, String> observacao;
	public static volatile SingularAttribute<PedidoProduto, Integer> quantidade;
}
