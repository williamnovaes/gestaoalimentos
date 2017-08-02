package br.com.will.gestao.filtro.cdi;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.will.gestao.entidade.Estado;
import br.com.will.gestao.entidade.Usuario;

public interface FiltravelNovo extends Serializable, Cloneable {

	List<Usuario> getExecutivos();
	
	void setExecutivos(List<Usuario> executivos);
	
	List<String> getExecutivosSelecionados();
	
	void setExecutivosSelecionados(List<String> executivosSelecionados);

	void setCoordenadoresAgenteAutorizado(List<Usuario> coordenadoresAgenteAutorizado);
	
	List<Usuario> getCoordenadoresAgenteAutorizado();
	
	List<String> getCoordenadoresAgenteAutorizadoSelecionados();
	
	void setCoordenadoresAgenteAutorizadoSelecionados(List<String> coordenadoresAgenteAutorizadoSelecionados);
	
	List<String> getConsultoresSelecionados();
	
	void setConsultoresSelecionados(List<String> selecionados);
	
	List<Usuario> getConsultores();
	
	void setConsultores(List<Usuario> consultores);
	
	List<String> getRegionaisSelecionadas();
	
	void setRegionaisSelecionadas(List<String> selecionadas);
	
	Integer getRegionalSelecionada();

	void setRegionalSelecionada(Integer id);
	
	List<Estado> getEstados();
	
	void setEstados(List<Estado> hierarquias);

	List<String> getEstadosSelecionados();

	void setEstadosSelecionados(List<String> selecionadas);
	
	List<String> getAgentesAutorizadosSelecionados();

	void setAgentesAutorizadosSelecionados(List<String> selecionadas);
	
	void setAgenteAutorizadoSelecionado(Integer id);
	
	Integer getAgenteAutorizadoSelecionado();
	
	List<String> getCanaisVendaSelecionados();

	void setCanaisVendaSelecionados(List<String> selecionadas);
	
	void setCanalVendaSelecionado(Integer id);
	
	Integer getCanalVendaSelecionado();
	
	List<String> getCoordenacoesSelecionadas();
	
	void setCoordenacoesSelecionadas(List<String> selecionadas);

	void setCoordenacaoSelecionada(Integer id);
	
	Integer getCoordenacaoSelecionada();
	
	List<String> getEquipesVendaSelecionadas();
	
	void setEquipesVendaSelecionadas(List<String> selecionadas);
	
	void setEquipeVendaSelecionada(Integer id);
	
	Integer getEquipeVendaSelecionada();
	
	List<Usuario> getVendedores();
	
	void setVendedores(List<Usuario> hierarquias);
	
	List<String> getVendedoresSelecionados();
	
	void setVendedoresSelecionados(List<String> selecionadas);
	
	List<Usuario> getAtendentes();
	
	void setAtendentes(List<Usuario> hierarquias);
	
	List<String> getAtendentesSelecionados();
	
	void setAtendentesSelecionados(List<String> selecionadas);
	
	void setVendedorSelecionado(Integer id);
	
	Integer getVendedorSelecionado();
	
	Date getDataInicial();
	
	void setDataInicial(Date dataInicial);

	Date getDataFinal();
	
	void setDataFinal(Date dataFinal);
	
	boolean isConsiderarUsuarioLogado();
	
	void setConsiderarUsuarioLogado(boolean considerarUsuarioLogado);
	
	void setDataUltimoCarregamentoDadosFiltro(Calendar dataUltimoCarregamentoDadosFiltro);
	
	Calendar getDataUltimoCarregamentoDadosFiltro();
	
	void setEntradaFiltro(String entradaFiltro);
	
	String getEntradaFiltro();
	
	Integer getIdAgenteAutorizadoSelecionado();
	
	void setIdAgenteAutorizadoSelecionado(Integer id);
	
	List<Usuario> getUsuarios();
	
	void setUsuarios(List<Usuario> usuarios);
	
	Integer getIdUsuarioSelecionado();
	
	void setIdUsuarioSelecionado(Integer id);
}
