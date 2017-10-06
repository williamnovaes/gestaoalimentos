package br.com.will.gestao.componente;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.entidade.util.ConfiguracaoSistema;
import br.com.will.gestao.entidade.util.DataUtil;
import br.com.will.gestao.servico.CaixaServico;
import br.com.will.gestao.servico.ConfiguracaoSistemaServico;
import br.com.will.gestao.util.SistemaConstantes;

@Named
@ApplicationScoped
public class ConfiguracaoApplication implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger log;
	@EJB
	private ConfiguracaoSistemaServico configuracaoSistemaServico;
	@EJB
	private CaixaServico caixaServico;
	private Map<String, ConfiguracaoSistema> cacheConfiguracaoSistema;
	private Caixa caixa;

	@PostConstruct
	public void inicializar() {
		carregarConfiguracaoSistema();
	}

	public ConfiguracaoSistema obterConfiguracaoSistema(String descricao) {
		if (cacheConfiguracaoSistema.containsKey(descricao)) {
			return cacheConfiguracaoSistema.get(descricao);
		}
		return null;
	}

	public void carregarConfiguracaoSistema() {
		try {
			String hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
			log.info("######################## HOSTNAME - GESTAO ######################## ");
			log.info("# HOSTNAME - " + hostname + " - HOSTNAME #");
			log.info("######################## HOSTNAME - GESTAO ######################## ");
			cacheConfiguracaoSistema = new HashMap<String, ConfiguracaoSistema>();
			List<ConfiguracaoSistema> configuracoes = configuracaoSistemaServico.obterTodos("descricao");
			for (ConfiguracaoSistema configuracao : configuracoes) {
				cacheConfiguracaoSistema.put(configuracao.getDescricao(), configuracao);
			}
			caixa = caixaServico.obterPorData(DataUtil.getDataInicioDia(Calendar.getInstance()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isExecutandoNoServidor() {
		try {
			String host1 = obterConfiguracaoSistema(SistemaConstantes.HOSTNAME_SERVIDOR_1).getValor();
			return getHostname().equals(host1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getHostname() {
		try {
			return InetAddress.getLocalHost().getHostName().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			return "Hostname nao encontrado.";
		}
	}
	
	public Caixa getCaixa() {
		return caixa;
	}
}