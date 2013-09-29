
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
import view.error.ErrorGenerico;

import controller.BajaController;
import controller.ComboBoxController;
import controller.ConsultaController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import libreria.ElementoComboBox;
import libreria.ElementosSistemaEspecifico;
import libreria.ListUniqueID;
import libreria.MyConstants;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosEliminarElemento;
import libreria.ParametrosListaBBDD;

public class BajaElementoEspecifico extends JFrame implements PropertyChangeListener{
	
	private JTable tblElementos;
	private String[] nombresColum = {"ID Elemento Sistema Específico", "ID Sistema Específico", "ID Serie", "ID Modelo", "ID Fabricante", "Nombre Elemento", "Interesting", "Subframes", "Palabras", "Bits", "N Atributos"}; 
	
	private JScrollPane scrollPane;	
	private DefaultTableModel modeloTablaElementoEspecifico;	
	private List <ElementosSistemaEspecifico> listaElementoEspecifico = new ArrayList<ElementosSistemaEspecifico>();
		
	private UUID miIdElementoEspecifico;//Identificador unico de cada objeto
	private ConsultaController miConsultaController;
	private BajaController miBajaController;
	
	public static final String PROP_ELIMINAR_ELEMENTO_ESPECIFICO = "eliminarElementoEspecifico";
	public static final String PROP_LISTAR_ELEMENTO_ESPECIFICO = "listarElementoEspecifico";

	public static final String PROP_ELIMINAR_SELECCION_ESTRUCTURA_ATRIBUTO = "eliminarEstructuraAtributo";
	public static final String PROP_LISTAR_ESTRUCTURA_ATRIBUTO = "listarEstructuraAtributo";
	
	private List<Object []> dataElementoEspecifico = new ArrayList();  
	private Object idElementoEspecificoSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
		
	private JComboBox cbFabricante;
	private Vector<ElementoComboBox> dataComboBoxFabricante;
	private ElementoComboBox elementoCbFabricante;
	
	private JComboBox cbModelo;
	private Vector<ElementoComboBox> dataComboBoxModelo;
	private ElementoComboBox elementoCbModelo;
	
	private JComboBox cbSerie;
	private Vector<ElementoComboBox> dataComboBoxSerie;
	private ElementoComboBox elementoCbSerie;
	
	private JComboBox cbSistemaEspecifico;
	private Vector<ElementoComboBox> dataComboBoxSistemaEspecifico;
	private ElementoComboBox elementoCbSistemaEspecifico;
	
	private ComboBoxController miComboBoxController;
	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";
	public static final String PROP_CONSULTAR_MODELO = "consultarModelo";
	public static final String PROP_CONSULTAR_SERIE = "consultarSerie";
	public static final String PROP_CONSULTAR_SISTEMA = "consultarSistema";	

	private int intIdFabricante;
	private int intIdModelo;
	private int intIdSerie;
	private int intIdSistemaEspecifico;
	private List <Object> listaIdElementoEspecificoSeleccionado; //Son los objetos pertenecientes a los elementos seleccionados en la tabla
	private int intNAtributo;
	private ListUniqueID listUniqueIdVerModificarElementoEspecifico;//Lista compartida para garantizar la atomicidad del acceso a la informacion de la fila escogida
	
	public BajaElementoEspecifico(BajaController bajaController, ConsultaController consultaController,
								ComboBoxController comboBoxController, UUID idElementoEspecifico, 
								ListUniqueID listUniqueIdVerModificarElementoEspecifico) {
	
		miBajaController = bajaController;		
		miConsultaController = consultaController;
		miComboBoxController = comboBoxController;
		miIdElementoEspecifico = idElementoEspecifico;
		this.listUniqueIdVerModificarElementoEspecifico = listUniqueIdVerModificarElementoEspecifico;		
		setTitle("Baja Elementos de Sistema Especifico");
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ ){
			Object [] dataArray = {"  ","  ","  ","  ","  ","  ","  ","  ","  ","  ","  "};
			dataElementoEspecifico.add(dataArray);	
		}
		getContentPane().setLayout(null);		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 206, 1071, 295);
		getContentPane().add(scrollPane);		
		Object [][] data = new Object [dataElementoEspecifico.size()][];
		dataElementoEspecifico.toArray(data);
		modeloTablaElementoEspecifico = new DefaultTableModel( data , nombresColum){
			@Override
			public boolean isCellEditable (int row, int column){				
				//all cells false
				return false;
			}
		};

		tblElementos = new JTable(modeloTablaElementoEspecifico);
		tblElementos.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblElementos);
		tblElementos.addMouseListener(new MouseAdapter(){
			public void mouseClicked (MouseEvent e){
				int fila = tblElementos.rowAtPoint(e.getPoint());
				int columna = 0; 
				int columnaAtributo = 10;
				int []filas =  tblElementos.getSelectedRows(); 
				listaIdElementoEspecificoSeleccionado = new ArrayList<Object>();
				//Varias Filas
				if (filas.length > 0){					
					for (int i = 0; i < filas.length ; i++){						
						//Se guarda la lista de los id seleccionados
						listaIdElementoEspecificoSeleccionado.add(modeloTablaElementoEspecifico.getValueAt(filas[i], columna)); 
						Object aux = modeloTablaElementoEspecifico.getValueAt(filas[i], columna);
					}					
				}
				if ((fila > -1)){					
					idElementoEspecificoSeleccionado = modeloTablaElementoEspecifico.getValueAt(fila, columna);
					intNAtributo = (Integer) modeloTablaElementoEspecifico.getValueAt(fila, columnaAtributo);
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
					intIdFabricante = elementoCbFabricante.getIdElemento();miComboBoxController.comboBoxModelo(intIdFabricante, miIdElementoEspecifico);
					miConsultaController.listaElementoEspecifico(intIdFabricante, intIdModelo,intIdSerie,intIdSistemaEspecifico, miIdElementoEspecifico, false);
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
				if (cbModelo.getItemCount() > 0){ //Evitamos una exepcion si no hay objetos en el cbModelo.					
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();miComboBoxController.comboBoxSerie(intIdFabricante, intIdModelo, miIdElementoEspecifico);
					miConsultaController.listaElementoEspecifico(intIdFabricante, intIdModelo,intIdSerie,intIdSistemaEspecifico, miIdElementoEspecifico, false);
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
				if (cbSerie.getItemCount() > 0){ //Evitamos una exepcion si no hay objetos en el cbSerie.
					elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro de la serie del combobox
					intIdSerie = elementoCbSerie.getIdElemento();
					miComboBoxController.comboBoxSistema(intIdFabricante, intIdModelo,intIdSerie, miIdElementoEspecifico);
					miConsultaController.listaElementoEspecifico(intIdFabricante, intIdModelo,intIdSerie,intIdSistemaEspecifico, miIdElementoEspecifico, false);
				}							
			}
		});
		
		cbSistemaEspecifico = new JComboBox();
		cbSistemaEspecifico.setBounds(225, 152, 235, 20);
		getContentPane().add(cbSistemaEspecifico);
		
		cbSistemaEspecifico.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				if (cbSistemaEspecifico.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbSistemaEspecifico.
				{
					elementoCbSistemaEspecifico = (ElementoComboBox)cbSistemaEspecifico.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro de la serie del combobox
					intIdSistemaEspecifico = elementoCbSistemaEspecifico.getIdElemento();
					miConsultaController.listaElementoEspecifico(intIdFabricante, intIdModelo,intIdSerie, intIdSistemaEspecifico, miIdElementoEspecifico, false);
					
				}
												
			}
		});
		
		JButton btnVerEstructuraAtributos = new JButton("Ver Estructura Atributos");
		btnVerEstructuraAtributos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnVerEstructuraAtributos_actionPerformed(arg0);
			}
		});
		
		
		btnVerEstructuraAtributos.setBounds(180, 517, 174, 23);
		getContentPane().add(btnVerEstructuraAtributos);
		
		
		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnBaja_actionPerformed(arg0);
			}
		});
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		
		btnCancelar.setBounds(811, 517, 97, 23);
		getContentPane().add(btnCancelar);
		
		
		btnBaja.setBounds(534, 517, 97, 23);
		getContentPane().add(btnBaja);
		
		JLabel lblSistemaEspecfico = new JLabel("Sistema Espec\u00EDfico");
		lblSistemaEspecfico.setBounds(45, 158, 130, 14);
		getContentPane().add(lblSistemaEspecfico);
		
		
		
		setVisible(true);  
		setSize(1099, 600);
		
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });
	}
	
	
	
	protected void do_btnVerEstructuraAtributos_actionPerformed(ActionEvent arg0) {
		//Asi solo funciona si se ha seleccionado una fila
		int intIdElementoEspecifico = (Integer)idElementoEspecificoSeleccionado;		
		if( intIdElementoEspecifico > 0){// && intNAtributo >0 ){
			if (!MessageUtils.mensajeSeleccionElementos(listUniqueIdVerModificarElementoEspecifico.containsElementList(listaIdElementoEspecificoSeleccionado))){
				listUniqueIdVerModificarElementoEspecifico.getUniqueListID().add(intIdElementoEspecifico);
				UUID idEstructuraAtributos = UUID.randomUUID();	
				BajaEstructuraAtributos bajaEstructuraAtributos = new BajaEstructuraAtributos(miBajaController, miConsultaController, idEstructuraAtributos, intIdFabricante,intIdModelo, intIdSerie, intIdSistemaEspecifico, intIdElementoEspecifico, miIdElementoEspecifico, listUniqueIdVerModificarElementoEspecifico);
				miBajaController.getBorrarElemento().addPropertyChangeListener(PROP_ELIMINAR_SELECCION_ESTRUCTURA_ATRIBUTO , bajaEstructuraAtributos);
				miConsultaController.getConsultasBBDD().addPropertyChangeListener( PROP_LISTAR_ESTRUCTURA_ATRIBUTO, bajaEstructuraAtributos);		
				//Con esta llamada se lista la estructura de Atributos, que pertenece a este elemento especifico
				miConsultaController.listaEstructuraAtributos(intIdElementoEspecifico, idEstructuraAtributos, false);
				idElementoEspecificoSeleccionado =0;//Así, la siguiente vez se opera solo si hay un elemento seleccionado 
			}		
		}/*else if (intNAtributo == 0){
			String mensaje = "El Elemento de Sistema Específico escogido no tiene atributos.";
			ErrorGenerico errorGenerico = new ErrorGenerico(MyConstants.StringConstant.BAJA.value(),mensaje );			
		}*/
	}
	
	
	/*
	 * Es llamado por el listener del boton baja. 
	 * 
	 * */		
	protected void do_btnBaja_actionPerformed(ActionEvent arg0) {		
		//Asi solo funciona si se ha seleccionado alguna fila	
		if (listaIdElementoEspecificoSeleccionado.size() > 0){
			int response = MessageConfirmationUtils.deleteRowsMessageConfirmation();    
			if (response == JOptionPane.YES_OPTION) {
				if (!MessageUtils.mensajeSeleccionElementos(listUniqueIdVerModificarElementoEspecifico.containsElementList(listaIdElementoEspecificoSeleccionado))){
					miBajaController.eliminarElementoEspecifico(listaIdElementoEspecificoSeleccionado, miIdElementoEspecifico);
					miConsultaController.listaElementoEspecifico(intIdFabricante,intIdModelo, intIdSerie, intIdSistemaEspecifico,  miIdElementoEspecifico, true);//Actualizo la lista
					listaIdElementoEspecificoSeleccionado.removeAll(listaIdElementoEspecificoSeleccionado);			
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
		miConsultaController.getConsultasBBDD().removePropertyChangeListener(PROP_LISTAR_ELEMENTO_ESPECIFICO, this);
		miBajaController.getBorrarElemento().removePropertyChangeListener(PROP_ELIMINAR_ELEMENTO_ESPECIFICO, this);

	
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
			if(parametrosConsultasCb.getIdElemento().equals(miIdElementoEspecifico)){
				intIdFabricante = 0;//Asi si no se actualiza el comboBox, el id =0
				dataComboBoxFabricante = parametrosConsultasCb.getDataElemento();// DataElemento es un Vector<ElementoComboBox>
				actualizarComboBox(cbFabricante,dataComboBoxFabricante );
			}		
		} else if (PROP_CONSULTAR_MODELO.equals(propertyName)){
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			//Se comprueba el id del objeto Estructura Frame
			if(parametrosConsultasCb.getIdElemento().equals(miIdElementoEspecifico)){
				intIdModelo =0;//Asi si no se actualiza el comboBox, el id =0
				dataComboBoxModelo = parametrosConsultasCb.getDataElemento();//(Vector<ElementoComboBox>)
				actualizarComboBox(cbModelo,dataComboBoxModelo );
			}
	
		} else if (PROP_CONSULTAR_SERIE.equals(propertyName)) {
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
						
			intIdSerie =0;//Asi si no se actualiza el comboBox, el id =0
			dataComboBoxSerie = parametrosConsultasCb.getDataElemento();//Es un Vector<ElementoComboBox>
			actualizarComboBox(cbSerie,dataComboBoxSerie );

			
		} else if (PROP_CONSULTAR_SISTEMA.equals(propertyName)) {
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
						
			intIdSistemaEspecifico =0;//Asi si no se actualiza el comboBox, el id =0
			dataComboBoxSistemaEspecifico = parametrosConsultasCb.getDataElemento();//Es un Vector<ElementoComboBox>
			actualizarComboBox(cbSistemaEspecifico,dataComboBoxSistemaEspecifico );

			
		}else if (PROP_LISTAR_ELEMENTO_ESPECIFICO.equals(propertyName)){
		 
			idElementoEspecificoSeleccionado =0;//Asi cada vez que se lista, se deselecciona el elemento seleccionado previamente
			
			parametrosListaBBDD = (ParametrosListaBBDD) evt.getNewValue();
			
			List<Object> dataElementoEspecificoBBDD ;

			if (parametrosListaBBDD.getIdElemento().equals(miIdElementoEspecifico)){//Se comprueba el id del objeto Estructura Frame

				// hace lo necesario para refrescar la tabla
				dataElementoEspecificoBBDD = (ArrayList<Object>) parametrosListaBBDD.getDataListaBBDD();
				dataElementoEspecifico.removeAll(dataElementoEspecifico);//Se borran datos antiguos de la lista
					
				//Se añaden los nuevos datos, de la query, a la lista
				Iterator it = dataElementoEspecificoBBDD.iterator();
				
				while (it.hasNext()){					
					ElementosSistemaEspecifico elementosSistemaEspecifico = (ElementosSistemaEspecifico) it.next();
					dataElementoEspecifico.add(elementosSistemaEspecifico.getArray());//Añade los Arrays cons los datos de fabricante a la List
				}
				
				actualizaTabla((ArrayList<Object[]>) dataElementoEspecifico, modeloTablaElementoEspecifico);
				
			}else if (parametrosListaBBDD.isActualizar())
				miConsultaController.listaElementoEspecifico(intIdFabricante, intIdModelo, intIdSerie, intIdSistemaEspecifico, miIdElementoEspecifico, false);
		}else if (PROP_ELIMINAR_ELEMENTO_ESPECIFICO.equals(propertyName)){				 
				parametrosEliminarElemento = (ParametrosEliminarElemento) evt.getNewValue();				
				//Se comprueba el id del objeto Modelo
				if (parametrosEliminarElemento.getIdElemento().equals(miIdElementoEspecifico)){
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
		tblElementos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblElementos.getColumnModel().getColumn(0).setPreferredWidth(200);
		tblElementos.getColumnModel().getColumn(1).setPreferredWidth(130);
		tblElementos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblElementos.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblElementos.getColumnModel().getColumn(4).setPreferredWidth(130);
		tblElementos.getColumnModel().getColumn(5).setPreferredWidth(140);
		tblElementos.getColumnModel().getColumn(6).setPreferredWidth(100);
		tblElementos.getColumnModel().getColumn(7).setPreferredWidth(100);
		tblElementos.getColumnModel().getColumn(8).setPreferredWidth(100);
		tblElementos.getColumnModel().getColumn(9).setPreferredWidth(100);
		tblElementos.getColumnModel().getColumn(10).setPreferredWidth(100);
		
		
	}

	
	/*
	 * Cambiar el id de este objeto.
	 * @param idSistemaEspecifico, es una UUID (identificador unico)
	 *  */
	
	public void setIdElementoEspecifico(UUID idElementoEspecifico){
		
		miIdElementoEspecifico = idElementoEspecifico;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdElementoEspecifico(){
		
		return miIdElementoEspecifico;
	}
}
