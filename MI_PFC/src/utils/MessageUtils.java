package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import libreria.MyConstants;
import libreria.ParametrosActualizarElemento;
import libreria.ParametrosEliminarElemento;
import libreria.ParametrosGuardarElemento;
import view.acierto.AciertoActualizar;
import view.acierto.AciertoAlta;
import view.error.ErrorActualizar;
import view.error.ErrorGenerico;
import view.modificar.ModificarInterface;

public class MessageUtils {
	/*
	 * Llama a la una venta que nos imprime un mensaje diciendonos si se elimino 
	 * o no el elemento
	 * @param parametros, contiene un flag que indica si el elemento fue eliminado con exito o no. 	 
	 * */	
	public static void mensajeEliminarSeleccionFila(ParametrosEliminarElemento parametros){
		
		String mensaje = null;
		ParametrosEliminarElemento parametrosEliminarElemento = parametros;
		if (parametrosEliminarElemento.getEliminadoElemento() ==1){		
			mensaje =  MyConstants.StringConstant.MENSAJE_ACIERTO_FILAS.value();
			//AciertoBaja aciertoBaja = new AciertoBaja(mensaje);			
		}else{
			mensaje =  MyConstants.StringConstant.MENSAJE_BAJA_ERROR_FILAS.value();
			String msg = mensaje+"\n\nError\n"+parametros.getMensaje();
			ErrorGenerico errorGenerico = new ErrorGenerico(MyConstants.StringConstant.BAJA.value(), msg);
			
		}
	}

	/*
	 * Llama a la una venta que nos imprime un mensaje diciendonos si se inserto
	 * o no el elemento
	 * @param parametros, contiene un flag que indica si el elemento fue eliminado con exito o no. 	 
	 * */	
	public static void mensajeInsertarElemento(ParametrosGuardarElemento parametros, String elemento ){		
		if (parametros.getInsertadoElemento() ==1){					
			AciertoAlta aciertoAlta = new AciertoAlta(elemento);		
		}else{			
			String accion = "no se insertó correctamente.";
			String msg = elemento+" "+accion+"\n\nError\n"+parametros.getMensaje();
			ErrorGenerico errorGenerico = new ErrorGenerico(MyConstants.StringConstant.ALTA.value(), msg);
			
		}
	}
	
	/*
	 * Llama a la una venta que nos imprime un mensaje diciendonos si se actualizó
	 * o no el elemento
	 * @param parametros, contiene un flag que indica si el elemento fue actualizado con exito o no. 	 
	 * */
	/*
	public static void mensajeActualizarElemento(ParametrosActualizarElemento parametros, String elemento){		
		if (parametros.getActualizadoElemento() ==1){					
			//AciertoActualizar aciertoActualizar = new AciertoActualizar(elemento);	
		}else{			
			String accion = "no se actualizó correctamente.";
			String msg = elemento+" "+accion+"\n\nError\n"+parametros.getMensaje();
			ErrorGenerico errorGenerico = new ErrorGenerico(MyConstants.StringConstant.VER_MODIFICAR.value(), msg);
		}
	}*/

	public static void mensajeActualizarElemento(ParametrosActualizarElemento parametros, String elemento, ModificarInterface ventana ){		
		if (parametros.getActualizadoElemento() ==1){					
			AciertoActualizar aciertoActualizar = new AciertoActualizar(elemento, ventana);	
		}else{			
			String accion = "no se actualizó correctamente.";
			String msg = elemento+" "+accion+"\n\nError\n"+parametros.getMensaje();
			ErrorGenerico errorGenerico = new ErrorGenerico(MyConstants.StringConstant.VER_MODIFICAR.value(), msg);
		}
	}
	public static String copyStringErrorMsg(String mensaje){		
		String copiaMensaje=null;
		int mensajeLenght = mensaje.length();
		int maxLenghtError = MyConstants.IntConstant.MAX_LENGHT_TEXT_ERROR_MSG.value();
		if (mensajeLenght < maxLenghtError){
			copiaMensaje = mensaje;
		}else{
			copiaMensaje =mensaje.substring(0,maxLenghtError)+"...";	
		}
		

		return copiaMensaje;
	}
	
	public static boolean mensajeSeleccionElementos(List <Integer> lista){
		boolean containsElements= false;
		Iterator it = lista.iterator();
		String mensaje = "Los siguientes elementos estan siendo utilizados : \n";
		if (lista.size() > 0){			
			while(it.hasNext()){
				int id = (Integer) it.next();
				mensaje = mensaje+"id = "+id+"\n";
			}
			ErrorGenerico errorGenerico = new ErrorGenerico(MyConstants.StringConstant.BAJA.value(), mensaje);
			containsElements = true;
		}
		return containsElements;
	}
}
