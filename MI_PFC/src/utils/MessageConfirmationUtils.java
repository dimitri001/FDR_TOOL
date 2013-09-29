package utils;

import javax.swing.JOptionPane;

import libreria.MyConstants;

public class MessageConfirmationUtils {
	
	public static int deleteRowsMessageConfirmation(){		
		return JOptionPane.showConfirmDialog(null, MyConstants.StringConstant.MENSAJE_BAJA.value(), MyConstants.StringConstant.TITULO_VENTANA_CONFIRMACION.value(),
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	public static int closeAplicationMessageConfirmation(){		
		return JOptionPane.showConfirmDialog(null, MyConstants.StringConstant.MENSAJE_CIERRE_APLICACION.value(), MyConstants.StringConstant.TITULO_VENTANA_CONFIRMACION.value(),
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	public static int deleteBbddMessageConfirmation(){		
		return JOptionPane.showConfirmDialog(null, MyConstants.StringConstant.MENSAJE_ELIMINAR_BBDD.value(), MyConstants.StringConstant.TITULO_VENTANA_CONFIRMACION.value(),
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
}
