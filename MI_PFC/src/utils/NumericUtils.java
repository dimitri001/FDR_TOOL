package utils;

import libreria.MutableBoolean;
import view.error.ErrorGenericoCampo;

public class NumericUtils {

	@SuppressWarnings({ "finally", "unused" })
	public static int parseInt(String strNumber,String modulo, String mensaje, MutableBoolean flag){
		int intNumber = -1;
		try {			
			intNumber = Integer.parseInt(strNumber);			
			flag.valor = true;			
		}catch (NumberFormatException e) {
			flag.valor = false;
			e.printStackTrace();
			ErrorGenericoCampo errorGenericoCampo= new ErrorGenericoCampo(modulo, mensaje);
		}finally{			
			return intNumber;
		}		
	}
	
	
	@SuppressWarnings({ "finally", "unused" })
	public static double parseDouble(String strNumber,String modulo, String mensaje, MutableBoolean flag){
		double dblNumber = -1;
		try {			
			dblNumber = Double.parseDouble(strNumber);			
			flag.valor = true;			
		}catch (NumberFormatException e) {
			flag.valor = false;
			e.printStackTrace();
			ErrorGenericoCampo errorGenericoCampo= new ErrorGenericoCampo(modulo, mensaje);
		}finally{			
			return dblNumber;
		}		
	}


	@SuppressWarnings({ "finally", "unused" })
	public static int parseIntPositive(String strNumber,String modulo, String mensaje, MutableBoolean flag){
		int intNumber = -1;
		try {			
			intNumber = Integer.parseInt(strNumber);
			if (intNumber >0){
				flag.valor = true;				
			}else{
				flag.valor = false;
				ErrorGenericoCampo errorGenericoCampo= new ErrorGenericoCampo(modulo, mensaje);
			}			
		}catch (NumberFormatException e) {
			flag.valor = false;
			e.printStackTrace();
			ErrorGenericoCampo errorGenericoCampo= new ErrorGenericoCampo(modulo, mensaje);
		}finally{			
			return intNumber;
		}		
	}
	
	
	@SuppressWarnings({ "finally", "unused" })
	public static double parseDoublePositive(String strNumber,String modulo, String mensaje, MutableBoolean flag){
		double dblNumber = -1;
		try {			
			dblNumber = Double.parseDouble(strNumber);			
			if (dblNumber >0){
				flag.valor = true;				
			}else{
				flag.valor = false;
				ErrorGenericoCampo errorGenericoCampo= new ErrorGenericoCampo(modulo, mensaje);
			}
		}catch (NumberFormatException e) {
			flag.valor = false;
			e.printStackTrace();
			ErrorGenericoCampo errorGenericoCampo= new ErrorGenericoCampo(modulo, mensaje);
		}finally{			
			return dblNumber;
		}		
	}

}
