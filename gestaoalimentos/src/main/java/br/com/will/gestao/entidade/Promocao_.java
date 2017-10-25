package br.com.will.gestao.entidade;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.will.gestao.entidade.util.ESituacao;

@Generated(value="Dali", date="2017-06-11T21:01:35.532-0300")
@StaticMetamodel(Promocao.class)
public class Promocao_ {
	public static volatile SingularAttribute<Promocao, Integer> id;
	public static volatile SingularAttribute<Promocao, String> descricao;
	public static volatile SingularAttribute<Promocao, String> observacao;
	public static volatile SingularAttribute<Promocao, Integer> quantidade;
	public static volatile SingularAttribute<Promocao, Calendar> dataInicio;
	public static volatile SingularAttribute<Promocao, Calendar> dataFim;
	public static volatile SingularAttribute<Promocao, Usuario> usuario;
	public static volatile SingularAttribute<Promocao, ESituacao> situacao;
}
