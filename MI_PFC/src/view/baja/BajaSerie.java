package view.baja;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import utils.MessageConfirmationUtils;
import utils.MessageUtils;

import controller.BajaController;
import controller.ComboBoxController;
import controller.ConsultaController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import libreria.ElementoComboBox;
import libreria.ListUniqueID;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosEliminarElemento;
import libreria.ParametrosListaBBDD;
import libreria.Serie;

public class BajaSerie extends JFrame implements PropertyChangeListener{
	
	private JTable tblSerie;
	private String[] nombresColum = {"ID Serie", "ID Modelo", "ID Fabricante", "Número de Serie","Descripcion", "Fecha de Fabricación", "Año de lanzamiento" };

	private JScrollPane scrollPane;	
	private DefaultTableModel modeloTablaSerie;	
	private List <Serie> listaSerie = new ArrayList<Serie>();
	
	
	private UUID miIdSerie;//Identificador unico de cada objeto

	private ConsultaController miConsultaController;
	private BajaController miBajaController;
	
	public static final String PROP_ELIMINAR_SERIE = "eliminarSerie";
	public static final String PROP_LISTAR_SERIE = "listarSerie";
	
	private List<Object []> dataSerie = new ArrayList();  
	private Object idSerieSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
		
	
	private JComboBox cbFabricante;
	private Vector<ElementoComboBox> dataComboBoxFabricante;
	private ElementoComboBox elementoCbFabricante;
	
	private JComboBox cbModelo;
	private Vector<ElementoComboBox> dataComboBoxModelo;
	private ElementoComboBox elementoCbModelo;
	

	private ComboBoxController miComboBoxController;
	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";
	public static final String PROP_CONSULTAR_MODELO = "consultarModelo";

	private int intIdFabricante;
	private int intIdModelo;
	private List <Object> listaIdSerieSeleccionado; //Son los objetos pertenecientes a los elementos seleccionados en la tabla
	private ListUniqueID listUniqueIdVerModificarSerie;
	
	public BajaSerie(BajaController bajaController, ConsultaController consultaController,
					ComboBoxController comboBoxController, UUID idSerie, 
					ListUniqueID listUniqueIdVerModificarSerie) {
		miBajaController = bajaController;		
		miConsultaController = consultaController;
		miComboBoxController = comboBoxController;
		miIdSerie = idSerie;
		this.listUniqueIdVerModificarSerie = listUniqueIdVerModificarSerie;
		setTitle("Baja Serie");
		
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ ){
			Object [] dataArray = {"  ","  ","  ","  ","  ","  "," "};
			dataSerie.add(dataArray);	
		}
		getContentPane().setLayout(null);		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 110, 803, 391);
		getContentPane().add(scrollPane);
		
		//Transformacion del ArrayList en un Object [][]
		//copia los datos de dataElemento a data
		Object [][] data = new Object [dataSerie.size()][];
		dataSerie.toArray(data);			
		
		modeloTablaSerie = new DefaultTableModel( data , nombresColum){
			@Override
			public boolean isCellEditable (int row, int column){
				//all cells false
				return false;
			}
		};
		tblSerie = new JTable(modeloTablaSerie);
		tblSerie.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblSerie);
		tblSerie.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent e){
				int columna = 0; 	
				int []filas =  tblSerie.getSelectedRows(); 
				listaIdSerieSeleccionado = new ArrayList<Object>();
				//Varias Filas
				if (filas.length > 0){					
					for (int i = 0; i < filas.length ; i++){						
						//Se guarda la lista de los id seleccionados
						listaIdSerieSeleccionado.add(modeloTablaSerie.getValueAt(filas[i], columna)); //inicializar ? new ArrayList();
						Object aux = modeloTablaSerie.getValueAt(filas[i], columna);				
					}					
				}
			}
		}	
		);
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		
		btnCancelar.setBounds(491, 512, 97, 23);
		getContentPane().add(btnCancelar);
		
		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setBounds(45, 31, 82, 14);
		getContentPane().add(lblFabricante);
		
		cbFabricante = new JComboBox();
		cbFabricante.setBounds(225, 25, 235, 20);
		getContentPane().add(cbFabricante);

		cbFabricante.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbFabricante.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();
					intIdModelo =0;					
					miComboBoxController.comboBoxModelo(intIdFabricante, miIdSerie);
					miConsultaController.listaSerie(intIdFabricante, intIdModelo, miIdSerie,false);
				}
												
			}
		});
		
		JLabel label = new JLabel("Nombre Modelo");
		label.setBounds(45, 69, 130, 14);
		getContentPane().add(label);
		
		cbModelo = new JComboBox();
		cbModelo.setBounds(225, 66, 235, 20);
		getContentPane().add(cbModelo);
		
		cbModelo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbModelo.getItemCount() > 0){ //Evitamos una exepcion si no hay objetos en el cbFabricante.
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();					
					miConsultaController.listaSerie(intIdFabricante, intIdModelo, miIdSerie,false);
				}							
			}
		});
		
		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnBaja_actionPerformed(arg0);
			}
		});
		btnBaja.setBounds(197, 512, 97, 23);
		getContentPane().add(btnBaja);
		
		setVisible(true);  
		setSize(839, 583);		
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
		//Asi solo funciona si se ha seleccionado alguna fila	
		if (listaIdSerieSeleccionado.size() > 0){	
			int response = MessageConfirmationUtils.deleteRowsMessageConfirmation();    
			if (response == JOptionPane.YES_OPTION) {
				if (!MessageUtils.mensajeSeleccionElementos(listUniqueIdVerModificarSerie.containsElementList(listaIdSerieSeleccionado))){
					miBajaController.eliminarSerie(listaIdSerieSeleccionado, miIdSerie);
					miConsultaController.listaSerie(intIdFabricante,intIdModelo, miIdSerie,true);//Actualizo la lista
					listaIdSerieSeleccionado.removeAll(listaIdSerieSeleccionado);			
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
		
		miComboBoxController.getConsultasComboBoxBBDD().removePropertyChangeListener(this);		
		miConsultaController.getConsultasBBDD().removePropertyChangeListener(PROP_LISTAR_SERIE, this);
		miBajaController.getBorrarElemento().removePropertyChangeListener(PROP_ELIMINAR_SERIE, this);

	}

	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
		String propertyName = evt.getPropertyName();
		
		ParametrosListaBBDD parametrosListaBBDD;
		ParametrosEliminarElemento parametrosEliminarElemento;
	
		ParametrosConsultasComboBoxBBDD parametrosConsultasCb ;
        dataComboBoxFabricante =new Vector<ElementoComboBox>();    
        dataComboBoxModelo =new Vector<ElementoComboBox>();

		
		if (PROP_CONSULTAR_FABRICANTE.equals(propertyName)){
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			
			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdSerie)){
	
				intIdFabricante = 0;//Asi si no se actualiza el comboBox, el id =0
				dataComboBoxFabricante = parametrosConsultasCb.getDataElemento();// DataElemento es un Vector<ElementoComboBox>
				actualizarComboBox(cbFabricante,dataComboBoxFabricante );
									
			}		
		} else if (PROP_CONSULTAR_MODELO.equals(propertyName)){
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			
			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdSerie)){
				intIdModelo =0;//Asi si no se actualiza el comboBox, el id =0
				dataComboBoxModelo = parametrosConsultasCb.getDataElemento();//(Vector<ElementoComboBox>)
				actualizarComboBox(cbModelo,dataComboBoxModelo );
			}
	
		} else if (PROP_LISTAR_SERIE.equals(propertyName)){
			idSerieSeleccionado=0;//Asi cada vez que se lista, se deselecciona el elemento seleccionado previamente
			parametrosListaBBDD = (ParametrosListaBBDD) evt.getNewValue();
			List<Object> dataSerieBBDD ;
			if (parametrosListaBBDD.getIdElemento().equals(miIdSerie)){//Se comprueba el id del objeto 
				// hace lo necesario para refrescar la tabla
				dataSerieBBDD = (ArrayList<Object>) parametrosListaBBDD.getDataListaBBDD();
				dataSerie.removeAll(dataSerie);//Se borran datos antiguos de la lista					
				//Se añaden los nuevos datos, de la query, a la lista
				Iterator it = dataSerieBBDD.iterator();				
				while (it.hasNext()){					
					Serie serie = (Serie) it.next();
					dataSerie.add(serie.getArray());//Añade los Arrays cons los datos de fabricante a la List
				}				
				actualizaTabla((ArrayList<Object[]>) dataSerie, modeloTablaSerie);
			}else if (parametrosListaBBDD.isActualizar())
				miConsultaController.listaSerie(intIdFabricante, intIdModelo, miIdSerie,false);
		}else if (PROP_ELIMINAR_SERIE.equals(propertyName)){parametrosEliminarElemento = (ParametrosEliminarElemento) evt.getNewValue();
				//Se comprueba el id del objeto Modelo
				if (parametrosEliminarElemento.getIdElemento().equals(miIdSerie)){
					MessageUtils.mensajeEliminarSeleccionFila(parametrosEliminarElemento);
				}
		}
	}

	
	/*
	 * Se encarga de actualizar el comboBox con los datos pasados en dataComboBox
	 * @param comboBox, es el comboBox en el cual se quiere hacer la actualizacion
	 * @param dataComboBox, es el vector que contiene los elementos con los que se actualizará el comboBox  
	 * */
	private void actualizarComboBox(JComboBox comboBox,Vector<ElementoComboBox> dataComboBox ){
		comboBox.removeAllItems();
		ElementoComboBox elementoComboBox; 
		
		elementoComboBox = new ElementoComboBox(0,"TODO");
		comboBox.addItem(elementoComboBox);
		
	    for(int i=0;i<dataComboBox.size();i=i+1){
	    	elementoComboBox = new ElementoComboBox(dataComboBox.get(i).getIdElemento(), dataComboBox.get(i).getNombreElemento());
	    	comboBox.addItem(elementoComboBox);
	   }

		
	}
	
	/*
	 * Actualiza el Jtable con las bases de datos que puede usar el usuario. 
	 * */
	private void actualizaTabla(ArrayList<Object []> dataLista, DefaultTableModel modeloTabla){
		Object [][] data1 = new Object [dataLista.size()][];
		dataLista.toArray(data1);
		modeloTabla.setDataVector(data1 , nombresColum);
		//Set width of a column 
		tblSerie.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblSerie.getColumnModel().getColumn(0).setPreferredWidth(90);
		tblSerie.getColumnModel().getColumn(1).setPreferredWidth(90);
		tblSerie.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblSerie.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblSerie.getColumnModel().getColumn(4).setPreferredWidth(140);
		tblSerie.getColumnModel().getColumn(5).setPreferredWidth(140);
		tblSerie.getColumnModel().getColumn(6).setPreferredWidth(140);	
	 }

	
	/*
	 * Cambiar el id de este objeto.
	 * @param idSerie, es una UUID (identificador unico)
	 *  */
	
	public void setIdSerie(UUID idSerie){
		
		miIdSerie = idSerie;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdSerie(){
		
		return miIdSerie;
	}


}
