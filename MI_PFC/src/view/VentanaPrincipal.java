package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Frame;

import javax.swing.JPanel;
import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MessageConfirmationUtils;
import view.alta.AltaElementoEspecifico;
import view.alta.AltaEstructuraFrame;
import view.alta.AltaFabricante;
import view.alta.AltaManualReferencia;
import view.alta.AltaModelo;
import view.alta.AltaSerie;
import view.alta.AltaSistemaEspecifico;
import view.baja.BajaElementoEspecifico;
import view.baja.BajaEstructuraFrame;
import view.baja.BajaFabricante;
import view.baja.BajaManualReferencia;
import view.baja.BajaModelo;
import view.baja.BajaSerie;
import view.baja.BajaSistemaEspecifico;
import view.inicio.CrearEstructuraBBDD;
import view.inicio.EliminarEstructuraBBDD;
import view.inicio.IniciarSesion;
import view.inicio.SeleccionarBBDD;
import view.ver.modificar.VerModificarElementoEspecifico;
import view.ver.modificar.VerModificarEstructuraFrame;
import view.ver.modificar.VerModificarFabricante;
import view.ver.modificar.VerModificarManualReferencia;
import view.ver.modificar.VerModificarModelo;
import view.ver.modificar.VerModificarSerie;
import view.ver.modificar.VerModificarSistemaEspecifico;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import controller.*;
import libreria.ContenedorConexion;
import libreria.ListUniqueID;
import libreria.MutableBoolean;
import libreria.MyConstants;
import model.*;


public class VentanaPrincipal extends JFrame{

	private ContenedorConexion contenedorConexion = new ContenedorConexion();
	private boolean actualizarLista = false;
			
	//Baja Y VM
	ConsultasBBDD consultasBBDD = new ConsultasBBDD(contenedorConexion);	
	ConsultaController consultaController = new ConsultaController(consultasBBDD);
	public static final String PROP_LISTAR_FABRICANTE = "listarFabricante";
	public static final String PROP_LISTAR_MODELO = "listarModelo";
	public static final String PROP_LISTAR_SERIE = "listarSerie";
	public static final String PROP_LISTAR_MANUAL = "listarManual";
	public static final String PROP_LISTAR_ESTRUCTURA_FRAME = "listarEstructuraFrame";
	public static final String PROP_LISTAR_SISTEMA_ESPECIFICO = "listarSistemaEspecifico";
	public static final String PROP_LISTAR_ELEMENTO_ESPECIFICO = "listarElementoEspecifico";
	private ListUniqueID listUniqueIdVerModificarFabricante = new ListUniqueID(MyConstants.StringConstant.MENSAJE_UNIQUE_VER_MODIFICAR.value(),MyConstants.StringConstant.VER_MODIFICAR.value());
	private ListUniqueID listUniqueIdVerModificarModelo = new ListUniqueID(MyConstants.StringConstant.MENSAJE_UNIQUE_VER_MODIFICAR.value(),MyConstants.StringConstant.VER_MODIFICAR.value());
	private ListUniqueID listUniqueIdVerModificarSerie = new ListUniqueID(MyConstants.StringConstant.MENSAJE_UNIQUE_VER_MODIFICAR.value(),MyConstants.StringConstant.VER_MODIFICAR.value());
	private ListUniqueID listUniqueIdVerModificarManual = new ListUniqueID(MyConstants.StringConstant.MENSAJE_UNIQUE_VER_MODIFICAR.value(),MyConstants.StringConstant.VER_MODIFICAR.value());
	private ListUniqueID listUniqueIdVerModificarEstructuraFrame = new ListUniqueID(MyConstants.StringConstant.MENSAJE_UNIQUE_VER_MODIFICAR.value(),MyConstants.StringConstant.VER_MODIFICAR.value());
	private ListUniqueID listUniqueIdVerModificarSistemaEspecifico = new ListUniqueID(MyConstants.StringConstant.MENSAJE_UNIQUE_VER_MODIFICAR.value(),MyConstants.StringConstant.VER_MODIFICAR.value());
	private ListUniqueID listUniqueIdVerModificarElementoEspecifico = new ListUniqueID(MyConstants.StringConstant.MENSAJE_UNIQUE_VER_MODIFICAR.value(),MyConstants.StringConstant.VER_MODIFICAR.value());

	ActualizarElemento actualizarElemento = new ActualizarElemento(contenedorConexion);
	VerModificarController verModificarController = new VerModificarController(actualizarElemento);
	Logger LOG = LoggerFactory.getLogger(VentanaPrincipal.class);
	
	//Baja	
	BorrarElemento borrarElemento = new BorrarElemento(contenedorConexion);
	BajaController bajaController = new BajaController(borrarElemento);
	public static final String PROP_ELIMINAR_FABRICANTE = "eliminarFabricante";
	public static final String PROP_ELIMINAR_MODELO = "eliminarModelo";
	public static final String PROP_ELIMINAR_SERIE = "eliminarSerie";
	public static final String PROP_ELIMINAR_MANUAL = "eliminarManual";
	public static final String PROP_ELIMINAR_ESTRUCTURA_FRAME = "eliminarEstructuraFrame";
	public static final String PROP_ELIMINAR_SISTEMA_ESPECIFICO = "eliminarSistemaEspecifico";
	public static final String PROP_ELIMINAR_ELEMENTO_ESPECIFICO = "eliminarElementoEspecifico";
	private ListUniqueID listUniqueIdBajaElementoEspecifico = new ListUniqueID(MyConstants.StringConstant.MENSAJE_UNIQUE_BAJA_ELEMENTO_ESPECIFICO.value(),MyConstants.StringConstant.BAJA.value());

	
	//Alta
	GuardarElemento guardarElemento = new GuardarElemento(contenedorConexion);
	ConsultasComboBoxBBDD consultasComboBoxBBDD = new ConsultasComboBoxBBDD(contenedorConexion);
	ComboBoxController comboBoxController = new ComboBoxController(consultasComboBoxBBDD);
	AltaController altaController = new AltaController(guardarElemento, consultaController);
	public static final String PROP_INSERTAR_FABRICANTE = "insertarFabricante";
	public static final String PROP_INSERTAR_MODELO = "insertarModelo";
	public static final String PROP_INSERTAR_SERIE = "insertarSerie";
	public static final String PROP_INSERTAR_MANUAL = "insertarManual";	
	public static final String PROP_INSERTAR_ESTRUCTURA_FRAME = "insertarEstructuraFrame";
	public static final String PROP_INSERTAR_SISTEMA_ESPECIFICO = "insertarSistemaEspecifico";
	public static final String PROP_INSERTAR_ELEMENTO_ESPECIFICO = "insertarElementoEspecifico";
	
	//Inicio
	public static final String PROP_INICIAR_SESION = "iniciarSesion";
	ConexionBBDD conexionBBDD = new ConexionBBDD(contenedorConexion);
	InicioController inicioController = new InicioController(conexionBBDD);
	private MutableBoolean crearVentanaIniciarSesion = new MutableBoolean(true);
	private MutableBoolean crearVentanaSeleccionarBbdd = new MutableBoolean(true);	
	
	public VentanaPrincipal() {
		setTitle("FDR TOOL");
		getContentPane().setLayout(null);
		JTabbedPane panelPestañas = new JTabbedPane(JTabbedPane.TOP);
		panelPestañas.setBounds(0, 0, 499, 478);
		getContentPane().add(panelPestañas);
		/* Tab Inicio  */		
		JPanel tabInicio = new JPanel();
		panelPestañas.addTab("Inicio  ", null, tabInicio, null);
		tabInicio.setLayout(null);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnIniciarSesion_actionPerformed(arg0);
			}
		});
		btnIniciarSesion.setBounds(60, 71, 206, 23);
		tabInicio.add(btnIniciarSesion);
		
		JButton btnCrearEstructuraDe = new JButton("Crear Estructura de BBDD");
		btnCrearEstructuraDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCrearEstructuraDe_actionPerformed(arg0);
			}
		});
		btnCrearEstructuraDe.setBounds(60, 259, 206, 23);
		tabInicio.add(btnCrearEstructuraDe);
		
		JButton btnEliminarEstructuraDe = new JButton("Eliminar Estructura de BBDD");
		btnEliminarEstructuraDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnEliminarEstructuraDe_actionPerformed(arg0);
			}
		});
		btnEliminarEstructuraDe.setBounds(60, 353, 206, 23);
		tabInicio.add(btnEliminarEstructuraDe);
		
		JButton btnSeleccionarBbdd = new JButton("Seleccionar BBDD");
		btnSeleccionarBbdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnSeleccionarBbdd_actionPerformed(arg0);
			}
		});
		btnSeleccionarBbdd.setBounds(60, 165, 206, 23);
		tabInicio.add(btnSeleccionarBbdd);
		
		/*Tab Alta*/
		
		JPanel tabAlta = new JPanel();
		panelPestañas.addTab("Alta  ", null, tabAlta, null);
		tabAlta.setLayout(null);
		
		JButton btnFabricanteAlta = new JButton("Fabricante");
		btnFabricanteAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnFabricanteAlta_actionPerformed(arg0);
			}
		});
		btnFabricanteAlta.setBounds(60, 37, 187, 23);
		tabAlta.add(btnFabricanteAlta);
		
		JButton btnModeloAlta = new JButton("Modelo");
		btnModeloAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnModeloAlta_actionPerformed(arg0);
			}
		});
		btnModeloAlta.setBounds(60, 96, 187, 23);
		tabAlta.add(btnModeloAlta);
		
		JButton btnManualAlta = new JButton("Manual de Referencia");
		btnManualAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnManualAlta_actionPerformed(arg0);
			}
		});
		btnManualAlta.setBounds(60, 214, 187, 23);
		tabAlta.add(btnManualAlta);
		
		JButton btnSistemaEspecificoAlta = new JButton("Sistema Especifico");
		btnSistemaEspecificoAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnSistemaEspecificoAlta_actionPerformed(arg0);
			}
		});
		btnSistemaEspecificoAlta.setBounds(60, 332, 187, 23);
		tabAlta.add(btnSistemaEspecificoAlta);
		
		JButton btnEstructuraFrameAlta = new JButton("Estructura de Frame");
		btnEstructuraFrameAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnEstructuraFrameAlta_actionPerformed(arg0);
			}
		});
		btnEstructuraFrameAlta.setBounds(60, 273, 187, 23);
		tabAlta.add(btnEstructuraFrameAlta);
		
		JButton btnSerieAlta = new JButton("Serie ");
		btnSerieAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnSerieAlta_actionPerformed(arg0);
			}
		});
		btnSerieAlta.setBounds(60, 155, 187, 23);
		tabAlta.add(btnSerieAlta);
		
		JButton btnElementoEspecifico = new JButton("Elemento Especifico");
		btnElementoEspecifico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnElementoEspecifico_actionPerformed(arg0);
			}
		});
		btnElementoEspecifico.setBounds(60, 391, 187, 23);
		tabAlta.add(btnElementoEspecifico);
		
		/*Tab Baja*/
		
		JPanel tabBaja = new JPanel();
		panelPestañas.addTab("Baja", null, tabBaja, null);
		tabBaja.setLayout(null);
		
		JButton btnFabricanteBaja = new JButton("Fabricante");
		btnFabricanteBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnFabricanteBaja_actionPerformed(arg0);
			}
		});
		btnFabricanteBaja.setBounds(60, 37, 187, 23);
		tabBaja.add(btnFabricanteBaja);
		
		JButton btnModeloBaja = new JButton("Modelo");
		btnModeloBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnModeloBaja_actionPerformed(arg0);
			}
		});
		btnModeloBaja.setBounds(60, 95, 187, 23);
		tabBaja.add(btnModeloBaja);
		
		JButton btnSerieBaja = new JButton("Serie");
		btnSerieBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnSerieBaja_actionPerformed(arg0);
			}
		});
		btnSerieBaja.setBounds(60, 154, 187, 23);
		tabBaja.add(btnSerieBaja);
		
		JButton btnManualBaja = new JButton("Manual de Referencia");
		btnManualBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnManualBaja_actionPerformed(arg0);
			}
		});
		btnManualBaja.setBounds(60, 213, 187, 23);
		tabBaja.add(btnManualBaja);
		
		JButton btnEstructuraFrameBaja = new JButton("Estructura de Frame");
		btnEstructuraFrameBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnEstructuraFrameBaja_actionPerformed(arg0);
			}
		});
		btnEstructuraFrameBaja.setBounds(60, 272, 187, 23);
		tabBaja.add(btnEstructuraFrameBaja);
		
		JButton btnSistemaEspecificoBaja = new JButton("Sistema Especifico");
		btnSistemaEspecificoBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnSistemaEspecificoBaja_actionPerformed(arg0);
			}
		});
		btnSistemaEspecificoBaja.setBounds(60, 331, 187, 23);
		tabBaja.add(btnSistemaEspecificoBaja);
		
		JButton btnElementoEspecificoBaja = new JButton("Elemento Especifico");
		btnElementoEspecificoBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnElementoEspecificoBaja_actionPerformed(arg0);
			}
		});
		
		btnElementoEspecificoBaja.setBounds(60, 390, 187, 23);
		tabBaja.add(btnElementoEspecificoBaja);
		
		/*Tab Ver/Modificar */
		
		JPanel tabVerModificar = new JPanel();
		panelPestañas.addTab("Ver/Modificar", null, tabVerModificar, null);
		tabVerModificar.setLayout(null);
		
		JButton btnFabricanteVM = new JButton("Fabricante");
		btnFabricanteVM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnFabricanteVM_actionPerformed(arg0);
			}
		});
		btnFabricanteVM.setBounds(60, 36, 187, 23);
		tabVerModificar.add(btnFabricanteVM);
		
		JButton btnModeloVM = new JButton("Modelo");
		btnModeloVM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnModeloVM_actionPerformed(arg0);
			}
		});
		btnModeloVM.setBounds(60, 95, 187, 23);
		tabVerModificar.add(btnModeloVM);
		
		JButton btnSerieVM = new JButton("Serie");
		btnSerieVM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnSerieVM_actionPerformed(arg0);
			}
		});
		btnSerieVM.setBounds(60, 154, 187, 23);
		tabVerModificar.add(btnSerieVM);
		
		JButton btnManualVM = new JButton("Manual de Referencia");
		btnManualVM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnManualVM_actionPerformed(arg0);
			}
		});
		btnManualVM.setBounds(60, 213, 187, 23);
		tabVerModificar.add(btnManualVM);
		
		JButton btnEstructuraFrameVM = new JButton("Estructura de Frame");
		btnEstructuraFrameVM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnEstructuraFrameVM_actionPerformed(arg0);
			}
		});
		btnEstructuraFrameVM.setBounds(60, 272, 187, 23);
		tabVerModificar.add(btnEstructuraFrameVM);
		
		JButton btnSistemaEspecificoVM = new JButton("Sistema Especifico");
		btnSistemaEspecificoVM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnSistemaEspecificoVM_actionPerformed(arg0);
			}
		});
		btnSistemaEspecificoVM.setBounds(60, 331, 187, 23);
		tabVerModificar.add(btnSistemaEspecificoVM);
		
		JButton btnElementoEspecificoVM = new JButton("Elemento Especifico");
		btnElementoEspecificoVM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnElementoEspecificoVM_actionPerformed(arg0);
			}
		});
		btnElementoEspecificoVM.setBounds(60, 390, 187, 23);
		tabVerModificar.add(btnElementoEspecificoVM);
		
		setVisible(true);  
		setSize(506, 507);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   cerrar ();
					   }
					  });
					  
	}
	
	protected void cerrar (){
	
		int response = MessageConfirmationUtils.closeAplicationMessageConfirmation();
		if (response == JOptionPane.YES_OPTION) {
			List <Frame> listFrames = Arrays.asList(Frame.getFrames());
			ListIterator it = listFrames.listIterator(listFrames.size());
			while(it.hasPrevious()){
				Frame frame =  (Frame) it.previous();
				frame.dispose();
				 LOG.info("Cerrando Frame "+frame.getTitle());
			} 
			try{
				Connection conexion = conexionBBDD.getConexion();
				if (conexion != null)
					conexion.close();
				LOG.info("Cerrando Conexion de BBDD");
			} catch (SQLException e) {
				e.printStackTrace();
				LOG.error("Unable close the MySQL conexion", e);
			}				
		}
	}


	/* Acciones de botones para Inicio  */
	protected void do_btnIniciarSesion_actionPerformed(ActionEvent arg0) {
		if (crearVentanaIniciarSesion.valor){
			IniciarSesion iniciarSesion = new IniciarSesion(inicioController, this);			
			inicioController.getConexionBBDD().addPropertyChangeListener(iniciarSesion);
		}
		crearVentanaIniciarSesion.valor= false;
			
	}
	
	public void do_btnSeleccionarBbdd_actionPerformed(ActionEvent arg0) {
		if (crearVentanaSeleccionarBbdd.valor){
			SeleccionarBBDD seleccionarBBDD = new SeleccionarBBDD(inicioController, this);
			inicioController.getConexionBBDD().addPropertyChangeListener(seleccionarBBDD);
			inicioController.mostrarBBDD();			
		}
		crearVentanaSeleccionarBbdd.valor=false;
	}
	protected void do_btnCrearEstructuraDe_actionPerformed(ActionEvent arg0) {
		UUID idCrearEstructuraBBDD = UUID.randomUUID();
		CrearEstructuraBBDD crearEstructuraBBDD = new CrearEstructuraBBDD(inicioController, idCrearEstructuraBBDD);
		inicioController.getConexionBBDD().addPropertyChangeListener(crearEstructuraBBDD);
		inicioController.mostrarBBDD();
	}
	protected void do_btnEliminarEstructuraDe_actionPerformed(ActionEvent arg0) {
		UUID idEliminarEstructuraBBDD = UUID.randomUUID();
		EliminarEstructuraBBDD eliminarEstructuraBBDD = new EliminarEstructuraBBDD(inicioController, idEliminarEstructuraBBDD);
		inicioController.getConexionBBDD().addPropertyChangeListener(eliminarEstructuraBBDD);
		inicioController.mostrarBBDD();
	}
	
	
	/*Acciones botones Alta*/
	protected void do_btnFabricanteAlta_actionPerformed(ActionEvent arg0) {	
		UUID idFabricante = UUID.randomUUID();
		
		AltaFabricante altaFabricante= new AltaFabricante(altaController,idFabricante);
		altaController.getGuardarElemento().addPropertyChangeListener(PROP_INSERTAR_FABRICANTE, altaFabricante);	
		
	}
		
	
	protected void do_btnModeloAlta_actionPerformed(ActionEvent arg0) {
		UUID idModelo = UUID.randomUUID();

		AltaModelo altaModelo = new AltaModelo(altaController,comboBoxController, idModelo);
		altaController.getGuardarElemento().addPropertyChangeListener(PROP_INSERTAR_MODELO,altaModelo);
		
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(altaModelo);
		comboBoxController.comboBoxFabricante(idModelo);

	}
	
	protected void do_btnSerieAlta_actionPerformed(ActionEvent arg0) {
		
		UUID idSerie = UUID.randomUUID();
		
		AltaSerie altaSerie = new AltaSerie(altaController,comboBoxController,idSerie);
		altaController.getGuardarElemento().addPropertyChangeListener(PROP_INSERTAR_SERIE, altaSerie);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(altaSerie);
		comboBoxController.comboBoxFabricante(idSerie);

	}
	protected void do_btnManualAlta_actionPerformed(ActionEvent arg0) {

		UUID idManual = UUID.randomUUID();
		
		AltaManualReferencia altaManualReferencia = new AltaManualReferencia(altaController,comboBoxController,idManual);
		altaController.getGuardarElemento().addPropertyChangeListener(PROP_INSERTAR_MANUAL, altaManualReferencia);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(altaManualReferencia);
		comboBoxController.comboBoxFabricante(idManual);

		
	}
	protected void do_btnEstructuraFrameAlta_actionPerformed(ActionEvent arg0) {
		UUID idEstructuraFrame = UUID.randomUUID();
		
		AltaEstructuraFrame altaEstructuraFrame = new AltaEstructuraFrame(altaController,comboBoxController,idEstructuraFrame);
		altaController.getGuardarElemento().addPropertyChangeListener(PROP_INSERTAR_ESTRUCTURA_FRAME, altaEstructuraFrame);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(altaEstructuraFrame);
		comboBoxController.comboBoxFabricante(idEstructuraFrame);
				
	}
	protected void do_btnSistemaEspecificoAlta_actionPerformed(ActionEvent arg0) {
		
		UUID idSistemaEspecifico = UUID.randomUUID();

		AltaSistemaEspecifico altaSistemaEspecifico = new AltaSistemaEspecifico(altaController,comboBoxController,idSistemaEspecifico);		
		altaController.getGuardarElemento().addPropertyChangeListener(PROP_INSERTAR_SISTEMA_ESPECIFICO, altaSistemaEspecifico);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(altaSistemaEspecifico);
		comboBoxController.comboBoxFabricante(idSistemaEspecifico);
	
		
	}

	protected void do_btnElementoEspecifico_actionPerformed(ActionEvent arg0) {

		UUID idElementoEspecifico = UUID.randomUUID();

		AltaElementoEspecifico altaElementoEspecifico =  new AltaElementoEspecifico(altaController,comboBoxController,idElementoEspecifico);		
		
		altaController.getGuardarElemento().addPropertyChangeListener(PROP_INSERTAR_ELEMENTO_ESPECIFICO, altaElementoEspecifico);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(altaElementoEspecifico);
		comboBoxController.comboBoxFabricante(idElementoEspecifico);
	

	}
	
	/*Acciones botones Baja*/
	
	protected void do_btnFabricanteBaja_actionPerformed(ActionEvent arg0) {
		UUID idFabricante = UUID.randomUUID();		
		BajaFabricante bajaFabricante = new BajaFabricante(bajaController, consultaController, idFabricante, listUniqueIdVerModificarFabricante);
		bajaController.getBorrarElemento().addPropertyChangeListener(PROP_ELIMINAR_FABRICANTE, bajaFabricante);
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_FABRICANTE, bajaFabricante);		
		consultaController.listaFabricante(idFabricante, actualizarLista);
	}
	
	
	protected void do_btnModeloBaja_actionPerformed(ActionEvent arg0) {

		UUID idModelo = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaModelo, lista todos los modelos

		BajaModelo bajaModelo =  new BajaModelo(bajaController, consultaController, comboBoxController, idModelo, listUniqueIdVerModificarModelo);

		
		bajaController.getBorrarElemento().addPropertyChangeListener(PROP_ELIMINAR_MODELO, bajaModelo);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(bajaModelo);
		comboBoxController.comboBoxFabricante(idModelo);

		
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_MODELO, bajaModelo);		
		consultaController.listaModelo(intIdFabricante, idModelo, actualizarLista);


	}
	
	
	protected void do_btnSerieBaja_actionPerformed(ActionEvent arg0) {
		
		UUID idSerie = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaSerie, lista todos los modelos
		int intIdModelo = 0;
		BajaSerie bajaSerie = new BajaSerie(bajaController, consultaController, comboBoxController, idSerie, listUniqueIdVerModificarSerie);
				
		bajaController.getBorrarElemento().addPropertyChangeListener(PROP_ELIMINAR_SERIE, bajaSerie);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(bajaSerie);
		comboBoxController.comboBoxFabricante(idSerie);
		
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_SERIE, bajaSerie);		
		consultaController.listaSerie(intIdFabricante,intIdModelo, idSerie, actualizarLista);

	}
	
	
	protected void do_btnManualBaja_actionPerformed(ActionEvent arg0) {
		
		
		UUID idManual = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaManual, lista todos los modelos
		int intIdModelo = 0;
		int intIdSerie = 0;
		
		BajaManualReferencia bajaManual = new BajaManualReferencia(bajaController, consultaController, comboBoxController, idManual, listUniqueIdVerModificarManual);
					
		bajaController.getBorrarElemento().addPropertyChangeListener(PROP_ELIMINAR_MANUAL, bajaManual);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(bajaManual);
		comboBoxController.comboBoxFabricante(idManual);
		
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_MANUAL, bajaManual);		
		consultaController.listaManual(intIdFabricante,intIdModelo,intIdSerie, idManual, actualizarLista);

	}
	
	protected void do_btnEstructuraFrameBaja_actionPerformed(ActionEvent arg0) {
		
		UUID idEstructuraFrame = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaEstructuraFrame, lista todo
		int intIdModelo = 0;
		int intIdSerie = 0;
		
		BajaEstructuraFrame bajaEstructuraFrame = new BajaEstructuraFrame(bajaController, consultaController, comboBoxController, idEstructuraFrame, listUniqueIdVerModificarEstructuraFrame);
		bajaController.getBorrarElemento().addPropertyChangeListener(PROP_ELIMINAR_ESTRUCTURA_FRAME, bajaEstructuraFrame);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(bajaEstructuraFrame);
		comboBoxController.comboBoxFabricante(idEstructuraFrame);
		
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_ESTRUCTURA_FRAME, bajaEstructuraFrame);		
		consultaController.listaEstructuraFrame(intIdFabricante,intIdModelo,intIdSerie, idEstructuraFrame, actualizarLista);

	
	}
	
	
	protected void do_btnSistemaEspecificoBaja_actionPerformed(ActionEvent arg0) {
				
		UUID idSistemaEspecifico = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaSistemaEspecifico, lista todo
		int intIdModelo = 0;
		int intIdSerie = 0;
		
		BajaSistemaEspecifico bajaSistemaEspecifico = new BajaSistemaEspecifico(bajaController, consultaController, comboBoxController, idSistemaEspecifico, listUniqueIdVerModificarSistemaEspecifico);
		
		bajaController.getBorrarElemento().addPropertyChangeListener(PROP_ELIMINAR_SISTEMA_ESPECIFICO , bajaSistemaEspecifico);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(bajaSistemaEspecifico);
		comboBoxController.comboBoxFabricante(idSistemaEspecifico);
		
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_SISTEMA_ESPECIFICO, bajaSistemaEspecifico);		
		consultaController.listaSistemaEspecifico(intIdFabricante,intIdModelo,intIdSerie, idSistemaEspecifico, actualizarLista);
	}
	

	protected void do_btnElementoEspecificoBaja_actionPerformed(ActionEvent arg0) {
		
		UUID idElementoEspecifico = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaElementoEspecifico, lista todo
		int intIdModelo = 0;
		int intIdSerie = 0;
		int intIdSistema = 0;
		
		//BajaElementoEspecifico bajaElementoEspecifico = new BajaElementoEspecifico(bajaController, consultaController, comboBoxController, idElementoEspecifico, listUniqueIdBajaElementoEspecifico);
		BajaElementoEspecifico bajaElementoEspecifico = new BajaElementoEspecifico(bajaController, consultaController, comboBoxController, idElementoEspecifico, listUniqueIdVerModificarElementoEspecifico);
		bajaController.getBorrarElemento().addPropertyChangeListener(PROP_ELIMINAR_ELEMENTO_ESPECIFICO , bajaElementoEspecifico);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(bajaElementoEspecifico);
		comboBoxController.comboBoxFabricante(idElementoEspecifico);
		
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_ELEMENTO_ESPECIFICO, bajaElementoEspecifico);		
		consultaController.listaElementoEspecifico(intIdFabricante,intIdModelo,intIdSerie, intIdSistema, idElementoEspecifico, actualizarLista);

		
	}
	
	/*Acciones botones Ver/Modificar*/
	protected void do_btnFabricanteVM_actionPerformed(ActionEvent arg0) {		
		UUID idFabricante = UUID.randomUUID();				
		VerModificarFabricante verModificarFabricante = new VerModificarFabricante(verModificarController, consultaController, idFabricante, listUniqueIdVerModificarFabricante);
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_FABRICANTE, verModificarFabricante);		
		consultaController.listaFabricante(idFabricante, actualizarLista);		
	}
	
	protected void do_btnModeloVM_actionPerformed(ActionEvent arg0) {			
		UUID idModelo = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaElementoEspecifico, lista todo		
		VerModificarModelo verModificarModelo = new VerModificarModelo(verModificarController, consultaController,comboBoxController, idModelo, listUniqueIdVerModificarModelo);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(verModificarModelo);
		comboBoxController.comboBoxFabricante(idModelo);
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_MODELO, verModificarModelo);		
		consultaController.listaModelo(intIdFabricante, idModelo, actualizarLista);
	}
	
	protected void do_btnSerieVM_actionPerformed(ActionEvent arg0) {		
		UUID idSerie = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaElementoEspecifico, lista todo
		int intIdModelo = 0;
		VerModificarSerie verModificarSerie = new VerModificarSerie(verModificarController, consultaController,comboBoxController, idSerie, listUniqueIdVerModificarSerie);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(verModificarSerie);
		comboBoxController.comboBoxFabricante(idSerie);
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_SERIE, verModificarSerie);		
		consultaController.listaSerie(intIdFabricante,intIdModelo, idSerie, actualizarLista);	
	}
	
	

	protected void do_btnManualVM_actionPerformed(ActionEvent arg0) {
		UUID idManual = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaElementoEspecifico, lista todo
		int intIdModelo = 0;
		int intIdSerie = 0;
		VerModificarManualReferencia verModificarManualReferencia = new VerModificarManualReferencia(verModificarController, consultaController,comboBoxController, idManual, listUniqueIdVerModificarManual);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(verModificarManualReferencia);
		comboBoxController.comboBoxFabricante(idManual);
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_MANUAL, verModificarManualReferencia);		
		consultaController.listaManual(intIdFabricante, intIdModelo, intIdSerie, idManual, actualizarLista);	

	}
	protected void do_btnEstructuraFrameVM_actionPerformed(ActionEvent arg0) {
		UUID idEstructuraFrame = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaElementoEspecifico, lista todo
		int intIdModelo = 0;
		int intIdSerie = 0;
		VerModificarEstructuraFrame verModificarEstructuraFrame = new VerModificarEstructuraFrame(verModificarController, consultaController,comboBoxController, idEstructuraFrame, listUniqueIdVerModificarEstructuraFrame);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(verModificarEstructuraFrame);
		comboBoxController.comboBoxFabricante(idEstructuraFrame);
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_ESTRUCTURA_FRAME, verModificarEstructuraFrame);		
		consultaController.listaEstructuraFrame(intIdFabricante, intIdModelo, intIdSerie, idEstructuraFrame, actualizarLista);	

	}
	protected void do_btnSistemaEspecificoVM_actionPerformed(ActionEvent arg0) {
		UUID idSistemaEspecifico = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaElementoEspecifico, lista todo
		int intIdModelo = 0;
		int intIdSerie = 0;
		VerModificarSistemaEspecifico verModificarSistemaEspecifico = new VerModificarSistemaEspecifico(verModificarController, consultaController,comboBoxController, idSistemaEspecifico, listUniqueIdVerModificarSistemaEspecifico);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(verModificarSistemaEspecifico);
		comboBoxController.comboBoxFabricante(idSistemaEspecifico);
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_SISTEMA_ESPECIFICO, verModificarSistemaEspecifico);		
		consultaController.listaSistemaEspecifico(intIdFabricante, intIdModelo, intIdSerie, idSistemaEspecifico, actualizarLista);
	
	}
	
	
	protected void do_btnElementoEspecificoVM_actionPerformed(ActionEvent arg0) {
		UUID idElementoEspecifico = UUID.randomUUID();
		int intIdFabricante = 0;//Asi en la listaElementoEspecifico, lista todo
		int intIdModelo = 0;
		int intIdSerie = 0;
		int intIdSistema = 0;
		VerModificarElementoEspecifico verModificarElementoEspecifico = new VerModificarElementoEspecifico(verModificarController, consultaController,comboBoxController, idElementoEspecifico, listUniqueIdVerModificarElementoEspecifico);
		comboBoxController.getConsultasComboBoxBBDD().addPropertyChangeListener(verModificarElementoEspecifico);
		comboBoxController.comboBoxFabricante(idElementoEspecifico);
		consultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_ELEMENTO_ESPECIFICO, verModificarElementoEspecifico);		
		consultaController.listaElementoEspecifico(intIdFabricante, intIdModelo, intIdSerie, intIdSistema, idElementoEspecifico, actualizarLista);	

	}

	public MutableBoolean getCrearVentanaIniciarSesion() {
		return crearVentanaIniciarSesion;
	}

	public MutableBoolean getCrearVentanaSeleccionarBbdd() {
		return crearVentanaSeleccionarBbdd;
	}

	public void setCrearVentanaIniciarSesion(MutableBoolean crearVentanaIniciarSesion) {
		this.crearVentanaIniciarSesion = crearVentanaIniciarSesion;
	}

	public void setCrearVentanaSeleccionarBbdd(MutableBoolean crearVentanaSeleccionarBbdd) {
		this.crearVentanaSeleccionarBbdd = crearVentanaSeleccionarBbdd;
	}
	

	/*
	 * Main del programa
	 * 
	 * */
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {

		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();		
		
		
	}

}
