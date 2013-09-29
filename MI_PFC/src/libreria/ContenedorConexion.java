package libreria;

import java.sql.Connection;

public class ContenedorConexion {

	private Connection miConexion;
	
	public void setConexion(Connection conexion){
		
		miConexion = conexion;
		
	}
	
	public Connection getConexion(){
		
		return(miConexion);
	}
	
}
