package libreria;

import java.util.UUID;

public class ParametrosConexionBBDD {
	
	private int usarBBDD = 0;	
	private int hayTablas = 0;
	private UUID idElemento;//Id del elemento que consulta la bbdd

	//GET
	
	public UUID getIdElemento() {
		return idElemento;
	}

	public int getUsarBBDD()
	{
		return usarBBDD;
	}

	public int getHayTablas()
	{
		return hayTablas;
	}
	
	//SET
	public void setUsarBBDD(int usarBBDD)
	{
		this.usarBBDD = usarBBDD;
	}
	
	public void setHayTablas(int hayTablas)
	{
		this.hayTablas = hayTablas;
	}
	
	public void setIdElemento(UUID idElemento) {
		this.idElemento = idElemento;
	}
	
	
}
