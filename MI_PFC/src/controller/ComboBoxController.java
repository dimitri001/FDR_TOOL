package controller;

import java.util.UUID;

import model.ConsultasComboBoxBBDD;

public class ComboBoxController {

	
	private ConsultasComboBoxBBDD miConsultasComboBoxBBDD;
	
	
	public ComboBoxController (ConsultasComboBoxBBDD consultasComboBoxBBDD){
		
		miConsultasComboBoxBBDD = consultasComboBoxBBDD;
		
	}
	
	public void comboBoxFabricante(UUID idObjeto){
		
		miConsultasComboBoxBBDD.consultaFabricante(idObjeto);
		
	}


	public void comboBoxModelo(int idFabricante, UUID idObjeto){
		
		miConsultasComboBoxBBDD.consultaModelo(idFabricante, idObjeto);
		
	}

	public void comboBoxSerie(int idFabricante, int idModelo, UUID idObjeto){
		
		miConsultasComboBoxBBDD.consultaSerie(idFabricante, idModelo, idObjeto);
		
	}


	public void comboBoxSistema(int idFabricante, int idModelo, int idSerie, UUID idObjeto){
		
		miConsultasComboBoxBBDD.consultaSistema(idFabricante, idModelo, idSerie, idObjeto);
		
	}

	public ConsultasComboBoxBBDD getConsultasComboBoxBBDD(){
		
		return(miConsultasComboBoxBBDD);
	}

}
