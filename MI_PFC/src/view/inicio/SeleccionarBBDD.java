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

import view.VentanaPrincipal;

import view.acierto.AciertoSeleccionBBDD;
import view.error.ErrorSeleccionBBDD;

import controller.*;
import libreria.MutableBoolean;
import model.*;


import java.beans.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.awt.event.ActionListener;



public class SeleccionarBBDD extends JFrame implements PropertyChangeListener {
	
	private JTable tblBBDD;
	private String[] nombresColum = {"Base de datos","Host"};
	private DefaultTableModel modeloTabla;
	private Object bbddSelecionada;
	private VentanaPrincipal miVentanaPrincipal;
				
	private InicioController miInicioController;
	public static final String PROP_MOSTRAR_BBDD = "mostrarBBDD";
	public static final String PROP_USAR_BBDD = "usarBBDD";	
	
    private List<Object []> dataLista = new ArrayList();  
	
    private ConexionBBDD conexionBBDD;
    Logger LOG = LoggerFactory.getLogger(SeleccionarBBDD.class);
    
	public SeleccionarBBDD(InicioController inicioController, VentanaPrincipal ventanaPrincipal ){//, ConexionBBDD conexion) {
		
		
		miInicioController = inicioController;
		setTitle("Seleccionar BBDDD");
		getContentPane().setLayout(null);
		
		conexionBBDD = miInicioController.getConexionBBDD();
		miVentanaPrincipal = ventanaPrincipal;		
		
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ ){
			Object [] dataArray = {" "," "};		    
		    dataLista.add(dataArray);	
		}
	    

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 11, 381, 200);
		getContentPane().add(scrollPane);
		
		//Transformacion del ArrayList en un Object [][]
		Object [][] data = new Object [dataLista.size()][];
		dataLista.toArray(data);			
		
		modeloTabla = new DefaultTableModel( data , nombresColum);
		

		tblBBDD = new JTable(modeloTabla);
		tblBBDD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblBBDD.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblBBDD);
		
		tblBBDD.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent e)
			{
				int fila = tblBBDD.rowAtPoint(e.getPoint());
				int columna = 0; //tblBBDD.columnAtPoint(e.getPoint);				
				if ((fila > -1)){
					bbddSelecionada = tblBBDD.getValueAt(fila, columna);
				}	
			}
		}
			
		);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnSeleccionar_actionPerformed(arg0);
			}
		});
		btnSeleccionar.setBounds(66, 222, 108, 23);
		getContentPane().add(btnSeleccionar);
		

		
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCerrar_actionPerformed(arg0);
			}
		});
		btnCerrar.setBounds(240, 222, 108, 23);
		getContentPane().add(btnCerrar);
		
		
		setVisible(true);  
		setSize(430,290);
		
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCerrar_actionPerformed(null);
					   }
					  });

	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();		
		List<Object []> dataListaBBDD;//Lista de bbdd
		int usarBBDD;// flag que nos indica si se pudo usar (1) o no la bbdd (0)		
		
		if (PROP_MOSTRAR_BBDD.equals(propertyName)){
			dataListaBBDD =  (ArrayList<Object []>) evt.getNewValue();
			dataLista.removeAll(dataLista);//Se borran datos antiguos de la lista
		    dataLista.addAll(0,dataListaBBDD);//Se añaden los nuevos datos, de la query, a la lista		    		    		    
		    actualizaTabla();

		} else if (PROP_USAR_BBDD.equals(propertyName)){			
			 usarBBDD = (Integer)evt.getNewValue();
			 mensajeUsarBBDD(usarBBDD);			
		}

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
	 * Muestra una ventana de acierto o error dependiendo del flag, que creamos al ejecutar USE DATABASE;
	 * @param usarBBDD	flag, es 1 ó 0
	 * */
	private void mensajeUsarBBDD(int usarBBDD)
	{
		int flagUsarBBDD = usarBBDD;
		
		if (flagUsarBBDD == 1){			
			AciertoSeleccionBBDD aciertoSeleccionBBDD = new AciertoSeleccionBBDD(this);
			LOG.info("La base de datos "+bbddSelecionada +" ha sido seleccionada correctamente");
		}else{			
			ErrorSeleccionBBDD errorSeleccionBBDD = new ErrorSeleccionBBDD();
			LOG.error("La base de datos "+bbddSelecionada +" no ha podido ser seleccionada");
		}
			
	}
	
	
	protected void do_btnSeleccionar_actionPerformed(ActionEvent arg0) {		
		miInicioController.usarBBDD(bbddSelecionada);	
	}
	
	
	public void do_btnCerrar_actionPerformed(ActionEvent arg0) {		
		dispose();//el objeto ya no se visualiza
		//Nota el conexionBBDD deberia ser manejado solo por el controlador,en InicioController deberia haber un 
		//metodo que se encargue de hacer esto.
		conexionBBDD.removePropertyChangeListener(this);//se remueve el el objeto de los listeners. Asi el GC de java borrara este objeto
		miVentanaPrincipal.setCrearVentanaSeleccionarBbdd(new MutableBoolean(true));
		
	}
	
	
}
