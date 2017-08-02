package br.com.will.gestao.entidade;

import br.com.will.gestao.entidade.util.ESituacao;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-19T22:59:38.326-0300")
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Integer> id;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, String> cpf;
	public static volatile SingularAttribute<Usuario, String> rg;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> login;
	public static volatile SingularAttribute<Usuario, String> senha;
	public static volatile SingularAttribute<Usuario, Nivel> nivel;
	public static volatile SingularAttribute<Usuario, ESituacao> situacao;
	public static volatile SingularAttribute<Usuario, Empresa> empresa;
	public static volatile SingularAttribute<Usuario, Calendar> dataUltimaInteracao;
	public static volatile SingularAttribute<Usuario, Calendar> dataCadastro;
	public static volatile SingularAttribute<Usuario, Calendar> dataNascimento;
}
