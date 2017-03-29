package br.com.nx.tickets.servico;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.nx.tickets.dao.SocioTorcedorDAO;
import br.com.nx.tickets.entidade.Arquivo;
import br.com.nx.tickets.entidade.Cliente;
import br.com.nx.tickets.entidade.EventoSocioTorcedor;
import br.com.nx.tickets.entidade.socio.SocioTorcedor;
import br.com.nx.tickets.servico.exception.BaseServicoException;
import br.com.nx.tickets.util.LeitorArquivo;

@Stateless
public class SocioTorcedorServico extends BaseServico<SocioTorcedor> {

	private static final long serialVersionUID = 1L;
	@Inject
	private SocioTorcedorDAO socioTorcedorDao;

	@Override
	@PostConstruct
	public void inicializar() {
		setDao(socioTorcedorDao);
	}
	
	public SocioTorcedor obterPorCodigo(String codigo) throws BaseServicoException {
		try {
			return socioTorcedorDao.consultarPorCodigo(codigo);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<EventoSocioTorcedor> obterParticipacoes(SocioTorcedor socio) throws BaseServicoException {
		try {
			return socioTorcedorDao.consultarParticipacoes(socio);
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	public Arquivo salvar(Arquivo arquivo, Cliente cliente) throws BaseServicoException {
		try {
			List<SocioTorcedor> sociosTorcedores = socioTorcedorDao.consultarPorCliente(cliente);
			HashMap<String, SocioTorcedor> sociosTorcedoresCache = new HashMap<>(); 
			for (SocioTorcedor socioTorcedor : sociosTorcedores) {
				sociosTorcedoresCache.put(socioTorcedor.getCarteirinha(), socioTorcedor);
			}
			List<SocioTorcedor> socioTorcedores = processar(arquivo);
			for (SocioTorcedor socioTorcedor : socioTorcedores) {
				socioTorcedor.setCliente(cliente);
			}
			List<SocioTorcedor> sociosTorcedoresAux = new ArrayList<>();
			for (SocioTorcedor socioTorcedor : socioTorcedores) {
				SocioTorcedor st = sociosTorcedoresCache.get(socioTorcedor.getCarteirinha());
				if (st == null) {
					sociosTorcedoresAux.add(socioTorcedor);
					sociosTorcedoresCache.put(socioTorcedor.getCarteirinha(), socioTorcedor);
				} else {
					socioTorcedor.setId(st.getId());
					socioTorcedorDao.alterar(socioTorcedor);
				}
			}
			if (!sociosTorcedoresAux.isEmpty()) {
				socioTorcedorDao.salvar(sociosTorcedoresAux);
			}
			return arquivo;
		} catch (Exception e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
	
	private List<SocioTorcedor> processar(Arquivo arquivo) throws Exception {
		try {
			List<SocioTorcedor> sociosTorcedores = new ArrayList<>();
			List<String[]> obj = LeitorArquivo.processarXLS(new FileInputStream(arquivo.getCaminhoArquivo()), 1);
			for (String[] strings : obj) {
				SocioTorcedor st = new SocioTorcedor(strings);
				if (!st.getCarteirinha().isEmpty()) {
					sociosTorcedores.add(st);
				} else {
					getLog().error(strings[2]);
				}
			}
			return sociosTorcedores;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
}