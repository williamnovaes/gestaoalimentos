package br.com.will.gestao.entidade;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-11T20:35:26.686-0300")
@StaticMetamodel(Empresa.class)
public class Empresa_ {
	public static volatile SingularAttribute<Empresa, Integer> id;
	public static volatile SingularAttribute<Empresa, String> cnpj;
	public static volatile SingularAttribute<Empresa, String> nomeFantasia;
	public static volatile SingularAttribute<Empresa, String> razaoSocial;
	public static volatile SingularAttribute<Empresa, String> telefone;
	public static volatile SingularAttribute<Empresa, String> telefone2;
	public static volatile SingularAttribute<Empresa, String> email;
	public static volatile SingularAttribute<Empresa, Endereco> endereco;
	public static volatile ListAttribute<Empresa, Usuario> usuariosCadastro;
	public static volatile SingularAttribute<Empresa, Calendar> dataCadastro;
}
