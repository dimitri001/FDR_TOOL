package controller;

import java.util.List;
import java.util.UUID;


import libreria.*;
import model.ActualizarElemento;

public class VerModificarController {

private ActualizarElemento miActualizarElemento;
	
	public VerModificarController (ActualizarElemento actualizarElemento){
		
		miActualizarElemento = actualizarElemento;
	}
	
	
	public void modificarFabricante(Fabricante fabricante, UUID idObjetoModificar){
		
		miActualizarElemento.actualizarFabricante(fabricante, idObjetoModificar);

	}
	
	public void modificarModelo(Modelo modelo, UUID idObjetoModificar){
		
		miActualizarElemento.actualizarModelo(modelo, idObjetoModificar);

	}
	public void modificarSerie(Serie serie, UUID idObjetoModificar){
		
		miActualizarElemento.actualizarSerie(serie, idObjetoModificar);

	}
	
	public void modificarManual(Manual manual, UUID idObjetoModificar){
		
		miActualizarElemento.actualizarManual(manual, idObjetoModificar);

	}
	
	public void modificarSistemaEspecifico (SistemaEspecifico sistemaEspecifico , UUID idObjetoModificar){
		
		miActualizarElemento.actualizarSistemaEspecifico(sistemaEspecifico, idObjetoModificar);

	}
	
	public void modificarEstructuraFrame (EstructuraFrame estructuraFrame , UUID idObjetoModificar){
		
		miActualizarElemento.actualizarEstructuraFrame(estructuraFrame, idObjetoModificar);

	}
	
	public void modificarElementoSistema (ElementosSistemaEspecifico elementoSistema , UUID idObjetoModificar, List <Object> listaAtributoElementoActualizar, List <Object> listaAtributoElementoInsertar){
		
		miActualizarElemento.actualizarElementosSistema(elementoSistema, idObjetoModificar, listaAtributoElementoActualizar, listaAtributoElementoInsertar);

	}

	public ActualizarElemento getActualizarElemento(){
		
		return(miActualizarElemento);
	}

}
