package view.ver.modificar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
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

import view.modificar.ModificarSerie;

import libreria.ElementoComboBox;
import libreria.ListUniqueID;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosListaBBDD;
import libreria.Serie;

import controller.ComboBoxController;
import controller.ConsultaController;
import controller.VerModificarController;


public class VerModificarSerie extends JFrame implements PropertyChangeListener {
	
	
	private JTable tblSerie;
	private String[] nombresColum = {"ID Serie", "ID Modelo", "ID Fabricante", "Número de Serie","Descripcion", "Fecha de Fabricación", "Año de lanzamiento" };

	private JScrollPane scrollPane;	
	private DefaultTableModel modeloTablaSerie;	
	private List <Serie> listaSerie = new ArrayList<Serie>();
	
	
	private UUID miIdSerie;//Identificador unico de cada objeto

	private ConsultaController miConsultaController;
	private VerModificarController miVerModificarController;
	
	public static final String PROP_ACTUALIZAR_SERIE = "actualizarSerie";
	public static final String PROP_LISTAR_SERIE = "listarSerie";
	
	private List<Object []> dataSerie = new ArrayList();  
	private Object idSerieSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
	private Serie objetoSerie;	
	
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
	
	private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
	private ListUniqueID listUniqueIdVerModificarSerie;
	
	public VerModificarSerie(VerModificarController verModificarController, ConsultaController consultaController, 
			ComboBoxController comboBoxController, UUID idSerie, ListUniqueID listUniqueIdVerModificarSerie) {
		
		miVerModificarController = verModificarController;		
		miConsultaController = consultaController;
		miComboBoxController = comboBoxController;
		miIdSerie = idSerie;
		this.listUniqueIdVerModificarSerie = listUniqueIdVerModificarSerie;
		setTitle("Ver/Modificar Serie");

		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ )
		{
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
		tblSerie.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		tblSerie.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblSerie);
		
		tblSerie.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent e)
			{
				int fila = tblSerie.rowAtPoint(e.getPoint());
				int columna = 0; //tblBBDD.columnAtPoint(e.getPoint);
				
				if ((fila > -1)){
					idSerieSeleccionado = modeloTablaSerie.getValueAt(fila, columna);					
					obtenerSerie(fila,(Integer) idSerieSeleccionado);
				}					
			}
		}			
		);
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnCancelar_actionPerformed(e);
			}
		});
		btnCancelar.setBounds(515, 511, 97, 23);
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
					miConsultaController.listaSerie(intIdFabricante, intIdModelo, miIdSerie, false);
				}
												
			}
		});
		
		JLabel lblModelo = new JLabel("Nombre Modelo");
		lblModelo.setBounds(45, 69, 130, 14);
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
					miConsultaController.listaSerie(intIdFabricante, intIdModelo, miIdSerie,false);
				}
												
			}
		});
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnModificar_actionPerformed(e);
			}
		});
		btnModificar.setBounds(209, 511, 97, 23);
		getContentPane().add(btnModificar);
		
		setVisible(true);  
		//setSize(653, 417);
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
	 * Se realizan las acciones necesarias para que el elemento escogido
	 * se pase a la ventana respectiva ModificarXXX
	 * */
	protected void do_btnModificar_actionPerformed(ActionEvent e) {
	
		int intIdSerieSeleccionado = (Integer)idSerieSeleccionado;
	
		//Asi solo funciona si se ha seleccionado una fila			
		if ( intIdSerieSeleccionado > 0){
			boolean bl1 = listUniqueIdVerModificarSerie.add(intIdSerieSeleccionado);	
			if (bl1){
				//Se le pasa el cbFabricante, para que al actualizar la tabla desde miModificarXXX, lo haga desde el comboBox que el usuario a escogido
				ModificarSerie miModificarSerie = new ModificarSerie(objetoSerie, miVerModificarController, miConsultaController,miIdSerie,cbFabricante, cbModelo, listUniqueIdVerModificarSerie );
				miVerModificarController.getActualizarElemento().addPropertyChangeListener(PROP_ACTUALIZAR_SERIE, miModificarSerie);
				idSerieSeleccionado =0;
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

		}		
	}

	
	public void obtenerSerie (int fila, int intIdSerie ) {
		
		int intIdModelo = (Integer) modeloTablaSerie.getValueAt(fila, 1);//Seria el mismo que el del comboBox,  siempre que sea mayor que 0
		int intIdFabricante = (Integer)modeloTablaSerie.getValueAt(fila, 2);
		String strNumeroSerie = (String)modeloTablaSerie.getValueAt(fila, 3);
		String strDescripcion = (String)modeloTablaSerie.getValueAt(fila, 4);
		String strFechaFabricacion = formatoDelTexto.format(modeloTablaSerie.getValueAt(fila, 5)); 
		String strAnyoLanzamiento = (String)modeloTablaSerie.getValueAt(fila, 6);	
		
		objetoSerie = new Serie(intIdSerie, intIdModelo, intIdFabricante, strNumeroSerie, strDescripcion, strFechaFabricacion, strAnyoLanzamiento);
			
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
