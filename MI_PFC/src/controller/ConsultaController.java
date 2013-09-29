package controller;

import java.util.UUID;

import model.ConsultasBBDD;
import model.ConsultasComboBoxBBDD;

public class ConsultaController {

	
	private ConsultasBBDD miConsultasBBDD;
	

	public ConsultaController(ConsultasBBDD consultasBBDD){
		
		miConsultasBBDD = consultasBBDD;
		
	}
	
	

	public void listaFabricante(UUID idObjeto, boolean actualizar){
		
		miConsultasBBDD.consultaListaFabricante(idObjeto, actualizar);
		
	}
	
	public void listaModelo(int idFabricante,UUID idObjeto, boolean actualizar){
		
		miConsultasBBDD.consultaListaModelo(idFabricante, idObjeto, actualizar);
		
	}
	
	public void listaSerie(int idFabricante,int idModelo, UUID idObjeto, boolean actualizar){
		
		miConsultasBBDD.consultaListaSerie(idFabricante,idModelo, idObjeto, actualizar);
		
	}
	
	public void listaManual(int idFabricante,int idModelo,int idSerie, UUID idObjeto, boolean actualizar){
		
		miConsultasBBDD.consultaListaManual(idFabricante,idModelo,idSerie, idObjeto, actualizar);
		
	}

	public void listaEstructuraFrame(int idFabricante,int idModelo,int idSerie, UUID idObjeto, boolean actualizar){
		
		miConsultasBBDD.consultaListaEstructuraFrame(idFabricante,idModelo,idSerie, idObjeto, actualizar);
		
	}

	public void listaSistemaEspecifico(int idFabricante,int idModelo,int idSerie, UUID idObjeto, boolean actualizar){
		
		miConsultasBBDD.consultaListaSistemaEspecifico(idFabricante,idModelo,idSerie, idObjeto, actualizar);
		
	}
	
	public void listaElementoEspecifico(int idFabricante,int idModelo,int idSerie,int idSistema, UUID idObjeto, boolean actualizar){
		
		miConsultasBBDD.consultaListaElementoEspecifico(idFabricante,idModelo,idSerie,idSistema, idObjeto, actualizar);
		
	}
	
	public void listaEstructuraAtributos( int idElemento, UUID idObjeto, boolean actualizar){
		
		miConsultasBBDD.consultaListaEstructuraAtributos(idElemento, idObjeto, actualizar);
		
	}
	public ConsultasBBDD getConsultasBBDD(){
		
		return(miConsultasBBDD);
	}


}
