package controller;

import java.util.List;
import java.util.UUID;
import model.BorrarElemento;

public class BajaController {

	private BorrarElemento miBorrarElemento;
	
	public BajaController(BorrarElemento borrarElemento){
		
		miBorrarElemento = borrarElemento;
	}
	
	
	
	public void eliminarFabricante(List<Object> listaIdFabricante, UUID idObjetoFabricante){
			miBorrarElemento.eliminarSeleccionFabricante(listaIdFabricante, idObjetoFabricante);
	}			
	
	public void eliminarModelo(List <Object> listaIdModeloSeleccionado, UUID idObjetoModelo){
		
		miBorrarElemento.eliminarSeleccionModelo(listaIdModeloSeleccionado, idObjetoModelo);

	}
	
	public void eliminarSerie(List <Object> listaIdSerie, UUID idObjetoSerie){
	
		miBorrarElemento.eliminarSeleccionSerie(listaIdSerie, idObjetoSerie);

	}
	
	public void eliminarManual(List <Object> listaIdManual, UUID idObjetoManual){
		
		miBorrarElemento.eliminarSeleccionManual(listaIdManual, idObjetoManual);

	}

	public void eliminarEstructuraFrame(List <Object> listaIdEstructuraFrame, UUID idObjetoEstructuraFrame){
		
		miBorrarElemento.eliminarSeleccionEstructuraFrame(listaIdEstructuraFrame, idObjetoEstructuraFrame);
			
	}

	public void eliminarSistemaEspecifico(List <Object> listaIdSistemaEspecifico, UUID idObjetoSistemaEspecifico){
		

		miBorrarElemento.eliminarSeleccionSistemaEspecifico(listaIdSistemaEspecifico, idObjetoSistemaEspecifico);
		
	}

	public void eliminarElementoEspecifico(List <Object> listaIdElementoEspecifico, UUID idObjetoElementoEspecifico){

		miBorrarElemento.eliminarSeleccionElementoEspecifico(listaIdElementoEspecifico, idObjetoElementoEspecifico);		

	}

	public void eliminarEstructuraAtributo(List<Object> listaIdEstructuraAtributos, UUID idEstructura, int idElementoEspecificoSeleccionado, int counterVisibleRowTrue){

		miBorrarElemento.eliminarSeleccionEstructuraAtributo( listaIdEstructuraAtributos, idEstructura, idElementoEspecificoSeleccionado, counterVisibleRowTrue);		

	}
	
	public BorrarElemento getBorrarElemento(){
		
		return(miBorrarElemento);
	}



	
	
	
}
