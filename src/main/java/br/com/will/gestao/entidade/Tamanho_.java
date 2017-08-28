package br.com.will.gestao.entidade;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-06T22:48:21.864-0300")
@StaticMetamodel(Tamanho.class)
public class Tamanho_ {
	public static volatile SingularAttribute<Tamanho, Integer> id;
	public static volatile SingularAttribute<Tamanho, String> tamanho;
	public static volatile SingularAttribute<Tamanho, String> descricao;
	public static volatile SingularAttribute<Tamanho, Integer> sequencia;
	public static volatile SingularAttribute<Tamanho, TamanhoTipo> tamanhoTipo;
	public static volatile SingularAttribute<Tamanho, ProdutoTipo> produtoTipo;
	public static volatile SingularAttribute<Tamanho, BigDecimal> valor;
}
