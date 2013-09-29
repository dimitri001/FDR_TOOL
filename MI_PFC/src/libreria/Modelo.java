package libreria;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;


//Ejemplo creacion de fecha 	    java.sql.Date date = new Date(104, 4, 22);

public class Modelo {

	private int idModelo;
	private int idFabricante;
	private String nombre;
	private String descripcion;
	private Date fechaFabricacion = null;
	private String anyoLanzamiento;
	private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
	private Object [] arrayModelo;

//	private	DateFormat dfDateMedium = DateFormat.getDateInstance(DateFormat.MEDIUM);

	public Modelo (int idModelo,int idFabricante, String nombre, String descripcion, String strFechaFabricacion, String anyoLanzamiento)
	{
		this.idModelo = idModelo;
		this.idFabricante = idFabricante;
		this.nombre = nombre;
		this.descripcion = descripcion;
		
		try{
			this.fechaFabricacion = new Date(formatoDelTexto.parse(strFechaFabricacion).getTime());// Obtiene util.Date y lo transforma a sql.Date con getTime()
			} catch (ParseException ex){
				ex.printStackTrace();
			}
			
	    this.anyoLanzamiento = anyoLanzamiento;

		//arrayModelo = {this.idModelo,this.idFabricante , this.nombre, this.descripcion, this.fechaFabricacion, this.anyoLanzamiento};
		Object [] aux = {this.idModelo, this.idFabricante , this.nombre, this.descripcion, this.fechaFabricacion, this.anyoLanzamiento};
		arrayModelo = aux;
	
	}
	
	
	//GET

	public int getIdModelo()
	{
		return idModelo;
	}

	public int getIdFabricante()
	{
		return idFabricante;
	}
	
	public String getNombre()
	{
		return nombre;
	}

	
	public String getDescripcion()
	{
		return descripcion;
	}
	
	
	public String getFechaFabricacionString()
	{
		 String strFechaFabricacion = formatoDelTexto.format(this.fechaFabricacion);//dfDateMedium.format(this.fechaFabricacion);
		
		return strFechaFabricacion;
	}
	
	public Date getFechaFabricacionDate()
	{
		
		return fechaFabricacion;
	}
	
	public String getAnyoLanzamiento()
	{
		return anyoLanzamiento;
	}
	
	
	public Object [] getArray(){
		
		return arrayModelo;

	}	
		
	
	//SET	

	public void setIdModelo(int idModelo)
	{
		arrayModelo[0]= idModelo;
		this.idModelo = idModelo;
	}

	public void setIdFabricante(int idFabricante)
	{ 
		arrayModelo[1]= idFabricante;
		this.idFabricante = idFabricante;
	}
	
	public void setNombre(String nombre)
	{
		arrayModelo[2]= nombre;
		this.nombre = nombre;
	}

	
	public void setDescripcion(String descripcion)
	{
		arrayModelo[3]= descripcion;
		this.descripcion = descripcion;
	}

		
	public void setFechaFabricacionString(String strFechaFabricacion)
	{
		
		
		try{
			this.fechaFabricacion = new Date(formatoDelTexto.parse(strFechaFabricacion).getTime());// Obtiene util.Date y lo transforma a sql.Date con getTime()
			arrayModelo[4]= fechaFabricacion;
		} catch (ParseException ex){
			ex.printStackTrace();
		}
	}
	
	public void setFechaFabricacionDate(Date dtFechaFabricacion)
	{
		arrayModelo[4]= fechaFabricacion;
		fechaFabricacion = dtFechaFabricacion;
	}
	
	public void setAnyoLanzamiento(String anyoLanzamiento)
	{
		arrayModelo [5] =  anyoLanzamiento;
		this.anyoLanzamiento = anyoLanzamiento;
		
	}
	
	
	public void setArray( Object [] newArray){
		
		arrayModelo = newArray;
		
	}

}
