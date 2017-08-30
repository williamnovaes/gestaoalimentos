package br.com.will.gestao.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import br.com.will.gestao.componente.ESeparador;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public final class LeitorArquivo {

	private LeitorArquivo() {
	}
	
	public static List<String[]> processarCSV(InputStream arquivo, ESeparador separador) {
		List<String[]> arrayDados = new ArrayList<String[]>();
		BufferedReader br = null;
		String linha = "";
		String split = separador.getSplit();

		try {
			br = new BufferedReader(new InputStreamReader(arquivo, "UTF-8"));
			br.readLine();
			while ((linha = br.readLine()) != null) {
				linha = linha.toUpperCase();
				String[] array = Util.substituirCaracteresEspeciaisSemVirgula(linha).split(split);
				for (int i = 1; i < array.length; i++) {
					array[i] = array[i].trim();
				}
				arrayDados.add(array);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return arrayDados;
	}
	
	public static List<String[]> processarXLS(InputStream arquivo, Integer linhaInicio)
			throws Exception {
		List<String[]> arrayDados;
		WorkbookSettings se = new WorkbookSettings();
		se.setEncoding("Cp1252");
		Workbook workbook = Workbook.getWorkbook(arquivo, se);
		int colunas = 0;
		int linhas = 0;
		try {
			Sheet sheet = workbook.getSheet(0);
			colunas = sheet.getColumns();
			linhas = sheet.getRows();
			String[] array;
			arrayDados = new ArrayList<String[]>();
			for (int j = linhaInicio; j < linhas; j++) {
				array = new String[colunas];
				for (int i = 0; i < colunas; i++) {
					array[i] = Util
							.substituirCaracteresEspeciais(
									sheet.getCell(i, j).getContents()).trim()
							.toUpperCase();
				}
				arrayDados.add(array);
			}
			return arrayDados;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Linhas: " + linhas + ", Colunas: " + colunas
					+ " - " + e.getMessage());
		} finally {
			workbook.close();
		}
	}
}
