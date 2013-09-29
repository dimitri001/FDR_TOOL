package libreria;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParametrosListaBBDD {

    private List<Object> dataListaBBDD;// = new ArrayList();//Vector que contiene los elementos de una tabla, cada elemento tiene un id y un nombre                          
    private UUID idObjeto;//Id del elemento que consulta el ComboBox
    private boolean actualizar;	

	public void setActualizar(boolean actualizar) {
		this.actualizar = actualizar;
	}
	
	public boolean isActualizar() {
		return actualizar;
	}

	public void setDataListaBBDD(ArrayList<Object > newDataListaBBDD){
		
		dataListaBBDD = newDataListaBBDD;
		
	} 
	
	public ArrayList<Object > getDataListaBBDD(){
		

		return (ArrayList<Object >) dataListaBBDD;
		
	} 
	
	
	
	public void setIdElemento(UUID id){
		
		idObjeto= id;
	}
	
	
	
	public UUID getIdElemento(){
		
		return idObjeto;
	}
	

	
}
