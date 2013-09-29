package libreria;



public class ElementosSistemaEspecifico {

	private int idElementosSistemaEspecifico;
	private int idSistemaEspecifico;
	private int idSerie;
	private int idModelo;
	private int idFabricante;
	private String nombreElemento;
	private Boolean interesting;
	private String subFrames;
	private String palabras;
	private String bits;
	private int nAtributos;
	private Object [] arrayElementoEspecifico;
	
	public ElementosSistemaEspecifico ( int idElementosSistemaEspecifico,int idSistemaEspecifico, int idSerie, int idModelo, int idFabricante, String nombreElemento, Boolean interesting, String subFrames, String palabras, String bits, int nAtributos)
	{
		this.idElementosSistemaEspecifico = idElementosSistemaEspecifico;
		this.idSistemaEspecifico = idSistemaEspecifico;
		this.idSerie = idSerie;
		this.idModelo = idModelo;
		this.idFabricante = idFabricante;
		this.nombreElemento = nombreElemento;
		this.interesting = interesting;
		this.subFrames = subFrames;
		this.palabras = palabras;
		this.bits = bits;
		this.nAtributos = nAtributos;		
		//arrayElementoEspecifico = {this.idSistemaEspecifico, this.idSerie, this.idModelo, this.idFabricante , this.nombre, this.descripcion };
		Object [] aux = {this.idElementosSistemaEspecifico, this.idSistemaEspecifico, this.idSerie, this.idModelo, this.idFabricante , this.nombreElemento, this.interesting, this.subFrames, this.palabras, this.bits, this.nAtributos };
		arrayElementoEspecifico = aux;		
	}
	
	
	//GET
	public int getIdElementosSistemaEspecifico()
	{
		return idElementosSistemaEspecifico;
	}
	
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

	public String getNombreElemento()
	{
		return nombreElemento;
	}
	
	public Boolean getInteresting()
	{
		return interesting;
	}

	public String getSubFrames()
	{
		return subFrames;
	}

	public String getPalabras()
	{
		return palabras;
	}

	public String getBits()
	{
		return bits;
	}

	public int getNAtributos()
	{
		return nAtributos;
	}
		
	public Object [] getArray(){
		
		return arrayElementoEspecifico;

	}
			
	
	//SET	
	public void setIdElementosSistemaEspecifico(int idElementosSistemaEspecifico)
	{
		this.idElementosSistemaEspecifico = idElementosSistemaEspecifico;
	}
	
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
	
	public void setNombreElemento(String nombreElemento)
	{
		this.nombreElemento = nombreElemento;
	}
	
	public void setInteresting(Boolean interesting)
	{
		this.interesting = interesting;
	}
	
	public void setSubFrames(String subFrames)
	{
		this.subFrames = subFrames;
	}

	
	public void setPalabras(String palabras)
	{
		this.palabras = palabras;
	}
	
	public void setBits(String bits)
	{
		this.bits = bits;
	}
	
	public void setNAtributos(int nAtributos)
	{
		this.nAtributos = nAtributos;
	}

	public void setArray( Object [] newArray){
		
		arrayElementoEspecifico = newArray;
		
	}
	
}
