package br.com.will.gestao.entidade;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.will.gestao.entidade.util.ESituacao;

@Generated(value="Dali", date="2017-06-12T22:31:33.292-0300")
@StaticMetamodel(Arquivo.class)
public class Arquivo_ {
	public static volatile SingularAttribute<Arquivo, Integer> id;
	public static volatile SingularAttribute<Arquivo, String> nomeArquivo;
	public static volatile SingularAttribute<Arquivo, Calendar> dataCadastro;
	public static volatile SingularAttribute<Arquivo, String> caminhoArquivo;
	public static volatile SingularAttribute<Arquivo, ESituacao> situacao;
	public static volatile SingularAttribute<Arquivo, ArquivoTipo> arquivoTipo;
	public static volatile SingularAttribute<Arquivo, Empresa> empresa;
}
