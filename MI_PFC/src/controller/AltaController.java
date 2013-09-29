package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import libreria.ContenedorVisualAtributoElemento;
import model.GuardarElemento;

public class AltaController {

	private GuardarElemento miGuardarElemento;
	private ConsultaController miConsultaController;
	private UUID miIdObjetoAltaController = UUID.randomUUID();
	private boolean actualizar = true;
	

	public AltaController (GuardarElemento guardarElemento, ConsultaController consultaController){
		
		miGuardarElemento = guardarElemento;
		miConsultaController= consultaController;
	}
	
	public void guardarFabricante(String nombre, String web, UUID idFabricante){
		
		miGuardarElemento.insertarFabricante(nombre, web, idFabricante);
		miConsultaController.listaFabricante(miIdObjetoAltaController, actualizar);
	}

	public void guardarModelo(int idFabricante,String nombreModelo, String descripcion, Date fechaFabricacion, String anyoLanzamiento, UUID idModelo){
		
		miGuardarElemento.insertarModelo(idFabricante, nombreModelo,  descripcion, fechaFabricacion, anyoLanzamiento, idModelo);
		miConsultaController.listaModelo(0, miIdObjetoAltaController, actualizar);
	}

	
	public void guardarSerie(int idModelo, int idFabricante, String numeroSerie, String descripcion, Date fechaFabricacion, String anyoLanzamiento, UUID idSerie){
	
		miGuardarElemento.insertarSerie(idModelo, idFabricante, numeroSerie, descripcion, fechaFabricacion, anyoLanzamiento, idSerie);
		miConsultaController.listaSerie(0, 0, miIdObjetoAltaController, actualizar);

	}

	public void guardarManual(int idSerie, int idModelo, int idFabricante, String descripcion, String rutaFichero, UUID idManual){
		
		miGuardarElemento.insertarManual(idSerie, idModelo, idFabricante, descripcion, rutaFichero, idManual);
		miConsultaController.listaManual(0, 0, 0, miIdObjetoAltaController, actualizar);
	}

	public void guardarEstructuraFrame(int idSerie, int idModelo, int idFabricante,int numeroSubframes,double tiempoSubframe, int palabrasSegundo, int bitsPalabra, String descripcion, UUID idEstructuraFrame){
		
		miGuardarElemento.insertarEstructuraFrame(idSerie, idModelo, idFabricante, numeroSubframes, tiempoSubframe, palabrasSegundo, bitsPalabra, descripcion, idEstructuraFrame);
		miConsultaController.listaEstructuraFrame(0, 0, 0, miIdObjetoAltaController, actualizar);	
	}

	public void guardarSistemaEspecifico(int idSerie, int idModelo, int idFabricante, String nombre, String descripcion, UUID idSistemaEspecifico){
		
		miGuardarElemento.insertarSistemaEspecifico(idSerie, idModelo, idFabricante, nombre, descripcion, idSistemaEspecifico);
		miConsultaController.listaSistemaEspecifico(0, 0, 0, miIdObjetoAltaController, actualizar);
	
	}

	public void guardarElementoEspecifico(int idSistema, int idSerie, int idModelo, int idFabricante, String nombre, boolean interesting, String subframes, String palabras, String Bits, int nAtributos, List<ContenedorVisualAtributoElemento> listAtributoEspecifico, UUID idElementoEspecifico){

		miGuardarElemento.insertarElementoEspecifico(idSistema, idSerie, idModelo, idFabricante, nombre, interesting, subframes, palabras, Bits, nAtributos, (ArrayList<ContenedorVisualAtributoElemento>) listAtributoEspecifico, idElementoEspecifico);		
		miConsultaController.listaElementoEspecifico(0, 0, 0, 0, miIdObjetoAltaController, actualizar);
	}

	public GuardarElemento getGuardarElemento(){
		
		return(miGuardarElemento);
	}

	public ConsultaController getMiConsultaController() {
		return miConsultaController;
	}

	public UUID getMiIdAltaController() {
		return miIdObjetoAltaController;
	}
	
	public void setGuardarElemento(GuardarElemento guardarElemento){
		
		miGuardarElemento = guardarElemento;
	}

	public void setMiConsultaController(ConsultaController miConsultaController) {
		this.miConsultaController = miConsultaController;
	}

	public void setMiIdAltaController(UUID miIdAltaController) {
		this.miIdObjetoAltaController = miIdAltaController;
	}
	
}
