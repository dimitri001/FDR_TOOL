package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MessageUtils;

import libreria.ContenedorConexion;
import libreria.MyConstants;
import libreria.ParametrosEliminarElemento;


public class BorrarElemento implements Serializable {

	private Connection miConexion;
	private ContenedorConexion miContenedorConexion;	
	private PropertyChangeSupport propertySupport;
		
	private String eliminarFabricante = "delete  from fabricante where idFabricante = ? ; " ;
	private String eliminarModelo = "delete from modelo where idModelo = ? ; " ;
	private String eliminarSerie = "delete from serie where idSerie = ? ; ";
	private String eliminarManual = "delete from manual where idManual = ? ;" ;
	private String eliminarEstructuraFrame = "delete from estructura_frame where idEstructura_Frame = ? ; ";
	private String eliminarSistemaEspecifico = "delete from sistema_especifico where idSistema_especifico = ? ; ";
	private String eliminarElementoEspecifico = "delete from elementos_sistema_especifico where idElementos_sistema_especifico = ?; ";
	//se borran todos los atributos al borrar el elemento
	private String eliminarTodaEstructuraAtributo = "delete from estructura_atributos_de_elemento where idElementos_sistema_especifico = ?; " ;
	//se borranlos atributos uno a uno
	private String eliminarSeleccionEstructuraAtributo = "delete from estructura_atributos_de_elemento where idEstructura_atributos_de_elemento = ? ; ";
	//se actualiza n_atributos de ElementoEspecifico
	private String actualizarElementoEspecificoNAtributos = "update elementos_sistema_especifico set n_atributos = n_atributos - ?  where idElementos_Sistema_Especifico = ?;";
	//se busca el idElementos_Sistema_Especifico de estructura_atributos_de_elemento
	private String idElementoEspecificoDeEstructuraAtributos = "select idElementos_Sistema_Especifico from estructura_atributos_de_elemento where idEstructura_Atributos_de_Elemento = ?;";	
	//selecciona campo n_atributos de idElementos_Sistema_Especifico
	private String selectNAtributos = "select n_atributos from elementos_sistema_especifico where idElementos_Sistema_Especifico = ?;";
	//actualizar campo interesting de idElementos_Sistema_Especifico
	private String actualizarInteresting = "update elementos_sistema_especifico set interesting = 0 where idElementos_Sistema_Especifico= ?;";

	public static final String PROP_ELIMINAR_FABRICANTE = "eliminarFabricante";
	public static final String PROP_ELIMINAR_MODELO = "eliminarModelo";
	public static final String PROP_ELIMINAR_SERIE = "eliminarSerie";
	public static final String PROP_ELIMINAR_MANUAL = "eliminarManual";
	public static final String PROP_ELIMINAR_ESTRUCTURA_FRAME = "eliminarEstructuraFrame";
	public static final String PROP_ELIMINAR_SISTEMA_ESPECIFICO = "eliminarSistemaEspecifico";
	public static final String PROP_ELIMINAR_ELEMENTO_ESPECIFICO = "eliminarElementoEspecifico";
	public static final String PROP_ELIMINAR_SELECCION_ESTRUCTURA_ATRIBUTO = "eliminarEstructuraAtributo";
	Logger LOG = LoggerFactory.getLogger(BorrarElemento.class);	
	public BorrarElemento (ContenedorConexion contenedorConexion){
		
		miContenedorConexion = contenedorConexion;		
		miConexion = miContenedorConexion.getConexion();// OJO se podria borrar		
		// Se crea el objeto del PropertyChangeSupport, para hacer esto necesitamos del bean (BorrarElemento), representado por this
		propertySupport = new PropertyChangeSupport(this);
	}	
	
	public void eliminarSeleccionFabricante(List <Object> listaIdFabricante, UUID idObjetoFabricante){
		ParametrosEliminarElemento parametrosEliminarElemento = new ParametrosEliminarElemento();
		ParametrosEliminarElemento oldParametrosEliminarElemento = new ParametrosEliminarElemento();
		parametrosEliminarElemento.setIdElemento(idObjetoFabricante);//No es necesario introducir el idModelo en oldParametrosEliminarElemento, porque solo se usa parametrosEliminarElemento
		miConexion = miContenedorConexion.getConexion();
		Iterator it = listaIdFabricante.iterator();	
		int id =0;
		try 
		{//Se puede eliminar el fabricante			
         	 
			while (it.hasNext()){
				PreparedStatement ps =  miConexion.prepareStatement(eliminarFabricante);
				id = (Integer) it.next();
				ps.setInt(1,id);//Inserta la var idEstructuraAtributo en la ps
				ps.executeUpdate();
			}	
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);
			
		}catch (Exception e) {
			// No se puede eliminar el fabricante
			e.printStackTrace();
			String mensaje =MyConstants.StringConstant.FABRICANTE_ID.value()+ String.valueOf(id) +". "; 
			parametrosEliminarElemento.setEliminadoElemento(0);
			oldParametrosEliminarElemento.setEliminadoElemento(1);	
			parametrosEliminarElemento.setMensaje(mensaje + MessageUtils.copyStringErrorMsg(e.getMessage()));
			LOG.error("Error eliminar " + mensaje, e);
		}finally{			
			propertySupport.firePropertyChange(PROP_ELIMINAR_FABRICANTE, oldParametrosEliminarElemento, parametrosEliminarElemento);
		}		
	}
	
	public void eliminarSeleccionModelo(List <Object> listaIdModelo, UUID idObjetoModelo){
		ParametrosEliminarElemento parametrosEliminarElemento = new ParametrosEliminarElemento();
		ParametrosEliminarElemento oldParametrosEliminarElemento = new ParametrosEliminarElemento();
		parametrosEliminarElemento.setIdElemento(idObjetoModelo);//No es necesario introducir el idModelo en oldParametrosEliminarElemento, porque solo se usa parametrosEliminarElemento
		miConexion = miContenedorConexion.getConexion();
		Iterator it = listaIdModelo.iterator();
		int id =0;
		try 
		{//Se puede eliminar el modelo         	
			while (it.hasNext()){
				PreparedStatement ps =  miConexion.prepareStatement(eliminarModelo);
				id = (Integer) it.next();
				ps.setInt(1,id);//Inserta la var idEstructuraAtributo en la ps
				ps.executeUpdate();
			}			
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);			
		}catch (Exception e) {
			// No se puede eliminar el modelo
			e.printStackTrace();
			String mensaje = MyConstants.StringConstant.MODELO_ID.value()+String.valueOf(id)+". "; 
			parametrosEliminarElemento.setEliminadoElemento(0);
			oldParametrosEliminarElemento.setEliminadoElemento(1);
			parametrosEliminarElemento.setMensaje(mensaje+e.getMessage().substring(0, MyConstants.IntConstant.MAX_LENGHT_TEXT_ERROR_MSG.value()));
			LOG.error("Error eliminar "+mensaje, e);
		}finally{			
			propertySupport.firePropertyChange(PROP_ELIMINAR_MODELO, oldParametrosEliminarElemento, parametrosEliminarElemento);
		}					
	}

	

	public void eliminarSeleccionSerie(List <Object> listaIdSerie, UUID idObjetoSerie){
		
		ParametrosEliminarElemento parametrosEliminarElemento = new ParametrosEliminarElemento();
		ParametrosEliminarElemento oldParametrosEliminarElemento = new ParametrosEliminarElemento();
		parametrosEliminarElemento.setIdElemento(idObjetoSerie);//No es necesario introducir el idModelo en oldParametrosEliminarElemento, porque solo se usa parametrosEliminarElemento
		miConexion = miContenedorConexion.getConexion();
		Iterator it = listaIdSerie.iterator();
		int id = 0;
		try 
		{//Se puede eliminar la serie	
			while (it.hasNext()){
				PreparedStatement ps =  miConexion.prepareStatement(eliminarSerie);
				id = (Integer) it.next();
				ps.setInt(1,id);//Inserta la var idEstructuraAtributo en la ps
				ps.executeUpdate();
			}			
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);			
		}catch (Exception e) {
			// No se puede eliminar el modelo
			e.printStackTrace();
			String mensaje = MyConstants.StringConstant.SERIE_ID.value()+String.valueOf(id)+". "; 
			parametrosEliminarElemento.setEliminadoElemento(0);
			oldParametrosEliminarElemento.setEliminadoElemento(1);
			parametrosEliminarElemento.setMensaje(mensaje+MessageUtils.copyStringErrorMsg(e.getMessage()));
			LOG.error("Error eliminar "+mensaje, e);
		}finally{			
			propertySupport.firePropertyChange(PROP_ELIMINAR_SERIE, oldParametrosEliminarElemento, parametrosEliminarElemento);
		}			
			
	}
	
	public void eliminarSeleccionManual(List <Object> listaIdManual, UUID idObjetoManual){		
		ParametrosEliminarElemento parametrosEliminarElemento = new ParametrosEliminarElemento();
		ParametrosEliminarElemento oldParametrosEliminarElemento = new ParametrosEliminarElemento();
		parametrosEliminarElemento.setIdElemento(idObjetoManual);//No es necesario introducir el idModelo en oldParametrosEliminarElemento, porque solo se usa parametrosEliminarElemento
		miConexion = miContenedorConexion.getConexion();
		Iterator it = listaIdManual.iterator();
		int id = 0;
		try 
		{//Se puede eliminar la serie
			while (it.hasNext()){
				PreparedStatement ps =  miConexion.prepareStatement(eliminarManual);
				id = (Integer) it.next();
				ps.setInt(1,id);//Inserta la var id en la ps
				ps.executeUpdate();
			}			
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);			
		}catch (Exception e) {
			// No se puede eliminar el modelo
			e.printStackTrace();
			String mensaje = MyConstants.StringConstant.MANUAL_ID.value()+String.valueOf(id)+". "; 
			parametrosEliminarElemento.setEliminadoElemento(0);
			oldParametrosEliminarElemento.setEliminadoElemento(1);
			parametrosEliminarElemento.setMensaje(mensaje+ MessageUtils.copyStringErrorMsg(e.getMessage()));
			LOG.error("Error eliminar " + mensaje, e);
		}finally{			
			propertySupport.firePropertyChange(PROP_ELIMINAR_MANUAL, oldParametrosEliminarElemento, parametrosEliminarElemento);
		}			
			
	}


	public void eliminarSeleccionEstructuraFrame(List <Object> listaIdEstructuraFrame, UUID idObjetoEstructuraFrame){
		
		ParametrosEliminarElemento parametrosEliminarElemento = new ParametrosEliminarElemento();
		ParametrosEliminarElemento oldParametrosEliminarElemento = new ParametrosEliminarElemento();
		parametrosEliminarElemento.setIdElemento(idObjetoEstructuraFrame);//No es necesario introducir el idModelo en oldParametrosEliminarElemento, porque solo se usa parametrosEliminarElemento
		miConexion = miContenedorConexion.getConexion();
		Iterator it = listaIdEstructuraFrame.iterator();
		int id = 0;
		try 
		{//Se puede eliminar la estructura
			while (it.hasNext()){
				PreparedStatement ps =  miConexion.prepareStatement(eliminarEstructuraFrame);
				id = (Integer) it.next();
				ps.setInt(1,id);//Inserta la var idEstructuraAtributo en la ps
				ps.executeUpdate();
			}			
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);			
		}catch (Exception e) {
			// No se puede eliminar el modelo
			e.printStackTrace();
			String mensaje = MyConstants.StringConstant.ESTRUCTURA_FRAME_ID.value()+String.valueOf(id)+". "; 
			parametrosEliminarElemento.setEliminadoElemento(0);
			oldParametrosEliminarElemento.setEliminadoElemento(1);
			parametrosEliminarElemento.setMensaje(mensaje+MessageUtils.copyStringErrorMsg(e.getMessage()));
			LOG.error("Error eliminar " + mensaje, e);
		}finally{			
			propertySupport.firePropertyChange(PROP_ELIMINAR_ESTRUCTURA_FRAME, oldParametrosEliminarElemento, parametrosEliminarElemento);
		}			
			
	}
	

	public void eliminarSeleccionSistemaEspecifico(List <Object> listaIdSistemaEspecifico, UUID idObjetoSistemaEspecifico){
		
		ParametrosEliminarElemento parametrosEliminarElemento = new ParametrosEliminarElemento();
		ParametrosEliminarElemento oldParametrosEliminarElemento = new ParametrosEliminarElemento();
		parametrosEliminarElemento.setIdElemento(idObjetoSistemaEspecifico);//No es necesario introducir el idModelo en oldParametrosEliminarElemento, porque solo se usa parametrosEliminarElemento
		miConexion = miContenedorConexion.getConexion();
		Iterator it = listaIdSistemaEspecifico.iterator();
		int id = 0;
		try 
		{//Se puede eliminar el sistema
			while (it.hasNext()){
				PreparedStatement ps =  miConexion.prepareStatement(eliminarSistemaEspecifico);
				id = (Integer) it.next();
				ps.setInt(1,id);//Inserta la var idEstructuraAtributo en la ps
				ps.executeUpdate();
			}			
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);			
		}catch (Exception e) {
			// No se puede eliminar el modelo
			e.printStackTrace();
			String mensaje = MyConstants.StringConstant.SISTEMA_ID.value()+String.valueOf(id)+". "; 
			parametrosEliminarElemento.setEliminadoElemento(0);
			oldParametrosEliminarElemento.setEliminadoElemento(1);
			parametrosEliminarElemento.setMensaje(mensaje+MessageUtils.copyStringErrorMsg(e.getMessage()));	
			LOG.error("Error eliminar " + mensaje, e);
		}finally{			
			propertySupport.firePropertyChange(PROP_ELIMINAR_SISTEMA_ESPECIFICO, oldParametrosEliminarElemento, parametrosEliminarElemento);
		}			
			
	}
	
	
	public void eliminarSeleccionElementoEspecifico(List <Object> listaIdElementoEspecifico, UUID idObjetoElementoEspecifico){
		
		ParametrosEliminarElemento parametrosEliminarElemento = new ParametrosEliminarElemento();
		ParametrosEliminarElemento oldParametrosEliminarElemento = new ParametrosEliminarElemento();
		parametrosEliminarElemento.setIdElemento(idObjetoElementoEspecifico);//No es necesario introducir el idModelo en oldParametrosEliminarElemento, porque solo se usa parametrosEliminarElemento
		miConexion = miContenedorConexion.getConexion();
		Iterator it = listaIdElementoEspecifico.iterator();
		int id = 0;	
		int flag1=0;
		int flag2=0;
		try 
		{//Se eliminan los atributos y despues el elemento
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);
			while (it.hasNext()){
				id = (Integer) it.next();
				PreparedStatement ps = miConexion.prepareStatement(eliminarTodaEstructuraAtributo);
				ps.setInt(1, id);
				ps.executeUpdate();
				flag1=1;
				PreparedStatement ps1 =  miConexion.prepareStatement(eliminarElementoEspecifico);
				ps1.setInt(1,id);//Inserta la var idElementoEspecifico en la ps
				ps1.executeUpdate();
				flag2=1;
			}			
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);			
		}catch (Exception e) {
			// No se puede eliminar el modelo
			e.printStackTrace();
			String mensaje = null;
			if (flag1==0){
				mensaje="Error borrando Estructura de Atributos al borrar el ";
			}else if(flag2==0){
				mensaje="Error borrando Elemento Sistema Especifico";				
			}
			mensaje= mensaje + MyConstants.StringConstant.ELEMENTOS_ID.value()+String.valueOf(id)+". "; 
			parametrosEliminarElemento.setEliminadoElemento(0);
			oldParametrosEliminarElemento.setEliminadoElemento(1);
			parametrosEliminarElemento.setMensaje(mensaje+MessageUtils.copyStringErrorMsg(e.getMessage()));		
			LOG.error(mensaje, e);
		}finally{			
			propertySupport.firePropertyChange(PROP_ELIMINAR_ELEMENTO_ESPECIFICO, oldParametrosEliminarElemento, parametrosEliminarElemento);
		}			
			
	}
	
	public void eliminarSeleccionEstructuraAtributo(List <Object> listaIdEstructuraAtributos, UUID idObjetoEstructura, int idElementoEspecificoSeleccionado, int counterVisibleRowTrue){
		
		ParametrosEliminarElemento parametrosEliminarElemento = new ParametrosEliminarElemento();
		ParametrosEliminarElemento oldParametrosEliminarElemento = new ParametrosEliminarElemento();
		parametrosEliminarElemento.setIdElemento(idObjetoEstructura);//No es necesario introducir el idModelo en oldParametrosEliminarElemento, porque solo se usa parametrosEliminarElemento
		miConexion = miContenedorConexion.getConexion();
		Iterator it = listaIdEstructuraAtributos.iterator();
		int id =0;
		try 
		{//Se puede eliminar la seleccion de estructura de atributos			
         	int idElementoSistema = idElementoEspecificoSeleccionado;
         	
			while (it.hasNext()){
				id = (Integer) it.next();				
				PreparedStatement ps =  miConexion.prepareStatement(eliminarSeleccionEstructuraAtributo);
				ps.setInt(1,id);//Inserta la var idEstructuraAtributo en la ps
				ps.executeUpdate();				
			}
			//Modifica los campos n_atributo e interesting de la tabla elementos_sistema_especifico
			PreparedStatement ps2 =  miConexion.prepareStatement(actualizarElementoEspecificoNAtributos);
			//ps2.setInt(1, listaIdEstructuraAtributos.size());
			ps2.setInt(1, counterVisibleRowTrue);
			ps2.setInt(2,idElementoSistema);
			ps2.executeUpdate();			
			PreparedStatement ps3 =  miConexion.prepareStatement(selectNAtributos);
			ps3.setInt(1,idElementoSistema);
			ResultSet rs1 = ps3.executeQuery (); 	         	
         	rs1.next();
         	int nAtributos = rs1.getInt(1);         	
         	if (nAtributos==0){
         		PreparedStatement ps4 =  miConexion.prepareStatement(actualizarInteresting);
    			ps4.setInt(1,idElementoSistema);
    			ps4.executeUpdate();
         	}
			parametrosEliminarElemento.setEliminadoElemento(1);
			oldParametrosEliminarElemento.setEliminadoElemento(0);
		}catch (Exception e) {
			// No se puede eliminar la serie
			e.printStackTrace();
			String mensaje = MyConstants.StringConstant.ESTRUCTURA_ATRIBUTOS_ID.value()+String.valueOf(id)+". "; 
			parametrosEliminarElemento.setEliminadoElemento(0);
			oldParametrosEliminarElemento.setEliminadoElemento(1);	
			parametrosEliminarElemento.setMensaje(mensaje+MessageUtils.copyStringErrorMsg(e.getMessage()));	
			LOG.error("Error eliminar " + mensaje, e);				
		}finally{			
			propertySupport.firePropertyChange(PROP_ELIMINAR_SELECCION_ESTRUCTURA_ATRIBUTO, oldParametrosEliminarElemento, parametrosEliminarElemento);
		}			
			
	}

    /*Añade un Listener al elemento atado de esta clase*/
	public void addPropertyChangeListener(String prop, PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(prop,listener);
	}
	
	
	/*Remueve un Listener al elemento atado de esta clase*/
	public void removePropertyChangeListener(String prop, PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(prop, listener);
    }
}
