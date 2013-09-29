package view.inicio;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.acierto.AciertoCrearEstructuraBBDD;
import view.error.ErrorCrearEstructuraBBDD;
import view.error.ErrorSeleccionBBDD;


import controller.*;
import libreria.ParametrosConexionBBDD;
import model.*;

import java.beans.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;
import java.awt.event.ActionListener;


public class CrearEstructuraBBDD extends JFrame implements PropertyChangeListener {

	private JTable tblBBDD;

	private String[] nombresColum = {"Base de datos","Host"};
	private DefaultTableModel modeloTabla;
	private Object bbddSelecionada;
			
	private InicioController miInicioController;
	public static final String PROP_MOSTRAR_BBDD = "mostrarBBDD";
	public static final String PROP_USAR_BBDD = "usarBBDD";	
	public static final String PROP_CREAR_BBDD = "crearBBDD";
	
    private List<Object []> dataLista = new ArrayList();  
	
    private ConexionBBDD conexionBBDD;
    private UUID miIdCrearEstructuraBBDD;//Identificador unico de cada objeto
    Logger LOG = LoggerFactory.getLogger(CrearEstructuraBBDD.class);

	public CrearEstructuraBBDD(InicioController inicioController, UUID idCrearEstructuraBBDD){//, ConexionBBDD conexion) {
		
		miInicioController = inicioController;
		miIdCrearEstructuraBBDD = idCrearEstructuraBBDD;
		setTitle("Crear Estructura de BBDDD");
		getContentPane().setLayout(null);
		
		conexionBBDD = miInicioController.getConexionBBDD();
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ ){
			Object [] dataArray = {" "," "};		    
		    dataLista.add(dataArray);	
		}
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 21, 362, 191);
		getContentPane().add(scrollPane);
		
		//Transformacion del ArrayList en un Object [][]
		Object [][] data = new Object [dataLista.size()][];
		dataLista.toArray(data);

		modeloTabla = new DefaultTableModel( data , nombresColum);

		tblBBDD = new JTable(modeloTabla);
		tblBBDD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblBBDD.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblBBDD);

		
		tblBBDD.addMouseListener(new MouseAdapter(){
			public void mouseClicked (MouseEvent e){
				int fila = tblBBDD.rowAtPoint(e.getPoint());
				int columna = 0;
				
				if ((fila > -1)){
					bbddSelecionada = tblBBDD.getValueAt(fila, columna);
				}	
					
			}
		}
			
		);
		

	JButton btnCrear = new JButton("Crear");
	btnCrear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			do_btnCrear_actionPerformed(arg0);
		}
	});
	btnCrear.setBounds(76, 223, 89, 23);
	getContentPane().add(btnCrear);
	
	JButton btnCancelar = new JButton("Cancelar");
	btnCancelar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			do_btnCancelar_actionPerformed(arg0);
		}
	});
	btnCancelar.setBounds(240, 223, 89, 23);
	getContentPane().add(btnCancelar);
	
	setSize(421, 298);
	setVisible(true);
	
	//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
	//y se cierra correctamente el objeto
	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	this.addWindowListener(new WindowAdapter() {
				   public void windowClosing(WindowEvent evt) {
					   do_btnCancelar_actionPerformed(null);
				   }
				  });

	
	}	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();		
		List<Object []> dataListaBBDD;//Lista de bbdd
		if (PROP_MOSTRAR_BBDD.equals(propertyName)){
			dataListaBBDD =  (ArrayList<Object []>) evt.getNewValue();
			dataLista.removeAll(dataLista);//Se borran datos antiguos de la lista
		    dataLista.addAll(0,dataListaBBDD);//Se añaden los nuevos datos, de la query, a la lista		    		    		    
		    actualizaTabla();
		} 
		else if(PROP_CREAR_BBDD.equals(propertyName)){
			ParametrosConexionBBDD parametrosCrearEstructuraBBDD = (ParametrosConexionBBDD)evt.getNewValue();		
			if(parametrosCrearEstructuraBBDD.getIdElemento().equals(miIdCrearEstructuraBBDD))
				mensajeCrearEstructuraBBDD(parametrosCrearEstructuraBBDD);			
		}
		// Si hay un fallo en el uso de la bbdd se computara en mensajeCrearEstructuraBBDD
		
	}
	
	
	/*
	 * Actualiza el Jtable con las bases de datos que puede usar el usuario. 
	 * */
	private void actualizaTabla(){
				
		Object [][] data1 = new Object [dataLista.size()][];
		dataLista.toArray(data1);
		modeloTabla.setDataVector(data1 , nombresColum);
			
		
	 }
	
	/*
	 * Muestra una ventana de acierto o error dependiendo del objeto parametros,
	 * @param parametros objeto que contiene flags
	 * */
	private void mensajeCrearEstructuraBBDD(ParametrosConexionBBDD parametros)
	{
		ParametrosConexionBBDD parametrosCrearEstructuraBBDD = parametros;		
		//Entra en el if, si hay error usando la bbdd
		if (parametrosCrearEstructuraBBDD.getUsarBBDD() == 0){			
			
			ErrorSeleccionBBDD errorSeleccionBBDD = new ErrorSeleccionBBDD(); 
		}else{			
			if (parametrosCrearEstructuraBBDD.getHayTablas()== 0){
				//Se puede crear la estructura de BBDD
				AciertoCrearEstructuraBBDD aciertoCrearEstructuraBBDD = new AciertoCrearEstructuraBBDD();
				LOG.info("Se crearia la estructura en la base de datos ", bbddSelecionada.toString());
			}else{
				//No se puede crear la estructura de BBDD
				ErrorCrearEstructuraBBDD errorCrearEstructuraBBDD = new ErrorCrearEstructuraBBDD();
			}
		}
			
	}
	
	
	protected void do_btnCrear_actionPerformed(ActionEvent arg0) {		
		miInicioController.crearEstructuraBBDD(bbddSelecionada,miIdCrearEstructuraBBDD);		
	}
	
	
	
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {		
		dispose();//el objeto ya no se visualiza
		conexionBBDD.removePropertyChangeListener(this);//se remueve el el objeto de los listeners
		//Asi ek GC de java borrara este objeto
	}
}
