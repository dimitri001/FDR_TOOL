package libreria;

import java.util.UUID;

public class ParametrosGuardarElemento {
 
	private int insertadoElemento= 0;//flag que nos indica si el elemento fue insertado o no en la tabla                       
    private UUID idElemento;//Id del elemento que consulta el ComboBox
    private String mensaje;//Mensaje de error
    
    //mensaje
    public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	//IdElemento	
	public void setIdElemento(UUID id){
		
		idElemento= id;
	}
	
	public UUID getIdElemento(){		
		return idElemento;
	}
	//insertadoElemento       
	public void setInsertadoElemento(int valor){		
		insertadoElemento = valor;
	} 
	
	
	public int getInsertadoElemento(){
		return insertadoElemento;
	}
	

	

}
