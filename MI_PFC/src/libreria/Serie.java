package libreria;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Serie {

	private int idSerie;
	private int idModelo;
	private int idFabricante;
	private String numeroSerie;
	private String descripcion;
	private Date fechaFabricacion = null;
	private String anyoLanzamiento;
	private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
	private Object [] arraySerie;
	
	public Serie ( int idSerie, int idModelo, int idFabricante, String numeroSerie, String descripcion, String strFechaFabricacion, String anyoLanzamiento)
	{
		this.idSerie = idSerie;
		this.idModelo = idModelo;
		this.idFabricante = idFabricante;
		this.numeroSerie = numeroSerie;
		this.descripcion = descripcion;

		try{
			this.fechaFabricacion = new Date(formatoDelTexto.parse(strFechaFabricacion).getTime());// Obtiene util.Date y lo transforma a sql.Date con getTime()
			} catch (ParseException ex){
				ex.printStackTrace();
			}
			
	    this.anyoLanzamiento = anyoLanzamiento;
	    
		//arraySerie = {this.idSerie, this.idModelo, this.idFabricante , this.numeroSerie, this.descripcion, this.fechaFabricacion, this.anyoLanzamiento};
		Object [] aux = {this.idSerie, this.idModelo, this.idFabricante , this.numeroSerie, this.descripcion, this.fechaFabricacion, this.anyoLanzamiento};
		arraySerie = aux;
		
	}
	
	
	//GET
	
	public int getIdSerie()
	{
		return idSerie;
	}
	
	public int getIdModelo()
	{
		return idModelo;
	}
	
	public int getIdFabricante()
	{
		return idFabricante;
	}
	
	public String getNumeroSerie()
	{
		return numeroSerie;
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
		
		return arraySerie;

	}	

		
	
	//SET	
	
	public void setIdSerie(int idSerie)
	{
		this.idSerie = idSerie;
	}
	
	public void setIdModelo(int idModelo)
	{
		this.idModelo = idModelo;
	}
	
	public void setIdFabricante(int idFabricante)
	{
		this.idFabricante = idFabricante;
	}
	
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setNumeroSerie(String numeroSerie)
	{
		this.numeroSerie = numeroSerie;
	}
	
	public void setFechaFabricacionString(String strFechaFabricacion)
	{
		
		
		try{
			this.fechaFabricacion = new Date(formatoDelTexto.parse(strFechaFabricacion).getTime());// Obtiene util.Date y lo transforma a sql.Date con getTime()
			arraySerie[5]= fechaFabricacion;
		} catch (ParseException ex){
			ex.printStackTrace();
		}
	}
	
	public void setFechaFabricacionDate(Date dtFechaFabricacion)
	{
		arraySerie[5]= fechaFabricacion;
		fechaFabricacion = dtFechaFabricacion;
	}
		
	public void setAnyoLanzamiento(String anyoLanzamiento)
	{
		this.anyoLanzamiento = anyoLanzamiento;
		
	}
	
	public void setArray( Object [] newArray){
		
		arraySerie = newArray;
		
	}
}
