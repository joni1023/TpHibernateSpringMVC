package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContoladorTp {
	
	@RequestMapping("/realizar/{operacion}/{cadena}")
	
	public ModelAndView realizarOperaciones (@PathVariable String operacion,
			@PathVariable String cadena) {
		ModelMap modelo=new ModelMap();
		modelo.put("operacion", operacion);
		modelo.put("cadena", cadena);
		String resultado = "";
		switch(operacion) {
		case "pasarAMayuscula":
			resultado=cadena.toUpperCase();
			modelo.put("resultado", resultado);
		break;
		case "pasarAMinuscula":
			resultado=cadena.toLowerCase();
			modelo.put("resultado", resultado);
			break;
		case "invertirOrden":
			for(int i=cadena.length()-1;i>=0;i--){
				resultado+=cadena.charAt(i);
				}
			modelo.put("resultado", resultado);
			break;
		case "cantidadDeCaracteres":
			Integer cantidad=cadena.length();
			modelo.put("resultado",cantidad);
			break;
		default:
			resultado="La operacion es incorrecta!!! pasarAMayuscula,pasarAMinuscula,invertirOrden,cantidadDeCaracteres.";
			modelo.put("resultado", resultado);
		
		}
		return new ModelAndView ("realizar",modelo);
	}
	

}
