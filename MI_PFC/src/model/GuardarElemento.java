package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;// OJO import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MessageUtils;

import libreria.ContenedorConexion;
import libreria.ContenedorVisualAtributoElemento;
import libreria.MyConstants;
import libreria.ParametrosGuardarElemento;


public class GuardarElemento implements Serializable{
	
	private Connection miConexion;
	private ContenedorConexion miContenedorConexion;
	
	private PropertyChangeSupport propertySupport;

	
	private String insertarFabricante = "insert into fabricante (nombre, Web) values (?,?);";
	private String insertarModelo = "insert into modelo (idFabricante, nombre, descripcion, fecha_fabricacion, anyo_lanzamiento)" +
									"values (?,?,?,?,?);";
	private String insertarSerie = "insert into serie(idModelo, idFabricante, numero_serie, descripcion, fecha_fabricacion, anyo_lanzamiento)"+
								   "values (?,?,?,?,?,?);";		

	private String insertarManual = "insert into manual(idSerie, idModelo, idFabricante, descripcion, ruta_fichero_pdf) "+
	   							    "values (?,?,?,?,?);";	

	private String insertarEstructuraFrame = "insert into estructura_frame(idSerie, idModelo, idFabricante,N_subframes, tiempo_subframe,wps, bits_pw, descripcion)"+
											 "values (?,?,?,?,?,?,?,?);";
	
	private String insertarSistemaEspecifico = "insert into sistema_especifico(idSerie, idModelo, idFabricante,nombre,descripcion)"+ 
											   "values (?,?,?,?,?);";

	private String insertarElementoEspecifico = "insert into elementos_sistema_especifico(idSistema_Especifico,idSerie, idModelo, idFabricante,nombre_elemento, interesting, subframes, palabras, bits, n_atributos) "+ 
												"values (?,?,?,?,?,?,?,?,?,?);";

	private String insertarEstructuraAtributosElemento = "insert into estructura_atributos_de_elemento (idElementos_Sistema_Especifico, idSistema_Especifico,idSerie, idModelo, idFabricante, nombre_atributo, tipo_atributo, visible) "+ 														 
														 "values (?,?,?,?,?,?,?,?); ";

	private String idUltimoInsertElementoEspecifico = " select max(idElementos_Sistema_Especifico) as id from elementos_sistema_especifico;" ; 
	
	public static final String PROP_INSERTAR_FABRICANTE = "insertarFabricante";
	public static final String PROP_INSERTAR_MODELO = "insertarModelo";
	public static final String PROP_INSERTAR_SERIE = "insertarSerie";
	public static final String PROP_INSERTAR_MANUAL = "insertarManual";
	public static final String PROP_INSERTAR_ESTRUCTURA_FRAME = "insertarEstructuraFrame";
	public static final String PROP_INSERTAR_SISTEMA_ESPECIFICO = "insertarSistemaEspecifico";
	public static final String PROP_INSERTAR_ELEMENTO_ESPECIFICO = "insertarElementoEspecifico";
	Logger LOG = LoggerFactory.getLogger(GuardarElemento.class);
	
	public GuardarElemento(ContenedorConexion contenedorConexion){
		miContenedorConexion= contenedorConexion;
		miConexion = miContenedorConexion.getConexion();// OJO se podria borrar		
		// Se crea el objeto del PropertyChangeSupport, para hacer esto necesitamos del bean (GuardarElemento), representado por this
		propertySupport = new PropertyChangeSupport(this);
	}
	
	
	/*
	 * Nos permite guardar los datos del Fabricante en la BBDD
	 * @param nombre, campo nombre
	 * @param web, campo web
	 * */
	
	public void insertarFabricante(String nombre, String web, UUID miIdFabricante){
		
		
		ParametrosGuardarElemento parametrosGuardarElemento = new ParametrosGuardarElemento();
		ParametrosGuardarElemento oldParametrosGuardarElemento = new ParametrosGuardarElemento();
		
		parametrosGuardarElemento.setIdElemento(miIdFabricante);//No es necesario introducir el idModelo en oldParametrosGuardarElemento, porque solo se usa parametrosGuardarElemento
		
		miConexion = miContenedorConexion.getConexion();
		
		try 
		{//Se puede insertar el fabricante
			int rc =0;			
			PreparedStatement ps =  miConexion.prepareStatement(insertarFabricante);
			ps.setString(1, nombre);//Inserta la var nombre en la ps
			ps.setString(2, web);//Inserta la var we en la ps
			ps.executeUpdate();
			
			parametrosGuardarElemento.setInsertadoElemento(1);
			oldParametrosGuardarElemento.setInsertadoElemento(0);
			
		}catch (Exception e) {
			// No se puede insertar el fabricante
			e.printStackTrace();
			parametrosGuardarElemento.setInsertadoElemento(0);
			oldParametrosGuardarElemento.setInsertadoElemento(1);
			parametrosGuardarElemento.setMensaje(MessageUtils.copyStringErrorMsg(e.getMessage()));
			LOG.error("Error al insertar un Fabricante ", e);
			
		}finally{			
			propertySupport.firePropertyChange(PROP_INSERTAR_FABRICANTE, oldParametrosGuardarElemento, parametrosGuardarElemento);
		}
		
	}

	/*
	 * Nos permite guardar los datos del modelo en la BBDD
	 * 
	 * @param idFabricante, campo idFabricante
	 * @param nombreModelo,campo nombreModelo 
	 * @param descripcion, campo descripcion 
	 * @param fechaFabricacion, campo fechaFabricacion 
	 * @param anyoLanzamiento, campo anyoLanzamiento 
	 * @param miIdModelo, id asociado al objeto que llama a insertarModelo 
	 *  
	 */
	
	public void insertarModelo(int idFabricante,String nombreModelo, String descripcion, Date fechaFabricacion, String anyoLanzamiento,  UUID miIdModelo){
	
		ParametrosGuardarElemento parametrosGuardarElemento = new ParametrosGuardarElemento();
		ParametrosGuardarElemento oldParametrosGuardarElemento = new ParametrosGuardarElemento();		
		parametrosGuardarElemento.setIdElemento(miIdModelo);//No es necesario introducir el idModelo en oldParametrosGuardarElemento, porque solo se usa parametrosGuardarElemento
		miConexion = miContenedorConexion.getConexion();
		try 
		{//Se puede insertar el modelo						
			PreparedStatement ps =  miConexion.prepareStatement(insertarModelo);
			//Se insertan las variables en la ps
			ps.setInt(1, idFabricante);
			ps.setString(2, nombreModelo);
			ps.setString(3, descripcion);
			ps.setDate(4, fechaFabricacion);
			ps.setString(5, anyoLanzamiento);			
			ps.executeUpdate();			
			parametrosGuardarElemento.setInsertadoElemento(1);
			oldParametrosGuardarElemento.setInsertadoElemento(0);
			
		}catch (Exception e) {
			// No se puede insertar el modelo
			e.printStackTrace();			
			parametrosGuardarElemento.setInsertadoElemento(0);
			oldParametrosGuardarElemento.setInsertadoElemento(1);
			parametrosGuardarElemento.setMensaje(MessageUtils.copyStringErrorMsg(e.getMessage()));
			LOG.error("Error al insertar un Modelo ", e);
		}finally{			
			propertySupport.firePropertyChange(PROP_INSERTAR_MODELO, oldParametrosGuardarElemento, parametrosGuardarElemento);
		}
		
	}

	/*
	 * Nos permite guardar los datos de la serie en la BBDD
	 * @param idModelo,campo idModelo 
	 * @param idFabricante, campo idFabricante
	 * @param numeroSerie, campo numeroSerie
	 * @param descripcion, campo descripcion 
	 * @param fechaFabricacion, campo fechaFabricacion 
	 * @param anyoLanzamiento, campo anyoLanzamiento 
	 * @param miIdSerie, id asociado al objeto que llama a insertarSerie 
	 *  
	 */
	
	public void insertarSerie(int idModelo, int idFabricante, String numeroSerie, String descripcion, Date fechaFabricacion, String anyoLanzamiento, UUID miIdSerie){
		ParametrosGuardarElemento parametrosGuardarElemento = new ParametrosGuardarElemento();
		ParametrosGuardarElemento oldParametrosGuardarElemento = new ParametrosGuardarElemento();		
		parametrosGuardarElemento.setIdElemento(miIdSerie);//No es necesario introducir el id en oldParametrosGuardarElemento, porque solo se usa parametrosGuardarElemento
		miConexion = miContenedorConexion.getConexion();
		try 
		{//Se puede insertar la serie
			PreparedStatement ps =  miConexion.prepareStatement(insertarSerie);
			//Se insertan las variables en la psinsertarSerie
			ps.setInt(1,idModelo);
			ps.setInt(2, idFabricante);
			ps.setString(3, numeroSerie);
			ps.setString(4, descripcion);
			ps.setDate(5, fechaFabricacion);
			ps.setString(6, anyoLanzamiento);			
			ps.executeUpdate();			
			parametrosGuardarElemento.setInsertadoElemento(1);
			oldParametrosGuardarElemento.setInsertadoElemento(0);			
		}catch (Exception e) {
			// No se puede insertar la serie
			e.printStackTrace();			
			parametrosGuardarElemento.setInsertadoElemento(0);
			oldParametrosGuardarElemento.setInsertadoElemento(1);
			parametrosGuardarElemento.setMensaje(MessageUtils.copyStringErrorMsg(e.getMessage()));
			LOG.error("Error al insertar una Serie", e);
		}finally{			
			propertySupport.firePropertyChange(PROP_INSERTAR_SERIE, oldParametrosGuardarElemento, parametrosGuardarElemento);
		}
		
	}

	/*
	 * Nos permite guardar los datos del manual en la BBDD
	 * @param idSerie,campo idModelo 
	 * @param idModelo,campo idModelo 
	 * @param idFabricante, campo idFabricante
	 * @param descripcion, campo descripcion 
	 * @param rutaFichero, campo ruta_fichero_pdf 
	 * @param miIdManual, id asociado al objeto que llama a insertarManual
	 *  
	 */
	

	public void insertarManual(int idSerie, int idModelo, int idFabricante, String descripcion,String rutaFichero , UUID miIdManual){
		ParametrosGuardarElemento parametrosGuardarElemento = new ParametrosGuardarElemento();
		ParametrosGuardarElemento oldParametrosGuardarElemento = new ParametrosGuardarElemento();		
		parametrosGuardarElemento.setIdElemento(miIdManual);//No es necesario introducir el id en oldParametrosGuardarElemento, porque solo se usa parametrosGuardarElemento
		miConexion = miContenedorConexion.getConexion();
		
		try 
		{//Se puede insertar el manual
						
			PreparedStatement ps =  miConexion.prepareStatement(insertarManual);
			//Se insertan las variables en la psinsertarSerie
			ps.setInt(1,idSerie);
			ps.setInt(2,idModelo);
			ps.setInt(3, idFabricante);
			ps.setString(4, descripcion);
			ps.setString(5, rutaFichero);
			
			ps.executeUpdate();
			
			parametrosGuardarElemento.setInsertadoElemento(1);
			oldParametrosGuardarElemento.setInsertadoElemento(0);
			
		}catch (Exception e) {
			// No se puede insertar el manual
			e.printStackTrace();			
			parametrosGuardarElemento.setInsertadoElemento(0);
			oldParametrosGuardarElemento.setInsertadoElemento(1);
			parametrosGuardarElemento.setMensaje(MessageUtils.copyStringErrorMsg(e.getMessage()));
			LOG.error("Error al insertar un Manual de Referencia ", e);
		}finally{			
			propertySupport.firePropertyChange(PROP_INSERTAR_MANUAL, oldParametrosGuardarElemento, parametrosGuardarElemento);
		}
		
	}
	
	

		/*
		 * Nos permite guardar los datos del estructura frame en la BBDD
		 * @param idSerie,campo idModelo 
		 * @param idModelo,campo idModelo 
		 * @param idFabricante, campo idFabricante
		 * @param numeroSubframes, campo N_subframes
		 * @param tiempoSubframe, tiempo_subframe
		 * @param palabrasSubframe, wps 
		 * @param bitsPalabra bits_pw
		 * @param descripcion, campo descripcion 
		 * @param rutaFichero, campo fichero_pdf 
		 * @param miIdEstructuraFrame, id asociado al objeto que llama a insertarManual
		 * 
		 */
		

		public void insertarEstructuraFrame(int idSerie, int idModelo,int  idFabricante,int numeroSubframes,double tiempoSubframe, int palabrasSegundo,int bitsPalabra,String descripcion,UUID miIdEstructuraFrame){

			
			ParametrosGuardarElemento parametrosGuardarElemento = new ParametrosGuardarElemento();
			ParametrosGuardarElemento oldParametrosGuardarElemento = new ParametrosGuardarElemento();
			
			parametrosGuardarElemento.setIdElemento(miIdEstructuraFrame);//No es necesario introducir el id en oldParametrosGuardarElemento, porque solo se usa parametrosGuardarElemento
			
			miConexion = miContenedorConexion.getConexion();
			
			try 
			{//Se puede insertar el manual
							
				PreparedStatement ps =  miConexion.prepareStatement(insertarEstructuraFrame);
				//Se insertan las variables en la psinsertarSerie
				ps.setInt(1,idSerie);
				ps.setInt(2,idModelo);
				ps.setInt(3,idFabricante);
				ps.setInt(4,numeroSubframes);
				ps.setDouble(5,tiempoSubframe);
				ps.setInt(6,palabrasSegundo);		
				ps.setInt(7,bitsPalabra);		
				ps.setString(8,descripcion);
				
				
				ps.executeUpdate();
				
				parametrosGuardarElemento.setInsertadoElemento(1);
				oldParametrosGuardarElemento.setInsertadoElemento(0);
				
			}catch (Exception e) {
				// No se puede insertar el manual
				e.printStackTrace();				
				parametrosGuardarElemento.setInsertadoElemento(0);
				oldParametrosGuardarElemento.setInsertadoElemento(1);
				parametrosGuardarElemento.setMensaje(MessageUtils.copyStringErrorMsg(e.getMessage()));
				LOG.error("Error al insertar una Estructura de Frame ", e);
			}finally{				
				propertySupport.firePropertyChange(PROP_INSERTAR_ESTRUCTURA_FRAME, oldParametrosGuardarElemento, parametrosGuardarElemento);
			}
			
		}


		/*
		 * Nos permite guardar los datos de un sistema especifico en la BBDD
		 * @param idSerie,campo idModelo 
		 * @param idModelo,campo idModelo 
		 * @param idFabricante, campo idFabricante
		 * @param nombre, campo nombre
		 * @param descripcion, campo descripcion 
		 * @param miIdSistemaEspecifico, id asociado al objeto que llama a insertarManual
		 *  
		 */
		

		public void insertarSistemaEspecifico(int idSerie, int idModelo,int  idFabricante,String nombre,String descripcion,UUID miIdSistemaEspecifico){

			
			ParametrosGuardarElemento parametrosGuardarElemento = new ParametrosGuardarElemento();
			ParametrosGuardarElemento oldParametrosGuardarElemento = new ParametrosGuardarElemento();
			
			parametrosGuardarElemento.setIdElemento(miIdSistemaEspecifico);//No es necesario introducir el id en oldParametrosGuardarElemento, porque solo se usa parametrosGuardarElemento
			
			miConexion = miContenedorConexion.getConexion();
			
			try 
			{//Se puede insertar el manual
							
				PreparedStatement ps =  miConexion.prepareStatement(insertarSistemaEspecifico);
				//Se insertan las variables en la ps 
				ps.setInt(1, idSerie);
				ps.setInt(2, idModelo);
				ps.setInt(3, idFabricante);
				ps.setString(4, nombre);	
				ps.setString(5,descripcion);
				
				
				ps.executeUpdate();
				
				parametrosGuardarElemento.setInsertadoElemento(1);
				oldParametrosGuardarElemento.setInsertadoElemento(0);
				
			}catch (Exception e) {
				// No se puede insertar el manual
				e.printStackTrace();				
				parametrosGuardarElemento.setInsertadoElemento(0);
				oldParametrosGuardarElemento.setInsertadoElemento(1);
				parametrosGuardarElemento.setMensaje(MessageUtils.copyStringErrorMsg(e.getMessage()));
				LOG.error("Error al insertar un Sistema Específico ", e);
			}finally{
				
				propertySupport.firePropertyChange(PROP_INSERTAR_SISTEMA_ESPECIFICO, oldParametrosGuardarElemento, parametrosGuardarElemento);
			}
			
		}

		
		
		
		/*
		 * Nos permite guardar los datos de un sistema especifico en la BBDD
		 * @param idSistema,campo idSistema 
		 * @param idSerie,campo idSerie 
		 * @param idModelo,campo idModelo 
		 * @param idFabricante, campo idFabricante
		 * @param nombre, campo nombre
		 * @param interesting, campo interesting
		 * @param subframes, campo subframes
		 * @param palabras, campo palabras
		 * @param bits, campo bits
		 * @param nAtributos, campo n_atributos
		 * @param miIdSistemaEspecifico, id asociado al objeto que llama a insertarManual
		 *  
		 */
		

		public void insertarElementoEspecifico(int idSistema, int idSerie, int idModelo,int  idFabricante,String nombre, boolean interesting, String subframes, String palabras, String bits,int nAtributos,ArrayList<ContenedorVisualAtributoElemento> listAtributoEspecifico, UUID miIdElementoEspecifico){

			int idElementoSistema;
			Iterator it;
			ParametrosGuardarElemento parametrosGuardarElemento = new ParametrosGuardarElemento();
			ParametrosGuardarElemento oldParametrosGuardarElemento = new ParametrosGuardarElemento();
			parametrosGuardarElemento.setIdElemento(miIdElementoEspecifico);//No es necesario introducir el id en oldParametrosGuardarElemento, porque solo se usa parametrosGuardarElemento
			miConexion = miContenedorConexion.getConexion();
			int flag1=0;
			int flag2=0;
			try {//Se puede insertar el manual
				PreparedStatement ps =  miConexion.prepareStatement(insertarElementoEspecifico);
				//Se insertan las variables en la ps 				
				ps.setInt(1, idSistema);
				ps.setInt(2, idSerie);
				ps.setInt(3, idModelo);
				ps.setInt(4, idFabricante);
				ps.setString(5, nombre);
				ps.setBoolean(6, interesting);
				ps.setString(7,subframes);
				ps.setString(8, palabras);
				ps.setString(9, bits);
				ps.setInt(10, nAtributos);
				ps.executeUpdate();				
				//Obtenemos el id del ultimo Elemento Especifico insertado
				Statement s = miConexion.createStatement(); 		         
	         	ResultSet rs = s.executeQuery (idUltimoInsertElementoEspecifico);	         	
	         	rs.next();
	         	idElementoSistema = rs.getInt(1);	
	         	flag1=1;
	         	//Procedemos a la insercion de los atributos de este elemento 
				it = listAtributoEspecifico.iterator();
				for (int i=0 ; i< nAtributos; i++){					
					ContenedorVisualAtributoElemento contenedorAtributo = (ContenedorVisualAtributoElemento) it.next();					
					PreparedStatement ps1 =  miConexion.prepareStatement(insertarEstructuraAtributosElemento);										
					//Se insertan las variables en la ps 							
					ps1.setInt(1, idElementoSistema);
					ps1.setInt(2, idSistema);
					ps1.setInt(3, idSerie);
					ps1.setInt(4, idModelo);
					ps1.setInt(5, idFabricante);
					ps1.setString(6, contenedorAtributo.getStrNombreAtributo());	
					ps1.setString(7, contenedorAtributo.getStrCbTipo());
					ps1.setBoolean(8, contenedorAtributo.isVisible());
					ps1.executeUpdate();
				}
				flag2=1;
				parametrosGuardarElemento.setInsertadoElemento(1);
				oldParametrosGuardarElemento.setInsertadoElemento(0);
				
			}catch (Exception e) {
				// No se puede insertar el manual
				e.printStackTrace();				
				parametrosGuardarElemento.setInsertadoElemento(0);
				oldParametrosGuardarElemento.setInsertadoElemento(1);
				String mensaje = null;
				if (flag1==0){
					mensaje = "Error al insertar un Elemento Específico. ";
					LOG.error(mensaje, e);
					parametrosGuardarElemento.setMensaje(mensaje+MessageUtils.copyStringErrorMsg(e.getMessage()));
				}else if(flag2==0){
					mensaje = "Error al insertar una Estructura de Atributo de un Elemento Específico. ";
					LOG.error(mensaje, e);
					parametrosGuardarElemento.setMensaje(mensaje+MessageUtils.copyStringErrorMsg(e.getMessage()));
					
				}				
			}finally{				
				propertySupport.firePropertyChange(PROP_INSERTAR_ELEMENTO_ESPECIFICO, oldParametrosGuardarElemento, parametrosGuardarElemento);
			}			
		}

		
    /*Añade un Listener al elemento atado de esta clase*/
    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertySupport.addPropertyChangeListener(listener);
    }
    
	public void addPropertyChangeListener(String prop, PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(prop,listener);
	}

 
    /*Remueve un Listener al elemento atado de esta clase*/
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

   public void removePropertyChangeListener(String prop, PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(prop, listener);
    }


}
