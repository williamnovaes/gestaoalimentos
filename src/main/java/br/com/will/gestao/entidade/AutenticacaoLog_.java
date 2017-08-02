package br.com.will.gestao.entidade;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-11T20:35:26.681-0300")
@StaticMetamodel(AutenticacaoLog.class)
public class AutenticacaoLog_ {
	public static volatile SingularAttribute<AutenticacaoLog, Integer> id;
	public static volatile SingularAttribute<AutenticacaoLog, String> login;
	public static volatile SingularAttribute<AutenticacaoLog, String> senha;
	public static volatile SingularAttribute<AutenticacaoLog, String> terminal;
	public static volatile SingularAttribute<AutenticacaoLog, Calendar> dataCadastro;
}
