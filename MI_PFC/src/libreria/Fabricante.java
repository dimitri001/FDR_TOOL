package libreria;

public class Fabricante {
	
	private int idFabricante;
	private String nombre;
	private String web;
	
	private Object [] arrayFabricante;

	public Fabricante (int idFabricante, String nombre, String web)
	{
		this.idFabricante = idFabricante;
		this.nombre = nombre;
		this.web = web;
		
		//arrayFabricante = {this.idFabricante , this.nombre, this.web};
		
		Object [] aux = {this.idFabricante , this.nombre, this.web};
		arrayFabricante = aux;

	}
	
	//GET
	public int getIdFabricante()
	{
		return idFabricante;
	}
	
	public String getNombre()
	{
		return nombre;
	}

	
	public String getWeb()
	{
		return web;
	}
	
	
	
	public Object [] getArray(){
		
		return arrayFabricante;
		
		
	}
	
	
	//SET
	public void setIdFabricante(int idFabricante)
	{
		this.idFabricante = idFabricante;
	}
	
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	
	public void setWeb(String web)
	{
		this.web = web;
	}
	
	
	
	public void setArray( Object [] newArrayFabricante){
		
		arrayFabricante = newArrayFabricante;
		
	}
	
}
