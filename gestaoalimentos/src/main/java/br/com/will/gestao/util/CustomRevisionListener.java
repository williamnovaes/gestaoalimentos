package br.com.will.gestao.util;

import javax.faces.context.FacesContext;

import org.hibernate.envers.RevisionListener;

import br.com.will.gestao.bean.LoginBean;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.entidade.util.Revisao;

public class CustomRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		Revisao rev = (Revisao) revisionEntity;
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			LoginBean login = context.getApplication().evaluateExpressionGet(
					context, "#{loginBean}", LoginBean.class);
			rev.setUsuario(login.getUsuarioLogado());
		} else {
			rev.setUsuario(new Usuario(1));
		}
	}
}