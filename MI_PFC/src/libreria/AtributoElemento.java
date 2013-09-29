package libreria;



public class AtributoElemento {

	private int idAtributoElemento;
	private int idElementosSistemaEspecifico;
	private int idSistemaEspecifico;
	private int idSerie;
	private int idModelo;
	private int idFabricante;
	private String nombreAtributo;
	private String tipoAtributo;
	private boolean visible;	
	private Object [] arrayEstructuraAtributos;
	
	public AtributoElemento ( int idEstructuraAtributosElemento,int idElementosSistemaEspecifico,int idSistemaEspecifico, int idSerie, int idModelo, int idFabricante, String nombreAtributo, String tipoAtributo, Boolean visible)
	{

		this.idAtributoElemento = idEstructuraAtributosElemento;
		this.idElementosSistemaEspecifico = idElementosSistemaEspecifico;
		this.idSistemaEspecifico = idSistemaEspecifico;
		this.idSerie = idSerie;
		this.idModelo = idModelo;
		this.idFabricante = idFabricante;
		this.nombreAtributo = nombreAtributo;		
		this.tipoAtributo = tipoAtributo;
		this.visible = visible;
					
		//arraySistemaEspecifico= {this.idSistemaEspecifico, this.idSerie, this.idModelo, this.idFabricante , this.nombre, this.descripcion };
		Object [] aux = {this.idAtributoElemento,this.idElementosSistemaEspecifico, this.idSistemaEspecifico, this.idSerie, this.idModelo, this.idFabricante , this.nombreAtributo, this.tipoAtributo, this.visible };
		arrayEstructuraAtributos = aux;

	}
	
	
	//GET
	public int getIdAtributoElemento()
	{
		return idAtributoElemento;
	}
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

	public String getNombreAtributo()
	{
		return nombreAtributo;
	}
	
	public String getTipoAtributo()
	{
		return tipoAtributo;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public Object[] getArray() {
		return arrayEstructuraAtributos;
	}

			
	//SET	
	
	public void setIdAtributoElemento(int idEstructuraAtributosElemento)
	{
		this.idAtributoElemento = idEstructuraAtributosElemento;
	}
	
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
	
	public void setNombreAtributo(String nombreAtributo)
	{
		this.nombreAtributo = nombreAtributo;
	}
	
	
	public void setTipoAtributo(String tipoAtributo)
	{
		this.tipoAtributo = tipoAtributo;
	}

	public void setArrayEstructuraAtributos(Object[] newArray) {
			this.arrayEstructuraAtributos = newArray;
		}
	 
	 public void setVisible(boolean visible) {
			this.visible = visible;
		}
	
}
