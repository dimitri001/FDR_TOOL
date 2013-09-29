package utils;

import java.util.ArrayList;
import java.util.Iterator;

import libreria.MyConstants;

import view.acierto.AciertoBaja;
import view.error.ErrorBaja;
import view.error.ErrorGenerico;
import view.error.ErrorGenericoCampo;

public class ValidationUtils {

	@SuppressWarnings("unused")
	public static boolean validationInterestingNAtributos(boolean interesting, int intNAtributos, String modulo){		
		String mensaje= null;
		if ((interesting == false)&&(intNAtributos==0)){
			return true;							
		}else if ((interesting == true)&&(intNAtributos>0)){
				return true;		
		}else if ((interesting == false)&&(intNAtributos>0)){ 
			mensaje = "Si Interesting no es escogido, el Número de Atributos debe ser igual a 0, esta entrada";						
		}else if ((interesting == true)&&(intNAtributos == 0)){
			mensaje = "Si Interesting es escogido, el Número de Atributos se debe añadir y debe ser mayor que 0 y menor que 31, esta entrada";				
		}	
		ErrorGenericoCampo errorGenericoCampo = new ErrorGenericoCampo(modulo, mensaje);
		return false;
	}	
	
}
