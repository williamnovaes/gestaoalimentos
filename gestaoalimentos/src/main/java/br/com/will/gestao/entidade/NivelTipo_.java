package br.com.will.gestao.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.will.gestao.entidade.util.ESituacao;

@Generated(value="Dali", date="2017-06-19T22:52:42.060-0300")
@StaticMetamodel(NivelTipo.class)
public class NivelTipo_ {
	public static volatile SingularAttribute<NivelTipo, Integer> id;
	public static volatile SingularAttribute<NivelTipo, String> descricao;
	public static volatile SingularAttribute<NivelTipo, ESituacao> situacao;
}
