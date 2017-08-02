package br.com.will.gestao.entidade.permissao;

import br.com.will.gestao.entidade.Nivel;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-08T23:30:32.057-0300")
@StaticMetamodel(PaginaNivel.class)
public class PaginaNivel_ {
	public static volatile SingularAttribute<PaginaNivel, PaginaNivelPK> id;
	public static volatile SingularAttribute<PaginaNivel, Pagina> pagina;
	public static volatile SingularAttribute<PaginaNivel, Nivel> nivel;
}
