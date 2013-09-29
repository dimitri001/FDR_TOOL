package libreria;

import java.util.UUID;

public class ParametrosActualizarElemento {

	private int insertadoElemento= 0;//flag que nos indica si el elemento fue actualizado o no en la tabla                       
    private UUID idElemento;//Id del elemento que realiza la actualizacion
    private String mensaje;//Mensaje de error
    
    //mensaje
    public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    //Insertar    
    
	public void setActualizadoElemento(int valor){
		
		insertadoElemento = valor;
	} 
	
	
	public int getActualizadoElemento(){
		return insertadoElemento;
	}
	

	//IdElemento	
	public void setIdElemento(UUID id){
		
		idElemento= id;
	}
	
	
	
	public UUID getIdElemento(){
		
		return idElemento;
	}
	

}
