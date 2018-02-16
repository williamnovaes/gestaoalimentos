package br.com.will.gestao.phase;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

import br.com.will.gestao.bean.LoginBean;

public class AutorizacaoListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		try {
			FacesContext facesContext = event.getFacesContext();
			String currentPage = facesContext.getViewRoot().getViewId();
			if (currentPage != null && !currentPage.startsWith("/home")) {
				LoginBean loginBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginBean}",
						LoginBean.class);
				loginBean.fecharModalLogin();
//				if (!currentPage.contains("login") && !currentPage.contains("menu") && !currentPage.contains("error")) {
//					if (loginBean == null || !loginBean.isLogado()) {
//						facesContext.addMessage(null,
//								new FacesMessage(FacesMessage.SEVERITY_ERROR,
//										"É necessário estar autenticado para acessar este conteúdo."
//												+ "Caso a dificuldade persista, por favor limpe o cache do navegador",
//										null));
//						redirecionar(facesContext, "/pages/home?faces-redirect=true");
//						return;
//					}
					
					// if (loginBean != null &&
					// !loginBean.isChaveSessaoValida()) {
					// facesContext.addMessage(null,
					// new FacesMessage(FacesMessage.SEVERITY_ERROR,
					// "Alguém acessou o sistema utilizando as suas credenciais,
					// para sua a segurança, acesse o sistema novamente"
					// + "e faça a alteração da senha!",
					// null));
					// loginBean.fazerLogout();
					//
					// redirecionar(facesContext,
					// "/pages/home?faces-redirect=true");
					// return;
					// }

					// TODO: Descomentar depois que colocarmos o controle
					// dinamico
					// @marcelo
					// currentPage = currentPage.replaceAll("xhtml","ppd");
					// if (currentUser != null &&;
					// !currentUser.verificaPermissao(currentPage)) {
					// facesContext.addMessage(null,
					// new FacesMessage(FacesMessage.SEVERITY_ERROR,
					// "Sem permissão de acesso à página: " + currentPage,
					// null));
					// redirecionar(facesContext,
					// "/pages/home?faces-redirect=true");
					// }

//				} else if (loginBean != null && loginBean.isLogado()) {
					redirecionar(facesContext, "index?faces-redirect=true");
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void redirecionar(FacesContext facesContext, String pagina) {
		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
		// facesContext.getExternalContext().getFlash().setKeepMessages(true);
		nh.handleNavigation(facesContext, null, pagina);
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		try {
			FacesContext facesContext = event.getFacesContext();
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.setDateHeader("Expires", -1); // Prevents proxy caching.

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}