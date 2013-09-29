package libreria;



public class SistemaEspecifico {

	private int idSistemaEspecifico;
	private int idSerie;
	private int idModelo;
	private int idFabricante;
	private String nombre;
	private String descripcion;
	private Object [] arraySistemaEspecifico;
	
	public SistemaEspecifico ( int idSistemaEspecifico, int idSerie, int idModelo, int idFabricante, String nombre, String descripcion)
	{
		this.idSistemaEspecifico = idSistemaEspecifico;
		this.idSerie = idSerie;
		this.idModelo = idModelo;
		this.idFabricante = idFabricante;
		this.nombre = nombre;
		this.descripcion = descripcion;

		//arraySistemaEspecifico= {this.idSistemaEspecifico, this.idSerie, this.idModelo, this.idFabricante , this.nombre, this.descripcion };
		Object [] aux = {this.idSistemaEspecifico, this.idSerie, this.idModelo, this.idFabricante , this.nombre, this.descripcion };
		arraySistemaEspecifico = aux;
	}
	
	
	//GET
	public int getIdSistemaEspecifico()
	{
		return idSistemaEspecifico;
	}
	
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

	public String getNombre()
	{
		return nombre;
	}
		
	public String getDescripcion()
	{
		return descripcion;
	}
	
	public Object [] getArray(){
		
		return arraySistemaEspecifico;

	}
			
	
	//SET	
	public void setIdSistemaEspecifico(int idSistemaEspecifico)
	{
		this.idSistemaEspecifico = idSistemaEspecifico;
	}
	
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
	
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setArray( Object [] newArray){
		
		arraySistemaEspecifico = newArray;
		
	}
	
}
