package br.com.will.gestao.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Nivel;
import br.com.will.gestao.entidade.util.ETipoNivel;
import br.com.will.gestao.servico.NivelServico;

@Named
@ViewScoped
public class NivelCadastroBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private NivelServico nivelServico;
	
	private ETipoNivel[] tiposNivel = ETipoNivel.values();
	
	private Nivel nivel;
	private ETipoNivel tipoNivel;
	
	private Integer idNivelGrupoSelecionado;
	
	@PostConstruct
	public void inicializar() {
		try {
			if (nivel == null) {
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String idParam = ctx.getRequestParameterMap().get("idNivel");
				if (idParam != null && !idParam.equals("")) {
					try {
						nivel = nivelServico.obterPorId(Integer.parseInt(idParam));
						tipoNivel = ETipoNivel.valueOf(nivel.getNivelTipo().getDescricao());
					} catch (Exception e) {
						e.printStackTrace();
						adicionarError("Erro ao buscar Nível.");
					}
				}
				if (this.nivel == null) {
					this.nivel = new Nivel();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
	}

	public String salvar() {
		try {
			if (nivel.getDescricao() == null) {
				adicionarInfo("Insira a descrição.");
			} else if (idNivelGrupoSelecionado == null) {
				adicionarInfo("Selecione o Grupo.");
			} else if (tipoNivel == null) {
				adicionarInfo("Selecione o Tipo.");
			} else {
				if (nivel.getId() != null) {
					nivelServico.alterar(nivel);
					adicionarInfo("Alterado com sucesso!");
				} else {
					nivelServico.salvar(nivel);
					adicionarInfo("Cadastrado com sucesso!");
				}
				return "niveis?faces-redirect=true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			adicionarError(e.getMessage());
		}
		return null;
	}

	public ETipoNivel[] getTiposNivel() {
		return tiposNivel;
	}

	public void setTiposNivel(ETipoNivel[] tiposNivel) {
		this.tiposNivel = tiposNivel;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public ETipoNivel getTipoNivel() {
		return tipoNivel;
	}

	public void setTipoNivel(ETipoNivel tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	public Integer getIdNivelGrupoSelecionado() {
		return idNivelGrupoSelecionado;
	}

	public void setIdNivelGrupoSelecionado(Integer idNivelGrupoSelecionado) {
		this.idNivelGrupoSelecionado = idNivelGrupoSelecionado;
	}
}