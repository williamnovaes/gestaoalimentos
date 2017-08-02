package br.com.will.gestao.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-08T23:19:18.866-0300")
@StaticMetamodel(Endereco.class)
public class Endereco_ {
	public static volatile SingularAttribute<Endereco, Integer> id;
	public static volatile SingularAttribute<Endereco, String> bairro;
	public static volatile SingularAttribute<Endereco, String> bloco;
	public static volatile SingularAttribute<Endereco, String> cep;
	public static volatile SingularAttribute<Endereco, String> complemento;
	public static volatile SingularAttribute<Endereco, String> tipoLogradouro;
	public static volatile SingularAttribute<Endereco, String> logradouro;
	public static volatile SingularAttribute<Endereco, String> numero;
	public static volatile SingularAttribute<Endereco, Cidade> cidade;
	public static volatile ListAttribute<Endereco, Empresa> empresas;
}
