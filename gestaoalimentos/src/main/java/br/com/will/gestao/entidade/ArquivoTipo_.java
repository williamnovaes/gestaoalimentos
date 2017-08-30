package br.com.will.gestao.entidade;

import br.com.will.gestao.entidade.util.ESituacao;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-11T22:15:13.257-0300")
@StaticMetamodel(ArquivoTipo.class)
public class ArquivoTipo_ {
	public static volatile SingularAttribute<ArquivoTipo, Integer> id;
	public static volatile SingularAttribute<ArquivoTipo, String> descricao;
	public static volatile SingularAttribute<ArquivoTipo, String> diretorio;
	public static volatile SingularAttribute<ArquivoTipo, String> codigo;
	public static volatile SingularAttribute<ArquivoTipo, ESituacao> situacao;
	public static volatile SingularAttribute<ArquivoTipo, Empresa> empresa;
	public static volatile SingularAttribute<ArquivoTipo, String> layout;
}
