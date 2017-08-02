package br.com.will.gestao.entidade.permissao;

import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.entidade.util.ESituacao;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-11T20:35:26.694-0300")
@StaticMetamodel(Menu.class)
public class Menu_ {
	public static volatile SingularAttribute<Menu, Integer> id;
	public static volatile SingularAttribute<Menu, String> nome;
	public static volatile SingularAttribute<Menu, Integer> sequencia;
	public static volatile SingularAttribute<Menu, Calendar> dataCadastro;
	public static volatile SingularAttribute<Menu, EBoolean> visivel;
	public static volatile ListAttribute<Menu, Pagina> paginas;
	public static volatile SingularAttribute<Menu, ESituacao> situacao;
}
