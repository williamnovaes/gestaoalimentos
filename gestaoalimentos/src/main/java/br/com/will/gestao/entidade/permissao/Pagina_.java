package br.com.will.gestao.entidade.permissao;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.will.gestao.entidade.util.EPaginaTipo;
import br.com.will.gestao.entidade.util.ESituacao;

@Generated(value="Dali", date="2017-06-08T23:30:09.552-0300")
@StaticMetamodel(Pagina.class)
public class Pagina_ {
	public static volatile SingularAttribute<Pagina, Integer> id;
	public static volatile SingularAttribute<Pagina, String> nome;
	public static volatile SingularAttribute<Pagina, String> rotulo;
	public static volatile SingularAttribute<Pagina, String> bean;
	public static volatile SingularAttribute<Pagina, Integer> sequencia;
	public static volatile SingularAttribute<Pagina, Calendar> dataCadastro;
	public static volatile SingularAttribute<Pagina, EPaginaTipo> paginaTipo;
	public static volatile ListAttribute<Pagina, PaginaNivel> paginaNivel;
	public static volatile SingularAttribute<Pagina, Menu> menu;
	public static volatile SingularAttribute<Pagina, ESituacao> situacao;
}
