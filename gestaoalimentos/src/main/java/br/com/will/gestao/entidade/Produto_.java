package br.com.will.gestao.entidade;

import br.com.will.gestao.entidade.util.ESituacao;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T22:36:00.479-0300")
@StaticMetamodel(Produto.class)
public class Produto_ {
	public static volatile SingularAttribute<Produto, Integer> id;
	public static volatile SingularAttribute<Produto, String> nome;
	public static volatile SingularAttribute<Produto, Integer> index;
	public static volatile SingularAttribute<Produto, String> descricao;
	public static volatile SingularAttribute<Produto, Produto> produtoPai;
	public static volatile SingularAttribute<Produto, ProdutoTipo> produtoTipo;
	public static volatile SingularAttribute<Produto, ESituacao> situacao;
	public static volatile SingularAttribute<Produto, BigDecimal> valor;
	public static volatile SingularAttribute<Produto, Empresa> empresa;
}
