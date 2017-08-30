package br.com.will.gestao.bean;

import java.io.Serializable;

public interface DesativavelAtivavel<T> extends Serializable {

	void alterarSituacao(T t);
}
