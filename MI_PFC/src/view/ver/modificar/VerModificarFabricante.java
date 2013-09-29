
package view.ver.modificar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import view.modificar.ModificarFabricante;

import controller.BajaController;
import controller.ConsultaController;
import controller.VerModificarController;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import libreria.*;


public class VerModificarFabricante extends JFrame implements PropertyChangeListener {
	
	private JTable tblFabricante;
	private String[] nombresColum = {"ID Fabricante","Nombre"," Web"};
	
	private List <Integer> filasSelecionadas = new ArrayList<Integer>(); // Filas seleccionads en el JTable
	private int filasSeleccionadas2 []= null;//filas seleccionadas en la JTable
	
	private DefaultTableModel modeloTablaFabricante;

	
	private List <Fabricante> listaFabricante = new ArrayList<Fabricante>();
	
	
	private UUID miIdFabricante;//Identificador unico de cada objeto

	private ConsultaController miConsultaController;
	private VerModificarController miVerModificarController;
	
	public static final String PROP_ACTUALIZAR_FABRICANTE = "actualizarFabricante";
	public static final String PROP_LISTAR_FABRICANTE = "listarFabricante";


	private List<Object []> dataFabricante = new ArrayList();  

	private Object idFabricanteSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
	private Fabricante objetoFabricante;
	private ListUniqueID listUniqueIdVerModificarFabricante;//Lista compartida para garantizar la atomicidad del acceso a la informacion de la fila escogida
		
	public VerModificarFabricante(VerModificarController verModificarController, ConsultaController consultaController, 
								 UUID idFabricante, ListUniqueID listUniqueIdVerModificarFabricante) {
		
		miVerModificarController = verModificarController;		
		miConsultaController = consultaController;
		miIdFabricante = idFabricante;
		this.listUniqueIdVerModificarFabricante = listUniqueIdVerModificarFabricante;
		setTitle("Ver/Modificar Fabricante");

		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ )
		{
			Object [] dataArray = {" "," "," "};
		    
		    dataFabricante.add(dataArray);	
		}
	    
		
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 691, 488);
		getContentPane().add(scrollPane);
		
		//Transformacion del ArrayList en un Object [][]
		//copia los datos de dataElemento a data
		Object [][] data = new Object [dataFabricante.size()][];
		dataFabricante.toArray(data);			
		
		modeloTablaFabricante = new DefaultTableModel( data , nombresColum){
			
			@Override
			public boolean isCellEditable (int row, int column){
				
				//all cells false
				return false;
			}
		};
		
		tblFabricante = new JTable(modeloTablaFabricante);
		tblFabricante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		tblFabricante.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblFabricante);
		//tblFabricante.setEnabled(false);
		
		
		tblFabricante.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent e)
			{
				int fila = tblFabricante.rowAtPoint(e.getPoint());
				int columna = 0; //tblBBDD.columnAtPoint(e.getPoint);
				
				if ((fila > -1)){
					idFabricanteSeleccionado = modeloTablaFabricante.getValueAt(fila, columna);
					obtenerFabricante(fila);
				}	
					
			}
		}
			
		);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnModificar_actionPerformed(arg0);
			}
		});
		btnModificar.setBounds(167, 510, 105, 23);
		getContentPane().add(btnModificar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(439, 510, 105, 23);
		getContentPane().add(btnCancelar);
		
		setVisible(true);  
		setSize(727, 584);
		
		
		
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });
		
	}
	
	
	/*
	 * Se realizan las acciones necesarias para que el elemento escogido
	 * se pase a la ventana respectiva ModificarXXX
	 * */
	protected void do_btnModificar_actionPerformed(ActionEvent arg0) {
		
		//Asi solo funciona si se ha seleccionado una fila	
		int intIdFabricanteSeleccionado = (Integer)idFabricanteSeleccionado;
		if ( intIdFabricanteSeleccionado > 0){
			boolean bl1 = listUniqueIdVerModificarFabricante.add(intIdFabricanteSeleccionado);	
			if (bl1){
				ModificarFabricante miModificarFabricante = new ModificarFabricante(objetoFabricante, miVerModificarController, miConsultaController,miIdFabricante, listUniqueIdVerModificarFabricante );
				miVerModificarController.getActualizarElemento().addPropertyChangeListener(PROP_ACTUALIZAR_FABRICANTE, miModificarFabricante);
				idFabricanteSeleccionado =0;
			}
		}
	}
	
	
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {		
		dispose();		
		miConsultaController.getConsultasBBDD().removePropertyChangeListener(PROP_LISTAR_FABRICANTE, this);		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();		
		ParametrosListaBBDD parametrosListaBBDD;	
		if (PROP_LISTAR_FABRICANTE.equals(propertyName)){		 
			idFabricanteSeleccionado =0;//Asi cada vez que se lista, se deselecciona el elemento seleccionado previamente			
			parametrosListaBBDD = (ParametrosListaBBDD) evt.getNewValue();			
			List<Object> dataFabricanteBBDD ;
			//Se comprueba el id del objeto 
			if (parametrosListaBBDD.getIdElemento().equals(miIdFabricante)){
				// hacer lo necesario para refrescar la tabla
				dataFabricanteBBDD = (ArrayList<Object>) parametrosListaBBDD.getDataListaBBDD();
				dataFabricante.removeAll(dataFabricante);//Se borran datos antiguos de la lista
				//Se añaden los nuevos datos, de la query, a la lista
				Iterator it = dataFabricanteBBDD.iterator();				
				while (it.hasNext()){					
					Fabricante fabricante = (Fabricante) it.next();
					dataFabricante.add(fabricante.getArray());//Añade los Arrays cons los datos de fabricante a la List
				}				
				actualizaTabla((ArrayList<Object[]>) dataFabricante, modeloTablaFabricante);
			}else if (parametrosListaBBDD.isActualizar())
			miConsultaController.listaFabricante(miIdFabricante, false);

		}		
	}
	
	public void obtenerFabricante (int fila ) {		
			
		String strNombre = (String)modeloTablaFabricante.getValueAt(fila, 1);
		String strWeb = (String)modeloTablaFabricante.getValueAt(fila, 2);			
		objetoFabricante = new Fabricante((Integer)idFabricanteSeleccionado, strNombre, strWeb);	
	}
	
	
	/*
	 * Actualiza el Jtable con las bases de datos que puede usar el usuario. 
	 * */
	private void actualizaTabla(ArrayList<Object []> dataLista, DefaultTableModel modeloTabla)
	{
		
		Object [][] data1 = new Object [dataLista.size()][];
		dataLista.toArray(data1);
		modeloTabla.setDataVector(data1 , nombresColum);
		
		
	 }
	

	/*
	 * Cambiar el id de este objeto.
	 * @param idFabricante, es una UUID (identificador unico)
	 *  */
	
	public void setIdFabricante(UUID idFabricante){
		
		miIdFabricante = idFabricante;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdFabricante(){
		
		return miIdFabricante;
	}

	
}
