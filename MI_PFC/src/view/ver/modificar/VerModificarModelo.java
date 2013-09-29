package view.ver.modificar;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import view.modificar.ModificarModelo;

import controller.BajaController;
import controller.ComboBoxController;
import controller.ConsultaController;
import controller.VerModificarController;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import libreria.ElementoComboBox;
import libreria.ListUniqueID;
import libreria.Modelo;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosListaBBDD;

public class VerModificarModelo extends JFrame implements PropertyChangeListener {
	

	private JTable tblModelo;

	private String[] nombresColum = {"ID Modelo",  "ID Fabricante",  "Nombre Modelo", "Descripción", "Fecha de Fabricación", "Año de lanzamiento" };

	private JScrollPane scrollPane;	
	private DefaultTableModel modeloTablaModelo;	
	private List<Modelo> listaModelo = new ArrayList<Modelo>();
	
	
	private UUID miIdModelo;//Identificador unico de cada objeto

	private ConsultaController miConsultaController;
	private VerModificarController miVerModificarController;
	
	public static final String PROP_ACTUALIZAR_MODELO = "actualizarModelo";
	public static final String PROP_LISTAR_MODELO = "listarModelo";
	
	private List<Object []> dataModelo = new ArrayList();	
	private Object idModeloSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
	private Modelo objetoModelo;
	
	
	private JComboBox cbFabricante;
	private Vector<ElementoComboBox> dataComboBoxFabricante;
	private ElementoComboBox elementoCbFabricante;
	

	private ComboBoxController miComboBoxController;
	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";

	private int intIdFabricante;//Id del comboBox
	private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
	private ListUniqueID listUniqueIdVerModificarModelo;//Lista compartida para garantizar la atomicidad del acceso a la informacion de la fila escogida
		
	public VerModificarModelo(VerModificarController verModificarController, ConsultaController consultaController, 
							  ComboBoxController comboBoxController, UUID idModelo, ListUniqueID listUniqueIdVerModificarModelo) {
		
		miVerModificarController = verModificarController;		
		miConsultaController = consultaController;
		miIdModelo = idModelo;
		this.listUniqueIdVerModificarModelo = listUniqueIdVerModificarModelo;
		setTitle("Ver/Modificar Modelo");
	
		
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ ){
			Object [] dataArray = {"  ","  ","  ","  ","  ","  "};
		    dataModelo.add(dataArray);	
		}
		
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		//scrollPane.setBounds(20, 64, 586, 181);
		scrollPane.setBounds(10, 56, 691, 443);
		getContentPane().add(scrollPane);
		
	
		//Transformacion del ArrayList en un Object [][]
		//copia los datos de dataElemento a data
		Object [][] data = new Object [dataModelo.size()][];
		dataModelo.toArray(data);			
		
		modeloTablaModelo = new DefaultTableModel( data , nombresColum){
			
			@Override
			public boolean isCellEditable (int row, int column){				
				//all cells false
				return false;
			}
		};

		tblModelo = new JTable(modeloTablaModelo);
		tblModelo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		tblModelo.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblModelo);
		
		
		tblModelo.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent e)
			{
				int fila = tblModelo.rowAtPoint(e.getPoint());
				int columna = 0; //tblBBDD.columnAtPoint(e.getPoint);
				
				if ((fila > -1)){					
					idModeloSeleccionado = modeloTablaModelo.getValueAt(fila, columna);					
					obtenerModelo(fila);
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
		btnModificar.setBounds(177, 512, 89, 23);
		getContentPane().add(btnModificar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(443, 512, 89, 23);
		getContentPane().add(btnCancelar);
		
		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setBounds(49, 31, 82, 14);
		getContentPane().add(lblFabricante);
		
		cbFabricante = new JComboBox();
		cbFabricante.setBounds(181, 25, 235, 20);
		getContentPane().add(cbFabricante);
		
		cbFabricante.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Obtenemos el index del objeto, para obtener el ID en el vector. 
				//indexCbFabricante = cbFabricante.getSelectedIndex();				
				if (cbFabricante.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();					
					//Si intIdFabricante = 0, se listan todos los modelos de la tabla
					miConsultaController.listaModelo(intIdFabricante, miIdModelo, false);		
				}						
			}
		});
		
		
		setVisible(true);  
		//setSize(635, 339);
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
		int intIdModeloSeleccionado = (Integer)idModeloSeleccionado;
		if ( intIdModeloSeleccionado > 0){
			boolean bl1 = listUniqueIdVerModificarModelo.add(intIdModeloSeleccionado);	
			if (bl1){
				//Se le pasa el cbFabricante, para que al actualizar la tabla desde miModificarXXX, lo haga desde el comboBox que el usuario a escogido
				ModificarModelo miModificarModelo = new ModificarModelo(objetoModelo, miVerModificarController, miConsultaController,miIdModelo,cbFabricante, listUniqueIdVerModificarModelo );
				miVerModificarController.getActualizarElemento().addPropertyChangeListener(PROP_ACTUALIZAR_MODELO, miModificarModelo);
				idModeloSeleccionado =0;
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
		miConsultaController.getConsultasBBDD().removePropertyChangeListener(PROP_LISTAR_MODELO, this);
		
	}
	

	
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		ParametrosListaBBDD parametrosListaBBDD;
		
		ParametrosConsultasComboBoxBBDD parametrosConsultasCb ;
        dataComboBoxFabricante =new Vector<ElementoComboBox>();                         

		

        if (PROP_CONSULTAR_FABRICANTE.equals(propertyName)){
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			
			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdModelo)){
	
				intIdFabricante = 0;//Asi si no se actualiza el comboBox, el id =0
				dataComboBoxFabricante = parametrosConsultasCb.getDataElemento();// DataElemento es un Vector<ElementoComboBox>
				actualizarComboBox(cbFabricante,dataComboBoxFabricante );
									
			}		
		} if (PROP_LISTAR_MODELO.equals(propertyName)){
		 
			idModeloSeleccionado =0;//Asi cada vez que se lista, se deselecciona el elemento seleccionado previamente
			
			parametrosListaBBDD = (ParametrosListaBBDD) evt.getNewValue();
			
			List<Object> dataModeloBBDD ;

			//Se comprueba el id del objeto Modelo
			if (parametrosListaBBDD.getIdElemento().equals(miIdModelo)){
				// hacer lo necesario para refrescar la tabla
				dataModeloBBDD = (ArrayList<Object>) parametrosListaBBDD.getDataListaBBDD();
				dataModelo.removeAll(dataModelo);//Se borran datos antiguos de la lista
				//dataModelo.addAll(0,dataModeloBBDD);
				
				//Se añaden los nuevos datos, de la query, a la lista
				Iterator it = dataModeloBBDD.iterator();
				
				while (it.hasNext()){					
					Modelo modelo = (Modelo) it.next();
					dataModelo.add(modelo.getArray());//Añade los Arrays cons los datos de modelo a la List
				}
				
				actualizaTabla((ArrayList<Object[]>) dataModelo, modeloTablaModelo);
			}else if (parametrosListaBBDD.isActualizar())
				miConsultaController.listaModelo(intIdFabricante, miIdModelo, false);

		}		
	}
	
	public void obtenerModelo (int fila ) {
		
		int intIdFabricante = (Integer)modeloTablaModelo.getValueAt(fila, 1);//Seria el mismo que el del comboBox, siempre que sea mayor que 0
		String strNombre = (String)modeloTablaModelo.getValueAt(fila, 2);
		String strDescripcion = (String)modeloTablaModelo.getValueAt(fila, 3);
		String strFechaFabricacion = formatoDelTexto.format(modeloTablaModelo.getValueAt(fila, 4)); 
		
		
		String strAnyoLanzamiento = (String)modeloTablaModelo.getValueAt(fila, 5);	
		
		//Se crea el objeto 
		objetoModelo = new Modelo((Integer)idModeloSeleccionado, intIdFabricante, strNombre, strDescripcion, strFechaFabricacion, strAnyoLanzamiento);
		
	
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
		tblModelo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblModelo.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblModelo.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblModelo.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblModelo.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblModelo.getColumnModel().getColumn(4).setPreferredWidth(140);
		tblModelo.getColumnModel().getColumn(5).setPreferredWidth(140);
		
	 }
	

	/*
	 * Cambiar el id de este objeto.
	 * @param idModelo, es una UUID (identificador unico)
	 *  */
	
	public void setIdModelo(UUID idModelo){
		
		miIdModelo = idModelo;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdModelo(){
		
		return miIdModelo;
	}
}
