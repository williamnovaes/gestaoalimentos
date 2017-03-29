package br.com.nx.tickets.phase;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class AutorizacaoListener implements PhaseListener {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void afterPhase(PhaseEvent event) {
//		try {
//			FacesContext facesContext = event.getFacesContext();
//			String currentPage = facesContext.getViewRoot().getViewId();
//			LoginBean loginBean = facesContext.getApplication().evaluateExpressionGet(facesContext, 
//					"#{loginBean}", LoginBean.class);
//			if (!currentPage.contains("login") && !currentPage.contains("error") 
//					|| !currentPage.contains("promocao")) {
//				if (loginBean == null || !loginBean.isLogado()) {
//					redirecionar(facesContext, "/pages/login.nx");
//					FacesContext.getCurrentInstance().addMessage(null,
//							new FacesMessage(FacesMessage.SEVERITY_ERROR, 
//									"É necessário estar autenticado para acessar este conteúdo. "
//									+ "Caso a dificuldade persista, por favor limpe o cache do navegador.", null));
//				}
//				if (currentPage != null) {
//					String current = currentPage.split("/")[2].split("\\.")[0];
//					if (current != null && !loginBean.termPermissaoPagina(current) 
//							&& !currentPage.contains("home")) {
//						redirecionar(facesContext, "/error/500.nx");
//					}
//				}
//			} else if (loginBean != null && loginBean.isLogado()) {
//				redirecionar(facesContext, "home.nx");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private void redirecionar(FacesContext facesContext, String pagina) {
		NavigationHandler nh = facesContext.getApplication()
				.getNavigationHandler();
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