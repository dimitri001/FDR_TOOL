package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;// OJO import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import libreria.*;

public class ConsultasComboBoxBBDD  {

	private Connection miConexion;
	private ContenedorConexion miContenedorConexion;
	
	private PropertyChangeSupport propertySupport;

	
	private String selectFabricante = "select idFabricante, nombre from fabricante;";
	private String selectModelo = "select idModelo, nombre from modelo "+
								  "where idFabricante =  ? ; "	;

	private String selectSerie = "select idSerie, numero_serie from serie "+
	  							 "where idFabricante =  ? and idModelo = ?; ";

	private String selectSistema = "select idSistema_Especifico, nombre from sistema_especifico "+
		 						   "where idFabricante =  ? and idModelo = ? "+
								   "and idSerie = ? ;"	;

	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";
	public static final String PROP_CONSULTAR_MODELO = "consultarModelo";
	public static final String PROP_CONSULTAR_SERIE = "consultarSerie";
	public static final String PROP_CONSULTAR_MANUAL = "consultarManual";
	public static final String PROP_CONSULTAR_SISTEMA = "consultarSistema";	
	Logger LOG = LoggerFactory.getLogger(ConsultasComboBoxBBDD.class);
	
	public ConsultasComboBoxBBDD(ContenedorConexion contenedorConexion){		
		miContenedorConexion= contenedorConexion;		
		// Se crea el objeto del PropertyChangeSupport, para hacer esto necesitamos del bean (GuardarElemento), representado por this
		propertySupport = new PropertyChangeSupport(this);
	}
	
	
	public void consultaFabricante(UUID idObjeto){
		
		miConexion = miContenedorConexion.getConexion();
		
		ParametrosConsultasComboBoxBBDD parametrosConsultasCbFabricante= new ParametrosConsultasComboBoxBBDD();
		ParametrosConsultasComboBoxBBDD oldParametrosConsultasCbFabricante = new ParametrosConsultasComboBoxBBDD();
		
		parametrosConsultasCbFabricante.setIdElemento(idObjeto);
		oldParametrosConsultasCbFabricante.setIdElemento(idObjeto);

		try{  
			   Statement s = miConexion.createStatement(); 
         	   ResultSet rs = s.executeQuery (selectFabricante);
                while (rs.next()){
                	ElementoComboBox elementoComboBox = new ElementoComboBox(rs.getInt(1), rs.getString(2));
                    parametrosConsultasCbFabricante.getDataElemento().addElement(elementoComboBox);
    			}                
         }catch(Exception ex){
        	 LOG.error("Error Consulta ComboBox Fabricante ", ex);
         }finally{
        	 propertySupport.firePropertyChange(PROP_CONSULTAR_FABRICANTE,oldParametrosConsultasCbFabricante, parametrosConsultasCbFabricante);
         }  
	}


	public void consultaModelo(int idFabricante, UUID idObjeto){		
		miConexion = miContenedorConexion.getConexion();
        ParametrosConsultasComboBoxBBDD parametrosConsultasCbModelo= new ParametrosConsultasComboBoxBBDD();
		ParametrosConsultasComboBoxBBDD oldParametrosConsultasCbModelo = new ParametrosConsultasComboBoxBBDD();		
		// Se añade para que el Listener sea siempre notificado, por si en dataModelo sus elementos son null
        //dataElemento es del tipo Vector<ElementoComboBox>
		oldParametrosConsultasCbModelo.getDataElemento().add (new ElementoComboBox(0,"Elemento0"));		
		parametrosConsultasCbModelo.setIdElemento(idObjeto);
		oldParametrosConsultasCbModelo.setIdElemento(idObjeto);
        try{ 
         	   PreparedStatement ps = miConexion.prepareStatement(selectModelo);         	   
         	   ps.setInt(1,idFabricante);
         	   ResultSet rs =  ps.executeQuery();         	   
                while (rs.next()){
                	ElementoComboBox elementoComboBox = new ElementoComboBox(rs.getInt(1), rs.getString(2));
                    parametrosConsultasCbModelo.getDataElemento().addElement(elementoComboBox);
    			}
                
         }catch(Exception ex){
        	 LOG.error("Error Consulta ComboBox Modelo ", ex);
         }finally{
        	 propertySupport.firePropertyChange(PROP_CONSULTAR_MODELO,oldParametrosConsultasCbModelo, parametrosConsultasCbModelo);
        }   
	}

	public void consultaSerie(int idFabricante, int idModelo, UUID idObjeto){
		
		miConexion = miContenedorConexion.getConexion();
		
        ParametrosConsultasComboBoxBBDD parametrosConsultasCbSerie= new ParametrosConsultasComboBoxBBDD();
		ParametrosConsultasComboBoxBBDD oldParametrosConsultasCbSerie = new ParametrosConsultasComboBoxBBDD();
		
		// Se añade para que el Listener sea siempre notificado, por si en dataModelo sus elementos son null
        //dataElemento es del tipo Vector<ElementoComboBox>
		oldParametrosConsultasCbSerie.getDataElemento().add (new ElementoComboBox(0,"Elemento0"));
		
		parametrosConsultasCbSerie.setIdElemento(idObjeto);
		oldParametrosConsultasCbSerie.setIdElemento(idObjeto);
        try{  
         	   PreparedStatement ps = miConexion.prepareStatement(selectSerie);

         	   ps.setInt(1,idFabricante);
         	   ps.setInt(2,idModelo);
         	   ResultSet rs =  ps.executeQuery();
         	   
                while (rs.next()){
                	ElementoComboBox elementoComboBox = new ElementoComboBox(rs.getInt(1), rs.getString(2));
                    parametrosConsultasCbSerie.getDataElemento().addElement(elementoComboBox);
    			}
                
         }catch(Exception ex){
        	 LOG.error("Error Consulta ComboBox Serie ", ex);

         }finally{        	 
        	 propertySupport.firePropertyChange(PROP_CONSULTAR_SERIE,oldParametrosConsultasCbSerie, parametrosConsultasCbSerie);
         }   	
	}
	

	public void consultaSistema(int idFabricante, int idModelo, int idSerie, UUID idObjeto){
		
		miConexion = miContenedorConexion.getConexion();
		
        ParametrosConsultasComboBoxBBDD parametrosConsultasCbSistema= new ParametrosConsultasComboBoxBBDD();
		ParametrosConsultasComboBoxBBDD oldParametrosConsultasCbSistema = new ParametrosConsultasComboBoxBBDD();
		
		// Se añade para que el Listener sea siempre notificado, por si en dataModelo sus elementos son null
        //dataElemento es del tipo Vector<ElementoComboBox>
		oldParametrosConsultasCbSistema.getDataElemento().add (new ElementoComboBox(0,"Elemento0"));
		
		parametrosConsultasCbSistema.setIdElemento(idObjeto);
		oldParametrosConsultasCbSistema.setIdElemento(idObjeto);
        try{          	
         	   PreparedStatement ps = miConexion.prepareStatement(selectSistema);
         	   ps.setInt(1,idFabricante);
         	   ps.setInt(2,idModelo);
         	   ps.setInt(3,idSerie);
         	   ResultSet rs =  ps.executeQuery();         	   
                while (rs.next()){
                	ElementoComboBox elementoComboBox = new ElementoComboBox(rs.getInt(1), rs.getString(2));                     
                    parametrosConsultasCbSistema.getDataElemento().addElement(elementoComboBox);
    			}
                
         }catch(Exception ex){
        	 LOG.error("Error Consulta ComboBox Sistema Específico ", ex);
         }finally{        	 
        	 propertySupport.firePropertyChange(PROP_CONSULTAR_SISTEMA,oldParametrosConsultasCbSistema, parametrosConsultasCbSistema);
        }   
	}

	
	/*Añade un Listener al elemento atado de esta clase*/
    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertySupport.addPropertyChangeListener(listener);
    }
    /*Remueve un Listener al elemento atado de esta clase*/
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
	
}
