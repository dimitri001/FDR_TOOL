package controller;

import java.util.UUID;

import libreria.*;
import model.*;

public class InicioController {
	
	private ConexionBBDD miConexionBBDD ;
	
	public InicioController(ConexionBBDD conexionBBDD)
	{
		this.miConexionBBDD = conexionBBDD;
	}

	public void iniciarSesion(String usuario, String contraseña)
	{
		this.miConexionBBDD.iniciarSesion(usuario, contraseña);
		
	}
	
	public void mostrarBBDD()
	{
		this.miConexionBBDD.mostrarBBDD();
		
	}
	
	public void usarBBDD(Object bbddSelecionada )
	{
		this.miConexionBBDD.usarBBDD(bbddSelecionada);
	}
	
	public void crearEstructuraBBDD(Object bbddSelecionada, UUID idCrearEstructuraBBDD)
	{
		this.miConexionBBDD.crearEstructuraBBDD(bbddSelecionada, idCrearEstructuraBBDD);
		
	}
	
	public void eliminarEstructuraBBDD(Object bbddSelecionada, UUID idEliminarEstructuraBBDD)
	{
		this.miConexionBBDD.eliminarEstructuraBBDD(bbddSelecionada, idEliminarEstructuraBBDD);
		
	}
	
	public ConexionBBDD getConexionBBDD(){
		
		return miConexionBBDD;
	}
}
