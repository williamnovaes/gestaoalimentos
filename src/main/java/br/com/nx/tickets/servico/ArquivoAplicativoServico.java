package br.com.nx.tickets.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.ArquivoAplicativoDAO;
import br.com.nx.tickets.entidade.ArquivoAplicativo;
import br.com.nx.tickets.servico.exception.BaseServicoException;

@Stateless
public class ArquivoAplicativoServico extends BaseServico<ArquivoAplicativo> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ArquivoAplicativoDAO arquivoAplicativoDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(arquivoAplicativoDao);
	}

	public ArquivoAplicativo salvar(ArquivoAplicativo arquivo) throws BaseServicoException {
		try {
			arquivo = super.salvar(arquivo);
			return arquivo;
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}