package br.com.will.gestao.bean;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.will.gestao.entidade.Nivel;
import br.com.will.gestao.servico.NivelServico;

@Named
@ViewScoped
public class NivelListagemBean extends BaseListagemBean implements DesativavelAtivavel<Nivel> {

	private static final long serialVersionUID = 1L;

	@EJB
	private NivelServico nivelServico;

	private List<Nivel> niveis;

	private Integer idNivel;

	@Override
	@PostConstruct
	public void inicializar() {
		try {
			niveis = nivelServico.obterTodos();
			Collections.sort(niveis);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void alterarSituacao(Nivel r) {
		try {
			r.alterarSituacao();
			nivelServico.alterar(r);
			buscarRegistrosComPaginacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void fecharModal() {
		idNivel = null;
	}

	public List<Nivel> getNiveis() {
		return niveis;
	}

	public void setNiveis(List<Nivel> niveis) {
		this.niveis = niveis;
	}
	
	public Integer getIdNivel() {
		return idNivel;
	}
	
	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
	}
}