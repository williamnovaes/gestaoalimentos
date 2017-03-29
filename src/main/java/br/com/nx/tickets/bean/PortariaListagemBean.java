package br.com.nx.tickets.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.nx.tickets.entidade.Portaria;
import br.com.nx.tickets.entidade.Usuario;
import br.com.nx.tickets.servico.PortariaServico;
import br.com.nx.tickets.servico.UsuarioServico;
import br.com.nx.tickets.util.SistemaConstantes;

@Named
@ViewScoped
public class PortariaListagemBean extends BaseListagemBean implements Modable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PortariaServico portariaServico;
	@EJB
	private UsuarioServico usuarioServico;

	private Portaria portaria;
	private List<Usuario> usuariosDisponiveis;

	private boolean exibirModalUsuario;

	@Override
	@PostConstruct
	public void inicializar() {
		configurarPaginador(getFiltroPermissaoUsuario(), portariaServico, SistemaConstantes.DEZ);
		buscarRegistros();
	}

	public void buscarRegistros() {
		try {
			paginar(1);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void salvarUsuarioPortaria() {
		try {
			portariaServico.alterar(portaria);
			fecharModal();
			inicializar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obterUsuariosPorPortaria(Portaria p) {
		try {
			this.portaria = p;
			exibirModalUsuario = true;
			usuariosDisponiveis = usuarioServico.obterTodos("nome");
			if (portaria.getUsuario() != null) {
				usuariosDisponiveis.remove(portaria.getUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adicionarUsuario(Usuario us) {
		portaria.setUsuario(us);
		usuariosDisponiveis.remove(us);
	}
	
	public void alterarSituacao(Portaria pt) {
		try {
			pt.alterarSituacao();
			portariaServico.alterar(pt);
			buscarRegistros();
		} catch (Exception e) {
			adicionarError("Erro ao alterar situação!");
			e.printStackTrace();
		}
	}

	@Override
	public void fecharModal() {
		exibirModalUsuario = false;
	}

	public Portaria getPortaria() {
		return portaria;
	}

	public List<Usuario> getUsuariosDisponiveis() {
		return usuariosDisponiveis;
	}

	public boolean isExibirModalUsuario() {
		return exibirModalUsuario;
	}
}
