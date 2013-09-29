package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import libreria.*;

public class ConsultasBBDD {
	private Connection miConexion;
	private ContenedorConexion miContenedorConexion;
	private PropertyChangeSupport propertySupport;
	//Fabricante
	private String selectFabricante = "select idFabricante, nombre, Web from fabricante;";
	//Modelo
	//opcion , idFabricante = x, un fabricante
	private String selectModeloIdFabricante = "select idModelo, idFabricante, nombre, descripcion, fecha_fabricacion, anyo_lanzamiento from modelo where idFabricante = ? ;";
	//opcion idFabricante = 0, todos los fabricantes
	private String selectModelo = "select idModelo, idFabricante, nombre, descripcion, fecha_fabricacion, anyo_lanzamiento from modelo; ";
	//Serie
	//opcion  idFabricante = x idModelo = x,un fabricante y un modelo
	private String selectSerieIdModelo = "select idSerie, idModelo, idFabricante, numero_serie, descripcion, fecha_fabricacion, anyo_lanzamiento from serie where idFabricante = ? and idModelo = ?; ";
	//opcion  idFabricante = x idModelo =0,un fabricante y sus modelos
	private String selectSerieIdFabricante = "select idSerie, idModelo, idFabricante, numero_serie, descripcion, fecha_fabricacion, anyo_lanzamiento from serie where idFabricante = ?; ";
	//opcion idFabricante = 0 idModelo = 0 , todos los fabricantes 
	private String selectSerie = "select idSerie, idModelo, idFabricante, numero_serie, descripcion, fecha_fabricacion, anyo_lanzamiento from serie; ";
	//Manual
	//opcion  idFabricante = x idModelo =X idSerie = X
	private String selectManualIdSerie = " select idManual, idSerie, idModelo, idFabricante, descripcion, ruta_fichero_pdf from manual where idFabricante = ? and idModelo = ? and idSerie = ?; ";
	//opcion  idFabricante = x idModelo =X idSerie = 0,un fabricante, un modelo y sus series
	private String selectManualIdModelo = "select idManual, idSerie, idModelo, idFabricante, descripcion, ruta_fichero_pdf from manual where idFabricante = ? and idModelo = ? ; ";
	//opcion  idFabricante = x idModelo =0 idSerie = 0,un fabricante y sus modelos 
	private String selectManualIdFabricante = "select idManual, idSerie, idModelo, idFabricante, descripcion, ruta_fichero_pdf from manual where idFabricante = ? ; ";
	//opcion  idFabricante = 0 idModelo =0 idSerie = 0,todos los fabricante
	private String selectManual = "select idManual, idSerie, idModelo, idFabricante, descripcion, ruta_fichero_pdf from manual ; ";
	//Estructura Frame
	//opcion  idFabricante = x idModelo =X idSerie = X
	private String selectEstructuraFrameIdSerie = "select idEstructura_Frame, idSerie, idModelo, idFabricante, n_subframes, tiempo_subframe, wps, bits_pw, descripcion from estructura_frame where idFabricante = ? and idModelo = ? and idSerie = ?; ";
	//opcion  idFabricante = x idModelo =X idSerie = 0,un fabricante, un modelo y sus series
	private String selectEstructuraFrameIdModelo = "select idEstructura_Frame, idSerie, idModelo, idFabricante, n_subframes, tiempo_subframe, wps, bits_pw, descripcion from estructura_frame where idFabricante = ? and idModelo = ? ; ";
	//opcion  idFabricante = x idModelo =0 idSerie = 0,un fabricante y sus modelos 
	private String selectEstructuraFrameIdFabricante = "select idEstructura_Frame, idSerie, idModelo, idFabricante, n_subframes, tiempo_subframe, wps, bits_pw, descripcion from estructura_frame where idFabricante = ? ; ";
	//opcion  idFabricante = 0 idModelo =0 idSerie = 0,todos los fabricante
	private String selectEstructuraFrame = "select idEstructura_Frame, idSerie, idModelo, idFabricante, n_subframes, tiempo_subframe, wps, bits_pw, descripcion from estructura_frame ; ";
	//SistemaEspecifico
	//opcion  idFabricante = x idModelo =X idSerie = X,un fabricante, un modelo, una serie
	private String selectSistemaEspecificoIdSerie = "select idSistema_Especifico, idSerie, idModelo, idFabricante, nombre, descripcion from sistema_especifico where idFabricante = ? and idModelo = ? and idSerie = ?;";
	//opcion  idFabricante = x idModelo =X idSerie = 0,un fabricante, un modelo y sus series
	private String selectSistemaEspecificoIdModelo = "select idSistema_Especifico, idSerie, idModelo, idFabricante, nombre, descripcion from sistema_especifico where idFabricante = ? and idModelo = ? ;";
	//opcion  idFabricante = x idModelo =0 idSerie = 0,un fabricante y sus modelos 
	private String selectSistemaEspecificoIdFabricante = "select idSistema_Especifico, idSerie, idModelo, idFabricante, nombre, descripcion from sistema_especifico where idFabricante = ? ;";
	//opcion  idFabricante = 0 idModelo =0 idSerie = 0,todos los fabricante
	private String selectSistemaEspecifico = "select idSistema_Especifico, idSerie, idModelo, idFabricante, nombre, descripcion from sistema_especifico; ";
	//ElementoEspecifico
	//opcion  idFabricante = x idModelo =X idSerie = X idSistema_Especifico = X, un fabricante, un modelo, una serie, un sistema
	private String selectElementoEspecificoidSistema = "select idElementos_Sistema_Especifico, idSistema_Especifico, idSerie, idModelo, idFabricante, nombre_elemento, interesting, subframes, palabras, bits, n_atributos from Elementos_Sistema_Especifico where idFabricante = ? and idModelo = ? and idSerie = ? and idSistema_Especifico = ? ;";
	//opcion  idFabricante = x idModelo =X idSerie = X,un fabricante, un modelo, una serie
	private String selectElementoEspecificoIdSerie = "select idElementos_Sistema_Especifico, idSistema_Especifico, idSerie, idModelo, idFabricante, nombre_elemento, interesting, subframes, palabras, bits, n_atributos from Elementos_Sistema_Especifico where idFabricante = ? and idModelo = ? and idSerie = ?; ";
	//opcion  idFabricante = x idModelo =X idSerie = 0,un fabricante, un modelo y sus series
	private String selectElementoEspecificoIdModelo = "select idElementos_Sistema_Especifico, idSistema_Especifico, idSerie, idModelo, idFabricante, nombre_elemento, interesting, subframes, palabras, bits, n_atributos from Elementos_Sistema_Especifico where idFabricante = ? and idModelo = ? ;";
	//opcion  idFabricante = x idModelo =0 idSerie = 0,un fabricante y sus modelos 
	private String selectElementoEspecificoIdFabricante = "select idElementos_Sistema_Especifico, idSistema_Especifico, idSerie, idModelo, idFabricante, nombre_elemento, interesting, subframes, palabras, bits, n_atributos from Elementos_Sistema_Especifico where idFabricante = ? ;";
	//opcion  idFabricante = 0 idModelo =0 idSerie = 0,todos los fabricante
	private String selectElementoEspecifico = "select idElementos_Sistema_Especifico, idSistema_Especifico, idSerie, idModelo, idFabricante, nombre_elemento, interesting, subframes, palabras, bits, n_atributos from Elementos_Sistema_Especifico;";
	//AtributoElemento
	//opcion  idFabricante = x idModelo =X idSerie = X idSistema_Especifico = X  idElementos_Sistema_Especifico = X, un fabricante, un modelo, una serie, un sistema
	private String selectEstructuraAtributo = " select idEstructura_Atributos_de_Elemento, idElementos_Sistema_Especifico, idSistema_Especifico, idSerie, idModelo, idFabricante, nombre_atributo, tipo_atributo, visible from Estructura_Atributos_de_Elemento where idElementos_Sistema_Especifico = ?  ; ";
	public static final String PROP_LISTAR_FABRICANTE = "listarFabricante";
	public static final String PROP_LISTAR_MODELO = "listarModelo";
	public static final String PROP_LISTAR_SERIE = "listarSerie";
	public static final String PROP_LISTAR_MANUAL = "listarManual";
	public static final String PROP_LISTAR_ESTRUCTURA_FRAME = "listarEstructuraFrame";
	public static final String PROP_LISTAR_SISTEMA_ESPECIFICO = "listarSistemaEspecifico";
	public static final String PROP_LISTAR_ELEMENTO_ESPECIFICO = "listarElementoEspecifico";
	public static final String PROP_LISTAR_ESTRUCTURA_ATRIBUTO = "listarEstructuraAtributo";
	Logger LOG = LoggerFactory.getLogger(ConsultasBBDD.class);
	public ConsultasBBDD(ContenedorConexion contenedorConexion){
		
		miContenedorConexion= contenedorConexion;		
		// Se crea el objeto del PropertyChangeSupport, para hacer esto necesitamos del bean (GuardarElemento), representado por this
		propertySupport = new PropertyChangeSupport(this);
	}
	
	

	public void consultaListaFabricante(UUID idObjeto, boolean actualizar){		
		miConexion = miContenedorConexion.getConexion();		
		ArrayList <Object> oldDataListaBBDD =  new ArrayList <Object>();
		ArrayList<Object> dataListaBBDD = new ArrayList <Object>();		
		ParametrosListaBBDD parametrosListaBBDD = new ParametrosListaBBDD();
		ParametrosListaBBDD oldParametrosListaBBDD = new ParametrosListaBBDD();		
		parametrosListaBBDD.setIdElemento(idObjeto);
		oldParametrosListaBBDD.setIdElemento(idObjeto);
		parametrosListaBBDD.setActualizar(actualizar);
		try{     			 
			   Statement s = miConexion.createStatement();          
         	   ResultSet rs = s.executeQuery (selectFabricante);         
                while (rs.next()){                	
                	Fabricante fabricante = new Fabricante(rs.getInt(1), rs.getString(2), rs.getString(3));                                
                    dataListaBBDD.add(fabricante);
    			}
            
                parametrosListaBBDD.setDataListaBBDD(dataListaBBDD);                
         }catch(Exception ex){
        	 LOG.error("Error al consultar el listado del Fabricante", ex);
         }finally{
        	 propertySupport.firePropertyChange(PROP_LISTAR_FABRICANTE,oldParametrosListaBBDD , parametrosListaBBDD );
        	 
         }   	
				
	}
	
	

	public void consultaListaModelo(int idFabricante, UUID idObjeto,boolean actualizar){		
		miConexion = miContenedorConexion.getConexion();		
		ArrayList <Object> oldDataListaBBDD =  new ArrayList <Object>();
		ArrayList<Object> dataListaBBDD = new ArrayList <Object>();		
		ParametrosListaBBDD parametrosListaBBDD = new ParametrosListaBBDD();
		ParametrosListaBBDD oldParametrosListaBBDD = new ParametrosListaBBDD();		
		parametrosListaBBDD.setIdElemento(idObjeto);
		oldParametrosListaBBDD.setIdElemento(idObjeto);
		parametrosListaBBDD.setActualizar(actualizar);
		try{    
			   PreparedStatement ps; 
			   ResultSet rs;
			   Statement s;			   
			   if (idFabricante >0){//Hay idFabricante
				   ps = miConexion.prepareStatement(selectModeloIdFabricante);
				   ps.setInt(1, idFabricante);
				   rs = ps.executeQuery ();
			   }else {//Son todos los fabricantes	
	         	   s = miConexion.createStatement(); 	              
	         	   rs = s.executeQuery (selectModelo);
			   }         
                while (rs.next()){
                	Modelo modelo = new Modelo (rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                    dataListaBBDD.add(modelo);
    			}
                parametrosListaBBDD.setDataListaBBDD(dataListaBBDD);                
         }catch(Exception ex){
        	 LOG.error("Error al consultar el listado del Modelo", ex);
         }finally{
        	 propertySupport.firePropertyChange(PROP_LISTAR_MODELO,oldParametrosListaBBDD , parametrosListaBBDD );
         }   	
	}
	
	/*
	 * Hay tres posibilidades para listar las series de :
	 * un fabricante y un modelo idFabricante = x idModelo = x
	 * un fabricante y cualquier modelo idFabricante = x idModelo =0
	 * cualquier fabricante, idFabricante = 0 ( e idModelo = 0)
	
	 */
	public void consultaListaSerie(int idFabricante,int idModelo, UUID idObjeto, boolean actualizar){		
		miConexion = miContenedorConexion.getConexion();
		ArrayList <Object> oldDataListaBBDD =  new ArrayList <Object>();
		ArrayList<Object> dataListaBBDD = new ArrayList <Object>();		
		ParametrosListaBBDD parametrosListaBBDD = new ParametrosListaBBDD();
		ParametrosListaBBDD oldParametrosListaBBDD = new ParametrosListaBBDD();
		parametrosListaBBDD.setIdElemento(idObjeto);
		oldParametrosListaBBDD.setIdElemento(idObjeto);
		parametrosListaBBDD.setActualizar(actualizar);
		try{     
			   PreparedStatement ps; 
			   ResultSet rs;
			   Statement s;			   
			   if (idFabricante >0){//Hay idFabricante				   
				   if (idModelo > 0){//Hay idModelo, idFabricante = x idModelo = x					   
					   ps = miConexion.prepareStatement(selectSerieIdModelo);
					   ps.setInt(1, idFabricante);
					   ps.setInt(2, idModelo);
					   rs = ps.executeQuery ();					   
				   }else {//No hay idModelo, idFabricante = x idModelo =0					   
					   ps = miConexion.prepareStatement(selectSerieIdFabricante);
					   ps.setInt(1, idFabricante);
					   rs = ps.executeQuery ();
				   }				   
				   
			   }else {//Son todos los fabricantes, idFabricante = 0 ( e idModelo = 0)			  
	         	   
	         	   s = miConexion.createStatement(); 	              
	         	   rs = s.executeQuery (selectSerie);

			   }
                while (rs.next()){                	
                	Serie serie = new Serie(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                	dataListaBBDD.add(serie);
    			}
                parametrosListaBBDD.setDataListaBBDD(dataListaBBDD);                
         }catch(Exception ex){
        	 LOG.error("Error al consultar el listado de la Serie", ex);
         }finally{
        	 propertySupport.firePropertyChange(PROP_LISTAR_SERIE,oldParametrosListaBBDD , parametrosListaBBDD );        	 
         }   	
				
	}
	
	
	/*
	 * Hay cuatro posibilidades para listar los manuales de :
	 * idFabricante = x idModelo =X idSerie = X,un fabricante, un modelo, una serie
	 * idFabricante = x idModelo =X idSerie = 0,un fabricante, un modelo y sus series
	 * idFabricante = x idModelo =0 idSerie = 0,un fabricante y sus modelos 
	 * idFabricante = 0 idModelo =0 idSerie = 0,todos los fabricante

	 */
	public void consultaListaManual(int idFabricante,int idModelo,int idSerie, UUID idObjeto, boolean actualizar){		
		miConexion = miContenedorConexion.getConexion();
		ArrayList <Object> oldDataListaBBDD =  new ArrayList <Object>();
		ArrayList<Object> dataListaBBDD = new ArrayList <Object>();		
		ParametrosListaBBDD parametrosListaBBDD = new ParametrosListaBBDD();
		ParametrosListaBBDD oldParametrosListaBBDD = new ParametrosListaBBDD();		
		parametrosListaBBDD.setIdElemento(idObjeto);
		oldParametrosListaBBDD.setIdElemento(idObjeto);
		parametrosListaBBDD.setActualizar(actualizar);
		try{     
			   PreparedStatement ps; 
			   ResultSet rs;
			   Statement s;			   
			   if (idFabricante >0){//Hay idFabricante				   
				   if (idModelo > 0){//Hay idModelo, idFabricante = x idModelo = x					   
					   if (idSerie > 0){//Hay idSerie, idFabricante = x idModelo = x idSerie = x						   
						   ps = miConexion.prepareStatement(selectManualIdSerie);
						   ps.setInt(1, idFabricante);
						   ps.setInt(2, idModelo);
						   ps.setInt(3, idSerie);
						   rs = ps.executeQuery ();					   
					   }else{//Hay idModelo, idFabricante = x idModelo =x						   
						   ps = miConexion.prepareStatement(selectManualIdModelo);
						   ps.setInt(1, idFabricante);
						   ps.setInt(2, idModelo);
						   rs = ps.executeQuery ();				   
					   }					   
				   }else {//No hay idModelo, idFabricante = x idModelo =0					   
					   ps = miConexion.prepareStatement(selectManualIdFabricante);
					   ps.setInt(1, idFabricante);
					   rs = ps.executeQuery ();
				   }				   
			   }else {//Son todos los fabricantes, idFabricante = 0 ( e idModelo = 0)
	         	   s = miConexion.createStatement(); 	              
	         	   rs = s.executeQuery (selectManual);
			   }
	            while (rs.next()){
	            	
	            	Manual manual = new Manual(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
	            	dataListaBBDD.add(manual);
				}
	        
	            parametrosListaBBDD.setDataListaBBDD(dataListaBBDD);
	            
	     }catch(Exception ex){
	    	 LOG.error("Error al consultar el listado del Manual de Referencia", ex);
	     }finally{
	    	 propertySupport.firePropertyChange(PROP_LISTAR_MANUAL,oldParametrosListaBBDD , parametrosListaBBDD );	    	 
	     }   	
				
	}
		

	/*
	 * Hay cuatro posibilidades listar las estructuras frame de :
	 * idFabricante = x idModelo =X idSerie = X,un fabricante, un modelo, una serie
	 * idFabricante = x idModelo =X idSerie = 0,un fabricante, un modelo y sus series
	 * idFabricante = x idModelo =0 idSerie = 0,un fabricante y sus modelos 
	 * idFabricante = 0 idModelo =0 idSerie = 0,todos los fabricante

	 */
	public void consultaListaEstructuraFrame(int idFabricante,int idModelo,int idSerie, UUID idObjeto, boolean actualizar){
		
		miConexion = miContenedorConexion.getConexion();
		ArrayList <Object> oldDataListaBBDD =  new ArrayList <Object>();
		ArrayList<Object> dataListaBBDD = new ArrayList <Object>();		
		ParametrosListaBBDD parametrosListaBBDD = new ParametrosListaBBDD();
		ParametrosListaBBDD oldParametrosListaBBDD = new ParametrosListaBBDD();		
		parametrosListaBBDD.setIdElemento(idObjeto);
		oldParametrosListaBBDD.setIdElemento(idObjeto);
		parametrosListaBBDD.setActualizar(actualizar);

		try{     
			   PreparedStatement ps; 
			   ResultSet rs;
			   Statement s;			   
			   if (idFabricante >0){//Hay idFabricante				   
				   if (idModelo > 0){//Hay idModelo, idFabricante = x idModelo = x					   
					   if (idSerie > 0){//Hay idSerie, idFabricante = x idModelo = x idSerie = x						   
						   ps = miConexion.prepareStatement(selectEstructuraFrameIdSerie);
						   ps.setInt(1, idFabricante);
						   ps.setInt(2, idModelo);
						   ps.setInt(3, idSerie);
						   rs = ps.executeQuery ();					   
					   }else{//Hay idModelo, idFabricante = x idModelo =x						   
						   ps = miConexion.prepareStatement(selectEstructuraFrameIdModelo);
						   ps.setInt(1, idFabricante);
						   ps.setInt(2, idModelo);
						   rs = ps.executeQuery ();					   
					   }

					   
				   }else {//No hay idModelo, idFabricante = x idModelo =0					   
					   ps = miConexion.prepareStatement(selectEstructuraFrameIdFabricante);
					   ps.setInt(1, idFabricante);
					   rs = ps.executeQuery ();				   }
				   
				   
			   }else {//Son todos los fabricantes, idFabricante = 0 ( e idModelo = 0)			  
	         	   
	         	   s = miConexion.createStatement(); 	              
	         	   rs = s.executeQuery (selectEstructuraFrame);

			   }	     
	            while (rs.next()){            													
	            	EstructuraFrame estructuraFrame = new EstructuraFrame(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getInt(7), rs.getInt(8), rs.getString(9));
	            	dataListaBBDD.add(estructuraFrame);
				}	        
	            parametrosListaBBDD.setDataListaBBDD(dataListaBBDD);
	            
	     }catch(Exception ex){
	    	 LOG.error("Error al consultar el listado de la Estructura de Frame", ex);
	     }finally{
	    	 propertySupport.firePropertyChange(PROP_LISTAR_ESTRUCTURA_FRAME,oldParametrosListaBBDD , parametrosListaBBDD );
	    	 
	     }   	
		
		
	}
	
	
	/*
	 * Hay cuatro posibilidades listar los sistemas especificos de :
	 * idFabricante = x idModelo =X idSerie = X,un fabricante, un modelo, una serie
	 * idFabricante = x idModelo =X idSerie = 0,un fabricante, un modelo y sus series
	 * idFabricante = x idModelo =0 idSerie = 0,un fabricante y sus modelos 
	 * idFabricante = 0 idModelo =0 idSerie = 0,todos los fabricante

	 */
	public void consultaListaSistemaEspecifico(int idFabricante,int idModelo,int idSerie, UUID idObjeto, boolean actualizar){
		
		miConexion = miContenedorConexion.getConexion();		
		ArrayList <Object> oldDataListaBBDD =  new ArrayList <Object>();
		ArrayList<Object> dataListaBBDD = new ArrayList <Object>();		
		ParametrosListaBBDD parametrosListaBBDD = new ParametrosListaBBDD();
		ParametrosListaBBDD oldParametrosListaBBDD = new ParametrosListaBBDD();		
		parametrosListaBBDD.setIdElemento(idObjeto);
		oldParametrosListaBBDD.setIdElemento(idObjeto);
		parametrosListaBBDD.setActualizar(actualizar);
		try{ 
			   PreparedStatement ps; 
			   ResultSet rs;
			   Statement s;			   
			   if (idFabricante >0){//Hay idFabricante				   
				   if (idModelo > 0){//Hay idModelo, idFabricante = x idModelo = x					   
					   if (idSerie > 0){//Hay idSerie, idFabricante = x idModelo = x idSerie = x						   
						   ps = miConexion.prepareStatement(selectSistemaEspecificoIdSerie);
						   ps.setInt(1, idFabricante);
						   ps.setInt(2, idModelo);
						   ps.setInt(3, idSerie);
						   rs = ps.executeQuery ();					   
					   }else{//Hay idModelo, idFabricante = x idModelo =x						   
						   ps = miConexion.prepareStatement(selectSistemaEspecificoIdModelo);
						   ps.setInt(1, idFabricante);
						   ps.setInt(2, idModelo);
						   rs = ps.executeQuery ();					   
					   }
					   
				   }else {//No hay idModelo, idFabricante = x idModelo =0					   
					   ps = miConexion.prepareStatement(selectSistemaEspecificoIdFabricante);
					   ps.setInt(1, idFabricante);
					   rs = ps.executeQuery ();
				   }		   
				   
			   }else {//Son todos los fabricantes, idFabricante = 0 ( e idModelo = 0)				  
	         	   
	         	   s = miConexion.createStatement(); 	              
	         	   rs = s.executeQuery (selectSistemaEspecifico);

			   }	     
	            while (rs.next()){	            	
	            	SistemaEspecifico sistemaEspecifico = new SistemaEspecifico(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
	            	dataListaBBDD.add(sistemaEspecifico);
				}	        
	            parametrosListaBBDD.setDataListaBBDD(dataListaBBDD);
	            
	     }catch(Exception ex){
	    	 LOG.error("Error al consultar el listado del Sistema Específico", ex);
	     }finally{
	    	 propertySupport.firePropertyChange(PROP_LISTAR_SISTEMA_ESPECIFICO,oldParametrosListaBBDD , parametrosListaBBDD );
	    	 
	     }   	
		
		
	}
	
	
	
	/*
	 * Hay cuatro posibilidades listar los sistemas especificos de :
	 * idFabricante = x idModelo =X idSerie = X idSistema = X,un fabricante, un modelo, una serie, un sistema
	 * idFabricante = x idModelo =X idSerie = X,un fabricante, un modelo, una serie
	 * idFabricante = x idModelo =X idSerie = 0,un fabricante, un modelo y sus series
	 * idFabricante = x idModelo =0 idSerie = 0,un fabricante y sus modelos 
	 * idFabricante = 0 idModelo =0 idSerie = 0,todos los fabricante
	 */
	public void consultaListaElementoEspecifico(int idFabricante,int idModelo,int idSerie, int idSistema, UUID idObjeto, boolean actualizar){
		
		miConexion = miContenedorConexion.getConexion();			
		ArrayList <Object> oldDataListaBBDD =  new ArrayList <Object>();
		ArrayList<Object> dataListaBBDD = new ArrayList <Object>();		
		ParametrosListaBBDD parametrosListaBBDD = new ParametrosListaBBDD();
		ParametrosListaBBDD oldParametrosListaBBDD = new ParametrosListaBBDD();		
		parametrosListaBBDD.setIdElemento(idObjeto);
		oldParametrosListaBBDD.setIdElemento(idObjeto);
		parametrosListaBBDD.setActualizar(actualizar);
		try{  
			   PreparedStatement ps; 
			   ResultSet rs;
			   Statement s;
			   
			   if (idFabricante >0){//Hay idFabricante				   
				   if (idModelo > 0){//Hay idModelo, idFabricante = x idModelo = x					   
					   if (idSerie > 0){//Hay idSerie, idFabricante = x idModelo = x idSerie = x						   
						   if (idSistema > 0){//Hay idSistema, idFabricante = x idModelo = x idSerie = x idSistema =x							   
							   ps = miConexion.prepareStatement(selectElementoEspecificoidSistema);
							   ps.setInt(1, idFabricante);
							   ps.setInt(2, idModelo);
							   ps.setInt(3, idSerie);
							   ps.setInt(4, idSistema);
							   rs = ps.executeQuery ();									   
						   }else{ //Hay idSerie, idFabricante = x idModelo = x idSerie = x
							   
							   ps = miConexion.prepareStatement(selectElementoEspecificoIdSerie);
							   ps.setInt(1, idFabricante);
							   ps.setInt(2, idModelo);
							   ps.setInt(3, idSerie);
							   rs = ps.executeQuery ();							   
							   
						   }
						   
					   }else{//Hay idModelo, idFabricante = x idModelo =x						   
						   ps = miConexion.prepareStatement(selectElementoEspecificoIdModelo);
						   ps.setInt(1, idFabricante);
						   ps.setInt(2, idModelo);
						   rs = ps.executeQuery ();					   
					   }
					   
				   }else {//No hay idModelo, idFabricante = x idModelo =0					   
					   ps = miConexion.prepareStatement(selectElementoEspecificoIdFabricante);
					   ps.setInt(1, idFabricante);
					   rs = ps.executeQuery ();
				   }			   
				   
			   }else {//Son todos los fabricantes, idFabricante = 0 ( e idModelo = 0)			  
	         	   
	         	   s = miConexion.createStatement(); 	              
	         	   rs = s.executeQuery (selectElementoEspecifico);

			   }	     
	            while (rs.next()){	            	
	            	ElementosSistemaEspecifico elementoSistemaEspecifico = new ElementosSistemaEspecifico(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getBoolean(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11));
	            	dataListaBBDD.add(elementoSistemaEspecifico);
				}	        
	            parametrosListaBBDD.setDataListaBBDD(dataListaBBDD);
	            
	     }catch(Exception ex){
	    	 LOG.error("Error al consultar el listado del Elemento Específico", ex);
	     }finally{
	    	 propertySupport.firePropertyChange(PROP_LISTAR_ELEMENTO_ESPECIFICO,oldParametrosListaBBDD , parametrosListaBBDD );
	    	 
	     }   	
		
		
	}

	
	
	/*
	 * Hay solo una posibilidad de listar la estructura de atributos:
	 * idFabricante = x idModelo =X idSerie = X idSistema = X idElemento = X,un fabricante, un modelo, una serie, un sistema, un elemento
	 *
	 */
	public void consultaListaEstructuraAtributos(int idElemento, UUID idObjeto, boolean actualizar){
		
		miConexion = miContenedorConexion.getConexion();		
		
		ArrayList <Object> oldDataListaBBDD =  new ArrayList <Object>();
		ArrayList<Object> dataListaBBDD = new ArrayList <Object>();
		
		ParametrosListaBBDD parametrosListaBBDD = new ParametrosListaBBDD();
		ParametrosListaBBDD oldParametrosListaBBDD = new ParametrosListaBBDD();
		
		parametrosListaBBDD.setIdElemento(idObjeto);
		oldParametrosListaBBDD.setIdElemento(idObjeto);
		parametrosListaBBDD.setActualizar(actualizar);
		try{    
			   PreparedStatement ps; 
			   ResultSet rs;
			   Statement s;
			   ps = miConexion.prepareStatement(selectEstructuraAtributo);
			   ps.setInt(1, idElemento);
			   rs = ps.executeQuery ();				   
	            while (rs.next()){	            	
	            	AtributoElemento estructuraAtributosElemento = new AtributoElemento(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));
	            	dataListaBBDD.add(estructuraAtributosElemento);
				}	        
	            parametrosListaBBDD.setDataListaBBDD(dataListaBBDD);
	            
	     }catch(Exception ex){
	    	 LOG.error("Error al consultar el listado de la Estructura de Atributo", ex);
	     }finally{
	    	 propertySupport.firePropertyChange(PROP_LISTAR_ESTRUCTURA_ATRIBUTO,oldParametrosListaBBDD , parametrosListaBBDD );	    	 
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
