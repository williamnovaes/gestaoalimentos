package br.com.will.gestao.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-30T20:34:46.144-0300")
@StaticMetamodel(Tamanho.class)
public class Tamanho_ {
	public static volatile SingularAttribute<Tamanho, Integer> id;
	public static volatile SingularAttribute<Tamanho, String> tamanho;
	public static volatile SingularAttribute<Tamanho, String> descricao;
	public static volatile SingularAttribute<Tamanho, TamanhoTipo> tamanhoTipo;
	public static volatile SingularAttribute<Tamanho, ProdutoTipo> produtoTipo;
}
