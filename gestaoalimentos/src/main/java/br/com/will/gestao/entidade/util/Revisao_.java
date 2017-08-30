package br.com.will.gestao.entidade.util;

import br.com.will.gestao.entidade.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-08T23:31:30.752-0300")
@StaticMetamodel(Revisao.class)
public class Revisao_ {
	public static volatile SingularAttribute<Revisao, Long> id;
	public static volatile SingularAttribute<Revisao, Usuario> usuario;
	public static volatile SingularAttribute<Revisao, Date> dataRevisao;
}
