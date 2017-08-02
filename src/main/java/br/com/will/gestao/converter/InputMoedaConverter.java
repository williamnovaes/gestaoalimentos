package br.com.will.gestao.converter;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.will.gestao.util.SistemaConstantes;

@FacesConverter(value = "br.com.will.gestao.InputMoedaConverter", 
				forClass = BigDecimal.class)
public class InputMoedaConverter implements Converter {

	/**
	 * Sera settado no bean
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null) {
			value = value.replace("R", "");
			value = value.replace("$", "");
			value = value.replace(".", "");
			value = value.replace(",", ".");
			
			//Caso o número contenha casas decimais
			if (value.contains(".")) {
				//Verifica se está considerando apenas 2 casas decimais, do contrário ajusta
				if (value.indexOf(".") != (value.length() - SistemaConstantes.TRES)) {
					value = value.replace(".", "");
					value = value.substring(SistemaConstantes.ZERO, 
											value.length() - SistemaConstantes.DOIS)
							+ "."
							+ value.substring(value.length() - SistemaConstantes.DOIS);
				}
			}
		}
		return value;
	}

	/**
	 * Sera exibido na interface
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (value != null && !value.toString().trim().isEmpty()) {
			
			String retorno = value.toString();

			try {
		         NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));    
		         nf.setMaximumFractionDigits(2);    
		         nf.setMinimumFractionDigits(2);  
		         retorno = nf.format(new BigDecimal(value.toString()));
			} catch (Exception e) {
				retorno = (String) value;
			}

	        return retorno;    

		} else {
			return (String) value;
		}
	}
}
