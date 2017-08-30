package br.com.will.gestao.entidade;

import br.com.will.gestao.entidade.util.EBoolean;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-11T22:30:25.726-0300")
@StaticMetamodel(Caixa.class)
public class Caixa_ {
	public static volatile SingularAttribute<Caixa, Integer> id;
	public static volatile SingularAttribute<Caixa, Empresa> empresa;
	public static volatile SingularAttribute<Caixa, EBoolean> aberto;
	public static volatile SingularAttribute<Caixa, EBoolean> entrega;
	public static volatile SingularAttribute<Caixa, Calendar> dataAbertura;
	public static volatile SingularAttribute<Caixa, Calendar> dataFechamento;
	public static volatile SingularAttribute<Caixa, String> observacao;
}
