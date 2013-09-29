package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MessageUtils;

import libreria.AtributoElemento;
import libreria.ContenedorConexion;
import libreria.ContenedorVisualAtributoElemento;
import libreria.ElementosSistemaEspecifico;
import libreria.EstructuraFrame;
import libreria.Fabricante;
import libreria.Manual;
import libreria.Modelo;
import libreria.MyConstants;
import libreria.ParametrosActualizarElemento;
import libreria.ParametrosEliminarElemento;
import libreria.Serie;
import libreria.SistemaEspecifico;


public class ActualizarElemento implements Serializable {

	private Connection miConexion;
	private ContenedorConexion miContenedorConexion;	
	private PropertyChangeSupport propertySupport;
	
	private String actualizarFabricante = "update fabricante set nombre = ?, Web =  ? where idFabricante = ?;";
	private String actualizarModelo = "update modelo set nombre = ?, descripcion = ?, fecha_fabricacion = ?, anyo_lanzamiento = ?   where idFabricante = ? and idModelo = ?;";
	private String actualizarSerie = "update serie set numero_serie = ?, descripcion = ?, fecha_fabricacion = ?, anyo_lanzamiento = ? where idFabricante = ? and idModelo = ? and idSerie = ?;";		
	private String actualizarManual = "update manual set descripcion = ?, ruta_fichero_pdf = ? where idFabricante = ? and idModelo = ? and idSerie = ? and idManual = ?;";	
	private String actualizarEstructuraFrame = "update estructura_frame set N_subframes = ?, tiempo_subframe = ?, wps = ?, bits_pw = ?,  descripcion = ? where idFabricante = ? and idModelo = ? and idSerie = ? and idEstructura_Frame = ?;";
	private String actualizarSistemaEspecifico = "update sistema_especifico set nombre = ?,  descripcion = ? where idFabricante = ? and idModelo = ? and idSerie = ? and idSistema_Especifico = ?;";
	private String actualizarElementoEspecifico = "update elementos_sistema_especifico set nombre_elemento = ?,  interesting = ?, subframes = ?, palabras = ?, bits = ?, n_atributos = ?  where idFabricante = ? and idModelo = ? and idSerie = ? and idSistema_Especifico = ? and idElementos_Sistema_Especifico = ?;";
	private String actualizarEstructuraAtributosElemento = "update estructura_atributos_de_elemento set nombre_atributo = ?, tipo_atributo = ?, visible = ? where idFabricante = ? and idModelo = ? and idSerie = ? and idSistema_Especifico = ? and idElementos_Sistema_Especifico = ? and idEstructura_Atributos_de_Elemento = ?;";
	private String insertarEstructuraAtributosElemento = "insert into estructura_atributos_de_elemento (idElementos_Sistema_Especifico, idSistema_Especifico,idSerie, idModelo, idFabricante, nombre_atributo, tipo_atributo) "+ 														 
	 "values (?,?,?,?,?,?,?); ";

	//se borran todos los atributos al borrar el elemento
	private String eliminarTodaEstructuraAtributo = "delete from estructura_atributos_de_elemento where idElementos_sistema_especifico = ?; " ;
	
	public static final String PROP_ACTUALIZAR_FABRICANTE = "actualizarFabricante";
	public static final String PROP_ACTUALIZAR_MODELO = "actualizarModelo";
	public static final String PROP_ACTUALIZAR_SERIE = "actualizarSerie";
	public static final String PROP_ACTUALIZAR_MANUAL = "actualizarManual";
	public static final String PROP_ACTUALIZAR_ESTRUCTURA_FRAME = "actualizarEstructuraFrame";
	public static final String PROP_ACTUALIZAR_SISTEMA_ESPECIFICO = "actualizarSistemaEspecifico";
	public static final String PROP_ACTUALIZAR_ELEMENTO_ESPECIFICO = "actualizarElementoEspecifico";
	public static final String PROP_ACTUALIZAR_ESTRUCTURA_ATRIBUTO = "actualizarEstructuraAtributo";
	Logger LOG = LoggerFactory.getLogger(ActualizarElemento.class);	

	public ActualizarElemento (ContenedorConexion contenedorConexion){
		
		miContenedorConexion = contenedorConexion;		
		miConexion = miContenedorConexion.getConexion();// OJO se podria borrar		
		// Se crea el objeto del PropertyChangeSupport, para hacer esto necesitamos del bean (ActualizarElemento), representado por this
		propertySupport = new PropertyChangeSupport(this);
	}
	
	public void actualizarFabricante(Fabricante fabricante, UUID idObjetoFabricante){
	
		ParametrosActualizarElemento parametrosActualizarElemento = new ParametrosActualizarElemento();
		ParametrosActualizarElemento oldParametrosActualizarElemento = new ParametrosActualizarElemento();
		parametrosActualizarElemento.setIdElemento(idObjetoFabricante);//No es necesario introducir el idModelo en oldParametrosActualizarElemento, porque solo se usa parametrosActualizarElemento
		miConexion = miContenedorConexion.getConexion();		
		Fabricante objetoFabricante = fabricante;		
		try 
		{//Se puede actualizar el fabricante
			int rc =0;			
			PreparedStatement ps =  miConexion.prepareStatement(actualizarFabricante);
			ps.setString(1, objetoFabricante.getNombre());
			ps.setString(2, objetoFabricante.getWeb());
			ps.setInt(3, objetoFabricante.getIdFabricante());			
			ps.executeUpdate();			
			parametrosActualizarElemento.setActualizadoElemento(1);
			oldParametrosActualizarElemento.setActualizadoElemento(0);			
		}catch (Exception e) {
			// No se puede actualizar el elemento
			e.printStackTrace();
			parametrosActualizarElemento.setActualizadoElemento(0);
			oldParametrosActualizarElemento.setActualizadoElemento(1);	
			String mensaje =MyConstants.StringConstant.FABRICANTE_ID.value()+ String.valueOf(objetoFabricante.getIdFabricante()) +". "; 			
			LOG.error("Error actualizar "+mensaje, e);	
			parametrosActualizarElemento.setMensaje(mensaje+"\n"+MessageUtils.copyStringErrorMsg(e.getMessage()));			
		}finally{			
			propertySupport.firePropertyChange(PROP_ACTUALIZAR_FABRICANTE, oldParametrosActualizarElemento, parametrosActualizarElemento);
		}
		
	}
	
	
	
	
	public void actualizarModelo(Modelo modelo, UUID idObjetoModelo){
		
		ParametrosActualizarElemento parametrosActualizarElemento = new ParametrosActualizarElemento();
		ParametrosActualizarElemento oldParametrosActualizarElemento = new ParametrosActualizarElemento();
		parametrosActualizarElemento.setIdElemento(idObjetoModelo);//No es necesario introducir el idModelo en oldParametrosActualizarElemento, porque solo se usa parametrosActualizarElemento
		miConexion = miContenedorConexion.getConexion();		
		Modelo objetoModelo = modelo;
		try 
		{//Se puede actualizar el fabricante
			int rc =0;
			PreparedStatement ps =  miConexion.prepareStatement(actualizarModelo);
			ps.setString(1, objetoModelo.getNombre());
			ps.setString(2, objetoModelo.getDescripcion());
			ps.setDate(3, objetoModelo.getFechaFabricacionDate()) ;
			ps.setString(4, objetoModelo.getAnyoLanzamiento());			
			ps.setInt(5, objetoModelo.getIdFabricante());
			ps.setInt(6, objetoModelo.getIdModelo());			
			ps.executeUpdate();			
			parametrosActualizarElemento.setActualizadoElemento(1);
			oldParametrosActualizarElemento.setActualizadoElemento(0);			
		}catch (Exception e) {
			// No se puede actualizar el elemento
			e.printStackTrace();
			parametrosActualizarElemento.setActualizadoElemento(0);
			oldParametrosActualizarElemento.setActualizadoElemento(1);
			String mensaje =MyConstants.StringConstant.MODELO_ID.value()+ String.valueOf(objetoModelo.getIdModelo()) +". "; 			
			LOG.error("Error actualizar "+mensaje, e);	
			parametrosActualizarElemento.setMensaje(mensaje+"\n"+MessageUtils.copyStringErrorMsg(e.getMessage()));								
		}finally{			
			propertySupport.firePropertyChange(PROP_ACTUALIZAR_MODELO, oldParametrosActualizarElemento, parametrosActualizarElemento);
		}
		
	}
	
	
	public void actualizarSerie(Serie serie, UUID idObjetoSerie){
		
		ParametrosActualizarElemento parametrosActualizarElemento = new ParametrosActualizarElemento();
		ParametrosActualizarElemento oldParametrosActualizarElemento = new ParametrosActualizarElemento();
		parametrosActualizarElemento.setIdElemento(idObjetoSerie);//No es necesario introducir el idModelo en oldParametrosActualizarElemento, porque solo se usa parametrosActualizarElemento
		miConexion = miContenedorConexion.getConexion();
		Serie objetoSerie = serie;
		try 
		{//Se puede actualizar el fabricante
			int rc =0;
			PreparedStatement ps =  miConexion.prepareStatement(actualizarSerie);
			ps.setString(1, objetoSerie.getNumeroSerie());
			ps.setString(2, objetoSerie.getDescripcion());
			ps.setDate(3, objetoSerie.getFechaFabricacionDate()) ;
			ps.setString(4, objetoSerie.getAnyoLanzamiento());
			ps.setInt(5, objetoSerie.getIdFabricante());
			ps.setInt(6, objetoSerie.getIdModelo());
			ps.setInt(7, objetoSerie.getIdSerie());
			ps.executeUpdate();
			parametrosActualizarElemento.setActualizadoElemento(1);
			oldParametrosActualizarElemento.setActualizadoElemento(0);
			
		}catch (Exception e) {
			// No se puede actualizar el elemento
			e.printStackTrace();
			parametrosActualizarElemento.setActualizadoElemento(0);
			oldParametrosActualizarElemento.setActualizadoElemento(1);
			String mensaje =MyConstants.StringConstant.SERIE_ID.value()+ String.valueOf(objetoSerie.getIdSerie()) +". "; 			
			LOG.error("Error actualizar "+mensaje, e);		
			parametrosActualizarElemento.setMensaje(mensaje+"\n"+MessageUtils.copyStringErrorMsg(e.getMessage()));
		}finally{			
			propertySupport.firePropertyChange(PROP_ACTUALIZAR_SERIE, oldParametrosActualizarElemento, parametrosActualizarElemento);
		}
		
	}
	
	
	public void actualizarManual(Manual manual, UUID idObjetoManual){
		
		ParametrosActualizarElemento parametrosActualizarElemento = new ParametrosActualizarElemento();
		ParametrosActualizarElemento oldParametrosActualizarElemento = new ParametrosActualizarElemento();
		parametrosActualizarElemento.setIdElemento(idObjetoManual);//No es necesario introducir el idModelo en oldParametrosActualizarElemento, porque solo se usa parametrosActualizarElemento
		miConexion = miContenedorConexion.getConexion();
		Manual objetoManual = manual;		
		try 
		{//Se puede actualizar el fabricante
			int rc =0;
			PreparedStatement ps =  miConexion.prepareStatement(actualizarManual);
			ps.setString(1, objetoManual.getDescripcion());
			ps.setString(2, objetoManual.getRutaFicheroPdf());
			ps.setInt(3, objetoManual.getIdFabricante());
			ps.setInt(4, objetoManual.getIdModelo());
			ps.setInt(5, objetoManual.getIdSerie());
			ps.setInt(6, objetoManual.getIdManual());
			ps.executeUpdate();
			parametrosActualizarElemento.setActualizadoElemento(1);
			oldParametrosActualizarElemento.setActualizadoElemento(0);
		}catch (Exception e) {
			// No se puede actualizar el elemento
			e.printStackTrace();
			parametrosActualizarElemento.setActualizadoElemento(0);
			oldParametrosActualizarElemento.setActualizadoElemento(1);
			String mensaje =MyConstants.StringConstant.MANUAL_ID.value()+ String.valueOf(objetoManual.getIdManual()) +". "; 			
			LOG.error("Error actualizar "+mensaje, e);	
			parametrosActualizarElemento.setMensaje(mensaje+"\n"+MessageUtils.copyStringErrorMsg(e.getMessage()));
		}finally{			
			propertySupport.firePropertyChange(PROP_ACTUALIZAR_MANUAL, oldParametrosActualizarElemento, parametrosActualizarElemento);
		}
		
	}
	
	
	public void actualizarEstructuraFrame(EstructuraFrame estructuraFrame, UUID idObjetoEstructuraFrame){
		
		ParametrosActualizarElemento parametrosActualizarElemento = new ParametrosActualizarElemento();
		ParametrosActualizarElemento oldParametrosActualizarElemento = new ParametrosActualizarElemento();
		parametrosActualizarElemento.setIdElemento(idObjetoEstructuraFrame);//No es necesario introducir el idModelo en oldParametrosActualizarElemento, porque solo se usa parametrosActualizarElemento
		miConexion = miContenedorConexion.getConexion();
		EstructuraFrame objetoEstructuraFrame = estructuraFrame;
		
		try 
		{//Se puede actualizar el fabricante
			int rc =0;			
			PreparedStatement ps =  miConexion.prepareStatement(actualizarEstructuraFrame);
			ps.setInt(1, objetoEstructuraFrame.getNSubframes());
			ps.setDouble(2, objetoEstructuraFrame.getTiempoSubframe());			
			ps.setInt(3, objetoEstructuraFrame.getWPS());
			ps.setInt(4, objetoEstructuraFrame.getBitsPW());
			ps.setString(5, objetoEstructuraFrame.getDescripcion());
			ps.setInt(6, objetoEstructuraFrame.getIdFabricante());	
			ps.setInt(7, objetoEstructuraFrame.getIdModelo());
			ps.setInt(8, objetoEstructuraFrame.getIdSerie());
			ps.setInt(9, objetoEstructuraFrame.getIdEstructuraFrame());			
			ps.executeUpdate();			
			parametrosActualizarElemento.setActualizadoElemento(1);
			oldParametrosActualizarElemento.setActualizadoElemento(0);
			
		}catch (Exception e) {
			// No se puede actualizar el elemento
			e.printStackTrace();
			parametrosActualizarElemento.setActualizadoElemento(0);
			oldParametrosActualizarElemento.setActualizadoElemento(1);
			String mensaje =MyConstants.StringConstant.ESTRUCTURA_FRAME_ID.value()+ String.valueOf(objetoEstructuraFrame.getIdEstructuraFrame()) +". "; 			
			LOG.error("Error actualizar "+mensaje, e);	
			parametrosActualizarElemento.setMensaje(mensaje+"\n"+MessageUtils.copyStringErrorMsg(e.getMessage()));		
		}finally{			
			propertySupport.firePropertyChange(PROP_ACTUALIZAR_ESTRUCTURA_FRAME, oldParametrosActualizarElemento, parametrosActualizarElemento);
		}		
	}


	public void actualizarSistemaEspecifico(SistemaEspecifico sistemaEspecifico, UUID idObjetoSistemaEspecifico){
	
		ParametrosActualizarElemento parametrosActualizarElemento = new ParametrosActualizarElemento();
		ParametrosActualizarElemento oldParametrosActualizarElemento = new ParametrosActualizarElemento();
		parametrosActualizarElemento.setIdElemento(idObjetoSistemaEspecifico);//No es necesario introducir el idModelo en oldParametrosActualizarElemento, porque solo se usa parametrosActualizarElemento
		miConexion = miContenedorConexion.getConexion();
		SistemaEspecifico objetoSistemaEspecifico = sistemaEspecifico;
		try 
		{//Se puede actualizar el fabricante
			int rc =0;
			PreparedStatement ps =  miConexion.prepareStatement(actualizarSistemaEspecifico);
			ps.setString(1, objetoSistemaEspecifico.getNombre());
			ps.setString(2, objetoSistemaEspecifico.getDescripcion());	
			ps.setInt(3, objetoSistemaEspecifico.getIdFabricante());	
			ps.setInt(4, objetoSistemaEspecifico.getIdModelo());
			ps.setInt(5, objetoSistemaEspecifico.getIdSerie());
			ps.setInt(6, objetoSistemaEspecifico.getIdSistemaEspecifico());
			ps.executeUpdate();
			parametrosActualizarElemento.setActualizadoElemento(1);
			oldParametrosActualizarElemento.setActualizadoElemento(0);
		}catch (Exception e) {
			// No se puede actualizar el elemento
			e.printStackTrace();
			parametrosActualizarElemento.setActualizadoElemento(0);
			oldParametrosActualizarElemento.setActualizadoElemento(1);
			String mensaje =MyConstants.StringConstant.SISTEMA_ID.value()+ String.valueOf(objetoSistemaEspecifico.getIdSistemaEspecifico()) +". "; 			
			LOG.error("Error actualizar "+mensaje, e);	
			parametrosActualizarElemento.setMensaje(mensaje+"\n"+MessageUtils.copyStringErrorMsg(e.getMessage()));	
		}finally{			
		propertySupport.firePropertyChange(PROP_ACTUALIZAR_SISTEMA_ESPECIFICO, oldParametrosActualizarElemento, parametrosActualizarElemento);
		}	
	}

	

	public void actualizarElementosSistema(ElementosSistemaEspecifico elementoEspecifico, UUID idObjetoElementoEspecifico
			,List <Object> listaAtributoElementoActualizar, List <Object> listaAtributoElementoInsertar){
	
		ParametrosActualizarElemento parametrosActualizarElemento = new ParametrosActualizarElemento();
		ParametrosActualizarElemento oldParametrosActualizarElemento = new ParametrosActualizarElemento();
		parametrosActualizarElemento.setIdElemento(idObjetoElementoEspecifico);//No es necesario introducir el idModelo en oldParametrosActualizarElemento, porque solo se usa parametrosActualizarElemento
		miConexion = miContenedorConexion.getConexion();
		ElementosSistemaEspecifico objetoElementoEspecifico = elementoEspecifico;
		int intNAtributos = objetoElementoEspecifico.getNAtributos();
		
		try 
		{//Se puede actualizar el elemento Específico
			PreparedStatement ps =  miConexion.prepareStatement(actualizarElementoEspecifico);
			ps.setString(1, objetoElementoEspecifico.getNombreElemento());
			ps.setBoolean(2, objetoElementoEspecifico.getInteresting());			
			ps.setString(3, objetoElementoEspecifico.getSubFrames());
			ps.setString(4, objetoElementoEspecifico.getPalabras());
			ps.setString(5, objetoElementoEspecifico.getBits());
			ps.setInt(6, intNAtributos);			
			ps.setInt(7, objetoElementoEspecifico.getIdFabricante());	
			ps.setInt(8, objetoElementoEspecifico.getIdModelo());
			ps.setInt(9, objetoElementoEspecifico.getIdSerie());
			ps.setInt(10, objetoElementoEspecifico.getIdSistemaEspecifico());
			ps.setInt(11, objetoElementoEspecifico.getIdElementosSistemaEspecifico());
			ps.executeUpdate();
			
			//Modificacion de Atributos
			//TODO modificar, siempre se borraran los atributos de este elemento y
			//siempre se insertara el contenido de listaAtributoElemento
			/*Iterator it = listaAtributoElementoActualizar.iterator();			
			//Se puede eliminar la seleccion de estructura de atributos
			PreparedStatement ps2 = miConexion.prepareStatement(eliminarTodaEstructuraAtributo);
			ps2.setInt(1, objetoElementoEspecifico.getIdElementosSistemaEspecifico());
			ps2.executeUpdate();
			//Se introducen los atributos modificados si los hubiere
			while (it.hasNext()){					
				AtributoElemento atributoElemento = (AtributoElemento) it.next();					
				PreparedStatement ps1 =  miConexion.prepareStatement(insertarEstructuraAtributosElemento);
				ps1.setInt(1, atributoElemento.getIdElementosSistemaEspecifico());
				ps1.setInt(2, atributoElemento.getIdSistemaEspecifico());
				ps1.setInt(3, atributoElemento.getIdSerie());
				ps1.setInt(4, atributoElemento.getIdModelo());
				ps1.setInt(5, atributoElemento.getIdFabricante());
				ps1.setString(6, atributoElemento.getNombreAtributo());	
				ps1.setString(7, atributoElemento.getTipoAtributo());
				ps1.executeUpdate();
			}		
			*/
			
			Iterator it = listaAtributoElementoActualizar.iterator();	
			while (it.hasNext()){				
				AtributoElemento atributoElemento = (AtributoElemento) it.next();
				PreparedStatement ps1 =  miConexion.prepareStatement(actualizarEstructuraAtributosElemento);
				ps1.setString(1, atributoElemento.getNombreAtributo());
				ps1.setString(2, atributoElemento.getTipoAtributo());
				ps1.setBoolean(3, atributoElemento.isVisible());
				ps1.setInt(4, atributoElemento.getIdFabricante());	
				ps1.setInt(5, atributoElemento.getIdModelo());
				ps1.setInt(6, atributoElemento.getIdSerie());
				ps1.setInt(7, atributoElemento.getIdSistemaEspecifico());
				ps1.setInt(8, atributoElemento.getIdElementosSistemaEspecifico());
				ps1.setInt(9, atributoElemento.getIdAtributoElemento());				
				ps1.executeUpdate();
			}		
			
			
			Iterator it1 = listaAtributoElementoInsertar.iterator();
			while (it1.hasNext()){					
				AtributoElemento atributoElemento = (AtributoElemento) it1.next();					
				PreparedStatement ps2 =  miConexion.prepareStatement(insertarEstructuraAtributosElemento);
				ps2.setInt(1, atributoElemento.getIdElementosSistemaEspecifico());
				ps2.setInt(2, atributoElemento.getIdSistemaEspecifico());
				ps2.setInt(3, atributoElemento.getIdSerie());
				ps2.setInt(4, atributoElemento.getIdModelo());
				ps2.setInt(5, atributoElemento.getIdFabricante());
				ps2.setString(6, atributoElemento.getNombreAtributo());	
				ps2.setString(7, atributoElemento.getTipoAtributo());
				ps2.executeUpdate();
			}		
			
			parametrosActualizarElemento.setActualizadoElemento(1);
			oldParametrosActualizarElemento.setActualizadoElemento(0);
			
		}catch (Exception e) {
			// No se puede actualizar el elemento
			e.printStackTrace();
			parametrosActualizarElemento.setActualizadoElemento(0);
			oldParametrosActualizarElemento.setActualizadoElemento(1);
			String mensaje =MyConstants.StringConstant.ELEMENTOS_ID.value()+ String.valueOf(objetoElementoEspecifico.getIdElementosSistemaEspecifico()) +". "; 			
			LOG.error("Error actualizar "+mensaje, e);	
			parametrosActualizarElemento.setMensaje(mensaje+"\n"+MessageUtils.copyStringErrorMsg(e.getMessage()));	
		}finally{			
		propertySupport.firePropertyChange(PROP_ACTUALIZAR_ELEMENTO_ESPECIFICO, oldParametrosActualizarElemento, parametrosActualizarElemento);
		}
	
	}
	

	public void actualizarEstructuraAtributos(AtributoElemento estructuraAtributo, UUID idObjetoEstructuraAtributo){
		
		//Se podria reutilizar en el actualizar anterior o sino ver guardarElemento
		ParametrosActualizarElemento parametrosActualizarElemento = new ParametrosActualizarElemento();
		ParametrosActualizarElemento oldParametrosActualizarElemento = new ParametrosActualizarElemento();
		
		parametrosActualizarElemento.setIdElemento(idObjetoEstructuraAtributo);//No es necesario introducir el idModelo en oldParametrosActualizarElemento, porque solo se usa parametrosActualizarElemento
		
		miConexion = miContenedorConexion.getConexion();
		
		
		AtributoElemento objetoEstructuraAtributo = estructuraAtributo;
		
		try 
		{//Se puede actualizar el fabricante
			int rc =0;
			
			PreparedStatement ps =  miConexion.prepareStatement(actualizarEstructuraAtributosElemento);

			ps.setString(1, objetoEstructuraAtributo.getNombreAtributo());
			ps.setString(2, objetoEstructuraAtributo.getTipoAtributo());			
			ps.setBoolean(3, objetoEstructuraAtributo.isVisible());			
			ps.setInt(4, objetoEstructuraAtributo.getIdFabricante());	
			ps.setInt(5, objetoEstructuraAtributo.getIdModelo());
			ps.setInt(6, objetoEstructuraAtributo.getIdSerie());
			ps.setInt(7, objetoEstructuraAtributo.getIdSistemaEspecifico());
			ps.setInt(8, objetoEstructuraAtributo.getIdElementosSistemaEspecifico());
			ps.setInt(9, objetoEstructuraAtributo.getIdAtributoElemento());
			
			ps.executeUpdate();
			
			parametrosActualizarElemento.setActualizadoElemento(1);
			oldParametrosActualizarElemento.setActualizadoElemento(0);
			
		}catch (Exception e) {
			// No se puede actualizar el elemento
			e.printStackTrace();
			parametrosActualizarElemento.setActualizadoElemento(0);
			oldParametrosActualizarElemento.setActualizadoElemento(1);
			String mensaje =MyConstants.StringConstant.ESTRUCTURA_ATRIBUTOS_ID.value()+ String.valueOf(objetoEstructuraAtributo.getIdAtributoElemento()) +". "; 			
			LOG.error("Error actualizar "+mensaje, e);	
			parametrosActualizarElemento.setMensaje(mensaje+"\n"+MessageUtils.copyStringErrorMsg(e.getMessage()));			
		}finally{			
		propertySupport.firePropertyChange(PROP_ACTUALIZAR_ESTRUCTURA_ATRIBUTO, oldParametrosActualizarElemento, parametrosActualizarElemento);
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
