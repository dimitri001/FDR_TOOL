package libreria;

import java.util.UUID;
import java.util.Vector;

public class ParametrosConsultasComboBoxBBDD {

    private Vector<ElementoComboBox> dataElemento = new Vector<ElementoComboBox>();//Vector que contiene los elementos del ComboBox, cada elemento tiene un id y un nombre                          
    private UUID idElemento;//Id del elemento que consulta el ComboBox

	public void setDataModelo(Vector<ElementoComboBox> newDataElemento){
		
		dataElemento = newDataElemento;
		
	} 
	
	public Vector<ElementoComboBox> getDataElemento(){
		
		return dataElemento;
		
	} 
	
	
	
	public void setIdElemento(UUID id){
		
		idElemento= id;
	}
	
	
	
	public UUID getIdElemento(){
		
		return idElemento;
	}
	


}
