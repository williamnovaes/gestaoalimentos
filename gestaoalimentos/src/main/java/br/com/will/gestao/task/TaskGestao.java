package br.com.will.gestao.task;

import java.io.Serializable;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.will.gestao.entidade.Caixa;
import br.com.will.gestao.entidade.Empresa;
import br.com.will.gestao.entidade.util.DataUtil;
import br.com.will.gestao.entidade.util.EBoolean;
import br.com.will.gestao.servico.CaixaServico;
import br.com.will.gestao.servico.EmpresaServico;
import br.com.will.gestao.util.SistemaConstantes;

public class TaskGestao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private transient Logger log;
	@EJB
	private CaixaServico caixaServico;
	@EJB
	private EmpresaServico empresaServico;
	
	@Schedule(dayOfWeek = SistemaConstantes.ALL_DAYS_WITHOUT_MONDAY, hour = "18", minute = "00", second = "00", info = "aberturaCaixa")
	public void abrirCaixa() {
		try {
			log.info("INICIO ABRIR CAIXA");
			Calendar dataInicioDia = DataUtil.getDataInicioDia(Calendar.getInstance());
			Caixa caixa = caixaServico.obterPorData(dataInicioDia);
			if (caixa != null && caixa.isAberto()) {
				log.info("Caixa ja cadastrado e aberto");
			}
			if (caixa != null && !caixa.isAberto()) {
				caixaServico.abrirCaixa(caixa);
			}
			if (caixa == null) {
				Empresa empresa = empresaServico.obterEmpresa();
				caixa = new Caixa();
				caixa.setAberto(EBoolean.TRUE);
				caixa.setDataAbertura(Calendar.getInstance());
				caixa.setEmpresa(empresa);
				caixa.setObservacao("Caixa criado automaticamente pelo sistema");
				caixaServico.salvar(caixa);
			}
			log.info("CAIXA ABERTO");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("ERRO ABRIR CAIXA");
		}
	}
	
	@Schedule(dayOfWeek = SistemaConstantes.ALL_DAYS, hour = "04", minute = "00", second = "00", info = "fechamentoCaixa")
	public void fecharCaixa() {
		try {
			log.info("INICIO FECHAR CAIXA");
			log.info("CAIXA FECHADO");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("ERRO AO FECHAR CAIXA");
		}
	}
}
