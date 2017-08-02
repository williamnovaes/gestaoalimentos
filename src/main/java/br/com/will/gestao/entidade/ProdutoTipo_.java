package br.com.will.gestao.entidade;

import br.com.will.gestao.entidade.util.ESituacao;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-25T21:49:08.896-0300")
@StaticMetamodel(ProdutoTipo.class)
public class ProdutoTipo_ {
	public static volatile SingularAttribute<ProdutoTipo, Integer> id;
	public static volatile SingularAttribute<ProdutoTipo, String> descricao;
	public static volatile SingularAttribute<ProdutoTipo, Empresa> empresa;
	public static volatile SingularAttribute<ProdutoTipo, ESituacao> situacao;
}
