package model;

import view.*; 

import java.sql.*;
import java.sql.Statement;


import java.util.*;

import java.beans.*;
import java.io.Serializable;
import java.lang.Object.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import libreria.*;

public class ConexionBBDD implements Serializable{
	
	private Connection miConexion;	
	private ContenedorConexion miContenedorConexion;	
	private PropertyChangeSupport propertySupport;	
	//Seleccionar BBDD
    public ArrayList<Object []> dataListaBBDD = new ArrayList();      
	public static final String PROP_INICIAR_SESION = "iniciarSesion";
	public static final String PROP_MOSTRAR_BBDD = "mostrarBBDD";	
	public static final String PROP_USAR_BBDD = "usarBBDD";		
	public static final String PROP_CREAR_BBDD = "crearBBDD";	
	public static final String PROP_ELIMINAR_BBDD = "eliminarBBDD";	
	private String bbddSelecionada;
	Logger LOG = LoggerFactory.getLogger(ConexionBBDD.class);	
		
	//Nos conectamos a la bbdd con el driver del jar
	public ConexionBBDD(ContenedorConexion contenedorConexion){		
		miContenedorConexion= contenedorConexion;		
	// Se crea el objeto del	PropertyChangeSupport, para hacer esto necesitamos del bean (ConexionBBDD), representado por this
	propertySupport = new PropertyChangeSupport(this);
	}
	
	/* Metodo usado para iniciar sesion
	 * @param usuario, usuario de bbdd
	 * @param contraseña, contraseña del usuario anterior
	 * @param flagInicioSesion, flag, es 1 si la conexion ha sido exitosa y 0 si la conexion ha tenido algun fallo
	 * 
	 * */
	public void iniciarSesion(String usuario, String contraseña){
		int flagInicioSesion = 0;
		int oldflagInicioSesion = 0;		
		try{
			String strHost = "localhost"; 
			Class.forName("com.mysql.jdbc.Driver");
			miConexion = DriverManager.getConnection("jdbc:mysql://"+strHost,usuario,contraseña);
			miContenedorConexion.setConexion(miConexion);
			//Para la VentanaPrincipal
			//Posible mejora, implementarlo para IniciarSesion
			flagInicioSesion = 1; 
			oldflagInicioSesion = 0;			
		}catch(Exception e){
			e.printStackTrace();
			//Para la VentanaPrincipal
			flagInicioSesion = 0; 
			oldflagInicioSesion = 1;
			LOG.error("Error Iniciar Sesión usuario "+ usuario, e);			
		}finally{
			
			//se notifica del cambio a los listeners	
			propertySupport.firePropertyChange(PROP_INICIAR_SESION, oldflagInicioSesion, flagInicioSesion);	
					
		}
			
		
	}
	
	
	/*
	 * Metodo para mostrar las bbdd de el usuario que se conecto
	 * Aqui se llama a propertySupport.firePropertyChange, el cual notifica a los listeners de los cambios
	 * de dataListaBBDD
	 *
	 */
	public void mostrarBBDD(){
		
	    ArrayList<Object []> oldDataListaBBDD= new ArrayList<Object[]>(dataListaBBDD);
	    
	    //Es una backup de dataListaBBDD.
	    Collections.copy(oldDataListaBBDD,dataListaBBDD);
	    
	    // Se borra toda List anterior, de la que se ha hecho el backup
	    dataListaBBDD.removeAll(dataListaBBDD);
	    
		String host = null;
		String dataBase = null;
		try{
			Statement s = miConexion.createStatement(); 
			//Select USER(); nos muestra el usuario y el host de esta conexion
			ResultSet rs = s.executeQuery ("SELECT USER();");
			while (rs.next()){ 
				host = rs.getString (1);				
			}			
			//Show Databases; nos muesta las bbdd del usuario conectado
			 rs = s.executeQuery ("SHOW DATABASES;");			
			
			while (rs.next()){   
				dataBase = rs.getString (1);
				// Asi evitamos que se pueda usar esta bbdd en el sistema
				if (!dataBase.equals("information_schema")){					
				    Object [] data = {dataBase,host};//Crea el array de Objetos
				    dataListaBBDD.add(data);//Añade el array al ArrayList
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("Error Mostrar BBDD, Host = "+ host, e);	
		}finally{
			//se notifica del cambio a los listeners	
			propertySupport.firePropertyChange(PROP_MOSTRAR_BBDD, oldDataListaBBDD, dataListaBBDD);	
		}
	}

	/*
	 * Metodo para usar la bbdd seleccionada
	 * Aqui se llama a propertySupport.firePropertyChange, el cual notifica a los listeners de los cambios
	 * de usarBBDD
	 *
	 */
	public void usarBBDD(Object	bbddSelecionada)
	{
		bbddSelecionada = (String) bbddSelecionada;
		String mensajeUsarBBDD;
		
		int usarBBDD = 0;
		int oldUsarBBDD = 0;
		
		
		try
		{
			Statement s = miConexion.createStatement(); 
			oldUsarBBDD = usarBBDD;
			
			//Hace que se use la bbdd elegida.
			ResultSet rs = s.executeQuery ("USE "+bbddSelecionada+";");
			
			
			//Nos devuelve la base de datos usada o null, si no se esta usando ninguna bbdd
			rs = s.executeQuery ("SELECT DATABASE();");
			
			
			while (rs.next()) 
			{   
				mensajeUsarBBDD = rs.getString (1);
				
				//LOG.info("Usar BBDD "+ mensajeUsarBBDD); 
			   
			}
			
			
			if (rs.wasNull())
			{
				usarBBDD = 0;// se podria borrar
				oldUsarBBDD = 1;
			}else{
				
				usarBBDD = 1;
				oldUsarBBDD = 0;
			}
			
				
			
		}catch(Exception e){//Si el try da algun error, venimos aqui
			e.printStackTrace();			
			usarBBDD =0;
			oldUsarBBDD = 1;
			LOG.error("Error Usar BBDD  bbdd = "+ bbddSelecionada,e);
		}finally
		{  //se notifica del cambio a los listeners
			propertySupport.firePropertyChange(PROP_USAR_BBDD, oldUsarBBDD, usarBBDD);	
		}
		
	}	
	
	
	/*
	 * Metodo usado para crear la estructura de bbdd
	 * Aqui se llama a propertySupport.firePropertyChange, el cual notifica a los listeners de los cambios
	 * de parametrosCrearEstructuraBBDD
	 *
	 */
	
	public void crearEstructuraBBDD(Object bbddSelecionada, UUID idCrearEstructuraBBDD )
	{
		
		bbddSelecionada = (String) bbddSelecionada;
		String mensajeUsarBBDD;
		TablasBBDD tablasBBDD = new TablasBBDD();
		
		ParametrosConexionBBDD parametrosCrearEstructuraBBDD = new ParametrosConexionBBDD();
		ParametrosConexionBBDD oldParametrosCrearEstructuraBBDD = new ParametrosConexionBBDD();
		parametrosCrearEstructuraBBDD.setIdElemento(idCrearEstructuraBBDD);
		try
		{
			Statement s = miConexion.createStatement(); 
			
			
			//Hace que se use la bbdd elegida.
			ResultSet rs = s.executeQuery ("USE "+bbddSelecionada+";");
			
			
			//Nos devuelve la base de datos usada o null, si no se esta usando ninguna bbdd
			rs = s.executeQuery ("SELECT DATABASE();");
			
			
			while (rs.next()) {   
				mensajeUsarBBDD = rs.getString (1);				
				LOG.info("crearEstructuraBBDD bbdd usada = "+ mensajeUsarBBDD); 			   
			}
			
			//Se verifica si se puede usar o no la BBDD
			if (rs.wasNull()){//No se puede usar la bbdd
				
				parametrosCrearEstructuraBBDD.setUsarBBDD(0);
				oldParametrosCrearEstructuraBBDD.setUsarBBDD(1);
				
			}else{// Si se puede usar la bbdd	
				parametrosCrearEstructuraBBDD.setUsarBBDD(1);
				oldParametrosCrearEstructuraBBDD.setUsarBBDD(0);
				rs = s.executeQuery("SHOW TABLES;");
				//Se verifican si la BBDD usada tiene tablas
				//Si no hay tablas rs.next() devuelve false y si las hay devuelve true
				if (rs.next())
				{//Si hay tablas	
					parametrosCrearEstructuraBBDD.setHayTablas(1);
					oldParametrosCrearEstructuraBBDD.setHayTablas(0);
				}else{//No hay tablas
					parametrosCrearEstructuraBBDD.setHayTablas(0);
					oldParametrosCrearEstructuraBBDD.setHayTablas(1);
					ejecutarCrearTablas( s, rs, tablasBBDD);//Se ejecuta el script de creacion de tablas
				}	
				
			}
			
		}catch(Exception e){//Si el try da algun error, venimos aqui
			e.printStackTrace();
			oldParametrosCrearEstructuraBBDD.setUsarBBDD(1);
			oldParametrosCrearEstructuraBBDD.setHayTablas(1);
			LOG.error("Error al crear una Estructura de BBDD en la base de datos  " + bbddSelecionada, e);			
		}finally
		{   //se notifica del cambio a los listeners	
			propertySupport.firePropertyChange(PROP_CREAR_BBDD, oldParametrosCrearEstructuraBBDD, parametrosCrearEstructuraBBDD);	
		}
		
		
		
	}
	
	
	private void ejecutarCrearTablas(Statement s,ResultSet rs, TablasBBDD tablasBBDD){
		//Creacion de la base de datos
		try {
			String cadena= null;
			int rc =0;//rows counter
			cadena = tablasBBDD.getFabricante(); 
			rc = s.executeUpdate (cadena);			
			cadena = tablasBBDD.getModelo();
			rc = s.executeUpdate (cadena);			
			cadena = tablasBBDD.getSerie();
			rc = s.executeUpdate (cadena);
			cadena = tablasBBDD.getManual();
			rc = s.executeUpdate (cadena);			
			cadena = tablasBBDD.getEstructuraFrame();
			rc = s.executeUpdate (cadena);			
			cadena = tablasBBDD.getSistemaEspecifico();
			rc = s.executeUpdate (cadena);
			cadena = tablasBBDD.getElementosSistemaEspecifico();
			rc = s.executeUpdate (cadena);			
			cadena = tablasBBDD.getEstructuraAtributosElemento();
			rc = s.executeUpdate (cadena);
		}catch(Exception e){//Si el try da algun error, venimos aqui
			e.printStackTrace();
			LOG.error("Error al crear tablas en la bbdd seleccionada ", e);
		}
	}
	
	
	/*
	 * Metodo usado para eliminar la estructura de bbdd
	 * Aqui se llama a propertySupport.firePropertyChange, el cual notifica a los listeners de los cambios
	 * de parametrosCrearEstructuraBBDD
	 *
	 */
	
	public void eliminarEstructuraBBDD(Object bbddSelecionada, UUID idEliminarEstructuraBBDD){
		
		bbddSelecionada = (String) bbddSelecionada;
		String mensajeUsarBBDD;
		
		ParametrosConexionBBDD parametrosEliminarEstructuraBBDD = new ParametrosConexionBBDD();
		ParametrosConexionBBDD oldParametrosEliminarEstructuraBBDD = new ParametrosConexionBBDD();
		parametrosEliminarEstructuraBBDD.setIdElemento(idEliminarEstructuraBBDD);
		try{
			Statement s = miConexion.createStatement(); 
			//Hace que se use la bbdd elegida.
			ResultSet rs = s.executeQuery ("USE "+bbddSelecionada+";");
			//Nos devuelve la base de datos usada o null, si no se esta usando ninguna bbdd
			rs = s.executeQuery ("SELECT DATABASE();");
			while (rs.next()) {   
				mensajeUsarBBDD = rs.getString (1);
				LOG.info("eliminarEstructuraBBDD bbdd usada = "+ mensajeUsarBBDD); 
			}
			
			//Se verifica si se puede usar o no la BBDD
			if (rs.wasNull()){//No se puede usar la bbdd				
				parametrosEliminarEstructuraBBDD.setUsarBBDD(0);
				oldParametrosEliminarEstructuraBBDD.setUsarBBDD(1);				
			}else{// Si se puede usar la bbdd		
				parametrosEliminarEstructuraBBDD.setUsarBBDD(1);
				oldParametrosEliminarEstructuraBBDD.setUsarBBDD(0);
				ejecutarEliminarTablas(s, rs,(String) bbddSelecionada);
				rs = s.executeQuery("SHOW TABLES;");
				//Se verifican si la BBDD usada tiene tablas
				//Si no hay tablas rs.next() devuelve false y si las hay devuelve true
				if (rs.next()){//Si hay tablas		
					parametrosEliminarEstructuraBBDD.setHayTablas(1);
					oldParametrosEliminarEstructuraBBDD.setHayTablas(0);
				}else{//No hay tablas
					parametrosEliminarEstructuraBBDD.setHayTablas(0);
					oldParametrosEliminarEstructuraBBDD.setHayTablas(1);
				}	
			}
		}catch(Exception e){//Si el try da algun error, venimos aqui
			e.printStackTrace();
			
			parametrosEliminarEstructuraBBDD.setUsarBBDD(0);
			oldParametrosEliminarEstructuraBBDD.setUsarBBDD(1);
			
			parametrosEliminarEstructuraBBDD.setHayTablas(0);
			oldParametrosEliminarEstructuraBBDD.setHayTablas(1);
			LOG.error("Error al eliminar una estructura de bbdd en la bbdd = "+ bbddSelecionada, e);
		}finally{   
			//se notifica del cambio a los listeners	
			propertySupport.firePropertyChange(PROP_ELIMINAR_BBDD, oldParametrosEliminarEstructuraBBDD, parametrosEliminarEstructuraBBDD);	
		}
	}
	
	
	private void ejecutarEliminarTablas(Statement s,ResultSet rs, String bbddSelecionada){
		
		String bbdd = bbddSelecionada;
		//Eliminacion de la base de datos
		int rc;
		try {					
			rc = s.executeUpdate("DROP DATABASE "+bbdd+" ;");
			rc = s.executeUpdate("CREATE DATABASE "+bbdd+" ;");
			rc = s.executeUpdate("USE "+bbdd+" ;");
									
		}catch(Exception e)
		{//Si el try da algun error, venimos aqui
			e.printStackTrace();
			LOG.error("Error al eliminar las tablas en la bbdd = " + bbdd, e);
		}
	}
	
	/*
	 * Devuelve la conexion establecida con el servidor de bbdd
	 * 
	 * */
	public Connection getConexion (){
		
		return miConexion;
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
