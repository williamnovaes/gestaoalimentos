package br.com.will.gestao.entidade;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.will.gestao.entidade.util.EFormaPagamento;
import br.com.will.gestao.entidade.util.EStatus;
import br.com.will.gestao.entidade.util.ETipoEntrega;

@Generated(value="Dali", date="2017-06-12T22:06:06.126-0300")
@StaticMetamodel(Pedido.class)
public class Pedido_ {
	public static volatile SingularAttribute<Pedido, Integer> id;
	public static volatile SingularAttribute<Pedido, Calendar> dataCadastro;
	public static volatile SingularAttribute<Pedido, EFormaPagamento> formaPagamento;
	public static volatile SingularAttribute<Pedido, ETipoEntrega> tipoEntrega;
	public static volatile SingularAttribute<Pedido, Usuario> usuario;
	public static volatile SingularAttribute<Pedido, BigDecimal> total;
	public static volatile SingularAttribute<Pedido, Caixa> caixa;
	public static volatile SingularAttribute<Pedido, EStatus> statusAtendimento;
}
