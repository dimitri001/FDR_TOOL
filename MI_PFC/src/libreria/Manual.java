package libreria;



public class Manual {

	private int idManual;
	private int idSerie;
	private int idModelo;
	private int idFabricante;
	private String descripcion;
	private String rutaFicheroPdf;
	private Object [] arrayManual;
	
	public Manual ( int idManual, int idSerie, int idModelo, int idFabricante, String descripcion,String ficheroPdf)
	{
		this.idManual = idManual;
		this.idSerie = idSerie;
		this.idModelo = idModelo;
		this.idFabricante = idFabricante;
		this.descripcion = descripcion;
		this.rutaFicheroPdf = ficheroPdf;
		
		//arrayManual = {this.idManual, this.idSerie, this.idModelo, this.idFabricante , this.descripcion, this.fichero_pdf};
		Object [] aux = {this.idManual, this.idSerie, this.idModelo, this.idFabricante , this.descripcion, this.rutaFicheroPdf};
		arrayManual = aux;
	}
	
	
	//GET
	public int getIdManual()
	{
		return idManual;
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
	

	public String getDescripcion()
	{
		return descripcion;
	}
	
	
	public String getRutaFicheroPdf()
	{
		return rutaFicheroPdf;
	}

	public Object [] getArray(){
		
		return arrayManual;

	}
	
	//SET	
	public void setIdManual(int idManual)
	{
		this.idManual = idManual;
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
	
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setRutaFicheroPdf(String fichero_pdf)
	{
		this.rutaFicheroPdf = fichero_pdf;
	}

	public void setArray( Object [] newArray){
		
		arrayManual = newArray;
		
	}
	
}
