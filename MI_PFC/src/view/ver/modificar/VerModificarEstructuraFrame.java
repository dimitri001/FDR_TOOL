package view.ver.modificar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import view.modificar.ModificarEstructuraFrame;

import controller.ComboBoxController;
import controller.ConsultaController;
import controller.VerModificarController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import libreria.ElementoComboBox;
import libreria.EstructuraFrame;
import libreria.ListUniqueID;
import libreria.Manual;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosListaBBDD;
import libreria.Serie;

public class VerModificarEstructuraFrame  extends JFrame implements PropertyChangeListener {
	
	
	private JTable tblEstructuraFrame;
	private String[] nombresColum = {"ID Estructura Frame","Id Serie", "ID Modelo", "ID Fabricante", "Número de Subframes", "Tiempo por Subframe", "Palabras por Segundo", "Bits por Palabra", "Descripción"};	
	
	
	private JScrollPane scrollPane;	
	private DefaultTableModel modeloTablaEstructuraFrame;	
	private List <EstructuraFrame> listaEstructuraFrame = new ArrayList<EstructuraFrame>();
	
	
	private UUID miIdEstructuraFrame;//Identificador unico de cada objeto

	private ConsultaController miConsultaController;
	private VerModificarController miVerModificarController;
	
	public static final String PROP_ACTUALIZAR_ESTRUCTURA_FRAME = "actualizarEstructuraFrame";
	public static final String PROP_LISTAR_ESTRUCTURA_FRAME = "listarEstructuraFrame";
	
	private List<Object []> dataEstructuraFrame = new ArrayList();  
	private Object idEstructuraFrameSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
	private EstructuraFrame objetoEstructuraFrame;	
	
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
	private ListUniqueID listUniqueIdVerModificarEstructuraFrame;
	
		
	public VerModificarEstructuraFrame (VerModificarController verModificarController, ConsultaController consultaController, 
			ComboBoxController comboBoxController, UUID idEstructuraFrame,ListUniqueID listUniqueIdVerModificarEstructuraFrame) {
		
		miVerModificarController = verModificarController;		
		miConsultaController = consultaController;
		miComboBoxController = comboBoxController;
		miIdEstructuraFrame = idEstructuraFrame;
		this.listUniqueIdVerModificarEstructuraFrame= listUniqueIdVerModificarEstructuraFrame;		
		setTitle("Ver/Modificar Estructura de Frame");
		
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ )
		{
			Object [] dataArray = {"  ","  ","  ","  ","  ","  ","  ","  ","  "};
			    
			dataEstructuraFrame.add(dataArray);	
		}
		
		
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 157, 1036, 338);
		getContentPane().add(scrollPane);
		
		//Transformacion del ArrayList en un Object [][]
		//copia los datos de dataElemento a data
		Object [][] data = new Object [dataEstructuraFrame.size()][];
		dataEstructuraFrame.toArray(data);
		
		modeloTablaEstructuraFrame = new DefaultTableModel( data , nombresColum){
			
			@Override
			public boolean isCellEditable (int row, int column){
				
				//all cells false
				return false;
			}
		};
		
		tblEstructuraFrame =  new JTable(modeloTablaEstructuraFrame);
		tblEstructuraFrame.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
		tblEstructuraFrame.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblEstructuraFrame);
		
		tblEstructuraFrame.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent e)
			{
				int fila = tblEstructuraFrame.rowAtPoint(e.getPoint());
				int columna = 0; //tblBBDD.columnAtPoint(e.getPoint);
				
				if ((fila > -1)){	
					idEstructuraFrameSeleccionado = modeloTablaEstructuraFrame.getValueAt(fila, columna);
					
					obtenerEstructuraFrame(fila,(Integer) idEstructuraFrameSeleccionado);
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
		btnCancelar.setBounds(687, 511, 97, 23);
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
					miComboBoxController.comboBoxModelo(intIdFabricante, miIdEstructuraFrame);
					miConsultaController.listaEstructuraFrame(intIdFabricante, intIdModelo, intIdSerie, miIdEstructuraFrame, false);
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
				if (cbModelo.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();
					miComboBoxController.comboBoxSerie(intIdFabricante, intIdModelo, miIdEstructuraFrame);
					miConsultaController.listaEstructuraFrame(intIdFabricante, intIdModelo, intIdSerie, miIdEstructuraFrame, false);
				}
												
			}
		});

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnModificar_actionPerformed(arg0);
			}
		});
		btnModificar.setBounds(295, 511, 97, 23);
		getContentPane().add(btnModificar);
		
		JLabel lblNumeroDeSerie = new JLabel("N\u00FAmero de Serie");
		lblNumeroDeSerie.setBounds(45, 113, 130, 14);
		getContentPane().add(lblNumeroDeSerie);
		
		cbSerie = new JComboBox();
		cbSerie.setBounds(225, 107, 235, 20);
		getContentPane().add(cbSerie);
		
		cbSerie.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbSerie.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdSerie = elementoCbSerie.getIdElemento();
					miConsultaController.listaEstructuraFrame(intIdFabricante, intIdModelo, intIdSerie, miIdEstructuraFrame, false);					
				}
												
			}
		});

		setVisible(true); 		
		setSize(1096, 579);
		
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
	protected void do_btnModificar_actionPerformed(ActionEvent e) {
	
		int intIdEstructuraFrameSeleccionado = (Integer)idEstructuraFrameSeleccionado;
		//Asi solo funciona si se ha seleccionado una fila			
		if ( intIdEstructuraFrameSeleccionado > 0){
			boolean bl1 = listUniqueIdVerModificarEstructuraFrame.add(intIdEstructuraFrameSeleccionado);	
			if (bl1){
				//Se le pasa el cbFabricante, para que al actualizar la tabla desde miModificarXXX, lo haga desde el comboBox que el usuario a escogido
				ModificarEstructuraFrame miModificarEstructuraFrame = new ModificarEstructuraFrame(objetoEstructuraFrame, miVerModificarController, miConsultaController,miIdEstructuraFrame,cbFabricante, cbModelo, cbSerie, listUniqueIdVerModificarEstructuraFrame );
				miVerModificarController.getActualizarElemento().addPropertyChangeListener(PROP_ACTUALIZAR_ESTRUCTURA_FRAME, miModificarEstructuraFrame);
				idEstructuraFrameSeleccionado =0;			
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
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		ParametrosListaBBDD parametrosListaBBDD;
		
		ParametrosConsultasComboBoxBBDD parametrosConsultasCb ;
        dataComboBoxFabricante =new Vector<ElementoComboBox>();    
        dataComboBoxModelo =new Vector<ElementoComboBox>();

		
		if (PROP_CONSULTAR_FABRICANTE.equals(propertyName)){
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			
			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdEstructuraFrame)){
	
				intIdFabricante = 0;//Asi si no se actualiza el comboBox, el id =0
				dataComboBoxFabricante = parametrosConsultasCb.getDataElemento();// DataElemento es un Vector<ElementoComboBox>
				actualizarComboBox(cbFabricante,dataComboBoxFabricante );
									
			}		
		} else if (PROP_CONSULTAR_MODELO.equals(propertyName)){
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			
			//Se comprueba el id del objeto Serie
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

			//Se comprueba el id del objeto EstructuraFrame
			if (parametrosListaBBDD.getIdElemento().equals(miIdEstructuraFrame)){

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

		}		
	}

	
	public void obtenerEstructuraFrame (int fila, int intIdEstructuraFrame ) {
		
		int intIdSerie = (Integer) modeloTablaEstructuraFrame.getValueAt(fila, 1);//Seria el mismo que el del comboBox,  siempre que sea mayor que 0
		int intIdModelo = (Integer) modeloTablaEstructuraFrame.getValueAt(fila, 2);
		int intIdFabricante = (Integer)modeloTablaEstructuraFrame.getValueAt(fila, 3);
		int intNumeroSubframes = (Integer)modeloTablaEstructuraFrame.getValueAt(fila, 4);
		double dblTiempoSubframe = (Double)modeloTablaEstructuraFrame.getValueAt(fila, 5);
		int intPalabrasSubframe =  (Integer)modeloTablaEstructuraFrame.getValueAt(fila, 6);
		int intBitsPalabra =  (Integer)modeloTablaEstructuraFrame.getValueAt(fila, 7);		
		String strDescripcion = (String)modeloTablaEstructuraFrame.getValueAt(fila, 8);
		
		objetoEstructuraFrame = new EstructuraFrame(intIdEstructuraFrame, intIdSerie, intIdModelo, intIdFabricante, intNumeroSubframes, dblTiempoSubframe, intPalabrasSubframe, intBitsPalabra, strDescripcion);
	
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
		tblEstructuraFrame.getColumnModel().getColumn(6).setPreferredWidth(130);
		tblEstructuraFrame.getColumnModel().getColumn(7).setPreferredWidth(140);
		tblEstructuraFrame.getColumnModel().getColumn(8).setPreferredWidth(200);
		
	 }

	
	/*
	 * Cambiar el id de este objeto.
	 * @param idSerie, es una UUID (identificador unico)
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
