package view.inicio;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MessageConfirmationUtils;
import view.acierto.AciertoEliminarEstructuraBBDD;
import view.error.ErrorEliminarEstructuraBBDD;
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

public class EliminarEstructuraBBDD extends JFrame implements PropertyChangeListener {

	private JTable tblBBDD;
	private String[] nombresColum = { "Base de datos", "Host" };
	private DefaultTableModel modeloTabla;
	private Object bbddSelecionada;

	private InicioController miInicioController;
	public static final String PROP_MOSTRAR_BBDD = "mostrarBBDD";
	public static final String PROP_USAR_BBDD = "usarBBDD";
	public static final String PROP_ELIMINAR_BBDD = "eliminarBBDD";

	private List<Object[]> dataLista = new ArrayList();

    private ConexionBBDD conexionBBDD;
    private UUID miIdEliminarEstructuraBBDD;//Identificador unico de cada objeto
    Logger LOG = LoggerFactory.getLogger(EliminarEstructuraBBDD.class);
	public EliminarEstructuraBBDD(InicioController inicioController, UUID idEliminarEstructuraBBDD) {//, ConexionBBDD conexion) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		miInicioController = inicioController;
		miIdEliminarEstructuraBBDD = idEliminarEstructuraBBDD;
		setTitle("Eliminar Estructura de BBDDD");
		getContentPane().setLayout(null);
		
		conexionBBDD = miInicioController.getConexionBBDD();

		// Inicializacion de los arrays del arrayList
		for (int i = 0; i < 1; i++) {
			Object[] dataArray = { " ", " " };

			dataLista.add(dataArray);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 21, 362, 191);
		getContentPane().add(scrollPane);

		// Transformacion del ArrayList en un Object [][]
		Object[][] data = new Object[dataLista.size()][];
		dataLista.toArray(data);

		modeloTabla = new DefaultTableModel(data, nombresColum);

		tblBBDD = new JTable(modeloTabla);
		tblBBDD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblBBDD.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblBBDD);

		tblBBDD.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = tblBBDD.rowAtPoint(e.getPoint());
				int columna = 0;

				if ((fila > -1)) {
					bbddSelecionada = tblBBDD.getValueAt(fila, columna);
				}

			}
		}

		);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnEliminar_actionPerformed(arg0);
			}
		});

		btnEliminar.setBounds(71, 226, 89, 23);
		getContentPane().add(btnEliminar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});

		btnCancelar.setBounds(235, 226, 89, 23);
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

		List<Object[]> dataListaBBDD;// Lista de bbdd
		if (PROP_MOSTRAR_BBDD.equals(propertyName)) {
			dataListaBBDD = (ArrayList<Object[]>) evt.getNewValue();
			dataLista.removeAll(dataLista);// Se borran datos antiguos de la lista
			dataLista.addAll(0, dataListaBBDD);// Se añaden los nuevos datos, de la query, a la lista
			actualizaTabla();
		} else if (PROP_ELIMINAR_BBDD.equals(propertyName)) {
			ParametrosConexionBBDD parametrosEliminarEstructuraBBDD = (ParametrosConexionBBDD)evt.getNewValue();
			if (parametrosEliminarEstructuraBBDD.getIdElemento().equals(miIdEliminarEstructuraBBDD))			
				mensajeEliminarEstructuraBBDD(parametrosEliminarEstructuraBBDD);
		}		
	}

	/*
	 * Actualiza el Jtable con las bases de datos que puede usar el usuario.
	 */
	private void actualizaTabla() {

		Object[][] data1 = new Object[dataLista.size()][];
		dataLista.toArray(data1);
		modeloTabla.setDataVector(data1, nombresColum);

	}

	/*
	 * Muestra una ventana de acierto o error dependiendo del flag, que creamos
	 * al ejecutar USE DATABASE;
	 */
	private void mensajeEliminarEstructuraBBDD(ParametrosConexionBBDD parametros) {
		ParametrosConexionBBDD parametrosEliminarEstructuraBBDD = parametros;
		
		// Entra en el if, si hay error usando la bbdd
		if (parametrosEliminarEstructuraBBDD.getUsarBBDD() == 0) {
			ErrorSeleccionBBDD errorSeleccionBBDD = new ErrorSeleccionBBDD();

		}else{
			if(parametrosEliminarEstructuraBBDD.getHayTablas()== 0){ //Se borra la bbdd AciertoEliminarEstructuraBBDD
			   AciertoEliminarEstructuraBBDD aciertoEliminarEstructuraBBDD = new AciertoEliminarEstructuraBBDD();
			   LOG.info("Se borra la estructura en la bbdd = "+ bbddSelecionada);
			  }else{ //Se borra la bbdd AciertoEliminarEstructuraBBDD
				  ErrorEliminarEstructuraBBDD errorEliminarEstructuraBBDD = new ErrorEliminarEstructuraBBDD();				  
			  }

		}

	}

	
	protected void do_btnEliminar_actionPerformed(ActionEvent arg0) {
		int response = MessageConfirmationUtils.deleteBbddMessageConfirmation();
		if (response == JOptionPane.YES_OPTION) {
			miInicioController.eliminarEstructuraBBDD(bbddSelecionada, miIdEliminarEstructuraBBDD);
		}
	}

	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {

		dispose();//el objeto ya no se visualiza
		conexionBBDD.removePropertyChangeListener(this);//se remueve el el objeto de los listeners
		//Asi ek GC de java borrara este objeto

	}


}
