package view.baja;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import utils.MessageConfirmationUtils;
import utils.MessageUtils;

import controller.BajaController;
import controller.ConsultaController;

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



public class BajaFabricante extends JFrame implements PropertyChangeListener {
	
	private JTable tblFabricante;
	private String[] nombresColum = {"ID Fabricante","Nombre"," Web"};	
	private List <Integer> filasSelecionadas = new ArrayList<Integer>(); // Filas seleccionads en el JTable
	private int filasSeleccionadas2 []= null;//filas seleccionadas en la JTable
	private DefaultTableModel modeloTablaFabricante;
	private List <Fabricante> listaFabricante = new ArrayList<Fabricante>();
	private UUID miIdFabricante;//Identificador unico de cada objeto
	private ConsultaController miConsultaController;
	private BajaController miBajaController;
	public static final String PROP_ELIMINAR_FABRICANTE = "eliminarFabricante";
	public static final String PROP_LISTAR_FABRICANTE = "listarFabricante";
	private List<Object []> dataFabricante = new ArrayList();  
	private Object idFabricanteSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
	private List <Object> listaIdFabricanteSeleccionado; //Son los objetos pertenecientes a los elementos seleccionados en la tabla
	private ListUniqueID listUniqueIdVerModificarFabricante;
	public BajaFabricante(BajaController bajaController, ConsultaController consultaController, UUID idFabricante, ListUniqueID listUniqueIdVerModificarFabricante) {
		
		miBajaController = bajaController;		
		miConsultaController = consultaController;
		miIdFabricante = idFabricante;
		this.listUniqueIdVerModificarFabricante = listUniqueIdVerModificarFabricante;
		setTitle("Baja Fabricante");
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ ){
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
		tblFabricante.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblFabricante);		
		tblFabricante.addMouseListener(new MouseAdapter(){
			public void mouseClicked (MouseEvent e){
				int columna = 0; //tblBBDD.columnAtPoint(e.getPoint);
				int []filas =  tblFabricante.getSelectedRows(); 
				listaIdFabricanteSeleccionado = new ArrayList<Object>();
				//Varias Filas
				if (filas.length > 0){					
					for (int i = 0; i < filas.length ; i++){						
						//Se guarda la lista de los id seleccionados
						listaIdFabricanteSeleccionado.add(modeloTablaFabricante.getValueAt(filas[i], columna)); //inicializar ? new ArrayList();
					}					
				} 
					
			}
		}
			
		);
		
		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnBaja_actionPerformed(arg0);
			}
		});
		btnBaja.setBounds(167, 510, 105, 23);
		getContentPane().add(btnBaja);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(437, 510, 105, 23);
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
	 * Es llamado por el listener del boton baja. 
	 * 
	 * */
	
	protected void do_btnBaja_actionPerformed(ActionEvent arg0) {
		if (listaIdFabricanteSeleccionado.size() > 0){
			int response = MessageConfirmationUtils.deleteRowsMessageConfirmation();    
			if (response == JOptionPane.YES_OPTION) {
				if (!MessageUtils.mensajeSeleccionElementos(listUniqueIdVerModificarFabricante.containsElementList(listaIdFabricanteSeleccionado))){
					miBajaController.eliminarFabricante(listaIdFabricanteSeleccionado, miIdFabricante);
					miConsultaController.listaFabricante(miIdFabricante, true); //Actualizo la lista
					listaIdFabricanteSeleccionado.removeAll(listaIdFabricanteSeleccionado);		
				}					
			}
		}
	}

/*
 * Se encarga de cerrar la ventana y de quitar este objeto de la lista de listeners
 * de ConsultasBBDD y BorrarElemento
 * */	

	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {
		dispose();		
		miConsultaController.getConsultasBBDD().removePropertyChangeListener(PROP_LISTAR_FABRICANTE, this);
		miBajaController.getBorrarElemento().removePropertyChangeListener(PROP_ELIMINAR_FABRICANTE, this);
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		ParametrosListaBBDD parametrosListaBBDD;
		ParametrosEliminarElemento parametrosEliminarElemento;
		
		if (PROP_LISTAR_FABRICANTE.equals(propertyName)){
			idFabricanteSeleccionado =0;//Asi cada vez que se lista, se deselecciona el elemento seleccionado previamente
			parametrosListaBBDD = (ParametrosListaBBDD) evt.getNewValue();
			List<Object> dataFabricanteBBDD ;
			
			 if (parametrosListaBBDD.getIdElemento().equals(miIdFabricante)){//Se comprueba el id del objeto Modelo				
				// hacer lo necesario para refrescar la tabla
				dataFabricanteBBDD = (ArrayList<Object>) parametrosListaBBDD.getDataListaBBDD();
				dataFabricante.removeAll(dataFabricante);//Se borran datos antiguos de la lista
				//dataFabricante.addAll(0,dataFabricanteBBDD);
				
				//Se añaden los nuevos datos, de la query, a la lista
				Iterator it = dataFabricanteBBDD.iterator();
				
				while (it.hasNext()){
					
					Fabricante fabricante = (Fabricante) it.next();
					dataFabricante.add(fabricante.getArray());//Añade los Arrays cons los datos de fabricante a la List
				}				
				actualizaTabla((ArrayList<Object[]>) dataFabricante, modeloTablaFabricante);				
			}else if (parametrosListaBBDD.isActualizar())
				miConsultaController.listaFabricante(miIdFabricante, false);
		
			
		}else if (PROP_ELIMINAR_FABRICANTE.equals(propertyName)){
				 
				parametrosEliminarElemento = (ParametrosEliminarElemento) evt.getNewValue();	
				//Se comprueba el id del objeto Modelo
				if (parametrosEliminarElemento.getIdElemento().equals(miIdFabricante)){
					MessageUtils.mensajeEliminarSeleccionFila(parametrosEliminarElemento);
				}
		}

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
