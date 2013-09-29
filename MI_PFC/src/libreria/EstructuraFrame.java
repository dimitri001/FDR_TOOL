package libreria;



public class EstructuraFrame {

	private int idEstructuraFrame;
	private int idSerie;
	private int idModelo;
	private int idFabricante;
	private int nSubframes;
	private double tiempoSubframe;
	private int wps;
	private int bitsPW;
	private String descripcion;
	private Object [] arrayEstructuraFrame;
	
	
	public EstructuraFrame ( int idEstructuraFrame, int idSerie, int idModelo, int idFabricante, int nSubframes,double tiempoSubframe, int wps, int bitsPW,String descripcion)
	{
		this.idEstructuraFrame = idEstructuraFrame;
		this.idSerie = idSerie;
		this.idModelo = idModelo;
		this.idFabricante = idFabricante;
		this.nSubframes = nSubframes;
		this.tiempoSubframe = tiempoSubframe;
		this.wps = wps;
		this.bitsPW = bitsPW;
		this.descripcion = descripcion;
		
		//arrayEstructuraFrame = {this.idEstructuraFrame, this.idSerie, this.idModelo, this.idFabricante , this.nSubframes, this.tiempoSubframe, this.wps, this.bitsPW, this.descripcion };
		Object [] aux = {this.idEstructuraFrame, this.idSerie, this.idModelo, this.idFabricante , this.nSubframes, this.tiempoSubframe, this.wps, this.bitsPW, this.descripcion };
		arrayEstructuraFrame = aux;
		
	}
	
	
	//GET
	public int getIdEstructuraFrame()
	{
		return idEstructuraFrame;
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

	public int getNSubframes()
	{
		return nSubframes;
	}
	
	public double getTiempoSubframe()
	{
		return tiempoSubframe;
	}
	
	public int getWPS()
	{
		return wps;
	}
	
	public int getBitsPW()
	{
		return bitsPW;
	}
	
	public String getDescripcion()
	{
		return descripcion;
	}	
			
	public Object [] getArray(){
		
		return arrayEstructuraFrame;

	}
	
	
	//SET	
	public void setIdEstructuraFrame(int idEstructuraFrame)
	{
		this.idEstructuraFrame = idEstructuraFrame;
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
	
	public void setNSubframes(int nSubframes)
	{
		this.nSubframes = nSubframes;
	}
	
	public void setTiempoSubframe(double tiempoSubframe)
	{
		this.tiempoSubframe = tiempoSubframe;
	}
	
	public void setWPS(int wps)
	{
		this.wps = wps;
	}

	public void setbitsPW(int bitsPW)
	{
		this.bitsPW = bitsPW;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setArray( Object [] newArray){
		
		arrayEstructuraFrame = newArray;
		
	}
}
