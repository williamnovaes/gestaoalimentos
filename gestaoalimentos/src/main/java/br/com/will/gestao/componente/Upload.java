package br.com.will.gestao.componente;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.Part;

public final class Upload {

	public static final Upload UPLOAD = new Upload();

	private Upload() {

	}

	public String write(Part part) throws IOException {
		String fileName = extractFileName(part).replace(" ", "_");
		String filePath = "/opt/gestaoalimentos/arquivos";

		File fileSaveDir = new File(filePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		part.write(filePath + File.separator + fileName);
		String endereco = filePath + File.separator + fileName;
		return endereco;
	}

	public String write(Part part, String filePath) throws IOException {
		String fileName = extractFileName(part);

		File fileSaveDir = new File(filePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		part.write(filePath + File.separator + fileName);
		String endereco = filePath + File.separator + fileName;
		return endereco;
	}

	public String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	public static Upload getInstance() {
		return UPLOAD;
	}
}