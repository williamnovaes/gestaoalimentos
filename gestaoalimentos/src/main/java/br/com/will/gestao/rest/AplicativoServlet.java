package br.com.will.gestao.rest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.will.gestao.util.Util;

@WebServlet("/app")
public class AplicativoServlet extends HttpServlet {
    private static final long serialVersionUID = -4635239628819022096L;
 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		try {
			String nomeArquivo = request.getParameter("nome");
			response.setContentType("application/exe");
			response.addHeader("content-disposition", "filename=" + nomeArquivo + ".exe");
			OutputStream out = response.getOutputStream();
			out.write(Util.readFile(new File("/opt/gestao/arquivos/app/" + nomeArquivo + ".exe")));
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
