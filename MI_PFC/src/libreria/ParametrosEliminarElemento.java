
package libreria;

import java.util.UUID;

public class ParametrosEliminarElemento  {
 

	private int eliminadoElemento= 0;//flag que nos indica si el elemento fue insertado o no en la tabla                       
    private UUID idElemento;//Id del elemento que consulta el ComboBox
    private String mensaje;
       
    
	public void setEliminadoElemento(int valor){		
		eliminadoElemento = valor;
	} 	
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getEliminadoElemento(){
		return eliminadoElemento;
	}
	
	//IdElemento	
	public void setIdElemento(UUID id){		
		idElemento= id;
	}
	
	
	
	public UUID getIdElemento(){		
		return idElemento;
	}
	

}
