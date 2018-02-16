package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import br.com.will.gestao.entidade.Usuario;
import br.com.will.gestao.servico.UsuarioServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ViewScoped
public class UsuarioListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioServico usuarioServico;
	
	private Usuario usuario;
	
	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), usuarioServico, SistemaConstantes.DEZ);
	}

	public void buscarRegistros() {
		try {
			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void fecharModal() {
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}