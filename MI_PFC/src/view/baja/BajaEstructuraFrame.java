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
import libreria.EstructuraFrame;
import libreria.ListUniqueID;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosEliminarElemento;
import libreria.ParametrosListaBBDD;


public class BajaEstructuraFrame extends JFrame implements PropertyChangeListener{


	
	private JTable tblEstructuraFrame;
	
	private String[] nombresColum = {"ID Estructura Frame","Id Serie", "ID Modelo", "ID Fabricante", "Número de Subframes", "Tiempo por Subframe", "Palabras por Segundo", "Bits por Palabra", "Descripción"};	
	
	private JScrollPane scrollPane;	
	private DefaultTableModel modeloTablaEstructuraFrame;	
	private List <EstructuraFrame> listaEstructuraFrame = new ArrayList<EstructuraFrame>();
	
	
	private UUID miIdEstructuraFrame;//Identificador unico de cada objeto

	private ConsultaController miConsultaController;
	private BajaController miBajaController;
	
	public static final String PROP_ELIMINAR_ESTRUCTURA_FRAME = "eliminarEstructuraFrame";
	public static final String PROP_LISTAR_ESTRUCTURA_FRAME = "listarEstructuraFrame";
	
	
	private List<Object []> dataEstructuraFrame = new ArrayList();  
	private Object idEstructuraFrameSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
		
	
	private JComboBox cbFabricante;
	private Vector<ElementoComboBox> dataComboBoxFabricante;
	private ElementoComboBox elementoCbFabricante;
	
	private JComboBox cbModelo;
	private Vector<ElementoComboBox> dataComboBoxModelo;
	private ElementoComboBox elementoCbModelo;
	
	private JComboBox cbSerie;
	private Vector<ElementoComboBox> dataComboBoxSerie;
	private ElementoComboBox elementoCbSerie;

	private ComboBoxController miComboBoxController;
	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";
	public static final String PROP_CONSULTAR_MODELO = "consultarModelo";
	public static final String PROP_CONSULTAR_SERIE = "consultarSerie";
	
	private int intIdFabricante;
	private int intIdModelo;
	private int intIdSerie;
	private List <Object> listaIdEstructuraFrameSeleccionado; //Son los objetos pertenecientes a los elementos seleccionados en la tabla
	private ListUniqueID listUniqueIdVerModificarEstructuraFrame;	
	public BajaEstructuraFrame(BajaController bajaController, ConsultaController consultaController,
								ComboBoxController comboBoxController, UUID idEstructuraFrame, 
								ListUniqueID listUniqueIdVerModificarEstructuraFrame) {		
		miBajaController = bajaController;		
		miConsultaController = consultaController;
		miComboBoxController = comboBoxController;
		miIdEstructuraFrame = idEstructuraFrame;
		this.listUniqueIdVerModificarEstructuraFrame = listUniqueIdVerModificarEstructuraFrame;
		
		setTitle("Baja Estructura de Frame");
		
		
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ )
		{
			Object [] dataArray = {"  ","  ","  ","  ","  ","  ","  ","  ","  "};
			dataEstructuraFrame.add(dataArray);	
		}
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 149, 992, 351);
		getContentPane().add(scrollPane);
		
		Object [][] data = new Object [dataEstructuraFrame.size()][];
		dataEstructuraFrame.toArray(data);
		
		modeloTablaEstructuraFrame = new DefaultTableModel( data , nombresColum){			
			@Override
			public boolean isCellEditable (int row, int column){				
				//all cells false
				return false;
			}
		};

		tblEstructuraFrame = new JTable(modeloTablaEstructuraFrame);
		tblEstructuraFrame.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblEstructuraFrame);
		tblEstructuraFrame.addMouseListener(new MouseAdapter(){
			public void mouseClicked (MouseEvent e){
				int columna = 0; 	
				int []filas =  tblEstructuraFrame.getSelectedRows(); 
				listaIdEstructuraFrameSeleccionado = new ArrayList<Object>();
				//Varias Filas
				if (filas.length > 0){					
					for (int i = 0; i < filas.length ; i++){						
						//Se guarda la lista de los id seleccionados
						listaIdEstructuraFrameSeleccionado.add(modeloTablaEstructuraFrame.getValueAt(filas[i], columna)); //inicializar ? new ArrayList();
						Object aux = modeloTablaEstructuraFrame.getValueAt(filas[i], columna);						
					}					
				}
					
			}
		}
			
		);
		

		
		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setBounds(45, 31, 82, 14);
		getContentPane().add(lblFabricante);
		
		cbFabricante = new JComboBox();
		cbFabricante.setBounds(225, 25, 235, 20);
		getContentPane().add(cbFabricante);
		
		cbFabricante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbFabricante.getItemCount() > 0){ //Evitamos una exepcion si no hay objetos en el cbFabricante.
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();
					miComboBoxController.comboBoxModelo(intIdFabricante, miIdEstructuraFrame);
					miConsultaController.listaEstructuraFrame(intIdFabricante, intIdModelo,intIdSerie, miIdEstructuraFrame, false);
				}							
			}
		});
		
		JLabel lblModelo = new JLabel("Nombre Modelo");
		lblModelo.setBounds(45, 72, 130, 14);
		getContentPane().add(lblModelo);
		
		cbModelo = new JComboBox();
		cbModelo.setBounds(225, 66, 235, 20);
		getContentPane().add(cbModelo);
		
		cbModelo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbModelo.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbModelo.
				{
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();
					miComboBoxController.comboBoxSerie(intIdFabricante, intIdModelo, miIdEstructuraFrame);
					miConsultaController.listaEstructuraFrame(intIdFabricante, intIdModelo,intIdSerie, miIdEstructuraFrame, false);

				}
												
			}
		});
		
		

		JLabel lblNumeroDeSerie = new JLabel("Numero de Serie");
		lblNumeroDeSerie.setBounds(45, 113, 130, 14);
		getContentPane().add(lblNumeroDeSerie);
		
		cbSerie = new JComboBox();
		cbSerie.setBounds(225, 107, 235, 20);
		getContentPane().add(cbSerie);
		
		cbSerie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbSerie.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbSerie.
				{
					elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro de la serie del combobox
					intIdSerie = elementoCbSerie.getIdElemento();
					miConsultaController.listaEstructuraFrame(intIdFabricante, intIdModelo,intIdSerie, miIdEstructuraFrame, false);					
				}												
			}
		});
		
		
		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnBaja_actionPerformed(arg0);
			}
		});
		btnBaja.setBounds(272, 511, 97, 23);
		getContentPane().add(btnBaja);
		
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(641, 511, 97, 23);
		getContentPane().add(btnCancelar);
		
		
		setVisible(true);  
		setSize(1020, 589);
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
		if (listaIdEstructuraFrameSeleccionado.size() > 0){	
			int response = MessageConfirmationUtils.deleteRowsMessageConfirmation();    
			if (response == JOptionPane.YES_OPTION) {
				if (!MessageUtils.mensajeSeleccionElementos(listUniqueIdVerModificarEstructuraFrame.containsElementList(listaIdEstructuraFrameSeleccionado))){
					miBajaController.eliminarEstructuraFrame(listaIdEstructuraFrameSeleccionado, miIdEstructuraFrame);
					miConsultaController.listaEstructuraFrame(intIdFabricante,intIdModelo, intIdSerie,  miIdEstructuraFrame, true);//Actualizo la lista
					listaIdEstructuraFrameSeleccionado.removeAll(listaIdEstructuraFrameSeleccionado);			
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
		miConsultaController.getConsultasBBDD().removePropertyChangeListener(PROP_LISTAR_ESTRUCTURA_FRAME, this);
		miBajaController.getBorrarElemento().removePropertyChangeListener(PROP_ELIMINAR_ESTRUCTURA_FRAME, this);

	
	}


	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		ParametrosListaBBDD parametrosListaBBDD;
		ParametrosEliminarElemento parametrosEliminarElemento;
	
		ParametrosConsultasComboBoxBBDD parametrosConsultasCb ;
        dataComboBoxFabricante =new Vector<ElementoComboBox>();    
        dataComboBoxModelo =new Vector<ElementoComboBox>();

		
		if (PROP_CONSULTAR_FABRICANTE.equals(propertyName)){
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			
			//Se comprueba el id del objeto Estructura Frame
			if(parametrosConsultasCb.getIdElemento().equals(miIdEstructuraFrame)){
	
				intIdFabricante = 0;//Asi si no se actualiza el comboBox, el id =0
				dataComboBoxFabricante = parametrosConsultasCb.getDataElemento();// DataElemento es un Vector<ElementoComboBox>
				actualizarComboBox(cbFabricante,dataComboBoxFabricante );
									
			}		
		} else if (PROP_CONSULTAR_MODELO.equals(propertyName)){
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			
			//Se comprueba el id del objeto Estructura Frame
			if(parametrosConsultasCb.getIdElemento().equals(miIdEstructuraFrame)){
				intIdModelo =0;//Asi si no se actualiza el comboBox, el id =0
				dataComboBoxModelo = parametrosConsultasCb.getDataElemento();//(Vector<ElementoComboBox>)
				actualizarComboBox(cbModelo,dataComboBoxModelo );
			}
	
		} else if (PROP_CONSULTAR_SERIE.equals(propertyName)) {
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
						
			intIdSerie =0;//Asi si no se actualiza el comboBox, el id =0
			dataComboBoxSerie = parametrosConsultasCb.getDataElemento();//Es un Vector<ElementoComboBox>
			actualizarComboBox(cbSerie,dataComboBoxSerie );

			
		} else if (PROP_LISTAR_ESTRUCTURA_FRAME.equals(propertyName)){
		 
			idEstructuraFrameSeleccionado =0;//Asi cada vez que se lista, se deselecciona el elemento seleccionado previamente
			
			parametrosListaBBDD = (ParametrosListaBBDD) evt.getNewValue();
			
			List<Object> dataEstructuraFrameBBDD ;

			if (parametrosListaBBDD.getIdElemento().equals(miIdEstructuraFrame)){//Se comprueba el id del objeto Estructura Frame
				// hace lo necesario para refrescar la tabla
				dataEstructuraFrameBBDD = (ArrayList<Object>) parametrosListaBBDD.getDataListaBBDD();
				dataEstructuraFrame.removeAll(dataEstructuraFrame);//Se borran datos antiguos de la lista
					
				//Se añaden los nuevos datos, de la query, a la lista
				Iterator it = dataEstructuraFrameBBDD.iterator();
				
				while (it.hasNext()){
					
					EstructuraFrame estructuraFrame = (EstructuraFrame) it.next();
					dataEstructuraFrame.add(estructuraFrame.getArray());//Añade los Arrays cons los datos de fabricante a la List
				}
				
				actualizaTabla((ArrayList<Object[]>) dataEstructuraFrame, modeloTablaEstructuraFrame);
				
			}else if (parametrosListaBBDD.isActualizar())
				miConsultaController.listaEstructuraFrame(intIdFabricante, intIdModelo, intIdSerie, miIdEstructuraFrame, false);
	
			
		}else if (PROP_ELIMINAR_ESTRUCTURA_FRAME.equals(propertyName)){
				 
				parametrosEliminarElemento = (ParametrosEliminarElemento) evt.getNewValue();
				
				//Se comprueba el id del objeto Modelo
				if (parametrosEliminarElemento.getIdElemento().equals(miIdEstructuraFrame)){
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
		
	    for(int i=0;i<dataComboBox.size();i=i+1)
	    {
	    	elementoComboBox = new ElementoComboBox(dataComboBox.get(i).getIdElemento(), dataComboBox.get(i).getNombreElemento());
	    	
	    	comboBox.addItem(elementoComboBox);
	    	
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
		
		//Set width of a column 
		tblEstructuraFrame.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblEstructuraFrame.getColumnModel().getColumn(0).setPreferredWidth(130);
		tblEstructuraFrame.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblEstructuraFrame.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblEstructuraFrame.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblEstructuraFrame.getColumnModel().getColumn(4).setPreferredWidth(140);
		tblEstructuraFrame.getColumnModel().getColumn(5).setPreferredWidth(140);
		tblEstructuraFrame.getColumnModel().getColumn(6).setPreferredWidth(100);
		tblEstructuraFrame.getColumnModel().getColumn(7).setPreferredWidth(140);
		tblEstructuraFrame.getColumnModel().getColumn(8).setPreferredWidth(140);
		
		
	}

	
	/*
	 * Cambiar el id de este objeto.
	 * @param idEstructuraFrame, es una UUID (identificador unico)
	 *  */
	
	public void setIdEstructuraFrame(UUID idEstructuraFrame){
		
		miIdEstructuraFrame = idEstructuraFrame;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdEstructuraFrame(){
		
		return miIdEstructuraFrame;
	}
}
